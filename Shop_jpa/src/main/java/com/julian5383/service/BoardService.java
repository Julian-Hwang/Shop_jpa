package com.julian5383.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.julian5383.entity.Board;
import com.julian5383.repository.BoardRepository;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;
	
	public void boardWrite(Board board,MultipartFile file) throws Exception, IOException{
		if(!file.isEmpty()) {
			String path = "E:\\image\\sell";
			UUID uuid = UUID.randomUUID();
			String filename = uuid+"_"+file.getOriginalFilename();
			File saveFile = new File(path, filename);
			file.transferTo(saveFile);
			board.setPhoto(filename);
			board.setPhoto_url("/sell/"+filename);
		}
		
		boardRepository.save(board);
	}
	
	public void boardModify(Integer id, Board modifyboard,MultipartFile file) throws Exception, IOException{
		Board existboard=boardRepository.findById(id).get();
		
		existboard.setTitle(modifyboard.getTitle());
		existboard.setContent(modifyboard.getContent());
		existboard.setPrice(modifyboard.getPrice());
		existboard.setBuyornot(modifyboard.getBuyornot());
		existboard.setFinornot(modifyboard.getFinornot());
		
		if(!file.isEmpty()) {
			String path = "E:\\image\\sell";
			UUID uuid = UUID.randomUUID();
			String filename = uuid+"_"+file.getOriginalFilename();
			File saveFile = new File(path, filename);
			file.transferTo(saveFile);
			existboard.setPhoto(filename);
			existboard.setPhoto_url("/sell/"+filename);
		}
		
		boardRepository.save(existboard);
	}
	
	public Page<Board> boardList(Pageable pageable){
		return boardRepository.findAll(pageable);
	}

	public Page<Board> boardTitleSearchList(String searchKeyword,Pageable pageable){
		return boardRepository.findByTitleContaining(searchKeyword, pageable);
	}
	
	public Page<Board> boardContentSearchList(String searchKeyword,Pageable pageable){
		return boardRepository.findByContentContaining(searchKeyword, pageable);
	}
	
	public Page<Board> boardWriterSearchList(String searchKeyword, Pageable pageable){
		return boardRepository.findByWriterContaining(searchKeyword, pageable);
	}
	
	public Board boardView(Integer id) {
		return boardRepository.findById(id).get();
	}
	
	public void boardDelete(Integer id) {
		boardRepository.deleteById(id);
	}
	
	public int updateView(Integer id) {
		return boardRepository.updateView(id);
	}
}
