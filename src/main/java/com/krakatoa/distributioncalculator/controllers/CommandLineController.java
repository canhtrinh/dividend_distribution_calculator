package com.krakatoa.distributioncalculator.controllers;

import com.krakatoa.distributioncalculator.services.DistributionCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Controller;

/**
 * CommandLineRunner is a Controller class
 * that implements ApplicationRunner,
 * a Spring interface that abstracts the command line runner.
 */
@Controller
public class CommandLineController implements ApplicationRunner {

    private static final String CASH_DISTRIBUTION_ARG = "cashDistributionAmount";

    @Autowired
    DistributionCalculatorService calculationService;

    /**
    * Extracts cashDistributionAmount command line argumentmvn ,
    * sets the amount on the calculationService
    * and calls the getTotalPayout method on the calculationService that determines payouts
    * */
    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (args.getOptionValues(CASH_DISTRIBUTION_ARG) != null && args.getOptionValues(CASH_DISTRIBUTION_ARG).size() > 0) {

            Double distribution = Double.parseDouble(args.getOptionValues(CASH_DISTRIBUTION_ARG).get(0));

            calculationService.setRemainingAmtToDistribute(distribution);

            System.out.println(calculationService.getTotalPayout());

        }

    }
}
