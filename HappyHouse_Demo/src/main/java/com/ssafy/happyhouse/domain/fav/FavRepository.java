package com.ssafy.happyhouse.domain.fav;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FavRepository extends JpaRepository<Fav, Long> {

	@Query("Select f from Fav f where user_id = :user_id")
	List<Fav> findFavUserId(@Param("user_id") Long user_id);
	
	@Modifying
	@Transactional
	@Query("delete Fav f where user_id = ?1 and apt_id = ?2")
	int deleavBySth(Long user_id, String apt_id);
}
