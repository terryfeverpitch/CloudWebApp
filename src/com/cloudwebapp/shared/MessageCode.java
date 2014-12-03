package com.cloudwebapp.shared;

public class MessageCode {
	// positive
		// resister
	public final static int REGISTER_SUCCESS 				  = 1;
		// update
	public final static int UPDATE_SUCCESS 					  = 2;
		// set free or block
	public final static int AVAILABLE_SUCCESS_FREE  		  = 3;
	public final static int AVAILABLE_SUCCESS_BLOCK 		  = 4;
		// delete
	public final static int DELETE_SUCCESS					  = 5;
	
	// negative
		// verify
	public final static int VERIFY_FAILURE_EXCEPTION_ERROR	  = -1;
	public final static int VERIFY_FAILURE_WRONG_UN_PW     	  = -2;
	public final static int VERIFY_FAILURE_UNAVAILABLE        = -3;
		// register
	public final static int REGISTER_FAILURE_USERNAME_EXISTED = -4;
	public final static int REGISTER_FAILURE_EXCEPTION_ERROR  = -5;
		// ALL
	public final static int ACCOUNT_NOT_FOUND 				  = -6;
		// update
	public final static int UPDATE_FAILURE_EXCEPTION_ERROR 	  = -7;
	 	// set free or block
	public final static int AVAILABLE_FAILURE_EXCEPTION_ERROR = -8;
		// delete
	public final static int DELETE_FAILURE_EXCEPTION_ERROR	  = -9;
}
