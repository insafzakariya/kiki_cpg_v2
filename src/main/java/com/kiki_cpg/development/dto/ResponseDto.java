package com.kiki_cpg.development.dto;

public class ResponseDto {
		String endUserId;
		String referenceCode;
		AccountInfo accountInfo;
		public String getEndUserId() {
			return endUserId;
		}
		public void setEndUserId(String endUserId) {
			this.endUserId = endUserId;
		}
		public String getReferenceCode() {
			return referenceCode;
		}
		public void setReferenceCode(String referenceCode) {
			this.referenceCode = referenceCode;
		}
		public AccountInfo getAccountInfo() {
			return accountInfo;
		}
		public void setAccountInfo(AccountInfo accountInfo) {
			this.accountInfo = accountInfo;
		}
		
		
}
