package com.Infinite.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class JwtRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -891684202452588716L;
	
	private String username;
	private String password;
	
	public JwtRequest() {
		
	}
	
	
	public JwtRequest(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	
	

}
