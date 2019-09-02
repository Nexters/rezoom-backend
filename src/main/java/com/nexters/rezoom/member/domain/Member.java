package com.nexters.rezoom.member.domain;

import com.nexters.rezoom.notification.domain.NotificationSetting;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "member")
@Entity
public class Member {

    @Id
    @Column(name = "member_id")
    private String id;

    @Setter
    @Column(name = "name")
    private String name;

    @Setter
    @Column(name = "password")
    private String password;

    @Setter
    @Column(name = "motto") // 좌우명
    private String motto;

    @Setter
    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Builder.Default
    @OneToMany(
            mappedBy = "member",
            fetch = FetchType.EAGER, cascade = CascadeType.ALL
    )
    private Set<NotificationSetting> notificationSettings = new HashSet<>();

    public Member(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public void addNotificationSetting(NotificationSetting notificationSetting) {
        this.notificationSettings.add(notificationSetting);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}