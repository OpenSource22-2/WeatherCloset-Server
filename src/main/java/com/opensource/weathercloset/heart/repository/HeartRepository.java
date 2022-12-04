package com.opensource.weathercloset.heart.repository;

import com.opensource.weathercloset.heart.domain.Heart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeartRepository extends JpaRepository<Heart, Long> {

//    Optional<Heart> findByMemberAndRecord(Member member, Record record);

//    @Query(value = "select h.record from Heart h where h.member = :member")
//    List<Record> findAllByMember(@Param("member") Member member);

}
