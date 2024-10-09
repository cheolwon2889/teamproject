package com.itwillbs.persistence;

import com.itwillbs.domain.MemberVO;

public interface UserDAO {
	public MemberVO getBySns(String tell);
}
