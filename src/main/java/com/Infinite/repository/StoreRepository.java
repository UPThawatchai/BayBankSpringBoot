package com.Infinite.repository;

import java.util.List;

import com.Infinite.model.Request;

public interface StoreRepository {

	public List<Request> findAllUser() throws Exception;
	
	public boolean saveToDb(Request request) throws Exception;

}
