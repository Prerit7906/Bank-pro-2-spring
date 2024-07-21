package com.prerit.bank_pro_2;

import java.util.Scanner;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	private static final Scanner sc = new Scanner(System.in);
	private static final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
	private static final AccountJdbcDao accountJdbcDao = context.getBean(AccountJdbcDao.class);

	public static void main(String[] args) {
		boolean isExitted=false;
		do{
			System.out.println("Here are the options : ");
			System.out.println("Press 1 to login");
			System.out.println("Press 2 to Create a new account");
			System.out.println("Press 3 to exit");
			int option = sc.nextInt();
			sc.nextLine();
			switch (option) {
				case 1:
					loginMethods();
					break;
				case 2:
					accountJdbcDao.createAccount(getUserInputForCreatingAccount());
					break;
				case 3:
					System.out.println("Exitted!!!");
					isExitted=true;
					break;
				default:
					System.out.println("You have choosen a wrong option, Please choose again : ");
			}
		}while(!isExitted);
		sc.close();
		context.close();
	}

	private static Account getUserInputForCreatingAccount() {
		int pin;
		double balance=0;
		System.out.println("Enter name of the account holder : ");
		String name = sc.nextLine();
		System.out.println("Enter phone number : ");
		long phoneNumber = sc.nextLong();
		sc.nextLine();
		while(true){
			System.out.println("Do you want to create a zero balance account? : Y/N");
			String choice = sc.nextLine();
			if (choice.equalsIgnoreCase("y")) {
				balance = 0;
				break;
			} else if (choice.equalsIgnoreCase("n")) {
				System.out.println("Okay so the minimum amount to be credited is 500Rs. ");
				balance = 500;
				break;
			} else {
				System.out.println("You haven't choosen the right option, Please try again!!");
			}
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
				isPinWrong = false;
			}

		} while (isPinWrong);
		return new Account(name, phoneNumber, balance, pin);
	}

	private static void loginMethods() {
		boolean isExitted = false;
		int pin;
		double amount;
		System.out.println("Enter your account number : ");
		long accountNumber=sc.nextLong();
		System.out.println("Enter your pin : ");
		pin=sc.nextInt();sc.nextLine();
		if(!accountJdbcDao.checkIsUserValid(accountNumber,pin)){
			System.out.println("Invalid credentials, Please try again!!");
			return ;
		}
		System.out.println("Logged in successfully!!!\n");
		do {
			System.out.println("Here are the options : ");
			System.out.println("Press 1 to deposit amount");
			System.out.println("Press 2 to withdraw amount");
			System.out.println("Press 3 to show balance");
			System.out.println("Press 4 for fund transfer");
			System.out.println("Press 5 to print transactions");
			System.out.println("Press 0 to Log out");
			int option = sc.nextInt();
			sc.nextLine();
			switch (option) {
			case 1:
				System.out.println("Enter amount to be deposited : ");
				amount = sc.nextDouble();
				System.out.println("Enter your pin : ");
				pin = sc.nextInt();
				sc.nextLine();
				if (accountJdbcDao.depositAmount(accountNumber, amount, pin, false)) {
					System.out.println("Amount deposited successfully!!!");
				}
				break;
			case 2:
				System.out.println("Enter amount to be withdrawn : ");
				amount = sc.nextDouble();
				System.out.println("Enter your pin : ");
				pin = sc.nextInt();
				sc.nextLine();
				if (accountJdbcDao.withdrawAmount(accountNumber, amount, pin)) {
					System.out.println("Amount withdrawal was successful!!!");
				}
				break;
			case 3:
				System.out.println("Enter your pin : ");
				pin = sc.nextInt();
				sc.nextLine();
				accountJdbcDao.showBalance(accountNumber, pin,false);
				break;
			case 4:
				boolean areAccountsSame = true;
				long accountNumberOfReceiver;
				do {
					System.out.println("Enter account number of the receiver : ");
					accountNumberOfReceiver = sc.nextLong();
					if (accountNumber != accountNumberOfReceiver) {
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
				accountJdbcDao.fundTransfer(accountNumber, accountNumberOfReceiver, amount, pin);
				break;
			case 5:
				System.out.println("Enter your pin : ");
				pin = sc.nextInt();
				sc.nextLine();
				accountJdbcDao.printTransactions(accountNumber, pin);
				break;
			case 0:
				System.out.println("Logged out :(\n");
				isExitted = true;
				break;
			default:
				System.out.println("You have chosen wrong option, Please choose again : ");
			}
		} while (!isExitted);
	}
}
