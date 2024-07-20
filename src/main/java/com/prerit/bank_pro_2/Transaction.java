package com.prerit.bank_pro_2;

import java.sql.Date;

public class Transaction {
	private int transactionId;
	private long accountNumber;
	private double amount;
	private String transactionType;
	private Date dateOfTransaction;

	public Transaction() {
		
	}

	public Transaction(int transactionId, long accountNumber, double amount, String transactionType,
			Date dateOfTransaction) {
		super();
		this.transactionId = transactionId;
		this.accountNumber = accountNumber;
		this.amount = amount;
		this.transactionType = transactionType;
		this.dateOfTransaction = dateOfTransaction;
	}

	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", accountNumber=" + accountNumber + ", amount=" + amount
				+ ", transactionType=" + transactionType + ", dateOfTransaction=" + dateOfTransaction + "]";
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public Date getDateOfTransaction() {
		return dateOfTransaction;
	}

	public void setDateOfTransaction(Date dateOfTransaction) {
		this.dateOfTransaction = dateOfTransaction;
	}

	public int getTransactionId() {
		return transactionId;
	}
}
