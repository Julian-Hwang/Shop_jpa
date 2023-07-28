package com.julian5383.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.julian5383.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer>{

}
