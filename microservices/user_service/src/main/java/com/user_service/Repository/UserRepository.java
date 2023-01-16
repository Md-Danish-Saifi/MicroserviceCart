package com.user_service.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.user_service.Model.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

	int countByEmail(String email);

	@Query(value = "SELECT salt FROM user WHERE email = ?1 ",nativeQuery = true)
	String findSaltByEmail(String email);

	UserModel findByEmailAndPassword(String email, String completePassword);

}
