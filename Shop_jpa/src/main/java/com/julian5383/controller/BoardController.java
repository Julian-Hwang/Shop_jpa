package com.julian5383.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.julian5383.entity.Board;
import com.julian5383.entity.User;
import com.julian5383.entity.UserCreateForm;
import com.julian5383.service.BoardService;
import com.julian5383.service.CommentService;
import com.julian5383.service.UserService;

import jakarta.validation.Valid;

@Controller
public class BoardController{

	@Autowired
	private BoardService service;
	
	Logger logger = LoggerFactory.getLogger("com.julian5383.controller.BoardController");
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/write")
	public String write(Model model, Principal principal) {
		// TODO Auto-generated method stub
		if(principal==null) {
			model.addAttribute("user", "");
		} else {
			User user = userService.getUser(principal.getName());
			model.addAttribute("user", user);
		}
		return "sell/sell_write";
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/add")
	public String add(Board board,MultipartFile file) throws Exception {
		// TODO Auto-generated method stub
		service.boardWrite(board,file);
		return "redirect:list";
	}

	@GetMapping("/list")
	public String list(Model model,@PageableDefault(page = 0,size=5,sort="writedate",direction = Sort.Direction.DESC)Pageable pageable,@RequestParam(value="search",required = false)String search,@RequestParam(value="category",required = false)String category,Principal principal) {
		// TODO Auto-generated method stub
		//List<Board> list = service.boardList();
		logger.debug("======"+category);
		logger.debug("======"+search);
		Page<Board> list=null;
		
		if(search==null || "".equals(category)){
			list=service.boardList(pageable);
		} else if("title".equals(category)){
			list=service.boardTitleSearchList(search, pageable);
		} else if("content".equals(category)){
			list=service.boardContentSearchList(search, pageable);
		} else if("writer".equals(category)){
			list=service.boardWriterSearchList(search, pageable);
		} 
		
		int now=list.getPageable().getPageNumber()+1;
		int start = Math.max(now-4, 1);
		int end = Math.min(now+5, list.getTotalPages());
		
		model.addAttribute("board", list);
		model.addAttribute("now", now);
		model.addAttribute("start", start);
		model.addAttribute("end", end);
		
		if(principal==null) {
			model.addAttribute("user", "");
		} else {
			User user = userService.getUser(principal.getName());
			model.addAttribute("user", user);
		}
		return "sell/sell";
	}

	@GetMapping("/view")
	public String view(Integer id, Model model, Principal principal) {
		// TODO Auto-generated method stub
		service.updateView(id);
		model.addAttribute("board", service.boardView(id));		
		model.addAttribute("sell_c", commentService.commentList());
		
		if(principal==null) {
			model.addAttribute("user", "");
		} else {
			User user = userService.getUser(principal.getName());
			model.addAttribute("user", user);
		}
		
//		if(!service.boardView(id).getWrite_id().equals(principal.getName())) {
//			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정권한이 없습니다.");
//		}
		
		return "sell/sell_view";
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/edit")
	public String modify(Integer id, Board board,MultipartFile file)throws Exception {
		// TODO Auto-generated method stub
		
		service.boardModify(id,board,file);
		return "redirect:view?id="+id;
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/remove")
	public String delete(Integer id) {
		// TODO Auto-generated method stub
		service.boardDelete(id);
		return "redirect:list";
	}
	
	
	@Autowired
	private CommentService commentService;
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/add_comm_sc")
	public String addcomment(@RequestParam("writer")String writer,@RequestParam("com_con")String comment,@RequestParam("id")Integer sell_no) throws Exception {
		// TODO Auto-generated method stub
		commentService.commentinsert(writer,comment,sell_no);
		return "redirect:view?id="+sell_no;
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/edit_comm_sc")
	public String editcomment(@RequestParam("id")Integer id,@RequestParam("content")String comment,@RequestParam("sell_no")Integer sell_no) throws Exception {
		// TODO Auto-generated method stub
		
		commentService.commentModify(id, comment);
		return "redirect:view?id="+sell_no;
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/remove_comm_sc")
	public String removecomment(@RequestParam("id")Integer id,@RequestParam("sell_no")Integer sell_no) {
		commentService.commentDelete(id);
		return "redirect:view?id="+sell_no;
	}
	
	
	@Autowired
	private UserService userService;
	
	@PreAuthorize("isAnonymous()")
	@GetMapping("/signup")
	public String signup(UserCreateForm form) {
		return "login/signup_form";
	}
	
	@PreAuthorize("isAnonymous()")
	@PostMapping("/signup")
	public String signup(@Valid UserCreateForm form, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			return "login/signup_form";
		}
		
		if(!form.getPassword1().equals(form.getPassword2())) {
			bindingResult.rejectValue("password2", "passwordInCorrect","2개의 패스워드가 일치하지 않습니다.");
			return "login/signup_form";
		}
		
		try {
			userService.create(form.getUsername(), form.getEmail(), form.getPassword1());
		} catch (DataIntegrityViolationException e) {
			// TODO: handle exception
			e.printStackTrace();
			bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
			return "login/signup_form";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			bindingResult.reject("signupFailed", e.getMessage());
			return "login/signup_form";
		}
		
		return "redirect:/signup";
	}
	
	@PreAuthorize("isAnonymous()")
	@GetMapping("/login")
	public String login() {
		logger.debug("##########################");
		return "/login/login_form";
	}
}
