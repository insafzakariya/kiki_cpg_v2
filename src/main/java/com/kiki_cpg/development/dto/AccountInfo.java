package com.kiki_cpg.development.dto;

public class AccountInfo {

	String accountType;
	String accountStatus;
	Double creditLimit;
	Double balance;
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	public Double getCreditLimit() {
		return creditLimit;
	}
	public void setCreditLimit(Double creditLimit) {
		this.creditLimit = creditLimit;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	
}
