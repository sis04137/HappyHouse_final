package com.ssafy.happyhouse.domain.member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{
	Optional<Member> findByEmail(String email);
	
	@Query("select m from Member m where m.email=:email")
	Member loginEmail(@Param("email") String email);
	
	Member findFirstByOrderByIdDesc();

	

}
