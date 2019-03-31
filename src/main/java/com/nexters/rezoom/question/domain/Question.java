package com.nexters.rezoom.question.domain;

import com.nexters.rezoom.coverletter.domain.Coverletter;
import com.nexters.rezoom.hashtag.domain.Hashtag;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
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

    @Column(name = "contents", columnDefinition = "TEXT")
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coverletter_id", nullable = false)
    private Coverletter coverletter;

    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinTable(
            name = "question_hashtag",
            joinColumns = @JoinColumn(name = "question_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id", nullable = false)
    )
    private Set<Hashtag> hashtags = new HashSet<>();

    public Question(String title, String contents) {
        this.title = title;
        this.contents = contents;
        this.hashtags = new HashSet<>();
    }

    public Question(long id, String title, String contents) {
        this(title, contents);
        this.id = id;
    }

    public void updateData(String title, String contents) {
        this.setTitle(title);
        this.setContents(contents);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setHashtags(Set<Hashtag> hashtags) {
        this.hashtags = hashtags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;

        // question id가 0이면 새롭게 생성된 데이터이므로, 항상 false를 반환한다.
        if (question.id == 0) return false;
        else return id == question.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id + " " + title + " " + contents;
    }

    public void setCoverletter(Coverletter coverletter) {
        if (this.coverletter != null)
            this.coverletter.getQuestions().remove(this);

        this.coverletter = coverletter;

        if (!this.coverletter.getQuestions().contains(this))
            this.coverletter.getQuestions().add(this);
    }
}
