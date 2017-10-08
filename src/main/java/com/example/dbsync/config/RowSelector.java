package com.example.dbsync.config;

public class RowSelector {
	
	private int index, min, max;

	public RowSelector(int index, int min, int max) {
		super();
		this.index = index;
		this.min = min;
		this.max = max;
	}

	@Override
	public String toString() {
		return "RowSelector [index=" + index + ", min=" + min + ", max=" + max + "]";
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

}
