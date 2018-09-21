package com.capgemini.bankapp;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.capgemini.bankapp.config.AppConfig;
import com.capgemini.bankapp.controller.BankAccountController;
import com.capgemini.bankapp.exception.LowBalanceException;
import com.capgemini.bankapp.util.DbUtil;

public class Application {
public static void main(String[] args) throws LowBalanceException {
//	ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
	DbUtil dbUtil = context.getBean("dbUtil", DbUtil.class);
	dbUtil.getConnection();
	BankAccountController bankAccountController = context.getBean("bankAccountController",BankAccountController.class);
	System.out.println(bankAccountController.getBalance(2201));
	System.out.println(bankAccountController.withdraw(2201,10));
	System.out.println(bankAccountController.deposit(2202, 60));
	System.out.println(bankAccountController.fundTransfer(2201,2202,30));
}
}

