/**
 * 
 */
package com.cdk.retail.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdk.retail.dto.Bill;
import com.cdk.retail.service.BillingService;

/**
 * @author prabhakar
 *
 */
@RestController
@RequestMapping(value = "/retail/billing")
public class BillingController {

	@Autowired
	private BillingService billingService;

	@PostMapping(value = "/getTotalBillCost", consumes = "application/json")
	public ResponseEntity<Bill> getTotalBillCost(@RequestBody Bill customerBill) throws IOException {

		return new ResponseEntity<Bill>(billingService.getTotalBillCost(customerBill), HttpStatus.OK);
	}

}
