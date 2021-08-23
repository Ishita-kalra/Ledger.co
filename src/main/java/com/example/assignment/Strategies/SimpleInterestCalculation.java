package com.example.assignment.Strategies;

public class SimpleInterestCalculation implements InterestCalculationStrategy{

    @Override
    public int calculateInterest(int principal_amount, int interest,int time) {
        return (int)Math.ceil(((double)(principal_amount * interest * time))/100.00);
    }
}
