package com.meetravel.domain.chatroom.service;

import com.meetravel.domain.chatroom.dto.ChatMessage;
import com.meetravel.domain.chatroom.dto.CreateChatRoomResponse;
import com.meetravel.domain.chatroom.entity.ChatRoomEntity;
import com.meetravel.domain.chatroom.entity.UserChatRoomEntity;
import com.meetravel.domain.chatroom.event.model.ChatMessageEvent;
import com.meetravel.domain.chatroom.repository.ChatRoomRepository;
import com.meetravel.domain.chatroom.repository.UserChatRoomRepository;
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
        userChatRoomRepository.save(
                new UserChatRoomEntity(
                        userEntity,
                        savedChatRoomEntity
                )
        );

        return new CreateChatRoomResponse(savedChatRoomEntity);
    }

    @Transactional
    public void sendJoinedMessage(
            String userId,
            ChatMessage chatMessage
    ) {
        String joinedMessage = this.getJoinedMessage(
                userId,
                chatMessage.getChatRoomId()
        );

        chatMessage.setMessage(joinedMessage);

        rabbitTemplate.convertAndSend("chat.exchange", "chat.rooms." + chatMessage.getChatRoomId(), chatMessage);
        applicationEventPublisher.publishEvent(new ChatMessageEvent(chatMessage, userId));
    }

    private String getJoinedMessage(
            String userId,
            Long chatRoomId
    ) {
        this.validateUserJoinedChatRoom(userId, chatRoomId);

        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        return userEntity.getNickname() + "님이 들어왔습니다.";
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
                .filter(it -> userId.equals(it.getUser().getUserId()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_ROOM_NOT_JOINED));

        if (userChatRoomEntity.getUser() == null) {
            throw new NotFoundException(ErrorCode.USER_NOT_FOUND);
        }
    }

}
