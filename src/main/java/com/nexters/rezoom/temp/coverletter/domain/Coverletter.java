package com.nexters.rezoom.temp.coverletter.domain;

import com.nexters.rezoom.dto.QuestionDto_temp;
import com.nexters.rezoom.temp.member.domain.Member;
import com.nexters.rezoom.temp.question.domain.Question;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ToString
@Getter
@Table(name = "coverletter")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Coverletter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coverletter_id")
    private long id;

    @Column(name = "company_name")
    private String companyName;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    // 자기소개서 문항
    @OneToMany(mappedBy = "coverletter") // Coverletter는 Question과 양방향 관계, mappedBy에 오는 값은 Question.coverletter
    private List<Question> questions = new ArrayList<>();

    @Column(name = "createDate")
    private LocalDateTime createDate;

    @UpdateTimestamp
    @Column(name = "updateDate")
    private LocalDateTime updateDate;

    public Coverletter(Member member, String companyName) {
        this.member = member;
        this.companyName = companyName;
    }

    public void addQuestion(Question question) {
        if (!this.questions.contains(question))
            this.questions.add(question);

        // 연관관계 설정
        if (question.getCoverletter() == null || !question.getCoverletter().equals(this))
            question.setCoverletter(this);
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void changeAllQuestion(List<QuestionDto_temp.UpdateQuestionReq> req) {
        this.questions = req.stream()
                .map(q -> new Question(q.getId(), q.getTitle(), q.getContents()))
                .collect(Collectors.toList());
    }

}
