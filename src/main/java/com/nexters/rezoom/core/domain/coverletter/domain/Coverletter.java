package com.nexters.rezoom.core.domain.coverletter.domain;

import com.nexters.rezoom.core.domain.member.domain.Member;
import com.nexters.rezoom.core.global.config.jpa.YearAttributeConverter;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "coverletter")
@EqualsAndHashCode(of = "id")
public class Coverletter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coverletter_id")
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Setter
    @Column(name = "company_name")
    private String companyName;

    @Embedded
    private Deadline deadline;

    @Column(name = "application_type")
    private ApplicationType applicationType;

    @Column(name = "application_half")
    private ApplicationHalf applicationHalf;

    @Column(name = "application_state")
    private ApplicationState applicationState;

    @Column(name = "application_year")
    @Convert(converter = YearAttributeConverter.class)
    private Year applicationYear;

    @Column(name = "job_type")
    private String jobType;

    @Column(name = "pass_state")
    private PassState passState;

    @OneToMany(
            mappedBy = "coverletter",
            fetch = FetchType.LAZY, cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Question> questions;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder(builderMethodName = "newCoverletterBuilder")
    public Coverletter(Member member, String companyName, Deadline deadline, ApplicationType applicationType,
                       ApplicationHalf applicationHalf, ApplicationState applicationState, Year applicationYear,
                       String jobType, PassState passState) {

        this.member = member;
        this.companyName = companyName;
        this.deadline = deadline;
        this.applicationHalf = applicationHalf;
        this.applicationType = applicationType;
        this.applicationState = applicationState;
        this.applicationYear = applicationYear;
        this.jobType = jobType;
        this.passState = passState;
        this.questions = new ArrayList<>();
    }


    @Builder(builderMethodName = "existCoverletterBuilder")
    public Coverletter(Long id, Member member, String companyName, Deadline deadline, ApplicationType applicationType,
                       ApplicationHalf applicationHalf, ApplicationState applicationState, Year applicationYear,
                       String jobType, PassState passState) {

        this.id = id;
        this.member = member;
        this.companyName = companyName;
        this.deadline = deadline;
        this.applicationHalf = applicationHalf;
        this.applicationType = applicationType;
        this.applicationState = applicationState;
        this.applicationYear = applicationYear;
        this.jobType = jobType;
        this.passState = passState;
        this.questions = new ArrayList<>();
    }

    /**
     * Domain rule.
     * - 지원상태 'WAIT' -> 합격상태 'WAIT'
     * - 지원상태 'NO'   -> 합격상태 'FAIL'
     */

    public void checkPassStatus() {
        if (this.applicationState == ApplicationState.WAIT) {
            this.passState = PassState.WAIT;
            return;
        }

        if (this.applicationState == ApplicationState.NO) {
            this.passState = PassState.FAIL;
            return;
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
}
