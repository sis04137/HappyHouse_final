package com.ssafy.happyhouse.domain.board;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>{
	@Query("select b from Board b where b.title like concat('%',:keyword,'%') or b.content like concat('%',:keyword,'%')")
	List<Board> getListFromKeyword(@Param("keyword") String keyword);
}
