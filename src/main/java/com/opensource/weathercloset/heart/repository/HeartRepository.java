package com.opensource.weathercloset.heart.repository;

import com.opensource.weathercloset.heart.domain.Heart;
import com.opensource.weathercloset.member.domain.Member;
import com.opensource.weathercloset.record.domain.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart, Long> {

    Optional<Heart> findByMemberAndRecord(Member member, Record record);

    @Query(value = "select h.record from Heart h where h.member = :member")
    List<Record> findAllByMember(@Param("member") Member member);

}
