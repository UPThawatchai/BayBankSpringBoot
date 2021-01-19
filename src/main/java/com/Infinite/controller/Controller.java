package com.Infinite.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Infinite.repository.StoreRepository;
import com.Infinite.request.Request;
import com.Infinite.response.MemberType;
import com.Infinite.response.Response;
import com.Infinite.utils.Utils;
import com.nimbusds.oauth2.sdk.util.StringUtils;

@CrossOrigin("*")
@Service
@RestController
public class Controller {
	
	@Autowired
	private final StoreRepository saveRepo;
	
	private Response res = null;
	
	public Controller(StoreRepository saveRepository) {
		this.saveRepo = saveRepository;
	}
	
	@GetMapping
    public String hello() {
        return "Hello World!";
    }
	
    @GetMapping("/findAll")
    public List<Response> fineAllUser() throws Exception {
    	List<Response> res = new ArrayList<Response>(); 
    	List<Request> req = saveRepo.findAllUser();
    	if (null != req) {
    		//code
    	}
        return res;
    }
	
    @PostMapping("/register")
    public Response BayBankTest(@RequestBody Request request) throws Exception  {
    	System.out.println("BayBankTest ::: START");
    	Request req = new Request();
    	Response response = new Response();
    	MemberType mType = new MemberType();
    	if (null != request) {
    		res = new Response();
    		if (validateData(request)) {
    			req = prepareData(request);
    			response = saveToDb(req);
    			if (null != response) {
//    				mType.setMemberFname(response.get);
    			}
    		} else {
    			return res;
    		}
    	}
    	System.out.println("BayBankTest ::: END");
        return response;
    }
    
    
    public boolean validateData(Request request) {
    	boolean p = true;
    	res = new Response();
    	if (request.getSalary() < Utils.FIFTEEN_THOUNSAND) {
    		res.setRespnoseCode(Utils.E402);
    		res.setResponseDesc(Utils.EJECT_SALARY_E402);
    		p = false;
    	} else if (StringUtils.isBlank(request.getUsername())) {
    		res.setRespnoseCode(Utils.E401);
    		res.setResponseDesc(Utils.INPUT_CHECK_E401);
    		p = false;
    	} else if (StringUtils.isBlank(request.getPassword())) {
    		res.setRespnoseCode(Utils.E401);
    		res.setResponseDesc(Utils.INPUT_CHECK_E401);
    		p = false;
    	} else if (StringUtils.isBlank(request.getPhone()) && request.getPhone().length() != 10) {
    		res.setRespnoseCode(Utils.E401);
    		res.setResponseDesc(Utils.INPUT_CHECK_E401);
    		p = false;
    	}
    	res.setResponseDate(new Date());
        return p;
    }
    
    public Request prepareData(Request req) throws Exception {
    	Request request = new Request();
    	SimpleDateFormat sd1 = new SimpleDateFormat("yyyyMMdd");
    	Date date = new Date();
    	String regex = "^(.+)@(.+)$";
    	Pattern pattern = Pattern.compile(regex);
    	
    	request.setUsername(req.getUsername());
    	request.setPassword(req.getPassword());
    	request.setAddress(req.getAddress());
    	if (!StringUtils.isBlank(req.getPhone())) {
    		String d = sd1.format(date.getTime());
    		String last4Digit = req.getPhone().substring(6, req.getPhone().length());
    		request.setId(Long.valueOf(d.concat(last4Digit)));
    	}
    	if (!StringUtils.isBlank(req.getEmail())) {
    		Matcher matcher = pattern.matcher(req.getEmail());
    		boolean matched = matcher.matches();
    		if (matched) {
    			request.setEmail(req.getEmail());
    		}
    	}
    	request.setFirstName(req.getFirstName());
    	request.setLastName(req.getLastName());
    	request.setDateofbirth(req.getDateofbirth());
    	if (req.getSalary() != 0) {
    		int salary = req.getSalary();
    		if (salary > Utils.FIFTY_THOUNSAND) {
    			request.setMemberType("Platinum");
    		} else if (salary >= Utils.THIRTY_THOUNSAND && salary <= Utils.FIFTY_THOUNSAND) {
    			request.setMemberType("Gold");
    		} else if (salary < Utils.FIFTEEN_THOUNSAND) {
    			request.setMemberType("Silver");
    		}
    		request.setSalary(req.getSalary());
    	}
    	request.setCreateDate(req.getCreateDate());
    	request.setCreatedBy("AutoSys");
    	request.setUpdateDate(req.getUpdateDate());
    	request.setUpdateBy("AutoSys");
    	return request;
    }
    
    public Response saveToDb(Request request) throws Exception  {
    	Response rs = new Response();
    	if (saveRepo.saveToDb(request)) {
    		//code
    	}
    	return rs;
    }
    
//    public static void main(String args[]) {
//    	System.out.println(Integer.parseInt("85,000"));
//    }

}
