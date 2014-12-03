package com.cloudwebapp.shared;

import java.io.Serializable;

public class AccountDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private String signUpDate;
	private String name;	
    private String address;
    private String birthDate;
    private String email;
    private int type;
    private boolean available;
 
	// Constructor
	public AccountDTO() {
	}
	
	public void setBlock() {
		available = false;
	}
	
	public void setFree() {
		available = true;
	}
	
	public boolean isAvailable() {
		return available;
	}
	
	public void setValue(String un, String pw, String sd, String n, String a, String d, String e, int t, boolean available) {
		username = un;
		password = pw;
		signUpDate = sd;
		type = t;
		this.available  = available;
		setPeople(n, a, d, e);
	}
	
	public void setPeople(String n, String a, String d, String e) {
    	name = n;
		address = a;
		birthDate = d;
		email = e;
    }
	
	public void updateValue(String un, String pw, String n, String a, String d, String e) {
		username = un;
		password = pw;
		updatePeople(n, a, d, e);
	}
	
	public void updatePeople(String n, String a, String d, String e) {
    	name = n;
		address = a;
		birthDate = d;
		email = e;
    }
	
	public void setUsername(String username) {
        this.username = username;
    }
	
	public void setPassword(String password) {
        this.password = password;
    }
	
	public void setName(String name) {
        this.name = name;
    }
	
	public void setAddress(String address) {
        this.address = address;
    }
	
	public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
	
	public void setEmail(String email) {
        this.email = email;
    }
	
	public String getUsername() {
        return username;
    }
	
	public String getPassword() {
        return password;
    }
	
	public String getSignUpDate() {
		return signUpDate;
	}
	
	public String getName() {
        return name;
    }
	
	public String getAddress() {
        return address;
    }
	
	public String getBirthDate() {
        return birthDate;
    }
	
	public String getEmail() {
        return email;
    }
	
	public int getType() {
		return type;
	}
}
