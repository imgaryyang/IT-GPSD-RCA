package com.gps.repository;

import com.gps.vo.helper.UserVo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface UserRepository extends
		CrudRepository<UserVo, Serializable> {

	UserVo findByUserNameAndPassword(String userName, String password);
	

}
