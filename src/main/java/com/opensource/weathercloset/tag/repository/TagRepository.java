package com.opensource.weathercloset.tag.repository;

import com.opensource.weathercloset.tag.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}