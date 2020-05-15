/**
 * 
 */
package com.cdk.retail.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.cdk.retail.domain.CustomerType;

/**
 * @author prabhakar
 *
 */
public class Bill implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private CustomerType customerType;
	private BigDecimal purchaseAmount;
	private BigDecimal TotalCost;
	private BigDecimal discountCost;

	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}

	public BigDecimal getPurchaseAmount() {
		return purchaseAmount;
	}

	public void setPurchaseAmount(BigDecimal purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}

	public BigDecimal getTotalCost() {
		return TotalCost;
	}

	public void setTotalCost(BigDecimal totalCost) {
		TotalCost = totalCost;
	}

	public BigDecimal getDiscountCost() {
		return discountCost;
	}

	public void setDiscountCost(BigDecimal discountCost) {
		this.discountCost = discountCost;
	}

}
