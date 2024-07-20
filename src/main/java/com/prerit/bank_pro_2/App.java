package com.prerit.bank_pro_2;

import java.util.Scanner;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
		Scanner sc = new Scanner(System.in);
		AccountJdbcDao accountJdbcDao = context.getBean(AccountJdbcDao.class);
		boolean isExitted = false;
		int pin;
		long accountNumber;
		double amount;
		do {
			System.out.println("Here are the options : ");
			System.out.println("Press 1 to create account");
			System.out.println("Press 2 to deposit amount");
			System.out.println("Press 3 to withdraw amount");
			System.out.println("Press 4 to show balance");
			System.out.println("Press 5 for fund transfer");
			System.out.println("Press 6 to print transactions");
			System.out.println("Press 0 to Exit");
			int option = sc.nextInt();
			sc.nextLine();
			switch (option) {
			case 1:
				System.out.println("Enter name of the account holder : ");
				String name = sc.nextLine();
				System.out.println("Enter phone number : ");
				long phoneNumber = sc.nextLong();
				sc.nextLine();
				System.out.println("Do you want to create a zero balance account? : Y/N");
				String choice = sc.nextLine();
				double balance;
				if (choice.equalsIgnoreCase("y")) {
					balance = 0;
				} else if (choice.equalsIgnoreCase("n")) {
					System.out.println("Okay so the minimum amount to be credited is 500Rs. ");
					balance = 500;
				} else {
					System.out.println("You haven't choosen the right option, Please try again!!");
					continue;
				}
				boolean isPinWrong = true;
				do {
					System.out.println("Create the pin for this account: ");
					pin = sc.nextInt();
					System.out.println("Re-enter pin to confirm : ");
					int pin1 = sc.nextInt();
					sc.nextLine();
					if (pin1 != pin) {
						System.out.println("Pins didn't match, Please try again!!");
					} else {
						accountJdbcDao.createAccount(new Account(name, phoneNumber, balance, pin));
						isPinWrong = false;
					}

				} while (isPinWrong);

				break;
			case 2:
				System.out.println("Enter account number : ");
				accountNumber = sc.nextLong();
				System.out.println("Enter amount to be deposited : ");
				amount = sc.nextDouble();
				System.out.println("Enter your pin : ");
				pin = sc.nextInt();
				sc.nextLine();
				if (accountJdbcDao.depositAmount(accountNumber, amount, pin, false)) {
					System.out.println("Amount deposited successfully!!!");
				}
				break;
			case 3:
				System.out.println("Enter account number : ");
				accountNumber = sc.nextLong();
				System.out.println("Enter amount to be withdrawn : ");
				amount = sc.nextDouble();
				System.out.println("Enter your pin : ");
				pin = sc.nextInt();
				sc.nextLine();
				if (accountJdbcDao.withdrawAmount(accountNumber, amount, pin)) {
					System.out.println("Amount withdrawal was successful!!!");
				}
				break;
			case 4:
				System.out.println("Enter account number : ");
				accountNumber = sc.nextLong();
				System.out.println("Enter your pin : ");
				pin = sc.nextInt();
				sc.nextLine();
				accountJdbcDao.showBalance(accountNumber, pin);
				break;
			case 5:
				boolean areAccountsSame = true;
				long accountNumberOfSender, accountNumberOfReceiver;
				do {
					System.out.println("Enter account number of the sender : ");
					accountNumberOfSender = sc.nextLong();
					System.out.println("Enter account number of the receiver : ");
					accountNumberOfReceiver = sc.nextLong();
					if (accountNumberOfSender != accountNumberOfReceiver) {
						areAccountsSame = false;
					} else {
						System.out.println(
								"Both the accounts cann't be the same\nPlease enter different account numbers");
					}
				} while (areAccountsSame);
				System.out.println("Enter amount to be transferred : ");
				amount = sc.nextDouble();
				System.out.println("Enter your pin (Sender) : ");
				pin = sc.nextInt();
				sc.nextLine();
				accountJdbcDao.fundTransfer(accountNumberOfSender, accountNumberOfReceiver, amount, pin);
				break;
			case 6:
				System.out.println("Enter account number of account for which you want to print all transactions : ");
				accountNumber = sc.nextLong();
				System.out.println("Enter your pin : ");
				pin = sc.nextInt();
				sc.nextLine();
				accountJdbcDao.printTransactions(accountNumber, pin);
				break;
			case 0:
				System.out.println("Exitted!!!");
				isExitted = true;
				break;
			default:
				System.out.println("You have choosen wrong option, Please choose again : ");
				continue;
			}
		} while (!isExitted);
		sc.close();
		context.close();
	}
}
