package com.nexters.rezoom.coverletter.domain;

import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.question.domain.Question;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @OneToMany(mappedBy = "coverletter", cascade = CascadeType.ALL)
    private List<Question> questions = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "createDate")
    private LocalDateTime createDate;

    @UpdateTimestamp
    @Column(name = "updateDate")
    private LocalDateTime updateDate;

    public Coverletter(Member member, String companyName) {
        this.member = member;
        this.companyName = companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
        this.questions.forEach(this::addQuestion);

        // 연관관계 설정
        questions.forEach(question -> {
            if (question.getCoverletter() == null || !question.getCoverletter().equals(this)) {
                question.setCoverletter(this);
            }
        });
    }

    public void addQuestion(Question question) {
        if (!questions.contains(question)) {
            questions.add(question);
        }

        if (question.getCoverletter() == null)
            question.setCoverletter(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coverletter that = (Coverletter) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
