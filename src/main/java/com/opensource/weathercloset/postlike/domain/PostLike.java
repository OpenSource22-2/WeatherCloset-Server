package com.opensource.weathercloset.postlike.domain;

import com.opensource.weathercloset.member.domain.Member;
import com.opensource.weathercloset.post.domain.Post;
import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class PostLike {

    @Id @GeneratedValue
    @Column(name = "postLike_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

}
