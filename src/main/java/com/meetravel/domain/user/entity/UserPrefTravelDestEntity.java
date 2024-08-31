package com.meetravel.domain.user.entity;

import com.meetravel.domain.travel_destination.entity.TravelDestEntity;
import com.meetravel.global.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_pref_travel_dest", uniqueConstraints = { // 데이터 중복 방지를 위해 유니크 제약조건 설정
        @UniqueConstraint(columnNames = {"USER_ID", "TRAVEL_DEST_ID"})
})
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPrefTravelDestEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRAVEL_DEST_ID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private TravelDestEntity travelDest;

    public void setUser(UserEntity user) {
        this.user = user;
    }

}
