package com.itwillbs.persistence;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.itwillbs.domain.UserVO;


@Repository
public class UserDAOImpl implements UserDAO {
	
	@Inject
	private SqlSession sqlSession; // 자동으로 연결, 자원해제,SQL실행, mybatis...
	
	// Mapper namespace 정보 저장
	private static final String NAMESPACE = "com.itwillbs.mapper.UserMapper";
	

	@Override
	public UserVO getBySns(UserVO user) {
//		System.out.println(" DAO : getMember(String userid) ");
		
		// SQL 구문을 mapper에 생성
		// System.out.println(" DAO : mapper SQL 생성완료. ");
		// MemberVO resultVO = sqlSession.selectOne(NAMESPACE+".getMember", vo);
		// System.out.println(" DAO : "+resultVO);
		
		// return resultVO;
//		return sqlSession.selectOne(NAMESPACE+".getMember", userid);
		
		
		// member테이블에 sns정보가 있는지 확인하는 쿼리 짜면 될듯
		return null;
	}

}
