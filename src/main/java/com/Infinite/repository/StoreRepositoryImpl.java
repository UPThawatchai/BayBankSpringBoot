package com.Infinite.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.Infinite.request.Request;


@Repository
public class StoreRepositoryImpl implements StoreRepository {
	
	@Autowired
	private MongoTemplate mongoTemplate;

	
	@Override
	public List<Request> findAllUser() throws Exception {
	  return mongoTemplate.findAll(Request.class);
	}


	@Override
	public boolean saveToDb(Request request) throws Exception {
		Request req = mongoTemplate.save(request);
		return null != req ? true : false;
	}

	


}
