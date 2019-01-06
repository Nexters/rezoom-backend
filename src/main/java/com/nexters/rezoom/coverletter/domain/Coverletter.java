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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(
            mappedBy = "coverletter",
            fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<Question> questions = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "createDate")
    private LocalDateTime createDate;

    @UpdateTimestamp
    @Column(name = "updateDate")
    private LocalDateTime updateDate;

    public Coverletter(long id, Member member, String companyName) {
        this.id = id;
        this.member = member;
        this.companyName = companyName;
    }

    public Coverletter(Member member, String companyName) {
        this.member = member;
        this.companyName = companyName;
    }

    public void setQuestions(List<Question> questions) {
        clearQuestion(questions);
        questions.forEach(this::addQuestion);
    }

    // 이렇게 짯는데 어떻게 누적되서 안들어가지????????? 이따 디버깅해보자

    private void addQuestion(Question question) {
        if (!this.questions.contains(question)) {
            questions.add(question);
            question.setCoverletter(this);
        }
    }

    private void clearQuestion(List<Question> questions) {
        this.questions.retainAll(questions);
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
