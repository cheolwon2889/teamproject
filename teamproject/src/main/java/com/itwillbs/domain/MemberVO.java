package com.itwillbs.domain;

import java.sql.Timestamp;

import lombok.Data;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.ToString;


/**
 * VO (Value Object) : 데이터 저장 객체 (값을 저장하는 동작 이외의 동작O)
 *	DTO (Data Transfer Object) : 데이터 전송 객체 (값을 저장하는 동작 이외의 동작X)
 *
 * tbl_member 테이블정보를 저장하는 객체
 *
 */
// @Data => set/get메서드 자동생성.
//@Setter
//@Getter
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
@Data
public class MemberVO {

	// private String Uid;
	// private String uId; // getUId() x
	// private String userId; // getUserId() o 웬만하면 x
	
	private String member_id;
	private String member_pw;
	private String member_name;
	private String member_email;
	private String member_tel;
	// 
	private String member_birth;
	// 
	private String department_id;
	private String employee_rank;
	private Timestamp create_date;
	private Timestamp update_date;
	private String permission_id;
	private String member_state;
	private String is_company;
	private String approval_status;
	
	
	
}
