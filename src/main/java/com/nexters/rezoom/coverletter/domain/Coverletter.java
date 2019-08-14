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
            fetch = FetchType.EAGER, cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Question> questions = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @UpdateTimestamp
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    /**
     * Domain rule.
     * - 지원상태 'WAIT' -> 합격상태 'WAIT'
     * - 지원상태 'NO'   -> 합격상태 'FAIL'
     */

    public void checkPassStatus() {
        if (this.isApplication == IsApplication.WAIT) {
            this.isPass = IsPass.WAIT;
        } else if (this.isApplication == IsApplication.NO) {
            this.isPass = IsPass.FAIL;
        }
    }

    /**
     * 연관관계 편의 메소드
     */

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
