package com.nexters.rezoom.member.domain;

import lombok.*;

import javax.persistence.*;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "member")
@Entity
@EqualsAndHashCode(of = {"id"})
public class Member {

    @Id
    @Column(name = "member_id")
    protected String id;

    @Setter
    @Column(name = "name")
    protected String name;

    @Setter
    @Column(name = "password")
    private String password;

    @Setter
    @Column(name = "motto")
    private String motto;

    public Member(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public void updateMemberInfo(String name, String motto) {
        this.name = name;
        this.motto = motto;
    }
}