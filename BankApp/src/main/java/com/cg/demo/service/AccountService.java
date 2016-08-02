package com.cg.demo.service;

import com.cg.demo.exceptions.InsufficientBalanceException;
import com.cg.demo.exceptions.InvalidAccountException;
import com.cg.demo.exceptions.InvalidInitialAmountException;
import com.cg.demo.pojo.Account;

public interface AccountService {

	Account createAccount(double amount) throws InvalidInitialAmountException;

	Account showBalance(int accountNumber) throws InvalidAccountException;
	
	Account withdraw(int accountNumber, double amount)throws InvalidAccountException,InsufficientBalanceException;
	
	Account deposit(int accountNumber, double amount)throws InvalidAccountException;
	
}
