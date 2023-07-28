package com.julian5383.entity;

import lombok.Getter;

@Getter
public enum UserRole {
	ADMIN("ROLE_ADMIN"), USER("ROLE_USER");

	private String value;

	UserRole(String value) {
		// TODO Auto-generated constructor stub
		this.value=value;
	}
}
