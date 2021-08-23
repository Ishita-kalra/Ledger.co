package com.example.assignment.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import java.util.TreeMap;

@Data
@ToString
@AllArgsConstructor
public class Loan {
    private int PrincipalAmount;  //just storing for debugging and data view purpose
    private int TotalAmount;
    private BANK bank;
    private String borrowerName;
    private TreeMap<Integer, Integer> lumpsums;  //key = emi_no after which lumpsum is paid
    private int emi_amount;
    private int time;       //time in years
}
