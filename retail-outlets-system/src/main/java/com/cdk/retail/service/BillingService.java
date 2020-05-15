package com.cdk.retail.service;

import org.springframework.stereotype.Service;

import com.cdk.retail.dto.Bill;

@Service
public interface BillingService {

	public Bill getTotalBillCost(Bill customerBill);
	
}
