package com.example.assignment.Manager;

import com.example.assignment.Model.BANK;
import com.example.assignment.Model.Loan;
import com.example.assignment.Strategies.InterestCalculationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class LoanManager {
    HashMap<String,Loan> loans=new HashMap<>();
    InterestCalculationStrategy interestCalculationStrategy;
    public LoanManager(InterestCalculationStrategy interestCalculationStrategy){
        this.interestCalculationStrategy = interestCalculationStrategy;
    }

    public void startLoan(BANK bank, String borrower,int principal_amount,int interest,int time){
        int total_amount = principal_amount+interestCalculationStrategy.calculateInterest(principal_amount,interest,time);
        int emi_amount = (int)Math.ceil(((double)total_amount)/(time*(12.00)));
        Loan loan = new Loan(principal_amount,total_amount,bank,borrower,null,emi_amount,time);
        loans.put(getLoanKey(bank,borrower),loan);
    }
    public String getLoanKey(BANK bank,String borrower){
        return bank.name()+":"+borrower;
    }

    public void payLumpsum(int emi_no, int lump_sum, String borrower, BANK bank){
        Loan loan= loans.get(getLoanKey(bank,borrower));
        if(emi_no < loan.getTime()*12 && loan.getLumpsums()== null)
        {
            loan.setLumpsums(new TreeMap<>());
        }
        loan.getLumpsums().put(emi_no,lump_sum);
    }

    public int getAmountPaid(int emi_no, String borrower, BANK bank){
        Loan loan = loans.get(getLoanKey(bank,borrower));
        int emis_paid = loan.getEmi_amount()*emi_no;
        int lump_sum = 0;
        if(loan.getLumpsums() != null && loan.getLumpsums().firstKey() <= emi_no){
           for(Map.Entry<Integer,Integer> entry: loan.getLumpsums().entrySet()){
               if(entry.getKey() <= emi_no){
                   lump_sum += entry.getValue();
               }
           }
        }
        return emis_paid + lump_sum;
    }
    public int getEmisLeft(String borrower,int amount_paid, BANK bank){
        Loan loan = loans.get(getLoanKey(bank,borrower));
        int amount_left = loan.getTotalAmount() - amount_paid;
        if(amount_left <= 0){
            return 0;
        }
        return (int) Math.ceil((double) amount_left/(double)loan.getEmi_amount());
    }
}
