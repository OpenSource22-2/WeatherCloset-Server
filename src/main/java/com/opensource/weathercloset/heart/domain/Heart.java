package com.opensource.weathercloset.heart.domain;

import com.opensource.weathercloset.common.domain.DateTimeEntity;
import com.opensource.weathercloset.member.domain.Member;
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
public class Heart extends DateTimeEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "heart_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "record_id")
    private Record record;

    @Builder
    public Heart(Member member, Record record) {
        this.member = member;
        this.record = record;
    }

    public boolean didHeart(Long recordId) {
        return record.getId()
                .equals(recordId);
    }
}
