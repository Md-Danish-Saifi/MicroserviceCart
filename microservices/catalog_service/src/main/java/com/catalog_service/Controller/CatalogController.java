package com.catalog_service.Controller;

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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.catalog_service.Model.CatalogModel;
import com.catalog_service.Repository.CatalogRepository;
import com.catalog_service.Util.MsgResponses;

@RestController
public class CatalogController {

	@Autowired
	CatalogRepository repo;

	@RequestMapping("/")
	public Map<String, Object> welcome() {
		Map<String, Object> data = new HashMap<>();
		data.put(MsgResponses.RESULT, MsgResponses.SUCCESS);
		data.put(MsgResponses.MSG, "You Have authorize successfully");
		return data;

	}

	@CrossOrigin
	@Validated
	@PostMapping(path = "/add")
	public Map<String, Object> addProduct(@RequestBody CatalogModel catalog)

	{
		Map<String, Object> data = new HashMap<>();

		try {
			repo.save(catalog);
		} catch (Exception e) {
			data.put(MsgResponses.RESULT, MsgResponses.FAILED);
			data.put(MsgResponses.MSG, "Failed to sign up");
			return data;
		}
		data.put(MsgResponses.RESULT, MsgResponses.SUCCESS);
		data.put(MsgResponses.MSG, "Product added");
		return data;

	}

	@CrossOrigin
	@GetMapping(path = "/getallproduct")
	public Map<String, Object> getAllProduct() {
		Map<String, Object> data = new HashMap<>();
		List<CatalogModel> lst = new ArrayList<CatalogModel>();
		try {
			lst = repo.findAll();
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
	@GetMapping(path = "/productdetails")
	public Map<String, Object> getProduct(@RequestParam(value = "productId") long productId) {
		Map<String, Object> data = new HashMap<>();
		Optional<CatalogModel> prod;
		try {
			prod = repo.findById(productId);
		} catch (Exception e) {
			data.put(MsgResponses.RESULT, MsgResponses.FAILED);
			data.put(MsgResponses.MSG, MsgResponses.INTERNALSERVERERROR);
			return data;
		}
		data.put(MsgResponses.RESULT, MsgResponses.SUCCESS);
		data.put("productDetails", prod);
		return data;

	}

	@CrossOrigin
	@Validated
	@DeleteMapping(path = "/deleteproduct")
	public Map<String, Object> deleteProduct(@RequestParam(value = "productId") long productId) {
		Map<String, Object> data = new HashMap<>();
		Optional<CatalogModel> prod;
		try {
			prod = repo.findById(productId);
		} catch (Exception e) {
			data.put(MsgResponses.RESULT, MsgResponses.FAILED);
			data.put(MsgResponses.MSG, MsgResponses.INTERNALSERVERERROR);
			return data;
		}
		if (prod != null) {
			repo.deleteById(productId);
			data.put(MsgResponses.RESULT, MsgResponses.SUCCESS);
			data.put(MsgResponses.MSG, "Product delete successfully");
			return data;
		} else {
			data.put(MsgResponses.RESULT, MsgResponses.FAILED);
			data.put(MsgResponses.MSG, "Invalid product details");
			return data;
		}

	}

}
