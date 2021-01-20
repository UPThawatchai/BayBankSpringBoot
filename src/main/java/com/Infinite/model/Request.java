package com.Infinite.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Component
public class Request  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4410938582974246348L;
	
	private long id;
	private String username;
	private String password;
	private String address;
	private String phone;
	private String email;
	private String firstName;
	private String lastName;
	private String memberType;
	private int salary;
	private String createdBy;
	private Date createDate;
	private Date updateDate;
	private String updateBy;
	
	
}
