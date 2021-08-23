package com.example.assignment.Controller;

import com.example.assignment.Manager.LoanManager;
import com.example.assignment.Model.BANK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


public class LoanService {


    private LoanManager loanManager;

    public LoanService(LoanManager loanManager){
        this.loanManager = loanManager;
    }

    public void createLoan(BANK bank, String borrower,int principal_amount,int time,int interest){
        loanManager.startLoan(bank,borrower,principal_amount,interest,time);
    }
    public void createPayment(BANK bank, String borrower,int lump_sum,int emi_no){
        loanManager.payLumpsum(emi_no,lump_sum,borrower,bank);
    }
    public String getBalance(BANK bank, String borrower,int emi_no){
        int paid = loanManager.getAmountPaid(emi_no, borrower, bank);
        int emis_left = loanManager.getEmisLeft(borrower,paid,bank);
        return bank.name()+" "+borrower+" "+paid+" "+emis_left;
    }
}
