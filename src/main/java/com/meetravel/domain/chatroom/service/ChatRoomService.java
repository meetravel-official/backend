package com.meetravel.domain.chatroom.service;

import com.meetravel.domain.chatroom.dto.*;
import com.meetravel.domain.chatroom.entity.ChatMessageEntity;
import com.meetravel.domain.chatroom.entity.ChatRoomEntity;
import com.meetravel.domain.chatroom.entity.UserChatRoomEntity;
import com.meetravel.domain.chatroom.enums.ChatMessageType;
import com.meetravel.domain.chatroom.event.model.ChatMessages;
import com.meetravel.domain.chatroom.repository.ChatMessageRepository;
import com.meetravel.domain.chatroom.repository.ChatRoomRepository;
import com.meetravel.domain.chatroom.repository.UserChatRoomRepository;
import com.meetravel.domain.chatroom.vo.ChatRoomPreviewInfo;
import com.meetravel.domain.matching_form.entity.MatchingFormEntity;
import com.meetravel.domain.matching_form.repository.MatchingFormRepository;
import com.meetravel.domain.user.entity.UserEntity;
import com.meetravel.domain.user.enums.SocialType;
import com.meetravel.domain.user.repository.UserRepository;
import com.meetravel.global.exception.BadRequestException;
import com.meetravel.global.exception.ErrorCode;
import com.meetravel.global.exception.NotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class ChatRoomService {
    private final ApplicationEventPublisher applicationEventPublisher;
    private final UserRepository userRepository;
    private final UserChatRoomRepository userChatRoomRepository;
    private final MatchingFormRepository matchingFormRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

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

        MatchingFormEntity matchingFormEntity = chatRoomEntity.getMatchingForms()
                .stream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_EXIST_MATCHING_FORM_CHAT_ROOM));

        long joinedUserCount = chatRoomEntity.getUserChatRooms()
                .stream()
                .filter(it -> it.getLeaveAt() == null)
                .count();

        if (joinedUserCount >= matchingFormEntity.getGroupSize().getNumber()) {
            throw new BadRequestException(ErrorCode.FULLED_GROUP_SIZE_CHAT_ROOM);
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

        this.sendMessageAndEventPublisher(
                userId,
                chatSendRequest.chatRoomId(),
                ChatMessageType.CHAT,
                chatSendRequest.message()
        );
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

    @Transactional(readOnly = true)
    public Page<ChatMessage> getChatMessages(
            String userId,
            Long chatRoomId,
            GetChatMessageRequest request
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

        Page<ChatMessageEntity> chatMessageEntityPage;
        if (request.getLastChatMessageId() == null) {
            chatMessageEntityPage = chatMessageRepository.findAllByChatRoom(request.generatePageable(), chatRoomEntity);
        } else {
            ChatMessageEntity chatMessageEntity = chatMessageRepository.findById(request.getLastChatMessageId())
                    .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_CHAT_MESSAGE));

            chatMessageEntityPage = chatMessageRepository.findAllByIdBeforeAndChatRoom(request.generatePageable(), chatMessageEntity.getId(), chatRoomEntity);
        }

        return new PageImpl<>(
                chatMessageEntityPage.getContent()
                        .stream()
                        .map(ChatMessage::new)
                        .toList()
                ,
                chatMessageEntityPage.getPageable(),
                chatMessageEntityPage.getTotalElements()
        );
    }

    private void sendJoinedMessage(
            String userId,
            Long chatRoomId
    ) {
        ChatMessageEntity joinedChatMessageEntity = this.getJoinedChatMessageEntity(userId, chatRoomId);
        List<ChatMessageEntity> welcomeBotChatMessageEntities = this.getWelcomeBotChatMessageEntities(chatRoomId);

        List<ChatMessageEntity> sendChatMessageEntities = new ArrayList<>();
        sendChatMessageEntities.add(joinedChatMessageEntity);
        sendChatMessageEntities.addAll(welcomeBotChatMessageEntities);

        this.sendMessagesAndEventPublisher(sendChatMessageEntities);
    }

    private void sendLeaveMessage(
            String userId,
            Long chatRoomId
    ) {
        String leftMessage = this.getLeaveMessage(
                userId,
                chatRoomId
        );
        this.sendMessageAndEventPublisher(
                userId,
                chatRoomId,
                ChatMessageType.LEAVE,
                leftMessage
        );
    }

    private ChatMessageEntity getJoinedChatMessageEntity(String userId, Long chatRoomId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        ChatRoomEntity chatRoomEntity = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CHAT_ROOM_NOT_FOUND));

        ChatMessageEntity chatMessageEntity = new ChatMessageEntity(
                userEntity,
                chatRoomEntity,
                userEntity.getNickname() + "님이 들어왔습니다",
                ChatMessageType.JOIN
        );

        return chatMessageRepository.save(chatMessageEntity);
    }

    private List<ChatMessageEntity> getWelcomeBotChatMessageEntities(Long chatRoomId) {
        UserEntity botUserEntity = userRepository.findAllBySocialType(SocialType.BOT)
                .stream()
                .max(Comparator.comparing(UserEntity::getUserId))
                .orElse(null);
        if (botUserEntity == null) return Collections.emptyList();

        ChatRoomEntity chatRoomEntity = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CHAT_ROOM_NOT_FOUND));

        List<String> welcomeMessages = List.of(
                "안녕하세요, 미트래블입니다!",
                "여행 계획을 시작해볼까요? 여행의 관광지, 식당, 숙박 등 장소를 정하는 방법은 간단해요.",
                "미트래블의 여행정보 탭에서 추천 장소를 확인하고, 채팅방으로 공유해 보세요."
        );

        return welcomeMessages.stream()
                .map(message -> chatMessageRepository.save(
                        new ChatMessageEntity(
                            botUserEntity,
                            chatRoomEntity,
                            message,
                            ChatMessageType.BOT
                        )
                ))
                .toList();
    }

    private String getLeaveMessage(
            String userId,
            Long chatRoomId
    ) {
        this.validateUserLeftChatRoom(userId, chatRoomId);

        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        return userEntity.getNickname() + "님이 나갔습니다";
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

    private void sendMessageAndEventPublisher(
            String userId,
            Long chatRoomId,
            ChatMessageType chatMessageType,
            String message
    ) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        ChatRoomEntity chatRoomEntity = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CHAT_ROOM_NOT_FOUND));

        ChatMessageEntity chatMessageEntity = new ChatMessageEntity(
                userEntity,
                chatRoomEntity,
                message,
                chatMessageType
        );

        ChatMessageEntity savedChatMessageEntity = chatMessageRepository.save(chatMessageEntity);
        applicationEventPublisher.publishEvent(new ChatMessage(savedChatMessageEntity));
    }

    private void sendMessagesAndEventPublisher(List<ChatMessageEntity> chatMessageEntities) {
        applicationEventPublisher.publishEvent(new ChatMessages(chatMessageEntities.stream().map(ChatMessage::new).toList()));
    }

}
