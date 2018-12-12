package com.nexters.rezoom.coverletter.infra;

import com.nexters.rezoom.coverletter.domain.Coverletter;
import com.nexters.rezoom.coverletter.domain.CoverletterRepository;
import com.nexters.rezoom.member.domain.Member;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class JpaCoverletterRepository implements CoverletterRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public void save(Coverletter coverletter) {
        entityManager.persist(coverletter);
    }

    @Override
    public Coverletter findById(Member member, long id) {
        TypedQuery<Coverletter> query = entityManager.createQuery("SELECT c FROM Coverletter c WHERE c.id =:id AND c.member =:member", Coverletter.class);
        query.setParameter("id", id);
        query.setParameter("member", member);

        List<Coverletter> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public List<Coverletter> findAll(Member member, int begin, int end) {
        TypedQuery<Coverletter> query = entityManager.createQuery("SELECT c FROM Coverletter c WHERE c.member =:member", Coverletter.class);

        // paging : beginRow <= rowNum <= endRow (이상, 포함관계)
        // TODO : 페이징 데이터 validation 필요
        // begin = begin == 0 ? 1 : begin (begin은 항상 1부터 시작)
        // end = end > max ? (내가정한페이징MAX) : end
        query.setFirstResult(begin);
        query.setMaxResults(end);
        query.setParameter("member", member);
        return query.getResultList();
    }

    @Transactional
    @Override
    public void delete(Coverletter coverletter) {
        entityManager.remove(coverletter);
    }


}
