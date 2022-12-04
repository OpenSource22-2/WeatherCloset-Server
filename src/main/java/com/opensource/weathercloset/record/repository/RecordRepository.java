package com.opensource.weathercloset.record.repository;

import com.opensource.weathercloset.member.domain.Member;
import com.opensource.weathercloset.record.domain.Record;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {

    List<Record> findAllByMember(Member member);

    @Query("select distinct r from Record r where r.weather.avgTa between :min and :max")
    List<Record> findAllByTemperatureBetween(@Param("min")double minTemperature, @Param("max")double maxTemperature);

    @Query(value = "select distinct * from record where member_id = :memberId and " +
            "date between :date and LAST_DAY(:date) " +
            "order by date ASC limit 8", nativeQuery = true)
    List<Record> findAllByMemberAndDate(@Param("memberId")Long memberId, @Param("date")LocalDate date);

    @Query(value = "select distinct r from Record r " +
            "where r.member.id = :memberId and " +
            "r.weather.avgTa between :temperature-5.0 and :temperature+5.0 " + "order by r.stars DESC, r.date DESC")
    List<Record> findAllByMemberAndTemperature(@Param("memberId")Long memberId, @Param("temperature")double temperature, Pageable pageable);
}