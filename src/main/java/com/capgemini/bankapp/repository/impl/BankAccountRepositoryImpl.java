package com.capgemini.bankapp.repository.impl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.capgemini.bankapp.entities.BankAccount;
import com.capgemini.bankapp.repository.BankAccountRepository;
import com.capgemini.bankapp.util.DbUtil;
@Repository
public class BankAccountRepositoryImpl implements BankAccountRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public double getBalance(long accountId) {
		double balance = jdbcTemplate.queryForObject("SELECT balance FROM bankaccount WHERE accountId = ?",new Object[] {accountId},Double.class);
		return balance;
	}

	@Override
	public boolean updateBalance(long accountId, double newBalance) {
		int count = jdbcTemplate.update("UPDATE bankaccount SET balance=? WHERE accountId = ?",new Object[] {accountId,newBalance});
		return count!=0;
	}

	@Override
	public boolean addBankAccount(BankAccount account) {
		int count = jdbcTemplate.update("INSERT INTO bankaccount VALUES(?,?,?,?)",new Object[] {account.getAccountId(),account.getAccountHolderName(),account.getAccountType(),account.getAccountBalance()});
		return count!=0;
	}

	@Override
	public BankAccount findBankAccountById(long accountId) {
		return jdbcTemplate.queryForObject("SELECT*FROM bankaccount  WHERE accountId=?", new Object[] {accountId}, new BankAccountRowMapper());
		
	}

	@Override
	public List<BankAccount> findAllBankAccounts() {
	return jdbcTemplate.query("SELECT * FROM bankaccounts", new Object[] {}, new BankAccountRowMapper());
		
	}

	@Override
	public BankAccount updateBabkAccount(BankAccount account) {
	jdbcTemplate.update("UPDATE bankaccount SET accountHolderName=?,accountType=? WHERE accountId=?",new Object[] {account.getAccountHolderName(),account.getAccountType()});
	return account;
		
	}

	@Override
	public boolean deleteBankAccount(long accountId) {
		int count = jdbcTemplate.update("DELETE FROM bankaccount WHERE accountId = ?",new Object[] {accountId});
		return count!=0;
		
	}


private class BankAccountRowMapper implements RowMapper<BankAccount>{

	@Override
	public BankAccount mapRow(ResultSet rs, int rowNum) throws SQLException {
		BankAccount account = new BankAccount();
		account.setAccountId(rs.getLong(1));
		account.setAccountHolderName(rs.getString(4));
		account.setAccountBalance(rs.getDouble(2));
		account.setAccountType(rs.getString(3));
		return null;
	}
}
}
	