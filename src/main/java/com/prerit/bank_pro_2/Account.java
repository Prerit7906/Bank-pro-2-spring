package com.prerit.bank_pro_2;

public class Account {
	private long accountNumber;
	private String accountHolderName;
	private int pinNumber;
	private long phoneNumber;
	private double balance;

	public Account() {
	}

	public Account(long accountNumber, String accountHolderName, long phoneNumber, double balanceint, int pinNumber) {
		this.accountNumber = accountNumber;
		this.accountHolderName = accountHolderName;
		this.phoneNumber = phoneNumber;
		this.balance = balance;
		this.pinNumber = pinNumber;
	}

	public Account(String accountHolderName, long phoneNumber, double balance, int pinNumber) {
		this.accountHolderName = accountHolderName;
		this.phoneNumber = phoneNumber;
		this.balance = balance;
		this.pinNumber = pinNumber;
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public int getPinNumber() {
		return pinNumber;
	}

	public void setPinNumber(int pinNumber) {
		this.pinNumber = pinNumber;
	}

	@Override
	public String toString() {
		return "Account [accountHolderName=" + accountHolderName + ", phoneNumber=" + phoneNumber + ", balance="
				+ balance + ", pinNumber=" + pinNumber + "]";
	}
}
