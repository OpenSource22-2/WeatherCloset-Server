package com.opensource.weathercloset.member.domain;

import com.opensource.weathercloset.common.domain.DateTimeEntity;
import com.opensource.weathercloset.record.domain.Record;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Entity
@Getter
@NoArgsConstructor
public class Member extends DateTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotNull
    @Column(unique = true)
    private String nickname;

    @OneToMany(mappedBy = "member", cascade = ALL, orphanRemoval = true)
    private List<Record> records = new ArrayList();

    @Builder
    public Member(String nickname) {
        this.nickname = nickname;
    }

}
