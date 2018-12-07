package com.nexters.rezoom.temp.question.domain;

import com.nexters.rezoom.temp.coverletter.domain.Coverletter;
import com.nexters.rezoom.temp.hashtag.domain.HashTag;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "contents")
    private String contents;

    @ManyToOne
    @JoinColumn(name = "coverletter_id") // Question(N)과 Coverletter(1)는 Coverletter_ID(FK)를 이용해 연관관계를 맺는다.
    private Coverletter coverletter;

    @ManyToMany
    @JoinTable(name = "question_hashtag",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id"))
    private Set<HashTag> hashTags = new HashSet<>();

    public Question(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public Question(long id, String title, String contents) {
        this(title, contents);
        this.id = id;
    }

    public void setCoverletter(Coverletter coverletter) {
        // 이미 coverletter가 존재한다면, 해당 coverletter에서 현재 question을 제거한다. (문항을 다른 자기소개서로 옮겼으므로)
        if (this.coverletter != null) {
            this.coverletter.getQuestions().remove(this);
        }
        this.coverletter = coverletter;

        // 연관관계 설정
        if (!this.coverletter.getQuestions().contains(this))
            this.coverletter.addQuestion(this);
    }

    public void addHashTag(HashTag hashTag) {
        this.hashTags.add(hashTag);
    }

    @Override
    public String toString() {
        return title + " " + contents;
    }
}
