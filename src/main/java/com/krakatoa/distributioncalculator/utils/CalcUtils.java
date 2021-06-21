package com.krakatoa.distributioncalculator.utils;

import com.krakatoa.distributioncalculator.models.entities.Party;
import com.krakatoa.distributioncalculator.models.financials.Holdings;

/**
 * Helper methods to calculate pro-rata share of distributions for:
 * 1 - the repayment of the initial Class B investors (calcInitInvReimbursement)
 * 2 - the proportional repayment to share class holders (calcSharePayout)
 */
public class CalcUtils {

    /**
     * calculates and returns the proportion of the total initial investment (in dollars)
     * owed to a Class B shareholder by taking the ratio of the number of Class B shares that party owns
     * to the total number of Class B shares
     *
     * @param party
     * @param totalClassBShares
     * @param totalInitialInvestment
     * @return
     */
    public static Double calcInitInvReimbursement(Party party, Integer totalClassBShares, Double totalInitialInvestment) {

        Holdings partyOwnershipDetails = party.getOwnership();

        return ( (double) partyOwnershipDetails.getNumBShares() / (double) totalClassBShares) * totalInitialInvestment;

    }

    /**
     * calculates and returns the proportion of the remaining dividends to pay (totalInvToPay)
     * owed to each shareholder by taking the ratio of the number of relevant shares (numerator) that party owns
     * to the total number of shares of the company
     *
     * @param numerator
     * @param numTotalShares
     * @param totalInvToPay
     * @return
     */
    public static Double calcSharePayout(Integer numerator, Integer numTotalShares, Double totalInvToPay) {

        return ( (double) numerator / (double) numTotalShares) * totalInvToPay;

    }

}
