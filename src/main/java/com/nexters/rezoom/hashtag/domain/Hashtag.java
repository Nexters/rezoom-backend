package com.nexters.rezoom.hashtag.domain;

import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.question.domain.Question;
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

    @ManyToMany(mappedBy = "hashtags", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Question> questions;

    public Hashtag(Member member, String value) {
        this.member = member;
        this.value = value;
        this.questions = new ArrayList<>();
    }

    public Hashtag(String value) {
        this.value = value;
    }

    // TODO:  상태를 바꾸지말고, 불변객체를 리턴하자
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


}
