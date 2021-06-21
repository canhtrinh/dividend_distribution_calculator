package com.krakatoa.distributioncalculator.models.entities;

import com.krakatoa.distributioncalculator.models.financials.Holdings;
import com.krakatoa.distributioncalculator.models.financials.Payouts;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Company {

    private String name;
    private List<Party> owners;
    private Holdings aggHoldings;
    private Payouts payout;

    /**
     * Constructor function that leverages the @AllArgsConstructor and initializes the company
     */
    public Company(String name) {

        this(name, new ArrayList<>(), new Holdings(), new Payouts());

        setupCompany();

    }

    /**
     * Establishes three initial owners of the company
     * with holdings (given as part of the discussion prompt)
     */
    public void setupCompany() {

        addOwner(new Party("Becky", new Holdings(250.00, 0, 10, 5), new Payouts()));
        addOwner(new Party("Alex", new Holdings(250.0, 0, 10, 0), new Payouts()));
        addOwner(new Party("David", new Holdings(0.00, 10, 0, 0), new Payouts()));

    }

    /**
     * Adds an owner to the owners list and aggregates that
     * owner's holdings profile to the overall company profile
     *
     * @param  party the input party
     */
    public void addOwner(Party party) {

        owners.add(party);

        Holdings partyOwnershipDetails = party.getOwnership();

        aggHoldings.setInitInvAmt(aggHoldings.getInitInvAmt() + partyOwnershipDetails.getInitInvAmt());
        aggHoldings.setNumAShares(aggHoldings.getNumAShares() + partyOwnershipDetails.getNumAShares());
        aggHoldings.setNumBShares(aggHoldings.getNumBShares() + partyOwnershipDetails.getNumBShares());
        aggHoldings.setNumCShares(aggHoldings.getNumCShares() + partyOwnershipDetails.getNumCShares());

    }

}
