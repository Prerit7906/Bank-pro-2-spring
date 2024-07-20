package com.prerit.bank_pro_2;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AccountJdbcDao implements Modules {
	private JdbcTemplate jdbcTemplate;
//	private DataSource dataSource;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	String query = "";

	@Override
	public void createAccount(Account account) {
		if (getAccountsUsingAccountHolderAndPhoneNumber(account).size() != 0) {
			System.out.println("This user is already registered, Please try again!!");
			return;
		}
		query = "insert into Account (accountHolderName, phoneNumber, balance, pinNumber) values (?,?,?,?)";
		int updateResponse = jdbcTemplate.update(query, account.getAccountHolderName(), account.getPhoneNumber(),
				account.getBalance(), account.getPinNumber());
		if (updateResponse > 0) {
			System.out.println("Account created successfully :)");
			printAccountNumberWhileCreationOfAccount(account);
		} else
			System.out.println("Couldn't create the account, please check the deatils or try again after some time :(");
	}

	@Override
	public void showBalance(long accountNumber, int pin) {
		if (!checkIsUserValid(accountNumber, pin)) {
			System.out.println("Invalid credentials, Please try again!!");
			return;
		}
		query = "select balance from Account where accountNumber=?";
		List<Account> account = jdbcTemplate.query(query, new BeanPropertyRowMapper<Account>(Account.class),
				accountNumber);
		System.out.println("The balance is : " + account.get(0).getBalance());

	}

	@Override
	public boolean depositAmount(long accountNumber, double amountToBeDeposited, int pin,
			boolean isCalledFromFundTransfer) {
		if (!isCalledFromFundTransfer && !checkIsUserValid(accountNumber, pin)) {
			System.out.println("Invalid credentials, Please try again!!");
			return false;
		}

		query = "update Account set balance=balance+? where accountNumber=?";
		int response = jdbcTemplate.update(query, amountToBeDeposited, accountNumber);
		query = "insert into Transaction (accountNumber,amount, transactionType) values (?,?,'DEPOSIT')";
		int response1 = jdbcTemplate.update(query, accountNumber, amountToBeDeposited);
		if (response1 > 0 && response > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean withdrawAmount(long accountNumber, double amountToBeWithdrawn, int pin) {
		if (!checkIsUserValid(accountNumber, pin)) {
			System.out.println("Invalid credentials, Please try again!!");
			return false;
		}
		if (!checkIfSufficientBalanceToWithdraw(accountNumber, amountToBeWithdrawn)) {
			System.out.println("Insufficient balance for this transaction!!");
			return false;
		}
		query = "update Account set balance=balance-? where accountNumber=?";
		int response = jdbcTemplate.update(query, amountToBeWithdrawn, accountNumber);
		query = "insert into Transaction (accountNumber,amount, transactionType) values (?,?,'WITHDRAW')";
		int response1 = jdbcTemplate.update(query, accountNumber, amountToBeWithdrawn);
		if (response1 > 0 && response > 0) {
			return true;
		}
		return false;

	}

	@Override
	public void fundTransfer(long accountNumberOfSender, long accountNumberOfReceiver, double amount, int pin) {
		if (withdrawAmount(accountNumberOfSender, amount, pin)
				&& depositAmount(accountNumberOfReceiver, amount, pin, true)) {
			System.out.println("Fund transferred successfully!!!");
		}
	}

	@Override
	public void printTransactions(long accountNumber, int pin) {
		if (!checkIsUserValid(accountNumber, pin)) {
			System.out.println("Invalid credentials, Please try again!!");
			return;
		}
		Transaction t;
		query = "select * from Transaction where accountNumber=?";
		List<Transaction> transactions = jdbcTemplate.query(query,
				new BeanPropertyRowMapper<Transaction>(Transaction.class), accountNumber);
		System.out.println("Here are the transactions : ");
		System.out.println("Txn. Id | Amount | Txn.Type | Date of Txn");
		for (Transaction value : transactions) {
			System.out.println(value.getTransactionId() + " | " + value.getAmount() + " | " + value.getTransactionType()
					+ " | " + value.getDateOfTransaction());
		}
	}

	private void printAccountNumberWhileCreationOfAccount(Account account) {
		query = "select accountNumber from Account where accountHolderName=? and phoneNumber=?";
		Account account1 = jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<Account>(Account.class),
				account.getAccountHolderName(), account.getPhoneNumber());
		System.out.println("Your account number is : " + account1.getAccountNumber() + ", Please note it down.");
	}

	private List<Account> getAccountsUsingAccountHolderAndPhoneNumber(Account account) {
		query = "select * from Account where accountHolderName=? and phoneNumber=?";
		return jdbcTemplate.query(query, new BeanPropertyRowMapper<Account>(Account.class),
				account.getAccountHolderName(), account.getPhoneNumber());

	}

	private boolean checkIsUserValid(long accountNumber, int pin) {
		query = "select * from Account where accountNumber=? and pinNumber= ?";
		List<Account> account = jdbcTemplate.query(query, new BeanPropertyRowMapper<Account>(Account.class),
				accountNumber, pin);
		if (account.size() != 0) {
			return true;
		}
		return false;
	}

	private boolean checkIfSufficientBalanceToWithdraw(long accountNumber, double amountToBeWithdrawn) {
		query = "select balance from Account where accountNumber=? and balance>=?";
		List<Account> account = jdbcTemplate.query(query, new BeanPropertyRowMapper<Account>(Account.class),
				accountNumber, amountToBeWithdrawn);
		if (account.size() != 0) {
			return true;
		}
		return false;
	}

}
