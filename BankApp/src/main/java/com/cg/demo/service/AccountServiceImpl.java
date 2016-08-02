package com.cg.demo.service;

import com.cg.demo.exceptions.InsufficientBalanceException;
import com.cg.demo.exceptions.InvalidAccountException;
import com.cg.demo.exceptions.InvalidInitialAmountException;
import com.cg.demo.pojo.Account;
import com.cg.demo.repo.AccountDAO;

public class AccountServiceImpl implements AccountService {

	private AccountDAO dao;

	public AccountServiceImpl(AccountDAO dao) {
		super();
		this.dao = dao;
	}

	@Override
	public Account createAccount(double amount) throws InvalidInitialAmountException {
		// TODO Auto-generated method stub
		
		if(amount < 500){
			throw new InvalidInitialAmountException();
		}
		Account a = new Account(1);
		a.setBalance(amount);
		if(dao.save(a)){
			return a;
		}
		return null;
	}

	@Override
	public Account showBalance(int accountNumber) throws InvalidAccountException {
		// TODO Auto-generated method stub
		Account a = dao.findByNumber(accountNumber);
		if(a!=null){
			return a;
		}
		throw new InvalidAccountException();
	}

	@Override
	public Account withdraw(int accountNumber, double amount)
			throws InvalidAccountException, InsufficientBalanceException {
		// TODO Auto-generated method stub
		if(amount<=0){
			throw new IllegalArgumentException();
		}
		Account a = dao.findByNumber(accountNumber);
		if(a==null){
			throw new InvalidAccountException();
		}
		if(a.getBalance()<amount){
			throw new InsufficientBalanceException();
		}
		a.setBalance(a.getBalance()-amount);
		return a;
	}

	@Override
	public Account deposit(int accountNumber, double amount) throws InvalidAccountException {
		// TODO Auto-generated method stub
		return null;
	}

}
