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
    public Coverletter save(Coverletter coverletter) {
        if (coverletter.getId() != 0) {
            em.merge(coverletter);
            return coverletter;
        }

        em.persist(coverletter);
        return coverletter;
    }

    @Override
    public Coverletter findById(Member member, long id) {
        TypedQuery<Coverletter> query = em.createQuery("SELECT c FROM Coverletter c WHERE c.id =:id AND c.member =:member", Coverletter.class);
        query.setParameter("id", id);
        query.setParameter("member", member);

        List<Coverletter> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0); // 애초에 널타입을 반환하는게 좋지 않음..
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

    @Override
    public List<Coverletter> findByDeadline(Member member) {
        TypedQuery<Coverletter> query = em.createQuery(
                "SELECT c FROM Coverletter c WHERE c.member =:member AND deadline IS NOT NULL AND deadline >= CURRENT_TIMESTAMP"
                , Coverletter.class);

        query.setParameter("member", member);
        return query.getResultList();
    }

    @Override
    public List<Coverletter> findAllByDeadline() {
        TypedQuery<Coverletter> query = em.createQuery(
                "SELECT c FROM Coverletter c WHERE deadline IS NOT NULL AND deadline >= CURRENT_TIMESTAMP"
                , Coverletter.class);

        return query.getResultList();
    }
}
