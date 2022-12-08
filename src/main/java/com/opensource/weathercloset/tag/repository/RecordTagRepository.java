package com.opensource.weathercloset.tag.repository;

import com.opensource.weathercloset.tag.domain.RecordTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordTagRepository extends JpaRepository<RecordTag, Long> {
}
