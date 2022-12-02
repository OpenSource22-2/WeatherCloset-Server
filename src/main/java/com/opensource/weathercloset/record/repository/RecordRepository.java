package com.opensource.weathercloset.record.repository;

import com.opensource.weathercloset.member.domain.Member;
import com.opensource.weathercloset.record.domain.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {

    List<Record> findAllByMember(Member member);

    @Query("select r from Record r where r.weather.avgTa between :minTemperature and :maxTemperature") //
    List<Record> findAllByTemperatureBetween(@Param("minTemperature")double minTemperature, @Param("maxTemperature")double maxTemperature);

}
