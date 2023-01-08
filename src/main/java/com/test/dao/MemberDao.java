package com.test.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.test.dto.MemberDto;

public class MemberDao {
	
	//DataSource
	private DataSource ds;
	//싱글톤패턴
	private static MemberDao instance;
	public static MemberDao getInstance() {
		if(instance==null) {
			instance = new MemberDao();
		}
		return instance;
	}
	private MemberDao() {
		//DB관련 코드 적용(DataSource(DBCP) / HikariDB / Mabatis / JPA ...)
		try {
			//1. JNDI  ==> https://opentutorials.org/module/3569/21223 이 사이트 들어가서 설명 읽어보기.
			InitialContext ic= new InitialContext();
			//2. DataSource 자원 찾기
			ds = (DataSource) ic.lookup("java:comp/env/jdbc/mysql");
			//										  ㅁ
			System.out.println("[DAO] DS : " + ds);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	//INSERT
	public int Insert(MemberDto dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			conn = ds.getConnection();
//			System.out.println("[DAO] CONN : " + conn);
			pstmt = conn.prepareStatement("insert into tbl_member values(?,?,?,?,?,?,?)");
			pstmt.setString(1, dto.getEmail());
			pstmt.setString(2, dto.getPwd());
			pstmt.setString(3, dto.getPhone());
			pstmt.setString(4, dto.getZipcode());
			pstmt.setString(5, dto.getAddr1());
			pstmt.setString(6, dto.getAddr2());
			pstmt.setString(7, "0");	//Grade
			
			result = pstmt.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {pstmt.close();}catch(Exception e) {}
			try {conn.close();}catch(Exception e) {}
		}
		return result;
	}
}
