package com.nexters.rezoom.core.domain.coverletter.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "question")
@EqualsAndHashCode(of = {"id"})
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "contents", columnDefinition = "TEXT")
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coverletter_id", nullable = false)
    private Coverletter coverletter;

    @ManyToMany(
            fetch = FetchType.LAZY,
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

    public Question(Long id, String title, String contents) {
        this(title, contents);
        this.id = id;
    }

    public Question(String title, String contents, Set<Hashtag> hashtags) {
        this(title, contents);
        this.hashtags = hashtags;
    }

    public void updateData(String title, String contents, Set<Hashtag> hashtags) {
        this.title = title;
        this.contents = contents;

        if (hashtags != null)
            this.hashtags = hashtags;
    }

    public void setHashtags(Set<Hashtag> hashtags) {
        this.hashtags = hashtags;
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
