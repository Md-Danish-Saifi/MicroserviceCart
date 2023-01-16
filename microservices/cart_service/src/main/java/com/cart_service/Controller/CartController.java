package com.cart_service.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cart_service.Model.CartModel;
import com.cart_service.Repository.CartRepository;
import com.cart_service.Util.MsgResponses;


@RestController
public class CartController {

	@Autowired
	CartRepository repo;
	
	@RequestMapping("/")
	public Map<String, Object> welcome() {
		Map<String, Object> data = new HashMap<>();
		data.put(MsgResponses.RESULT, MsgResponses.SUCCESS);
		data.put(MsgResponses.MSG, "You Have authorize successfully");
		return data;

	}
	
	@GetMapping("/status")
	public List<String> status(){
		List<String> ls = new ArrayList<String>();
		ls.add("String 1");
		ls.add("String 4");
		ls.add("String 3");
		ls.add("String 2");
		return ls;
	}

	@CrossOrigin
	@Validated
	@PostMapping(path = "/add")
	public Map<String, Object> addToCart(@RequestBody CartModel catalog)

	{
		Map<String, Object> data = new HashMap<>();

		try {
			repo.save(catalog);
		} catch (Exception e) {
			data.put(MsgResponses.RESULT, MsgResponses.FAILED);
			data.put(MsgResponses.MSG, "Failed to add item to cart");
			return data;
		}
		data.put(MsgResponses.RESULT,MsgResponses.SUCCESS);
		data.put(MsgResponses.MSG,"Product added");
		return data;

	}

	@CrossOrigin
	@GetMapping(path = "/mycart")
	public Map<String, Object> getCartProduct(@RequestParam(value = "userId") long userId) {
		
		Map<String, Object> data = new HashMap<>();
		List<CartModel> lst = new ArrayList<CartModel>();
		try {
			lst = repo.findAllByUserId(userId);
		} catch (Exception e) {
			data.put(MsgResponses.RESULT, MsgResponses.FAILED);
			data.put(MsgResponses.MSG, MsgResponses.INTERNALSERVERERROR);
			return data;
		}
		data.put(MsgResponses.RESULT, MsgResponses.SUCCESS);
		data.put("products", lst);
		return data;

	}
	
	@CrossOrigin
	@Validated
	@DeleteMapping(path = "/deletecartitem")
	public Map<String, Object> deleteCartProduct(@RequestParam(name = "id") long id ) {
		Map<String, Object> data = new HashMap<>();
		System.out.println("=================================================");
		Optional<CartModel> prod;
		try {
			prod = repo.findById(id);
		} catch (Exception e) {
			data.put(MsgResponses.RESULT, MsgResponses.FAILED);
			data.put(MsgResponses.MSG, MsgResponses.INTERNALSERVERERROR);
			return data;
		}
		if(prod !=null) {
			repo.deleteById(id);
		}
		data.put(MsgResponses.RESULT, MsgResponses.SUCCESS);
		data.put(MsgResponses.MSG, "Item deleted successfully");
		return data;

	}
}
