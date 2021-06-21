package com.krakatoa.distributioncalculator.services;

import com.krakatoa.distributioncalculator.models.financials.Holdings;
import com.krakatoa.distributioncalculator.models.entities.Company;
import com.krakatoa.distributioncalculator.utils.CalcUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

import static com.krakatoa.distributioncalculator.utils.ReportingUtils.generateReport;

/**
 * Service class where
 * 1 - company is established in the constructor
 * 2 - getTotalPayout invokes two helper methods that calculate the rebates initial Class B investorss
 *      and the pro rata distribution to each of the class owners
 */
@Setter
@Getter
@Service
public class DistributionCalculatorService {

    @Autowired
    Logger logger;

    private Company company;

    private Double remainingAmtToDistribute;

    /**
     * Constructor that sets up the company
     */
    public DistributionCalculatorService() {

        company = new Company("Krakatoa");

    }

    /**
     * helper method that invokes two helper methods that
     * calculates payout to initial investors and then payout to class holders
     *
     * @return JSON object with payout structure as a string
     * @throws JsonProcessingException
     */
    public String getTotalPayout() throws JsonProcessingException {

        payClassBInitialInvestors();
        payClassShareholders();

        HashMap<String, HashMap<String, String>> results = generateReport(company);
        String resultsAsString = new ObjectMapper().writeValueAsString(results);

        return resultsAsString;

    }

    /**
     * This method:
     * 1 - finds total amount of initial investment
     * 2 - decreases remainingAmtToDistribute (to be used by payClassShareholders later)
     *      by the amount of the total initial investment
     * 3 - distributes the total investment pro-rata among Class B holders (storing the result
     *      in both the party object and the company object),
     * using the calcInitInvReimbursement utils helper method
     */
    public void payClassBInitialInvestors() {

        if (remainingAmtToDistribute <= 0)
            return;

        Holdings companyOwnershipDetails = company.getAggHoldings();
        Integer totalClassBShares = companyOwnershipDetails.getNumBShares();
        Double totalInitialInvestment = companyOwnershipDetails.getInitInvAmt();
        Double amountToPay = remainingAmtToDistribute > totalInitialInvestment
                ? totalInitialInvestment
                : remainingAmtToDistribute;

        remainingAmtToDistribute -= amountToPay;

        company.getOwners().stream()
            .filter(p -> p.getOwnership().getNumBShares() > 0)
            .forEach(p -> {

                Double pmt = CalcUtils.calcInitInvReimbursement(p, totalClassBShares, amountToPay);
                p.getPayout().incInitInvDivsPaid(pmt);
                company.getPayout().incInitInvDivsPaid(pmt);

            });

    }

    /**
     * This method:
     * distributes the pro rata amount of dividends to each investors by share class
     * using the calcSharePayout utils helper method
     */
    public void payClassShareholders() {

        if (remainingAmtToDistribute <= 0)
            return;

        Holdings aggHoldings = company.getAggHoldings();

        Double totalPayout = remainingAmtToDistribute;

        Integer totalCoShares = aggHoldings.getNumAShares() + aggHoldings.getNumBShares() + aggHoldings.getNumCShares();

        company.getOwners().stream()
            .forEach( p -> {

                Double aPmt = CalcUtils.calcSharePayout(p.getOwnership().getNumAShares(), totalCoShares, totalPayout);
                Double bPmt = CalcUtils.calcSharePayout(p.getOwnership().getNumBShares(), totalCoShares, totalPayout);
                Double cPmt = CalcUtils.calcSharePayout(p.getOwnership().getNumCShares(), totalCoShares, totalPayout);

                p.getPayout().incADivsPaid(aPmt);
                p.getPayout().incBDivsPaid(bPmt);
                p.getPayout().incCDivsPaid(cPmt);

                company.getPayout().incADivsPaid(aPmt);
                company.getPayout().incBDivsPaid(bPmt);
                company.getPayout().incCDivsPaid(cPmt);

                remainingAmtToDistribute -= (aPmt + bPmt + cPmt);

            });

    }

}