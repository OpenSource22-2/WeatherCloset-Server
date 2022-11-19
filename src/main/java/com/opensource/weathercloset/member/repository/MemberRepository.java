package com.opensource.weathercloset.member.repository;

import com.opensource.weathercloset.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
