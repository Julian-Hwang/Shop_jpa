package com.julian5383.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.julian5383.entity.Comment;
import com.julian5383.repository.CommentRepository;

@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;
	
	public void commentinsert(String writer,String comment,Integer sell_no) {
		Comment q = new Comment();
		q.setSell_no(sell_no);
		q.setWrite_id(writer);
		q.setContent(comment);
		commentRepository.save(q);
	}
	
	public List<Comment> commentList(){
		return commentRepository.findAll();
	}
	
	public void commentDelete(Integer id) {
		commentRepository.deleteById(id);
	}
	
	public void commentModify(int id, String modifycomment) {
		Optional<Comment> oq = commentRepository.findById(id);
		Comment q = oq.get();
		q.setContent(modifycomment);
		commentRepository.save(q);
	}
}
