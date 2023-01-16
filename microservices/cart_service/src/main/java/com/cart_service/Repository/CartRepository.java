package com.cart_service.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cart_service.Model.CartModel;

@Repository
public interface CartRepository extends JpaRepository<CartModel, Long>{

	void deleteByUserId(long userId);

	List<CartModel> findAllByUserId(long userId);

}
