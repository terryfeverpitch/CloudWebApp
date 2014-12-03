package com.cloudwebapp.server;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Account {
	public final static int ACCOUNT_TYPE_ADMIN = 0;
	public final static int ACCOUNT_TYPE_USER = 1;
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private String username;
	@Persistent
	private String password;
	@Persistent
	private String signUpDate;
	@Persistent
	private int type;
	@Persistent
	private String name;	
    @Persistent
    private String address;
    @Persistent
    private String birthDate;
    @Persistent
    private String email;
    @Persistent
    private boolean available;
	
	// Constructor
	public Account() {
		
	}
	
	public Account(String username, String password) {
        this.username = username;
        this.password = password;
        this.signUpDate = null;
        this.name 		= null;
        this.address 	= null;
        this.birthDate 	= null;
        this.email 		= null;
        this.available  = true;
        this.type = ACCOUNT_TYPE_ADMIN;
    }
	
	public Account(String username, String password, String sd, String n, String a, String d, String e) {
        this.username = username;
        this.password = password;
        this.signUpDate = sd;
        this.name 		= n;
        this.address 	= a;
        this.birthDate 	= d;
        this.email 		= e;
        this.available  = true;
        this.type = ACCOUNT_TYPE_USER;
    }
	
	public void setBlock() {
		this.available = false;
	}
	
	public void setFree() {
		this.available = true;
	}
	
	public boolean isAvailable() {
		return available;
	}
	
	public void setValue(String un, String pw, String sd, String n, String a, String d, String e, int t) {
		username = un;
		password = pw;
		signUpDate = sd;
		type = t;
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
	
	public int getType() {
		return type;
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
}
