package com.user_service.Controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.user_service.Services;
import com.user_service.Model.CartModel;
import com.user_service.Model.CatalogModel;
import com.user_service.Model.UserModel;
import com.user_service.Repository.UserRepository;
import com.user_service.Util.ApisEndPoint;
import com.user_service.Util.Authentication;
import com.user_service.Util.MsgResponses;
import com.user_service.Util.Util;

@RestController
public class UserController {

	@Autowired
	private UserRepository repo;
	@Autowired
	private Services userService;
	@Autowired
	private RestTemplate restTemplate;

	@Value("${cart.service.url}")
	private String urlCart;

	@Value("${catalog.service.url}")
	private String urlCatalog;

	@SuppressWarnings("unchecked")
	@CrossOrigin
	@GetMapping("/getmycart")
	public Map<String, Object> getMyCart(@RequestParam(required = true) Long userId) {
		return restTemplate.getForObject(urlCart + "/mycart?userId=" + userId, Map.class);
	}

	@SuppressWarnings("unchecked")
	@CrossOrigin
	@DeleteMapping("/deletecartitem")
	public Map<String, Object> deleteMyCartItem(@RequestParam(required = true) long id) {
		Map<String, Object> data = new HashMap<>();
		try {
			restTemplate.delete(urlCart + "/deletecartitem?id=" + id, Map.class);
		} catch (Exception e) {
			data.put(MsgResponses.RESULT, MsgResponses.FAILED);
			data.put(MsgResponses.MSG, MsgResponses.INTERNALSERVERERROR);
			return data;
		}
		data.put(MsgResponses.RESULT, MsgResponses.SUCCESS);
		data.put(MsgResponses.MSG, "Cart item deleted successfully");
		return data;
	}

	@SuppressWarnings("unchecked")
	@CrossOrigin
	@GetMapping("/allproduct")
	public Map<String, Object> getAllProduct() {
		return restTemplate.getForObject(urlCatalog + "/getallproduct", Map.class);
	}

	@SuppressWarnings("unchecked")
	@CrossOrigin
	@GetMapping("/productdetails")
	public Map<String, Object> getAllProduct(@RequestParam(value = "productId") long productId) {
		return restTemplate.getForObject(urlCatalog + "/productdetails?" + productId, Map.class);
	}

	@SuppressWarnings("unchecked")
	@CrossOrigin
	@DeleteMapping("/deleteproduct")
	public Map<String, Object> deleteProduct(@RequestParam(value = "productId") long productId) {
		Map<String, Object> data = new HashMap<>();

		try {
			restTemplate.delete(urlCatalog + "/deleteproduct?productId=" + productId);
		} catch (Exception e) {
			data.put(MsgResponses.RESULT, MsgResponses.FAILED);
			data.put(MsgResponses.MSG, MsgResponses.INTERNALSERVERERROR);
			return data;
		}
		data.put(MsgResponses.RESULT, MsgResponses.SUCCESS);
		data.put(MsgResponses.MSG, "Product deleted successfully");
		return data;
	}

	@SuppressWarnings("unchecked")
	@CrossOrigin
	@PostMapping("/addproduct")
	public Map<String, Object> addNewProduct(@RequestBody CatalogModel catalog) {
		return restTemplate.postForObject(urlCatalog + "/add", catalog, Map.class);
	}

	@SuppressWarnings("unchecked")
	@CrossOrigin
	@Validated
	@PostMapping(path = "/addtocart")
	public Map<String, Object> deleteProduct(@RequestBody CartModel cart) {
		return restTemplate.postForObject(urlCart + "/add", cart, Map.class);
	}

	@CrossOrigin
	@Validated
	@PostMapping(path = ApisEndPoint.REGISTER)
	public Map<String, Object> saveCustomer(@RequestBody UserModel customer)

	{
		Map<String, Object> data = new HashMap<>();

		String Name = null;
		String email = null;
		String password = null;
		try {
			Name = customer.getName();
			email = customer.getEmail();
			password = customer.getPassword();
		} catch (Exception e) {
			data.put(MsgResponses.RESULT, MsgResponses.FAILED);
			data.put(MsgResponses.MSG, MsgResponses.INVALIDPARAMETERS);
			return data;
		}
		if (Name == null || Name.isEmpty()) {

			data.put(MsgResponses.RESULT, MsgResponses.FAILED);
			data.put(MsgResponses.MSG, "Name can't be empty.");
			return data;
		}
		if (email == null || email.isEmpty()) {

			data.put(MsgResponses.RESULT, MsgResponses.FAILED);
			data.put(MsgResponses.MSG, " Email can't be empty");
			return data;
		}

		if (password == null || password.isEmpty()) {
			data.put(MsgResponses.RESULT, MsgResponses.FAILED);
			data.put(MsgResponses.MSG, "Password can't be empty");
			return data;
		}

		if (!Util.checkEmail(email)) {
			data.put(MsgResponses.RESULT, MsgResponses.FAILED);
			data.put(MsgResponses.MSG, "invalid email address.");
			return data;
		}

		int countEmail = 0;

		try {
			countEmail = repo.countByEmail(email);
		} catch (Exception e) {
			System.out.println("exception caught due to " + e);
			data.put(MsgResponses.RESULT, MsgResponses.FAILED);
			data.put(MsgResponses.MSG, "Exceptoin cought in countEmail");
			return data;
		}

		if (countEmail != 0) {
			data.put(MsgResponses.RESULT, MsgResponses.FAILED);
			data.put(MsgResponses.MSG, "Email " + email + " Already registerd with another user!");
			return data;
		}

		try {
			return userService.addCustomer(customer);
		} catch (Exception e) {
			data.put(MsgResponses.RESULT, MsgResponses.FAILED);
			data.put(MsgResponses.MSG, "Failed to sign up");
			return data;
		}

	}

	@CrossOrigin
	@Validated
	@PostMapping(path = ApisEndPoint.LOGIN)
	public Map<String, Object> loginCustomer(@RequestBody UserModel login) {
		Map<String, Object> data = new HashMap<>();
		Authentication auth = new Authentication();

		String email = login.getEmail();
		String password = login.getPassword();

		if (email == null || email.isEmpty()) {

			data.put(MsgResponses.RESULT, MsgResponses.FAILED);
			data.put(MsgResponses.MSG, "Email can't be empty");
			return data;
		}

		if (password == null || password.isEmpty()) {
			data.put(MsgResponses.RESULT, MsgResponses.FAILED);
			data.put(MsgResponses.MSG, "Password can't be empty");
			return data;
		}
		int countEmail = 0;
		try {
			countEmail = repo.countByEmail(email);
		} catch (Exception e) {
			data.put(MsgResponses.RESULT, MsgResponses.FAILED);
			data.put(MsgResponses.MSG, "Exceptoin cought in countEmail");
			return data;
		}

		if (countEmail == 0) {
			data.put(MsgResponses.RESULT, MsgResponses.FAILED);
			data.put(MsgResponses.MSG, "Account doesn't exist with " + email);
			return data;
		}

		String salt = repo.findSaltByEmail(login.getEmail());
		String completePassword = login.getPassword() + salt;

		try {

			completePassword = Util.byteArrayToHexString(Util.computeHash(completePassword));

		} catch (Exception e1) {
			System.out.println("Exception is............" + e1);
			data.put(MsgResponses.RESULT, MsgResponses.FAILED);
			data.put(MsgResponses.MSG, "Exception caught during complete password!");
			return data;
		}

		UserModel loginDetails = new UserModel();
		try {

			loginDetails = (UserModel) repo.findByEmailAndPassword(login.getEmail(), completePassword);

			if (loginDetails != null) {

				data.put("userDetails", loginDetails);
				data.put("token", auth.getToken());

				data.put(MsgResponses.RESULT, MsgResponses.SUCCESS);
				return data;

			} else {
				data.put(MsgResponses.RESULT, MsgResponses.FAILED);
				data.put(MsgResponses.MSG, "invalid password");
				return data;
			}
		}

		catch (Exception e) {
			System.out.println("exception caught due to " + e);
			data.put(MsgResponses.RESULT, MsgResponses.FAILED);
			data.put(MsgResponses.MSG, "Exception caught during get login details!");
			return data;
		}

	}

}
