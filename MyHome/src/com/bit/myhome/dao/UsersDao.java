package com.bit.myhome.dao;

import java.util.List;

import com.bit.myhome.vo.UserVO;

public interface UsersDao {
	public List<UserVO> getList();
	public boolean insert(UserVO vo);
	public boolean update(UserVO vo);
	public boolean delete(Long no);
	public UserVO getUserByIdAndPassword(String Email, String Password);
}
