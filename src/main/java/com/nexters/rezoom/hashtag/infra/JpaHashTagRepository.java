package com.nexters.rezoom.hashtag.infra;

import com.nexters.rezoom.hashtag.domain.HashTagRepository;
import com.nexters.rezoom.hashtag.domain.Hashtag;
import com.nexters.rezoom.member.domain.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Transactional
@Service
public class JpaHashTagRepository implements HashTagRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Hashtag findByKey(Member member, String value) {
        TypedQuery<Hashtag> query = em.createQuery("SELECT h FROM Hashtag h WHERE h.member =:member AND h.value =:value", Hashtag.class);
        query.setParameter("member", member);
        query.setParameter("value", value);

        List<Hashtag> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public List<Hashtag> findAll(Member member) {
        TypedQuery<Hashtag> query = em.createQuery("SELECT h FROM Hashtag h WHERE h.member =:member", Hashtag.class);
        query.setParameter("member", member);

        return query.getResultList();
    }

    @Override
    public void delete(Hashtag hashTag) {
        em.remove(hashTag);
    }

    @Override
    public void save(Hashtag hashTag) {
        em.persist(hashTag);
    }

}
