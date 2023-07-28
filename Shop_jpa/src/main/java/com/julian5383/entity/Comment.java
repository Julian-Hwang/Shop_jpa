package com.julian5383.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="sell_comment")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="sell_no_sc")
	private int sell_no;
	
	@Column(name="write_id_sc")
	private String write_id;
	
	@Column(name="content_sc")
	private String content;
	
	@Column(name="write_date_sc",updatable = false)
	@CreationTimestamp
	private Timestamp write_date;
	
	@Column(name="update_date_sc",insertable = false)
	@UpdateTimestamp
	private Timestamp update_date;
}
