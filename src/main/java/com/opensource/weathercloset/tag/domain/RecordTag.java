package com.opensource.weathercloset.tag.domain;

import com.opensource.weathercloset.record.domain.Record;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Getter
@NoArgsConstructor
public class RecordTag {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "record_tag_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "record_id")
    private Record record;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @Builder
    public RecordTag(Record record, Tag tag) {
        this.record = record;
        this.tag = tag;
    }
}