package com.nexters.rezoom.core.domain.notification.domain;

import com.nexters.rezoom.core.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by momentjin@gmail.com on 2019-09-02
 * Github : http://github.com/momentjin
 */

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Optional<Notification> findByIdAndMember(Long id, Member member);
    List<Notification> findAllByMemberOrderByCreatedAtDesc(Member member);
}
