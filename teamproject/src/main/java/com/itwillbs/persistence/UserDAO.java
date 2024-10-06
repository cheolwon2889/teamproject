package com.itwillbs.persistence;

import com.itwillbs.domain.UserVO;

public interface UserDAO {
	public UserVO getBySns(UserVO user);
}
