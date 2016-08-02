import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cg.demo.exceptions.InsufficientBalanceException;
import com.cg.demo.exceptions.InvalidAccountException;
import com.cg.demo.exceptions.InvalidInitialAmountException;
import com.cg.demo.pojo.Account;
import com.cg.demo.repo.AccountDAO;
import com.cg.demo.service.AccountService;
import com.cg.demo.service.AccountServiceImpl;

public class AccountTest {

	private AccountService service;
	
	@Mock
	private AccountDAO dao;
	
	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
		service = new AccountServiceImpl(dao);
	}
	//1. System should create account successfully, if min Rs. 500/- are deposited while creating account
	//2. System should give error if user attempts to create account with less than Rs. 500/-
	
	
	@Test
	public void account_should_be_created_successfully_if_amount_is_min_500() throws InvalidInitialAmountException {
		
		Account a = new Account(1);
		a.setBalance(1000);
		
		when(dao.save(a)).thenReturn(true);
		
		Account newAccount = service.createAccount(1000);
		assertEquals(1000.0+"", newAccount.getBalance()+"");
	}
	
	@Test(expected=InvalidInitialAmountException.class)
	public void account_should_not_be_created_if_amount_is_less_than_500() throws InvalidInitialAmountException {
		service.createAccount(100);
	}

	//1. when valid accountNumber is passed showBalance(System) should return the Account information of that account
	//2. when invalid accountNumber is passed showBalance(System) should display error message 

	@Test
	public void when_valid_accountNumber_is_passed_method_should_return_account_object() throws InvalidAccountException{
		Account oldObject = new Account(100);
		oldObject.setBalance(15000);

		when(dao.findByNumber(100)).thenReturn(oldObject);
		
		assertEquals(15000.0+"", service.showBalance(100).getBalance()+"");
	}
	
	@Test(expected=InvalidAccountException.class)
	public void when_invalid_accountNumber_is_passed_method_should_throw_excpetion() throws InvalidAccountException{
		when(dao.findByNumber(1234)).thenReturn(null);
		
		service.showBalance(1234);
	}
	
	//1. When valid account Number and sufficient amount is passed system should complete the transaction successfully.
	//2. When invalid account number is given, system should generate error message.
	//3. when amount passed is greater than the balance system should generate error message. 
	//4. Amount passed should be greater than zero.
	
	@Test
	public void when_valid_accountNumber_and_sufficient_amount_is_passed_method_should_deduct_the_balance_and_return_the_account_object() throws InvalidAccountException, InsufficientBalanceException{
		Account oldAccount = new Account(123);
		oldAccount.setBalance(5000);
		
		when(dao.findByNumber(123)).thenReturn(oldAccount);
		
		assertEquals(4000.0+"", service.withdraw(123, 1000).getBalance()+"");
		
	}
	@Test(expected=InvalidAccountException.class)
	public void when_invalid_accountNumber_method_should_throw_exception() throws InvalidAccountException, InsufficientBalanceException{
		
		when(dao.findByNumber(1234)).thenReturn(null);

		service.withdraw(1234, 5400);
	}
	@Test(expected=InsufficientBalanceException.class)
	public void when_amount_passed_is_greater_than_balance_method_should_throw_exception() throws InvalidAccountException, InsufficientBalanceException{
		Account oldAccount = new Account(123);
		oldAccount.setBalance(5000);
		when(dao.findByNumber(123)).thenReturn(oldAccount);

		service.withdraw(123, 15000);
	}
	
	@Test(expected=java.lang.IllegalArgumentException.class)
	public void when_amount_passed_is_less_than_zero_method_should_throw_exception() throws InvalidAccountException, InsufficientBalanceException{

		service.withdraw(123, -5000);
	}

}


















