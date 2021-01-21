package com.Infinite.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.Infinite.model.JwtRequest;
import com.Infinite.model.JwtResponse;
import com.Infinite.model.Request;
import com.Infinite.model.Response;
import com.Infinite.repository.StoreRepository;
import com.Infinite.services.JwtUserDetailsService;
import com.Infinite.utils.JwtTokenUtil;
import com.Infinite.utils.Utils;

@CrossOrigin("*")
@Service
@RestController
public class Controller {

	@InjectMocks
	@Autowired
	private final StoreRepository saveRepo;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Mock
	private Response res = null;

	public Controller(StoreRepository saveRepo, AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil,
			JwtUserDetailsService userDetailsService, Response res) {
		super();
		this.saveRepo = saveRepo;
		this.authenticationManager = authenticationManager;
		this.jwtTokenUtil = jwtTokenUtil;
		this.userDetailsService = userDetailsService;
		this.res = res;
	}

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	@GetMapping("/findAllTest")
	public void getAllEmployeesTest() throws Exception {
		
		List<Request> req = null;

		when(saveRepo.findAllUser()).thenReturn(req);

		// test
		List<Request> empList = saveRepo.findAllUser();

		assertEquals(2, empList.size());
		verify(res, times(1));
	}

	@RequestMapping({ "/hello" })
	public String hello() {
		return "Hello World!";
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		System.out.println("createAuthenticationToken :: Start");
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		System.out.println("Username ::: " + authenticationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		System.out.println("Bearar ::: " + token);
		System.out.println("createAuthenticationToken :: End");
		return ResponseEntity.ok(new JwtResponse(token));
	}

	private void authenticate(String username, String password) throws Exception {
		System.out.println("authenticate :: Start");
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
		System.out.println("authenticate :: End");
	}

	@GetMapping("/findAll")
	public Response fineAllUser() throws Exception {
		List<Request> requestList = new ArrayList<Request>();
		Response response = null;
		Request request = null;
		List<Request> req = saveRepo.findAllUser();
		if (null != req) {
			for (Request re : req) {
				response = new Response();
				request = new Request();
				request.setId(re.getId());
				request.setUsername(re.getUsername());
				request.setPassword(re.getPassword());
				request.setAddress(re.getAddress());
				request.setPhone(re.getPhone());
				request.setEmail(re.getEmail());
				request.setFirstName(re.getFirstName());
				request.setLastName(re.getLastName());
				request.setMemberType(re.getMemberType());
				request.setSalary(re.getSalary());
				request.setCreateDate(re.getCreateDate());
				request.setCreatedBy(re.getCreatedBy());
				request.setUpdateDate(re.getUpdateDate());
				request.setUpdateBy(re.getUpdateBy());
				requestList.add(request);
				response.setRequestList(requestList);

			}
			response.setRespnoseCode(Utils.S101);
			response.setResponseDesc(Utils.SUCCESS);
			response.setResponseDate(new Date());
		}
		return response;
	}

	@PostMapping("/register")
	public Response BayBankTest(@RequestBody Request request) throws Exception {
		System.out.println("BayBankTest ::: START");
		Request req = new Request();
		Response response = new Response();
		if (null != request) {
			res = new Response();
			if (!validateData(request)) {
				req = prepareData(request);
				response = saveToDb(req);
				if (null != response) {
					List<Request> requestList = saveRepo.findAllUser();
					response.setRequestList(requestList);
					response.setRespnoseCode(Utils.S101);
					response.setResponseDesc(Utils.SUCCESS);
					response.setResponseDate(new Date());
				}
			} else {
				return res;
			}
		}
		System.out.println("BayBankTest ::: END");
		return response;
	}

	public boolean validateData(Request request) {
		res = new Response();
		res.setError(false);
		if (request.getSalary() < Utils.FIFTEEN_THOUNSAND) {
			res.setRespnoseCode(Utils.E402);
			res.setResponseDesc(Utils.EJECT_SALARY_E402);
			res.setError(true);
		} else if (null == request.getUsername() || request.getUsername().trim().isEmpty()) {
			res.setRespnoseCode(Utils.E401);
			res.setResponseDesc(Utils.INPUT_CHECK_E401);
			res.setError(true);
		} else if (null == request.getPassword() || request.getPassword().trim().isEmpty()) {
			res.setRespnoseCode(Utils.E401);
			res.setResponseDesc(Utils.INPUT_CHECK_E401);
			res.setError(true);
		} else if (null == request.getPhone() || request.getPhone().trim().isEmpty()
				|| request.getPhone().length() != 10) {
			res.setRespnoseCode(Utils.E401);
			res.setResponseDesc(Utils.INPUT_CHECK_E401);
			res.setError(true);
		}
		res.setResponseDate(new Date());
		return res.isError();
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
		if (null != req.getPhone() && !req.getPhone().trim().isEmpty()) {
			String d = sd1.format(date.getTime());
			String last4Digit = req.getPhone().substring(6, req.getPhone().length());
			request.setId(Long.valueOf(d.concat(last4Digit)));
			request.setPhone(req.getPhone());
		}
		if (null != req.getEmail() && !req.getEmail().trim().isEmpty()) {
			Matcher matcher = pattern.matcher(req.getEmail());
			boolean matched = matcher.matches();
			if (matched) {
				request.setEmail(req.getEmail());
			}
		}
		request.setFirstName(req.getFirstName());
		request.setLastName(req.getLastName());
		if (req.getSalary() != 0) {
			int salary = req.getSalary();
			if (salary > Utils.FIFTY_THOUNSAND) {
				request.setMemberType("Platinum");
			} else if (salary >= Utils.THIRTY_THOUNSAND && salary <= Utils.FIFTY_THOUNSAND) {
				request.setMemberType("Gold");
			} else if (salary >= Utils.FIFTEEN_THOUNSAND && salary < Utils.THIRTY_THOUNSAND) {
				request.setMemberType("Silver");
			}
			request.setSalary(req.getSalary());
		}
		request.setCreateDate(new Date());
		request.setCreatedBy("AutoSys");
		request.setUpdateDate(new Date());
		request.setUpdateBy("AutoSys");
		return request;
	}

	public Response saveToDb(Request request) throws Exception {
		Response rs = new Response();
		if (saveRepo.saveToDb(request)) {
			// code
		}
		return rs;
	}

}
