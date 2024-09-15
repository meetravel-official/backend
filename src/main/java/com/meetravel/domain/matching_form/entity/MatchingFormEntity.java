package com.meetravel.domain.matching_form.entity;

import com.meetravel.domain.chatroom.entity.ChatRoomEntity;
import com.meetravel.domain.matching_form.enums.Cost;
import com.meetravel.domain.matching_form.enums.Duration;
import com.meetravel.domain.matching_form.enums.GenderRatio;
import com.meetravel.domain.matching_form.enums.GroupSize;
import com.meetravel.domain.user.entity.UserEntity;
import com.meetravel.global.audit.BaseEntity;
import com.meetravel.global.converter.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "matching_application_form")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchingFormEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MATCHING_FORM_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHAT_ROOM_ID")
    private ChatRoomEntity chatRoom;

    @Column(name = "DURATION")
    @Convert(converter = DurationConverter.class)
    private Duration duration;

    @Column(name = "START_DT")
    private LocalDate startDate;

    @Column(name = "END_DT")
    private LocalDate endDate;

    @Column(name = "GROUP_SIZE")
    @Convert(converter = GroupSizeConverter.class)
    private GroupSize groupSize;

    @Column(name = "GENDER_RATIO")
    @Convert(converter = GenderRatioConverter.class)
    private GenderRatio genderRatio;

    @Column(name = "COST")
    @Convert(converter = CostConverter.class)
    private Cost cost;

    @Column(name = "AREA_CODE")
    private String areaCode;

    @Column(name = "AREA_NAME")
    private String areaName;

    @Column(name = "DETAIL_AREA_CODE")
    private String detailAreaCode;

    @Column(name = "DETAIL_AREA_NAME")
    private String detailAreaName;

    @OneToMany(mappedBy = "matchingForm", orphanRemoval = true)
    @Builder.Default
    private List<TravelKeywordEntity> travelKeywordList = new ArrayList<>();

    public void addTravelKeyword(TravelKeywordEntity travelKeyword) {
        travelKeywordList.add(travelKeyword);
        travelKeyword.setMatchingForm(this);
    }

    public void enterChatRoom(ChatRoomEntity chatRoomEntity) {
        this.chatRoom = chatRoomEntity;
    }
}
