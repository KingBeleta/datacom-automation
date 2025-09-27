package com.datacom.framework.models;

public class UserData {
	private int rowNumber;
	private String testName;
	private String firstName;
	private String lastName;
	private String phone;
	private String country;
	private String email;
	private String password;

	public UserData(int rowNumber, String testName, String firstName, String lastName, String phone, String country,
			String email, String password) {
		this.rowNumber = rowNumber;
		this.testName = testName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.country = country;
		this.email = email;
		this.password = password;
	}

	public int getRowNumber() {
		return rowNumber;
	}

	public String getTestName() {
		return testName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPhone() {
		return phone;
	}

	public String getCountry() {
		return country;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
}
