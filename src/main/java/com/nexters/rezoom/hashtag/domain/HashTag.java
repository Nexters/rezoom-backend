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
public class HashTag {

    // 해쉬태그의 경우 자유롭게 추가 수정하기 때문에, id에 의존적인것보다 value+member를 subkey로 설정하는게 낫다고 생각..
    // TODO : value + member_id 복합 UK 설정하기 (임베디드 타입)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "value")
    private String value;

    @ManyToMany(mappedBy = "hashTags", cascade = CascadeType.MERGE)
    private List<Question> questions;

    public HashTag(Member member, String value) {
        this.member = member;
        this.value = value;
        this.questions = new ArrayList<>();
    }

    public void updateValue(String value) {
        this.value = value;
    }

    public void addQuestion(Question question) {
        this.questions.add(question);

        // 문항에도 연관관계를 추가한다.
        if (!question.getHashTags().contains(this))
            question.addHashtag(this);
    }

    public void setQuestions(List<Question> questions) {
        questions.forEach(this::addQuestion);
    }

    @Override
    public String toString() {
        return id + " " + value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HashTag hashTag = (HashTag) o;
        return Objects.equals(member, hashTag.member) && Objects.equals(value, hashTag.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(member, value);
    }

}
