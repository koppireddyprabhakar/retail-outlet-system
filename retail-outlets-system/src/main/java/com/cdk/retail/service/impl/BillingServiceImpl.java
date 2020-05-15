package com.cdk.retail.service.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cdk.retail.domain.CustomerType;
import com.cdk.retail.domain.PremiumSlabs;
import com.cdk.retail.domain.RegularSlabs;
import com.cdk.retail.dto.Bill;
import com.cdk.retail.service.BillingService;
import com.cdk.retail.util.Constants;

@Service
public class BillingServiceImpl implements BillingService {

	private List<RegularSlabs> REGULAR_DISCOUNT = Arrays.asList(RegularSlabs.FIRST_SLAB, RegularSlabs.SECOND_SLAB,
			RegularSlabs.THIRD_SLAB);

	private List<PremiumSlabs> PREMIUM_DISCOUNT = Arrays.asList(PremiumSlabs.FIRST_SLAB, PremiumSlabs.SECOND_SLAB,
			PremiumSlabs.THIRD_SLAB, PremiumSlabs.FIRTH_SLAB);

	@Override
	public Bill getTotalBillCost(Bill customerBill) {
		List<RegularSlabs> applicableDiscount;
		List<PremiumSlabs> applicablePremiumDiscount;
		
		if (CustomerType.REGULAR.equals(customerBill.getCustomerType())) {
			applicableDiscount = findApplicableDiscount(customerBill, REGULAR_DISCOUNT);
			getFinalRegularDiscount(customerBill, applicableDiscount);
		} else if (CustomerType.PREMIUM.equals(customerBill.getCustomerType())) {
			applicablePremiumDiscount = findApplicablePremiumDiscount(customerBill, PREMIUM_DISCOUNT);
			getFinalPremiumDiscount(customerBill, applicablePremiumDiscount);
		}
		
		return customerBill;
	}

	private List<RegularSlabs> findApplicableDiscount(Bill customerBill, List<RegularSlabs> discounts) {

		List<RegularSlabs> applicableDiscounts = discounts.stream()
				.filter(x -> inRange(customerBill.getPurchaseAmount(), BigDecimal.valueOf(x.getMin())))
				.collect(Collectors.toList());

		return applicableDiscounts;
	}

	private List<PremiumSlabs> findApplicablePremiumDiscount(Bill customerBill, List<PremiumSlabs> discounts) {

		List<PremiumSlabs> applicableDiscounts = discounts.stream()
				.filter(x -> inRange(customerBill.getPurchaseAmount(), BigDecimal.valueOf(x.getMin())))
				.collect(Collectors.toList());

		return applicableDiscounts;
	}

	private boolean inRange(BigDecimal purchageCost, BigDecimal minSlab) {
		return (purchageCost.compareTo(minSlab) >= 0);
	}

	private Bill getFinalRegularDiscount(Bill customerBill, List<RegularSlabs> discountDetial) {

		BigDecimal totalDiscount = BigDecimal.ZERO;
		BigDecimal purchaseAmount = customerBill.getPurchaseAmount();

		if (discountDetial != null && !discountDetial.isEmpty()) {
			for (RegularSlabs regularSlab : discountDetial) {

				switch (regularSlab) {
				case FIRST_SLAB:
					purchaseAmount = purchaseAmount.subtract(BigDecimal.valueOf(regularSlab.getMax()));
					break;

				case SECOND_SLAB:
					BigDecimal value = findPurchaseAmount(discountDetial, purchaseAmount, regularSlab);
					totalDiscount = totalDiscount.add(value.multiply(new BigDecimal(regularSlab.getPercentage()))
							.divide(new BigDecimal(Constants.PERCENTAGE)));
					if (purchaseAmount.compareTo(value) > 0) {
						purchaseAmount = purchaseAmount.subtract(BigDecimal.valueOf(regularSlab.getMax())
								.subtract(BigDecimal.valueOf(regularSlab.getMin()).subtract(BigDecimal.ONE)));
					}					
					break;

				case THIRD_SLAB:
					totalDiscount = totalDiscount.add(purchaseAmount.multiply(
							new BigDecimal(regularSlab.getPercentage()).divide(new BigDecimal(Constants.PERCENTAGE))));
					break;

				default:
					break;
				}
			}
		}

		roundOff(customerBill, totalDiscount);
		return customerBill;
	}

	private BigDecimal findPurchaseAmount(List<RegularSlabs> discountDetial, BigDecimal purchaseAmount,
			RegularSlabs slab) {
		BigDecimal value = BigDecimal.valueOf(slab.getMax())
				.subtract(BigDecimal.valueOf(slab.getMin()).subtract(BigDecimal.ONE));

		if (purchaseAmount.compareTo(value) < 0) {
			value = value.min(purchaseAmount);
		}

		return value;

	}

	private Bill getFinalPremiumDiscount(Bill customerBill, List<PremiumSlabs> discountDetial) {

		BigDecimal totalDiscount = BigDecimal.ZERO;
		BigDecimal purchaseAmount = customerBill.getPurchaseAmount();

		if (discountDetial != null && !discountDetial.isEmpty()) {
			for (PremiumSlabs premiumSlab : discountDetial) {

				switch (premiumSlab) {
				case FIRST_SLAB:

					totalDiscount = totalDiscount.add(purchaseAmount.min(BigDecimal.valueOf(premiumSlab.getMax())))
							.multiply(new BigDecimal(premiumSlab.getPercentage()))
							.divide(new BigDecimal(Constants.PERCENTAGE));
					purchaseAmount = purchaseAmount.subtract(BigDecimal.valueOf(premiumSlab.getMax()));
					break;

				case SECOND_SLAB:

					BigDecimal value = purchaseAmount
							.min(BigDecimal.valueOf(premiumSlab.getMin()).subtract(BigDecimal.ONE));
					totalDiscount = totalDiscount.add(value.multiply(new BigDecimal(premiumSlab.getPercentage()))
							.divide(new BigDecimal(Constants.PERCENTAGE)));

					purchaseAmount = purchaseAmount
							.subtract(BigDecimal.valueOf(premiumSlab.getMin()).subtract(BigDecimal.ONE));
					break;

				case THIRD_SLAB:
					value = BigDecimal.valueOf(premiumSlab.getMax())
							.subtract(BigDecimal.valueOf(premiumSlab.getMin()).subtract(BigDecimal.ONE));
					if (purchaseAmount.compareTo(value) > 0) {
						purchaseAmount = purchaseAmount.subtract(BigDecimal.valueOf(premiumSlab.getMax())
								.subtract(BigDecimal.valueOf(premiumSlab.getMin()).subtract(BigDecimal.ONE)));
					} else {
						value = value.min(purchaseAmount);
					}

					totalDiscount = totalDiscount.add(value.multiply(new BigDecimal(premiumSlab.getPercentage()))
							.divide(new BigDecimal(Constants.PERCENTAGE)));
					break;
					
				case FIRTH_SLAB:
					totalDiscount = totalDiscount.add(purchaseAmount.multiply(
							new BigDecimal(premiumSlab.getPercentage()).divide(new BigDecimal(Constants.PERCENTAGE))));

					break;

				default:
					break;
				}
			}
		}

		roundOff(customerBill, totalDiscount);
		return customerBill;
	}
	
	private void roundOff(Bill customerBill,BigDecimal totalDiscount) {
		int newScale = 0; 
		totalDiscount.setScale(newScale);
		customerBill.setDiscountCost(totalDiscount);	
		customerBill.setTotalCost(customerBill.getPurchaseAmount().subtract(totalDiscount).setScale(newScale));
		
	}
}
