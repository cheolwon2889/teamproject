package com.itwillbs.web;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.itwillbs.domain.MemberVO;
import com.itwillbs.persistence.MemberDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class MeberDAOTest {
	// MemberDAO객체의 메서드 호출
	
	@Inject
	private MemberDAO mdao;
	
	// @Test
	public void getBean() {
		System.out.println("mdao : "+ mdao);
	}	
	
	// @Test
	public void getTime() {
		mdao.getTime();
	}
	
	// @Test
	public void 회원가입테스트() {
		System.out.println(" TEST : 회원가입테스트() 시작");
		MemberVO vo = new MemberVO();
		
		vo.setUserid("itwill5");
		vo.setUsername("학생5");
		vo.setUserpw("12345");
		vo.setUseremail("학생5@itwill.com");
		
		// mdao.insertMember(vo);
		
		System.out.println(" TEST : 회원가입테스트() 끝");
	}
	
//	@Test
	public void 로그인테스트() {
		System.out.println(" TEST : 로그인테스트() 시작 ");
		
		// MemberVO vo = new MemberVO();
		// vo.setUserid("itwill5");
		// vo.setUserpw("12345");
		// MemberVO resultVO =	mdao.loginMember(vo);
		
		MemberVO resultVO =	mdao.loginMember("admin", "1234");
		System.out.println(" TEST : " + resultVO);
		
		if(resultVO != null) {
			System.out.println(" TEST : 로그인 성공! ");
		}else {
			System.out.println(" TEST : 로그인 실패! ");
		}
		
		System.out.println(" TEST : 로그인테스트() 끝 ");
	}
	
	// @Test
	public void 사용자정보조회() {
		System.out.println(" TEST : 사용자 정보 조회() 시작 ");
		
//		MemberVO vo = new MemberVO();
//		vo.setUserid("itwill5");
//
//		
//		MemberVO resultVO = mdao.getMember(vo);
//		System.out.println(" TEST : " + resultVO);
//		
//		if(resultVO != null) {
//			System.out.println(" TEST : 회원정보 조회 성공! ");
//		}else {
//			System.out.println(" TEST : 회원정보 조회 실패! ");
//		}
		
		MemberVO resultVO = mdao.getMember("itwill5");
		if(resultVO != null) {
			System.out.println(" TEST : 회원정보 조회 성공! ");
		}else {
			System.out.println(" TEST : 회원정보 조회 실패! ");
		}
		System.out.println(" TEST : 사용자 정보 조회() 끝 ");
	}
	
	//@Test
	public void 회원정보수정() {
		System.out.println(" TEST : 회원정보수정 시작");
		MemberVO uvo = new MemberVO();
		uvo.setUserid("itwill5");
		uvo.setUserpw("12345");
		uvo.setUseremail("itwill5@naver.com");
		
		int result = mdao.updateMember(uvo);
		
		if(result == 1) {
			System.out.println(" 회원정보 수정 완료! ");
		}else {
			System.out.println(" 회원정보 수정 실패! ");			
		}
		
		System.out.println(" TEST : result : " +result);
		
		System.out.println(" TEST : 회원정보수정 끝");
	}
	
	// @Test
	public void 회원정보삭제() {
		
		System.out.println(" TEST : 회원정보 삭제 시작 ");
		MemberVO dvo = new MemberVO();
		dvo.setUserid("itwill3");
		dvo.setUserpw("12345");
		int result = mdao.deleteMember(dvo);
		
		if(result == 1) {
			System.out.println(" 회원정보 삭제 완료! ");
		}else{
			System.out.println(" 회원정보 삭제 실패! ");			
		}
		
		System.out.println(" TEST : 회원정보 삭제 끝 ");
	}
	
	@Test
	public void 회원목록_리스트() {
		System.out.println(" TEST : 회원목록 조회 시작 ");
		
		List<MemberVO> memberList = mdao.getMemberList();
		
		for (MemberVO memberVO : memberList) {
			System.out.println("memberVO : "+ memberVO);
		}
		
		System.out.println(" TEST : 회원목록 조회 시작 ");
	}
	
}
