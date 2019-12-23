package com.nexters.rezoom.coverletter.infra;

import com.mysema.query.jpa.impl.JPAQuery;
import com.nexters.rezoom.coverletter.domain.QQuestion;
import com.nexters.rezoom.coverletter.domain.Question;
import com.nexters.rezoom.coverletter.domain.QuestionRepository;
import com.nexters.rezoom.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional
@Repository
public class JpaQuestionRepository implements QuestionRepository {

    @PersistenceContext
    private EntityManager em;

    public Question findByKey(long questionId, Member member) {
        JPAQuery query = new JPAQuery(em);
        QQuestion qQuestion = QQuestion.question;

        return query.from(qQuestion)
                .where(qQuestion.id.eq(questionId).and(qQuestion.coverletter.member.eq(member)))
                .uniqueResult(qQuestion);
    }

    public Page<Question> findAllByMember(Pageable pageable, Member member) {
        QQuestion qQuestion = QQuestion.question;

        List<Question> questions = createJpaQuery()
                .from(qQuestion)
                .where(qQuestion.coverletter.member.eq(member))
                .offset(pageable.getPageNumber())
                .limit(pageable.getPageSize())
                .list(qQuestion);

        long countOfAllQuestion = createJpaQuery()
                .from(qQuestion)
                .where(qQuestion.coverletter.member.eq(member))
                .count();

        return new PageImpl<>(questions, pageable, countOfAllQuestion);
    }

    private JPAQuery createJpaQuery() {
        return new JPAQuery(em);
    }

}
