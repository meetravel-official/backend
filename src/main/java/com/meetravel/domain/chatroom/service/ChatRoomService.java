package com.meetravel.domain.chatroom.service;

import com.meetravel.domain.chatroom.dto.*;
import com.meetravel.domain.chatroom.entity.ChatRoomEntity;
import com.meetravel.domain.chatroom.entity.UserChatRoomEntity;
import com.meetravel.domain.chatroom.enums.ChatMessageType;
import com.meetravel.domain.chatroom.event.model.ChatMessageEvent;
import com.meetravel.domain.chatroom.repository.ChatRoomRepository;
import com.meetravel.domain.chatroom.repository.UserChatRoomRepository;
import com.meetravel.domain.chatroom.vo.ChatRoomPreviewInfo;
import com.meetravel.domain.matching_form.entity.MatchingFormEntity;
import com.meetravel.domain.matching_form.repository.MatchingFormRepository;
import com.meetravel.domain.user.entity.UserEntity;
import com.meetravel.domain.user.repository.UserRepository;
import com.meetravel.global.exception.BadRequestException;
import com.meetravel.global.exception.ErrorCode;
import com.meetravel.global.exception.NotFoundException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class ChatRoomService {
    private final RabbitTemplate rabbitTemplate;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final UserRepository userRepository;
    private final UserChatRoomRepository userChatRoomRepository;
    private final MatchingFormRepository matchingFormRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Transactional
    public CreateChatRoomResponse createChatRoom(
            String userId,
            Long matchingFormId
    ) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        MatchingFormEntity matchingFormEntity = matchingFormRepository.findByIdAndUser(matchingFormId, userEntity)
                .orElseThrow(() -> new NotFoundException(ErrorCode.MATCHING_FORM_NOT_FOUND));

        if (matchingFormEntity.getChatRoom() != null) {
            throw new BadRequestException(ErrorCode.ALREADY_EXISTS_ROOM_WITH_MATCHING_FORM);
        }

        ChatRoomEntity savedChatRoomEntity = chatRoomRepository.save(new ChatRoomEntity());

        matchingFormEntity.joinChatRoom(savedChatRoomEntity);
        matchingFormRepository.save(matchingFormEntity);

        return new CreateChatRoomResponse(savedChatRoomEntity);
    }

    @Transactional
    public void joinChatRoom(
            String userId,
            Long chatRoomId
    ) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        ChatRoomEntity chatRoomEntity = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CHAT_ROOM_NOT_FOUND));

        UserChatRoomEntity joinedUserChatRoomEntity = userEntity.getUserChatRooms()
                .stream()
                .filter(it -> chatRoomEntity.getId().equals(it.getChatRoom().getId()) && it.getLeaveAt() == null)
                .findFirst()
                .orElse(null);

        if (joinedUserChatRoomEntity != null) {
            throw new BadRequestException(ErrorCode.USER_ROOM_ALREADY_JOINED);
        }

        UserChatRoomEntity joinedUserOtherChatRoomEntity = userEntity.getUserChatRooms()
                .stream()
                .filter(it -> it.getLeaveAt() == null)
                .findFirst()
                .orElse(null);

        if (joinedUserOtherChatRoomEntity != null) {
            throw new BadRequestException(ErrorCode.ALREADY_EXISTS_JOINED_CHAT_ROOM);
        }

        userChatRoomRepository.save(
                new UserChatRoomEntity(
                        userEntity,
                        chatRoomEntity
                )
        );

        this.sendJoinedMessage(
                userEntity.getUserId(),
                chatRoomEntity.getId()
        );
    }

    @Transactional
    public void leaveChatRoom(
            String userId,
            Long chatRoomId
    ) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        ChatRoomEntity chatRoomEntity = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CHAT_ROOM_NOT_FOUND));

        UserChatRoomEntity joinedUserChatRoomEntity = userEntity.getUserChatRooms()
                .stream()
                .filter(it -> chatRoomEntity.getId().equals(it.getChatRoom().getId()) && it.getLeaveAt() == null)
                .findFirst()
                .orElse(null);

        if (joinedUserChatRoomEntity == null) {
            throw new BadRequestException(ErrorCode.USER_ROOM_NOT_JOINED);
        }

        joinedUserChatRoomEntity.leave();
        userChatRoomRepository.save(joinedUserChatRoomEntity);

        this.sendLeaveMessage(
                userEntity.getUserId(),
                chatRoomEntity.getId()
        );
    }

    @Transactional
    public void sendChatMessage(
            String userId,
            ChatSendRequest chatSendRequest
    ) {
        this.validateUserJoinedChatRoom(userId, chatSendRequest.chatRoomId());

        ChatMessage chatMessage = new ChatMessage(
                userId,
                chatSendRequest.chatRoomId(),
                ChatMessageType.CHAT,
                chatSendRequest.message()
        );

        this.sendMessageAndEventPublisher(chatMessage);
    }

    @Transactional(readOnly = true)
    public GetMyChatRoomResponse getMyChatRooms(String userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        List<ChatRoomPreviewInfo> chatRoomPreviewInfos = userEntity.getUserChatRooms()
                .stream()
                .map(userChatRoomEntity -> {
                    if (userChatRoomEntity.getLeaveAt() != null) return null;

                    ChatRoomEntity chatRoomEntity = userChatRoomEntity.getChatRoom();
                    MatchingFormEntity matchingFormEntity = chatRoomEntity.getMatchingForms()
                            .stream()
                            .findFirst()
                            .orElse(null);
                    if (matchingFormEntity == null) return null;

                    List<UserEntity> joinedUserEntities = chatRoomEntity.getUserChatRooms()
                            .stream()
                            .filter(it -> it.getLeaveAt() == null)
                            .map(UserChatRoomEntity::getUser)
                            .toList();

                    return new ChatRoomPreviewInfo(chatRoomEntity, matchingFormEntity, joinedUserEntities);
                })
                .filter(Objects::nonNull)
                .toList();

        return new GetMyChatRoomResponse(chatRoomPreviewInfos);
    }

    @Transactional(readOnly = true)
    public GetChatRoomResponse getChatRoom(
            String userId,
            Long chatRoomId
    ) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        ChatRoomEntity chatRoomEntity = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CHAT_ROOM_NOT_FOUND));

        UserChatRoomEntity joinedUserChatRoomEntity = userEntity.getUserChatRooms()
                .stream()
                .filter(it -> chatRoomEntity.getId().equals(it.getChatRoom().getId()) && it.getLeaveAt() == null)
                .findFirst()
                .orElse(null);

        if (joinedUserChatRoomEntity == null) {
            throw new BadRequestException(ErrorCode.USER_ROOM_NOT_JOINED);
        }

        List<UserEntity> userEntities = chatRoomEntity.getUserChatRooms()
                .stream()
                .filter(it -> it.getLeaveAt() == null)
                .map(UserChatRoomEntity::getUser)
                .toList();

        MatchingFormEntity matchingFormEntity = chatRoomEntity.getMatchingForms()
                .stream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_EXIST_MATCHING_FORM_CHAT_ROOM));

        return new GetChatRoomResponse(userEntities, matchingFormEntity);
    }

    private void sendJoinedMessage(
            String userId,
            Long chatRoomId
    ) {
        String joinedMessage = this.getJoinedMessage(userId);
        ChatMessage chatMessage = new ChatMessage(
                userId,
                chatRoomId,
                ChatMessageType.JOIN,
                joinedMessage
        );

        this.sendMessageAndEventPublisher(chatMessage);
    }

    private void sendLeaveMessage(
            String userId,
            Long chatRoomId
    ) {
        String leftMessage = this.getLeaveMessage(
                userId,
                chatRoomId
        );

        ChatMessage chatMessage = new ChatMessage(
                userId,
                chatRoomId,
                ChatMessageType.LEAVE,
                leftMessage
        );

        this.sendMessageAndEventPublisher(chatMessage);
    }

    private String getJoinedMessage(String userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        return userEntity.getNickname() + "님이 들어왔습니다.";
    }

    private String getLeaveMessage(
            String userId,
            Long chatRoomId
    ) {
        this.validateUserLeftChatRoom(userId, chatRoomId);

        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        return userEntity.getNickname() + "님이 나갔습니다.";
    }

    private void validateUserJoinedChatRoom(
            String userId,
            Long chatRoomId
    ) {
        if (userId.isEmpty()) {
            throw new NotFoundException(ErrorCode.USER_NOT_FOUND);
        }

        if (chatRoomId == null || chatRoomId <= 0) {
            throw new NotFoundException(ErrorCode.CHAT_ROOM_NOT_FOUND);
        }

        ChatRoomEntity chatRoomEntity = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CHAT_ROOM_NOT_FOUND));

        UserChatRoomEntity userChatRoomEntity = chatRoomEntity.getUserChatRooms()
                .stream()
                .filter(it -> userId.equals(it.getUser().getUserId()) && it.getLeaveAt() == null)
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_ROOM_NOT_JOINED));

        if (userChatRoomEntity.getUser() == null) {
            throw new NotFoundException(ErrorCode.USER_NOT_FOUND);
        }
    }

    private void validateUserLeftChatRoom(
            String userId,
            Long chatRoomId
    ) {
        if (userId.isEmpty()) {
            throw new NotFoundException(ErrorCode.USER_NOT_FOUND);
        }

        if (chatRoomId == null || chatRoomId <= 0) {
            throw new NotFoundException(ErrorCode.CHAT_ROOM_NOT_FOUND);
        }

        ChatRoomEntity chatRoomEntity = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CHAT_ROOM_NOT_FOUND));

        UserChatRoomEntity userChatRoomEntity = chatRoomEntity.getUserChatRooms()
                .stream()
                .filter(it -> userId.equals(it.getUser().getUserId()) && it.getLeaveAt() != null)
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_ROOM_NOT_LEAVE));

        if (userChatRoomEntity.getUser() == null) {
            throw new NotFoundException(ErrorCode.USER_NOT_FOUND);
        }
    }

    private void sendMessageAndEventPublisher(ChatMessage chatMessage) {
        rabbitTemplate.convertAndSend("chat.exchange", "chat.rooms." + chatMessage.getChatRoomId(), chatMessage);
        applicationEventPublisher.publishEvent(new ChatMessageEvent(chatMessage));
    }

}
