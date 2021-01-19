package com.Infinite.request;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Request {
	
	private long id;
	private String username;
	private String password;
	private String address;
	private String phone;
	private String email;
	private String firstName;
	private String lastName;
	private String memberType;
	private Date dateofbirth;
	private int salary;
	private String createdBy;
	private Date createDate;
	private Date updateDate;
	private String updateBy;
	
}
