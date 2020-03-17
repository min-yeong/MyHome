package com.bit.myhome.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bit.myhome.vo.UserVO;

public class UsersDaoImpl implements UsersDao {
	private String dbuser = null;
	private String dbpass = null;
	
	public UsersDaoImpl(String dbuser, String dbpass) {
		this.dbuser = dbuser;
		this.dbpass = dbpass;
	}
	
	private Connection getConnection() throws SQLException{
		Connection conn = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String dburl = "jdbc:oracle:thin:@localhost:1521:xe";

			conn = DriverManager.getConnection(dburl, dbuser, dbpass);
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} 
		return conn;
	}

	@Override
	public List<UserVO> getList() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<UserVO> list = new ArrayList<>();
		
		try {
			conn = getConnection();
			String sql = "SELECT no, name, password, email, gender, create_at FROM users ORDER BY create_at DESC"; //가장마지막에등록된것부터 출력
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				String password = rs.getString(3);
				String email = rs.getString(4);
				String gender = rs.getString(5);
				Date createdAt = rs.getDate(6);
				
				UserVO vo = new UserVO(no, name, password, email, gender, createdAt);
				list.add(vo);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean insert(UserVO vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int insertedCount = 0;
		
		try {
			conn = getConnection();
			String sql = "INSERT INTO users (no, name, password, email, gender) VALUES (seq_users_pk.nextval, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);		
			pstmt.setString(1, vo.getName());		
			pstmt.setString(2, vo.getPassword());			
			pstmt.setString(3, vo.getEmail());
			pstmt.setString(4, vo.getGender());
			
			insertedCount = pstmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch(Exception e) {}			
		}
		return insertedCount == 1;
	}


	@Override
	public boolean update(UserVO vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int updatedCount = 0;
		
		try {
			conn = getConnection();
			String sql = "UPDATE users SET name=?, password=?, email=?, gender=? WHERE no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getEmail());
			pstmt.setString(4, vo.getGender());
			
			updatedCount = pstmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch(Exception e) {}
		}
		return 1 == updatedCount;
	}

	@Override
	public boolean delete(Long no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int deletedCount = 0;
		
		try {
			conn = getConnection();
			String sql = "DELETE FROM users WHERE no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, no);
			
			deletedCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch(Exception e) {}
		}
		return deletedCount == 1;
	}

	@Override
	public UserVO getUserByIdAndPassword(String Email, String Password) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UserVO user = null;
		
		try {
			conn = getConnection();
			String sql = "SELECT * FROM users WHERE email=? AND password=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, Email);
			pstmt.setString(2, Password);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				String password = rs.getString(3);
				String email = rs.getString(4);
				String gender = rs.getString(5);
				Date createdAt = rs.getDate(6);
				
				user = new UserVO(no, name, password, email, gender, createdAt);
			}
			
		} catch(SQLException e) {
			System.err.println("SQL ERROR!");
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch(Exception e) {}
		}
		return user;
	}
}
