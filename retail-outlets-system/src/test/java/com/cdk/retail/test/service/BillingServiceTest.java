package com.cdk.retail.test.service;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cdk.retail.domain.CustomerType;
import com.cdk.retail.dto.Bill;
import com.cdk.retail.service.BillingService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BillingServiceTest {

	@Autowired
	private BillingService billingService;

	private Bill customerBill;
	private BigDecimal totalAmoutRegularsecondSlab = BigDecimal.valueOf(9500);
	private BigDecimal totalAmouRegularThirdSlab = BigDecimal.valueOf(13500);

	private BigDecimal totalAmoutPremiumFirstSlab = BigDecimal.valueOf(3600);
	private BigDecimal totalAmoutPremiumSecondSlab = BigDecimal.valueOf(7000);
	private BigDecimal totalAmoutPremiumThirdSlab = BigDecimal.valueOf(10200);
	private BigDecimal totalAmoutPremiumForthSlab = BigDecimal.valueOf(15800);

	@Before
	public void setup() {
		customerBill = new Bill();
		customerBill.setCustomerType(CustomerType.REGULAR);
		customerBill.setPurchaseAmount(BigDecimal.valueOf(5000));
	}

	@Test
	public void testRegularCustomerGetTotalBillCost() {
		billingService.getTotalBillCost(customerBill);
		Assert.assertEquals(customerBill.getPurchaseAmount(), customerBill.getTotalCost());
	}

	@Test
	public void testRegularSecondSlabCustomerGetTotalBillCost() {
		customerBill.setPurchaseAmount(BigDecimal.valueOf(10000));
		billingService.getTotalBillCost(customerBill);
		Assert.assertEquals(totalAmoutRegularsecondSlab, customerBill.getTotalCost());
	}

	@Test
	public void testRegularThirdSlabCustomerGetTotalBillCost() {
		customerBill.setPurchaseAmount(BigDecimal.valueOf(15000));
		billingService.getTotalBillCost(customerBill);
		Assert.assertEquals(totalAmouRegularThirdSlab, customerBill.getTotalCost());
	}

	@Test
	public void testPremiumCustomerGetTotalBillCost() {
		customerBill.setCustomerType(CustomerType.PREMIUM);
		customerBill.setPurchaseAmount(BigDecimal.valueOf(4000));
		billingService.getTotalBillCost(customerBill);
		Assert.assertEquals(totalAmoutPremiumFirstSlab, customerBill.getTotalCost());
	}

	@Test
	public void testPremiumSecondSlabCustomerGetTotalBillCost() {
		customerBill.setCustomerType(CustomerType.PREMIUM);
		customerBill.setPurchaseAmount(BigDecimal.valueOf(8000));
		billingService.getTotalBillCost(customerBill);
		Assert.assertEquals(totalAmoutPremiumSecondSlab, customerBill.getTotalCost());
	}

	@Test
	public void testPremiumThirdSlabCustomerGetTotalBillCost() {
		customerBill.setCustomerType(CustomerType.PREMIUM);
		customerBill.setPurchaseAmount(BigDecimal.valueOf(12000));
		billingService.getTotalBillCost(customerBill);
		Assert.assertEquals(totalAmoutPremiumThirdSlab, customerBill.getTotalCost());
	}

	@Test
	public void testPremiumFirthSlabCustomerGetTotalBillCost() {
		customerBill.setCustomerType(CustomerType.PREMIUM);
		customerBill.setPurchaseAmount(BigDecimal.valueOf(20000));
		billingService.getTotalBillCost(customerBill);
		Assert.assertEquals(totalAmoutPremiumForthSlab, customerBill.getTotalCost());
	}
}
