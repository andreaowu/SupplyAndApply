package com.uem.supplyandapply;

public class SupplyPartComparable implements Comparable<SupplyPartComparable> {
	private String name;
    private int count;

    public SupplyPartComparable(int count, String name) {
        this.count = count;
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	@Override
	public int compareTo(SupplyPartComparable arg0) {
		int comparedCount = arg0.getCount();
		return  this.count - comparedCount ;
	}

}
