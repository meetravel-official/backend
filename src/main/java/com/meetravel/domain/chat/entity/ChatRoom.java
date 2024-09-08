package com.meetravel.domain.chat.entity;

import com.meetravel.domain.matching_form.entity.MatchingFormEntity;
import com.meetravel.domain.user.entity.UserEntity;
import com.meetravel.global.audit.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "chat_room")
public class ChatRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;   // 채팅방 제목

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "user_list")
    @Builder.Default
    private Set<UserEntity> userList = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "matching_appl_form_id")
    private MatchingFormEntity matchingFormEntity;

    public void updateTitle(String title) {
        this.title = title;
    }

    public void addUsers(Set<UserEntity> users) {
        this.userList.addAll(users);
    }

    public void removeUser(UserEntity user) {
        this.userList.remove(user);
    }

    public boolean checkUserExists(UserEntity targetUser) {
        return this.userList.stream().anyMatch(user -> user.equals(targetUser));
    }
}
