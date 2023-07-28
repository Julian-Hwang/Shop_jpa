package com.julian5383.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.julian5383.entity.User;
import com.julian5383.entity.UserRole;
import com.julian5383.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

	@Autowired
	private UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<User> _user=this.repository.findByUsername(username);
		if (_user.isEmpty()) {
			throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
		}
		User user = _user.get();
		List<GrantedAuthority> authorities = new ArrayList<>();
		if ("admin".equals(username)) {
			authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
		} else {
			authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);
	}

}
