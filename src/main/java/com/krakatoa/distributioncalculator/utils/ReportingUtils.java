package com.krakatoa.distributioncalculator.utils;

import com.krakatoa.distributioncalculator.models.entities.Company;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.function.Function;

public class ReportingUtils {

    private static String CLASS_A               = "Class A";
    private static String CLASS_B               = "Class B";
    private static String CLASS_C               = "Class C";
    private static String PAYOUT_BY_IND         = "payoutByIndividual";
    private static String PAYOUT_BY_SHARE_CLASS = "payoutByShareClass";
    private static DecimalFormat df             = new DecimalFormat("0.00");

    static {
        /*decimals rounded down as per requirement*/
        df.setRoundingMode(RoundingMode.DOWN);
    }

    private static Function<Double, String> format = num -> df.format(num);

    /**
     * Generates a map of dividend payouts paid by investor "payoutByIndividual"
     *  and another map of dividends payouts paid by class "payoutByShareClass"
     * @param company
     * @return
     * @throws JsonProcessingException
     */
    public static HashMap<String, HashMap<String, String>> generateReport(Company company)
            throws JsonProcessingException
    {

        HashMap<String, HashMap<String, String>> results    = new HashMap<>();
        HashMap<String, String> payoutByIndividual          = new HashMap<>();
        HashMap<String, String> payoutByShareClass          = new HashMap<>();

        company.getOwners()
            .forEach(p -> payoutByIndividual.put(p.getName(),
                format.apply(p.getPayout().getInitInvRepaid()
                        + p.getPayout().getClassADivsPaid()
                        + p.getPayout().getClassBDivsPaid()
                        + p.getPayout().getClassCDivsPaid())
            )
        );

        payoutByShareClass.put(CLASS_A, format.apply(company.getPayout().getClassADivsPaid()));
        payoutByShareClass.put(CLASS_B, format.apply(company.getPayout().getClassBDivsPaid()
                + company.getPayout().getInitInvRepaid()));
        payoutByShareClass.put(CLASS_C, format.apply(company.getPayout().getClassCDivsPaid()));

        results.put(PAYOUT_BY_IND, payoutByIndividual);
        results.put(PAYOUT_BY_SHARE_CLASS, payoutByShareClass);

        return results;

    }

}
