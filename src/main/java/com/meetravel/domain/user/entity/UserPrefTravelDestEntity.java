package com.meetravel.domain.user.entity;

import com.meetravel.domain.travel_destination.entity.TravelDestEntity;
import com.meetravel.domain.travel_destination.enums.TravelDest;
import com.meetravel.global.audit.BaseEntity;
import com.meetravel.global.converter.TravelDestConverter;
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
    @Column(name = "USER_PREF_TRAVEL_DEST_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRAVEL_DEST_ID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private TravelDestEntity travelDest;

    @Column(name = "USER_PREF_TRAVEL_DEST")
    @Convert(converter = TravelDestConverter.class)
    private TravelDest userPrefTravelDest;

    public void setUser(UserEntity user) {
        this.user = user;
    }

}
