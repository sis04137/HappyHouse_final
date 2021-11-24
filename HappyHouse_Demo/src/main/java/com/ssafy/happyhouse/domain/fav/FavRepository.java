package com.ssafy.happyhouse.domain.fav;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FavRepository extends JpaRepository<Fav, Long> {

	@Query("Select f from Fav f where user_id = :user_id")
	List<Fav> findFavUserId(@Param("user_id") Long user_id);
}
