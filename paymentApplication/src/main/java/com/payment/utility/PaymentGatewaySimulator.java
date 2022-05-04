package com.payment.utility;

import com.payment.exception.InsufficientBalanceException;

import java.util.HashMap;
import java.util.Map;

public class PaymentGatewaySimulator {
    private static Map<String, Double> accountBalanceMap = new HashMap<String, Double>();

    static {
        accountBalanceMap.put("accnt-1", 1200.00);
        accountBalanceMap.put("accnt-2", 200.00);
        accountBalanceMap.put("accnt-3", 500.00);
    }

    public static boolean validateFareBalance(String accountNo, Double fare){
        if(fare>accountBalanceMap.get(accountNo)){
            throw new InsufficientBalanceException("You do not have sufficient balance in your account.");
        }
        return true;
    }
}
