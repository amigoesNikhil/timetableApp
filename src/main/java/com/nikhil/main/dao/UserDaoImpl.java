package com.nikhil.main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nikhil.main.entity.UserEntity;
import com.nikhil.main.entity.UserObject;
import com.nikhil.main.util.ConnectDatabase;

@Component
public class UserDaoImpl implements UserDao {

	@Autowired
	ConnectDatabase database;
	
	@Override
	public int Authenticate(String username,String password) {
		Connection con=database.getConnection();
		String sql="SELECT * FROM USER WHERE USERNAME=? AND PASSWORD=?";
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				return rs.getInt("ID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public int checkAdmin(int id) {
		Connection con=database.getConnection();
		String sql="SELECT * FROM USER WHERE ID=?";
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				int isAdmin=rs.getInt("IS_ADMIN");
				if(isAdmin==1) {
					return isAdmin;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public UserObject getTheUsersOtherThanRequest(String adminname) throws Exception {
		ArrayList<UserEntity> users=new ArrayList<UserEntity>();
		Connection con=database.getConnection();
		String sql="SELECT * FROM USER WHERE USERNAME!=?";
		PreparedStatement ps=con.prepareStatement(sql);
		ps.setString(1, adminname);
		ResultSet rs=ps.executeQuery();
		while(rs.next()) {
			int id=rs.getInt("ID");
			String username=rs.getString("USERNAME");
			int isAdmin=rs.getInt("IS_ADMIN");
			UserEntity user=new UserEntity();
			user.setId(id);
			user.setIsAdmin(isAdmin);
			user.setUsername(username);
			users.add(user);
		}
		UserObject arrayUsers=new UserObject();
		arrayUsers.setUsers(users);
		return arrayUsers;
	}

	@Override
	public void changeUserDetails(int userId, String changePassword,int isAdmin) throws Exception {
		String sql="UPDATE USER SET PASSWORD=?,IS_ADMIN=? WHERE ID=?";
		Connection con=database.getConnection();
		PreparedStatement ps=con.prepareStatement(sql);
		ps.setString(1, changePassword);
		ps.setInt(2, isAdmin);
		ps.setInt(3, userId);
		ps.executeUpdate();
	}

	@Override
	public void deleteUser(int userId) throws SQLException {
		String sql="DELETE FROM USER WHERE ID=?";
		Connection con=database.getConnection();
		PreparedStatement ps=con.prepareStatement(sql);
		ps.setInt(1, userId);
		ps.execute();
	}

	@Override
	public void addUser(String username, String password,int isAdmin) throws Exception {
		
		
		Connection con=database.getConnection();
		String sqlCheckUser="SELECT * FROM USER WHERE USERNAME=?;";
		String sql="INSERT INTO USER(USERNAME,PASSWORD,IS_ADMIN) VALUES(?,?,?);";
		PreparedStatement ps1=con.prepareStatement(sqlCheckUser);
		ps1.setString(1, username);
		ResultSet rs1=ps1.executeQuery();
		if(rs1.next()) {
			throw new Exception("User already present");
		}
		PreparedStatement ps=con.prepareStatement(sql);
		ps.setString(1, username);
		ps.setString(2, password);
		ps.setInt(3, isAdmin);
		ps.execute();	
	}

	@Override
	public UserEntity getUserData(int id) throws Exception{
		Connection con=database.getConnection();
		UserEntity user=null;
		String sqlToGetUserInfo="SELECT * FROM USER WHERE ID=?";
		PreparedStatement ps=con.prepareStatement(sqlToGetUserInfo);
		ps.setInt(1, id);
		ResultSet rs=ps.executeQuery();
		if(rs.next()) {
			user=new UserEntity();
			user.setId(id);
			user.setUsername(rs.getString("USERNAME"));
			user.setIsAdmin(rs.getInt("IS_ADMIN"));
			user.setPassword(rs.getString("PASSWORD"));
			//System.out.println("in daoimpl"+user);
		} else {
			throw new Exception("No User exist with id="+id);
		}
		return user;
	}
}
