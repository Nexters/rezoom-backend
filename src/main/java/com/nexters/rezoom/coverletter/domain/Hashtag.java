package com.nexters.rezoom.coverletter.domain;

import com.nexters.rezoom.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "hashtag")
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_id")
    private long id;

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

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id + " " + value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hashtag hashTag = (Hashtag) o;
        return Objects.equals(member, hashTag.member) && Objects.equals(value, hashTag.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(member, value);
    }


    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
