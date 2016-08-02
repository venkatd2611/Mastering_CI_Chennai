package com.cg.demo.repo;

import com.cg.demo.pojo.Account;

public interface AccountDAO {

	boolean save(Account a);
	
	Account findByNumber(int accountNumber);
}
