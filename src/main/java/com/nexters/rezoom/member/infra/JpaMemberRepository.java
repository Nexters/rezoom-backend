package com.nexters.rezoom.member.infra;

import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.member.domain.MemberRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class JpaMemberRepository implements MemberRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Member findById(String id) {
        return em.find(Member.class, id);
    }

    @Override
    public void save(Member member) {
        try {
            em.persist(member);
        } catch (PersistenceException e) {
            e.getCause();
        }
    }

    @Override
    public List<Member> findActiveMembers() {
        // TODO : Active 조건이 필요한디?
        TypedQuery<Member> query = em.createQuery("SELECT m FROM Member m", Member.class);
        return query.getResultList();
    }
}
