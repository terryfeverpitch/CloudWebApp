package com.cloudwebapp.shared;

public class FieldVerifier {

	public static boolean isValid(String username, String password) {
		if(username == null || password == null) {
			return false;
		}
		return (username.length() >= 4 && password.length() >= 4);
	}
	
	public static String hidePassword(String pw) {
		String hide = "";
		for(int i = 1; i < pw.length() - 1; i++)
			hide += "*";
		return pw.charAt(0) + hide + pw.charAt(pw.length() - 1);
	}
	
	public static boolean usernameFormatCheck(String str) {
		String USERNAME_REGEX = "^[a-zA-Z0-9\\_]*$";

		if(str.equals("") || str.matches(USERNAME_REGEX))
			return true;
		else 
			return false;
	}
	
	public static boolean peopleNameFormatCheck(String str) {
		String NAME_REGEX = "^[a-zA-Z0-9\\s\\.]*$";
		if(str.equals("") || str.matches(NAME_REGEX))
			return true;
		else 
			return false;
	}
	
	public static boolean peopleAddressFormatCheck(String str) {
		String ADDRESS_REGEX = "^[a-zA-Z0-9\\s\\.\\,]*$";
		if(str.equals("") || str.matches(ADDRESS_REGEX))
			return true;
		else 
			return false;
	}
	
	public static boolean peopleBirthDateFormatCheck(String str) {
		String DATE_REGEX = "^((19|20)?[0-9]{2}[- /.](0?[1-9]|1[012])[- /.](0?[1-9]|[12][0-9]|3[01]))*$";
		if(str.equals(null) || str.equals("") || str.matches(DATE_REGEX))
			return true;
		else 
			return false;
	}
	
	public static boolean emailFormatCheck(String str) {
		String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		if(str.equals("") || str.matches(EMAIL_REGEX))
			return true;
		else
			return false;
	}
	
	public static boolean folderNameCheck(String str) {
		if(str == null || str.length() == 0)
			return false; 
		String FOLDERNAME_REGEX = "^[a-zA-Z0-9\\s\\~\\!\\@\\#\\$\\%\\^\\&\\(\\)\\_\\-\\+\\=\\.]*$";
		if(str.matches(FOLDERNAME_REGEX))
			return true;
		else 
			return false;
	}
}
