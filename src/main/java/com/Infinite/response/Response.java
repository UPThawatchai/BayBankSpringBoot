package com.Infinite.response;

import java.util.Date;
import java.util.List;

import com.Infinite.request.Request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Response {
	
	private int respnoseCode;
	private String responseDesc;
	private Date responseDate;
	private String errorCause;
	private MemberType memberType;
	private Request req;

}
