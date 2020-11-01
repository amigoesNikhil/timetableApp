package com.nikhil.main.dao;


import com.nikhil.main.entity.UserEntity;
import com.nikhil.main.entity.UserObject;

public interface UserDao {
	public int Authenticate(String username,String password);
	public int checkAdmin(int id);
	public UserObject getTheUsersOtherThanRequest(String adminname) throws Exception;
	public void changeUserDetails(int userId,String changePassword,int isAdmin) throws Exception;
	public void deleteUser(int userId) throws Exception;
	public void addUser(String username,String password,int isAdmin) throws Exception;
	public UserEntity getUserData(int id) throws Exception;

}
