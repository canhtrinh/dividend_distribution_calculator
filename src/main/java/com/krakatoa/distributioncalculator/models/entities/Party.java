package com.krakatoa.distributioncalculator.models.entities;

import com.krakatoa.distributioncalculator.models.financials.Holdings;
import com.krakatoa.distributioncalculator.models.financials.Payouts;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Party {

    private String name;
    private Holdings ownership;
    private Payouts payout;

}
