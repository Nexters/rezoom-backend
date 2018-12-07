package com.nexters.rezoom.temp.hashtag.domain;

import com.nexters.rezoom.temp.question.domain.Question;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "hashtag")
public class HashTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_id")
    private long id;

    @Column(name = "value")
    private String value;

    @ManyToMany(mappedBy = "hashTags")
    private List<Question> questions;

    public HashTag(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return id + " " + value;
    }
}
