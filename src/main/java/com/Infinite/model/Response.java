package com.Infinite.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Component
public class Response implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1675981161009082486L;
	
	private int respnoseCode;
	private String responseDesc;
	private Date responseDate;
	private List<Request> requestList;
	private String errorCause;
	private boolean isError;

	
}
