## Project Information

### What is this project?
- A command line application that calculates a dividend distribution to owners of 
    "Krakatoa Ventures" across three classes of investors: Classes A, B, and C

### How to build this project?

- Run `mvn clean install` from the root `distribution-calculator` directory
- Then run `mvn package` to create the artifact in the target folder
     
### How to run the app?

- From the `distribution-calculator` directory, run:
    - `java -jar target/distribution-calculator-0.0.1.jar --cashDistributionAmount=1000`
    
- The output should give you a complex object with:
    - `payoutByShareClass` object that gives you the distribution breakout by 
        Class A, Class B, and Class C shares, as well as a 
    - `payoutByIndividual` object that gives you distribution breakout by investor
    
### How to run tests?

- From the `distribution-calculator` directory, run:
    - `mvn clean test`
    
## Code Information

Basic command line application built with Spring Boot's ApplicationRunner interface
    
### Directory Structure

- distribution-calculator
    |-- configuration
    |   |-- LoggingConfig (logging bean set up but not ultimately used)
    |-- controllers
    |   |-- CommandLineController (impl of ApplicationRunner)
    |-- models
    |   |-- entities
    |   |   |-- Company
    |   |   |-- Party
    |   |-- financials
    |   |   |-- Holdings (individual & aggregate share holdings)
    |   |   |-- Payouts (dividends paid for an entity across the different investment classes)
    |-- services
    |   |-- DistributionCalculatorService (methods called by controlller to calc divs)
    |-- utils
    |   |-- CalcUtils (helper methods to calculate payouts; used in service layer)
    |   |-- ReportingUtils (helper method that generates reports; used in service layer)

## Remarks / Assumptions

### Assumptions

- Dividends that are paid to initial Class B shareholders assumes that each of the 
    Class B shareholders contributes the same initial amount. (Given that Becky and Alex 
    both contributed $250 in our example).
    
    To calculate the dividends paid to repay these initial investors, a helper method in 
    CalcUtils.calcInitInvReimbursement does a simple pro-rata split across investors. 
    
    If investors contributed different amounts, this helper method could be modified 
    to accommodate such a case.
    
- Dividends distributed to different classes of shareholders assumes equal-weighting across
    classes. This might be obvious, but I am of the impression (anecdotally) that this may not
    always be the case. In case this needs to be adjusted, this can be done directly in
    the associated helper method CalcUtils.calcSharePayout accordingly.
    
- Only wrote some basic tests that could definitely be expanded to cover more of the 
    functional logic used in this program
    
### Remarks

- Thank you for the opportunity!