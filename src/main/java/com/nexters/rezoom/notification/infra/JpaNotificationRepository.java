package com.nexters.rezoom.notification.infra;

import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.notification.domain.Notification;
import com.nexters.rezoom.notification.domain.NotificationRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by momentjin@gmail.com on 2019-09-02
 * Github : http://github.com/momentjin
 */

@Repository
public class JpaNotificationRepository implements NotificationRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(Notification notification) {
        em.persist(notification);
    }

    @Override
    public Notification findById(Member member, long id) {
        TypedQuery<Notification> query = em.createQuery("SELECT n FROM Notification n WHERE n.id =:id AND n.member =:member", Notification.class);
        query.setParameter("id", id);
        query.setParameter("member", member);

        List<Notification> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public List<Notification> selectAll(Member member) {
        TypedQuery<Notification> query = em.createQuery("SELECT n FROM Notification n WHERE n.member =:member", Notification.class);
        query.setParameter("member", member);

        return query.getResultList();
    }

}
