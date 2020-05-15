package com.cdk.retail.domain;

public enum CustomerType {
	REGULAR("Regular"), 
	PREMIUM("Premium");
	
	private String customerType;

	private CustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getCustomerType() {
		return this.customerType;
	}

		
}
