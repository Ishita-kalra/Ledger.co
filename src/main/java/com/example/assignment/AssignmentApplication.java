package com.example.assignment;

import com.example.assignment.Controller.LoanService;
import com.example.assignment.Exceptions.InvalidInputException;
import com.example.assignment.Manager.LoanManager;
import com.example.assignment.Model.BANK;
import com.example.assignment.Strategies.InterestCalculationStrategy;
import com.example.assignment.Strategies.SimpleInterestCalculation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
public class AssignmentApplication {


    public static void main(String[] args) {
        SpringApplication.run(AssignmentApplication.class, args);
         //add the location of your txt file here
        String path = System.getProperty("user.home")+"/Documents/test.txt";
        if(Files.exists(Paths.get(path))) {
            try {
                File file = new File(path);
                BufferedReader br = new BufferedReader(new FileReader(file));
                InterestCalculationStrategy interestCalculationStrategy = new SimpleInterestCalculation();
                LoanManager loanManager = new LoanManager(interestCalculationStrategy);
                LoanService loanService = new LoanService(loanManager);
                String st;
                String res;
                while ((st = br.readLine()) != null) {
                    String[] instructions = st.split(" ");
                    switch (instructions[0]) {
                        case "LOAN":
                            if (instructions.length == 6) {
                                loanService.createLoan(BANK.valueOf(instructions[1]), instructions[2], Integer.valueOf(instructions[3]), Integer.valueOf(instructions[4]), Integer.valueOf(instructions[5]));
                            } else
                                throw new InvalidInputException("Invalid LOAN statement input!!");
                            break;
                        case "PAYMENT":
                            if (instructions.length == 5) {
                                loanService.createPayment(BANK.valueOf(instructions[1]), instructions[2], Integer.valueOf(instructions[3]), Integer.valueOf(instructions[4]));
                            } else
                                throw new InvalidInputException("Invalid PAYMENT statement input!!");
                            break;
                        case "BALANCE":
                            if (instructions.length == 4) {
                                res = loanService.getBalance(BANK.valueOf(instructions[1]), instructions[2], Integer.valueOf(instructions[3]));
                            } else
                                throw new InvalidInputException("Invalid BALANCE statement input!!");
                            System.out.println(res);
                            break;
                        default:
                            System.out.println("Invalid Input!!");
                            throw new InvalidInputException("Wrong Format of Input!!");
                    }

                }

            } catch (Exception e) {
                System.out.println("Exception Occured!! : " + e);
            }
        }else {
            System.out.println("Wrong path of input file recieved!!");
        }
    }

}
