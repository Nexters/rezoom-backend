package com.nexters.rezoom.coverletter.domain;

import com.nexters.rezoom.config.jpa.YearAttributeConverter;
import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.question.domain.Question;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
@Getter
@Entity
@Table(name = "coverletter")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Coverletter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coverletter_id")
    private long id;

    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Setter
    @Column(name = "company_name")
    private String companyName;

    @Embedded
    private Deadline deadline;

    @Builder.Default
    @Column(name = "application_type")
    private ApplicationType applicationType = ApplicationType.ETC;

    @Builder.Default
    @Column(name = "application_half")
    private ApplicationHalf applicationHalf = ApplicationHalf.ETC;

    @Builder.Default
    @Column(name = "is_application")
    private IsApplication isApplication = IsApplication.ETC;

    @Builder.Default
    @Column(name = "application_year")
    @Convert(converter = YearAttributeConverter.class)
    private Year applicationYear = Year.of(LocalDateTime.now().getYear());

    @Builder.Default
    @Column(name = "job_type")
    private String jobType = "";

    @Builder.Default
    @Column(name = "is_pass")
    private IsPass isPass = IsPass.ETC;

    @Builder.Default
    @OneToMany(
            mappedBy = "coverletter",
            fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<Question> questions = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @UpdateTimestamp
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    public void setQuestions(List<Question> questions) {
        if (this.questions == null)
            this.questions = new ArrayList<>();

        for (Question q : questions) {
            this.addQuestion(q);
        }
    }

    private void addQuestion(Question question) {
        if (question == null)
            return;

        this.questions.add(question);
        question.setCoverletter(this); // 양방향 연관관계 설정
    }

    /**
     * domain rule.
     */
    public void checkPassStatus() {
        // 지원상태가 'WAIT'이면, 합격상태도 'WAIT'이어야 한다.
        if (this.isApplication == IsApplication.WAIT) {
            this.isPass = IsPass.WAIT;
        }

        // 지원상태가 'NO'이면, 합격상태는 'FAIL'이어야 한다.
        else if (this.isApplication == IsApplication.NO) {
            this.isPass = IsPass.FAIL;
        }
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
