package com.opensource.weathercloset.tag.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "tag", fetch = EAGER)
    private Set<RecordTag> recordTagSet = new HashSet<>();

    public Tag(String name) {
        this.name = name;
    }
}
