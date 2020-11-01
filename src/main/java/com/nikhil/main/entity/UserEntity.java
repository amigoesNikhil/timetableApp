package com.nikhil.main.entity;

public class UserEntity {
	private String username;
	private int id;
	private int isAdmin;
	@Override
	public String toString() {
		return "UserEntity [username=" + username + ", id=" + id + ", isAdmin=" + isAdmin + ", password=" + password
				+ "]";
	}
	private String password;
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}

}
