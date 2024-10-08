package com.itwillbs.persistence;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;

import org.springframework.stereotype.Repository;

import com.itwillbs.domain.MemberVO;

/**
 * 
 * MemberDAO 동작을 수행
 *
 */
// @Repository : 스프링이 해당클래스를 DAO객체 (Bean)로 인식
// 				root-context.xml파일에서 해당객체를 사용하도록 설정.
// 
@Repository
public class MemberDAOImpl implements MemberDAO{
	// 파일에 S라고 붙어 있으면 spring이 관리하는대상을 나타냄.
	
	// 공통변수, 디비 연결, 자원해제
	// 디비 연결객체(SqlSessionFactory)가 필요함 => 의존관계 주입
//	@Inject
//	private SqlSessionFactory sqlFactory;
	
	@Inject
	private SqlSession sqlSession; // 자동으로 연결, 자원해제,SQL실행, mybatis...
	
	// Mapper namespace 정보 저장
	private static final String NAMESPACE = "com.itwillbs.mapper.MemberMapper";
	
	
	@Override
	public String getTime() {
		System.out.println(" DAO : getTime() 실행! ");
		// 1,2 디비 연결
		// SqlSession sqlSession = sqlFactory.openSession(); -> 생략을 하는게 SqlSession이라는 객체를 주입받아서 생략함.
		// 3 SQL 구문 & pstmt 객체
		// 4 SQL 실행
		//sqlFactory.selectOne(SQL구문);
		//sqlFactory.selectOne(SQL구문,전달정보);
		String result = sqlSession.selectOne("com.itwillbs.mapper.MemberMapper.getTime");
		// => Mapper의 sql 구문 id를 호출
		// = sqlSession.selectOne("select now()");
		// => 직접적으로 SQL구문 호출 X
		System.out.println("결과 : "+ result);
		
		
		// 5. 데이터 처리
		return result;
	}
	
	
	@Override
	public void insertMember(MemberVO vo) {
		System.out.println(" DAO : 회원가입 동작 실행! ");
				
		// 1.2 디비연결  => 생략 SqlSession객체 수행
		// 3. SQL 구문(Mapper 생성) & pstmt 객체 (mybatis 관리)
		// 4. SQL 실행
		// [com.itwillbs.mapper.MemberMapper.insertMember]
		int result = sqlSession.insert( NAMESPACE+".insertMember", vo);
		System.out.println(" DAO : result : "+ result );
		System.out.println(" DAO : 회원가입 완료! ");
	}
	
	@Override
	public MemberVO loginMember(MemberVO vo) {
		System.out.println(" DAO : loginMember(MemberVO vo) 실행 ");
		
		// SQL 구문을 mapper에 생성
		System.out.println(" DAO : mapper SQL 생성완료. ");
		// SQL 구문 실행.
		MemberVO resultVO = sqlSession.selectOne( NAMESPACE+".loginMember", vo);
		
		System.out.println(" DAO : "+resultVO);
		
		return resultVO;
	}
	
	@Override
	public MemberVO loginMember(String userid, String userpw) {
		System.out.println(" DAO : loginMember(String userid, String userpw) 실행");
		// SQL 구문을 mapper에 생성
		// SQL 구문 실행.
		// MemberVO resultVO = sqlSession.selectOne( NAMESPACE+".loginMember", userid, userpw); X
		// MemberVO vo = new MemberVO();
		// vo.setUserid(userid);
		// vo.setUserpw(userpw);
		//
		// MemberVO resultVO = sqlSession.selectOne( NAMESPACE+".loginMember", vo);
		// => 전달 받은 정보를 하나의 공통객체에 저장 => 전달할때 객체로 전달
		
		// * userid(회원가입), userpw(게시판)는 하나의 객체(MemberVO)에 
		//  => 저장이 불가능하다고 가정. Ex) join구문 실행할때
		Map<String , Object> paramMap = new HashMap<String, Object>();
		// paramMap.put("mapper에서 호출하는 이름", 전달되는 값);
		paramMap.put("userid", userid);
		paramMap.put("userpw", userpw);
		
		MemberVO resultVO = sqlSession.selectOne( NAMESPACE+".loginMember", paramMap);
		System.out.println(" DAO : "+resultVO);
		
		return resultVO;
	}
	
	@Override
//	public MemberVO getMember(MemberVO vo) {
	public MemberVO getMember(String member_id) {
		System.out.println(" DAO : getMember(String userid) ");
		
		// SQL 구문을 mapper에 생성
		// System.out.println(" DAO : mapper SQL 생성완료. ");
		// MemberVO resultVO = sqlSession.selectOne(NAMESPACE+".getMember", vo);
		// System.out.println(" DAO : "+resultVO);
		
		// return resultVO;
		return sqlSession.selectOne(NAMESPACE+".getMember", member_id);
	}
	
	@Override
	public MemberVO getMemberEmail(String email) {
		System.out.println(" DAO : getMemberEmail(String email) ");
		
		return sqlSession.selectOne(NAMESPACE+".getMemberEmail", email);

	}
	
	@Override
	public int updateMember(MemberVO uvo) {
		System.out.println(" DAO : updateMember(MemberVO uvo) ");
		System.out.println(uvo + "uvo");
		// mapper - sql 작성
		// sqlSession - sql 실행 (결과에 따른 정수데이터를 리턴)
		
		int result = sqlSession.update(NAMESPACE+".updateMember", uvo);
		
		return result;
	}
	
	@Override
	public Integer deleteMember(MemberVO dvo) {
		System.out.println(" DAO : deleteMember(MemberVO dvo) ");		
		return sqlSession.update(NAMESPACE+".deleteMember", dvo);
	}
	
	@Override
	public List<MemberVO> getMemberList() {
		System.out.println(" DAO : getMemberList() ");	
		
		return sqlSession.selectList(NAMESPACE+".getMemberList");
	}
	
	@Override
	public MemberVO getMemberTel(String tel) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(NAMESPACE+".getMemberTel");
	}
}
