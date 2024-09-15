package com.meetravel.domain.chatroom.service;

import com.meetravel.domain.chatroom.dto.CreateChatRoomResponse;
import com.meetravel.domain.chatroom.entity.ChatRoomEntity;
import com.meetravel.domain.chatroom.entity.UserChatRoomEntity;
import com.meetravel.domain.chatroom.repository.ChatRoomRepository;
import com.meetravel.domain.chatroom.repository.UserChatRoomRepository;
import com.meetravel.domain.matching_form.entity.MatchingFormEntity;
import com.meetravel.domain.matching_form.repository.MatchingFormRepository;
import com.meetravel.domain.user.entity.UserEntity;
import com.meetravel.domain.user.repository.UserRepository;
import com.meetravel.global.exception.BadRequestException;
import com.meetravel.global.exception.ErrorCode;
import com.meetravel.global.exception.NotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatRoomService {
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

        matchingFormEntity.enterChatRoom(savedChatRoomEntity);

        matchingFormRepository.save(matchingFormEntity);
        userChatRoomRepository.save(
                new UserChatRoomEntity(
                        userEntity,
                        savedChatRoomEntity
                )
        );

        return new CreateChatRoomResponse(savedChatRoomEntity);
    }
}
