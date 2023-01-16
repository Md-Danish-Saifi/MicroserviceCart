package com.user_service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user_service.Model.UserModel;
import com.user_service.Repository.UserRepository;
import com.user_service.Util.MsgResponses;
import com.user_service.Util.Util;

@Service
public class Services {
	
	@Autowired
	UserRepository repo;
	public Map<String, Object> addCustomer(UserModel user) throws Exception {

		Map<String, Object> data = new HashMap<String, Object>();


		String salt = Util.generateRandomString(10);
		String completePassword = user.getPassword() + salt;
		completePassword = Util.byteArrayToHexString(Util.computeHash(completePassword));
	
		user.setSalt(salt);
		user.setRole("user");
		user.setPassword(completePassword);
		try {
			repo.save(user);

		} catch (Exception e) {
			data.put(MsgResponses.RESULT, MsgResponses.FAILED);
			data.put(MsgResponses.MSG, "Failed to sign up");
			return data;
		}

		data.put(MsgResponses.RESULT, MsgResponses.SUCCESS);
		data.put(MsgResponses.MSG, "You have signed up successfully");
		data.put("customerId", user.getId());
		return data;

	}

}
