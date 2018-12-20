package com.nexters.rezoom.member.infra;

import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.member.domain.MemberRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

@Repository
public class JpaMemberRepository implements MemberRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Member findById(String id) {
        return em.find(Member.class, id);
    }

    @Override
    public void save(Member member) { // throws PersistenceException {
        try {
            em.persist(member);
        } catch (PersistenceException e) {
            e.getCause();
        }
    }
}
