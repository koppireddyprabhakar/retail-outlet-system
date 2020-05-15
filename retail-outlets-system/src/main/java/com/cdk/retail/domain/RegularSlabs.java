package com.cdk.retail.domain;

public enum RegularSlabs {
	FIRST_SLAB(0, 5000, 0), SECOND_SLAB(5001, 10000, 10), THIRD_SLAB(10001, Integer.MAX_VALUE, 20);

	private Integer min;
	private Integer max;
	private Integer percentage;

	private RegularSlabs(Integer min, Integer max, Integer percentage) {
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
