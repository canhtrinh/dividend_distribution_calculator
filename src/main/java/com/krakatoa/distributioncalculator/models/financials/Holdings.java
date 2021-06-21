package com.krakatoa.distributioncalculator.models.financials;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Holdings class captures the holdings for an entity
 * across the different investment classes
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Holdings {

    private Double initInvAmt = 0.0;
    private int numAShares    = 0;
    private int numBShares    = 0;
    private int numCShares    = 0;

}
