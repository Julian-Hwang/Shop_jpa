package com.julian5383.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.julian5383.entity.Board;

import jakarta.transaction.Transactional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer>{
	
	Page<Board> findByTitleContaining(String searchkeyword, Pageable pageable);
	
	Page<Board> findByContentContaining(String searchkeyword, Pageable pageable);
	
	Page<Board> findByWriterContaining(String searchkeyword, Pageable pageable);
	
	@Transactional
	@Modifying
	@Query("update Board b set b.view=b.view+1 where b.sell_no=:id")
	int updateView(Integer id);
}
