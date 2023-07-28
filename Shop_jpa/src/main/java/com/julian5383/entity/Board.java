package com.julian5383.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Data
@Table(name="sell")
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int sell_no;
	private String title;
	private String content;
	private String photo;
	private String photo_url;
	private int price;
	private String buyornot;
	private String finornot;
	
	@Column(name="write_id")
	private String writer;
	
	@Column(name="view", columnDefinition = "int default 0")
	private int view;
	
	@Column(name="write_date",updatable = false)
	@CreationTimestamp
	private Timestamp writedate;
	
	@Column(name="update_date",insertable = false)
	@UpdateTimestamp
	private Timestamp updatedate;

}
