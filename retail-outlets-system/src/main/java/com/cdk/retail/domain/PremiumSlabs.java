package com.cdk.retail.domain;

public enum PremiumSlabs {
	FIRST_SLAB(0, 4000, 10), SECOND_SLAB(4001, 8000, 15), THIRD_SLAB(8001, 12000, 20),
	FIRTH_SLAB(12001, Integer.MAX_VALUE, 30);

	private Integer min;
	private Integer max;
	private Integer percentage;

	private PremiumSlabs(Integer min, Integer max, Integer percentage) {
		this.min = min;
		this.max = max;
		this.percentage = percentage;
	}

	public Integer getMin() {
		return min;
	}

	public Integer getMax() {
		return max;
	}

	public Integer getPercentage() {
		return percentage;
	}

	public void setMin(Integer min) {
		this.min = min;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	public void setPercentage(Integer percentage) {
		this.percentage = percentage;
	}

}
