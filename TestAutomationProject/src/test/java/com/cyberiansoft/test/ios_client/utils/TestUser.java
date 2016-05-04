package com.cyberiansoft.test.ios_client.utils;

public class TestUser {
	
	private String username;
	private String password;
	
	public TestUser(String user, String password) {
		this.username = user;
		this.password = password;
	}
	
	public String getTestUserName() {
		return this.username;
	}
	
	public String getTestUserPassword() {
		return this.password;
	}

}
