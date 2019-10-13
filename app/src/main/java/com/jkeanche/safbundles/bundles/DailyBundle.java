package com.jkeanche.safbundles.bundles;

public class DailyBundle {
    private int optionNumber;
    private int bundleAmount;
    private int bundleValue;

    public DailyBundle() {
        this(-1,-1,-1); //default bundle with uspecified parameters
    }

    public DailyBundle(int optionNumber, int bundleAmount, int bundleValue) {
        this.optionNumber = optionNumber;
        this.bundleAmount = bundleAmount;
        this.bundleValue = bundleValue;
    }



    public int getOptionNumber() {
        return optionNumber;
    }

    public void setOptionNumber(int optionNumber) {
        this.optionNumber = optionNumber;
    }


    public int getBundleAmount() {
        return bundleAmount;
    }

    public void setBundleAmount(int bundleAmount) {
        this.bundleAmount = bundleAmount;
    }

    public int getBundleValue() {
        return bundleValue;
    }

    public void setBundleValue(int bundleValue) {
        this.bundleValue = bundleValue;
    }


}
