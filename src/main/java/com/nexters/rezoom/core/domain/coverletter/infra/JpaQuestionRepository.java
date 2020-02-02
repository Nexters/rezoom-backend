package com.nexters.rezoom.core.domain.coverletter.infra;

import com.mysema.query.jpa.impl.JPAQuery;
import com.nexters.rezoom.core.domain.coverletter.domain.Question;
import com.nexters.rezoom.core.domain.coverletter.domain.QuestionRepository;
import com.nexters.rezoom.core.domain.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static com.nexters.rezoom.core.domain.coverletter.domain.QQuestion.question;

@Transactional
@Repository
public class JpaQuestionRepository implements QuestionRepository {

    @PersistenceContext
    private EntityManager em;

    public Question findByKey(Long questionId, Member member) {
        JPAQuery query = new JPAQuery(em);

        return query.from(question)
                .where(question.id.eq(questionId).and(question.coverletter.member.eq(member)))
                .uniqueResult(question);
    }

    public Page<Question> findAllByMember(Pageable pageable, Member member) {

        List<Question> questions = new JPAQuery(em)
                .from(question)
                .where(question.coverletter.member.eq(member))
                .offset(pageable.getPageNumber())
                .limit(pageable.getPageSize())
                .list(question);

        Long countOfAllQuestion = new JPAQuery(em)
                .from(question)
                .where(question.coverletter.member.eq(member))
                .count();

        return new PageImpl<>(questions, pageable, countOfAllQuestion);
    }
}
