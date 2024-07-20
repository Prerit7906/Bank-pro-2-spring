package com.prerit.bank_pro_2;

public interface Modules {
	void createAccount(Account account);

	void showBalance(long accountNumber, int pin);

	boolean depositAmount(long accountNumber, double amountToBeDeposited, int pin, boolean isCalledFromFundTransfer);

	boolean withdrawAmount(long accountNumber, double amountToBeWithdrawn, int pin);

	void fundTransfer(long accountNumberOfSender, long accountNumberOfReceiver, double amount, int pin);

	void printTransactions(long accountNumber, int pin);
}
