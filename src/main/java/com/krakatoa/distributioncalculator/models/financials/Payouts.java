package com.krakatoa.distributioncalculator.models.financials;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Payouts class captures the dividends paid for an entity
 * across the different investment classes
 *
 * Incrementer helper functions are also implemented here
 * */
@Data
@NoArgsConstructor
public class Payouts {

    private Double classADivsPaid = 0.0;
    private Double classBDivsPaid = 0.0;
    private Double classCDivsPaid = 0.0;
    private Double initInvRepaid  = 0.0;

    public void incADivsPaid(Double payment) { classADivsPaid += payment; }
    public void incBDivsPaid(Double payment) {
        classBDivsPaid += payment;
    }
    public void incCDivsPaid(Double payment) {
        classCDivsPaid += payment;
    }
    public void incInitInvDivsPaid(Double payment) {
        initInvRepaid += payment;
    }

}
