package com.nexters.rezoom.coverletter.domain;

import com.nexters.rezoom.member.domain.Member;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "hashtag")
@EqualsAndHashCode(of = {"member", "value"})
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "value")
    private String value;

    @ManyToMany(mappedBy = "hashtags", fetch = FetchType.EAGER)
    private List<Question> questions;

    public Hashtag(Member member, String value) {
        this.member = member;
        this.value = value;
        this.questions = new ArrayList<>();
    }

    public Hashtag(String value) {
        this.value = value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
