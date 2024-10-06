package com.itwillbs.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.itwillbs.domain.UserVO;
import com.itwillbs.persistence.UserDAO;


@Service
public class UserServiceImpl implements UserService {
	
	@Inject
	private UserDAO dao;
	
	
	@Override
	public UserVO getBySns(UserVO user) {
		return dao.getBySns(user);
	}
}
