package com.nexters.rezoom.coverletter.infra;

import com.nexters.rezoom.coverletter.domain.Coverletter;
import com.nexters.rezoom.coverletter.domain.CoverletterRepository;
import com.nexters.rezoom.member.domain.Member;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Transactional
@Repository
public class JpaCoverletterRepository implements CoverletterRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(Coverletter coverletter) {
        em.persist(coverletter);
    }

    @Override
    public Coverletter findById(Member member, long id) {
        TypedQuery<Coverletter> query = em.createQuery("SELECT c FROM Coverletter c WHERE c.id =:id AND c.member =:member", Coverletter.class);
        query.setParameter("id", id);
        query.setParameter("member", member);

        List<Coverletter> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public List<Coverletter> findAll(Member member, int beginRow, int numberPerPage) {
        TypedQuery<Coverletter> query = em.createQuery("SELECT c FROM Coverletter c WHERE c.member =:member", Coverletter.class);

        query.setParameter("member", member);
        query.setFirstResult(beginRow);
        query.setMaxResults(numberPerPage);
        return query.getResultList();
    }

    @Override
    public void delete(Coverletter coverletter) {
        em.remove(coverletter);
    }


}
