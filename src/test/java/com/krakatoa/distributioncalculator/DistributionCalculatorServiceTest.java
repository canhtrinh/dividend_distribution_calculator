package com.krakatoa.distributioncalculator;

import com.krakatoa.distributioncalculator.services.DistributionCalculatorService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class DistributionCalculatorServiceTest {

    @Autowired
    DistributionCalculatorService distributionCalculatorService;

    @BeforeEach
    public void setup() {

        distributionCalculatorService.setRemainingAmtToDistribute(1000.00);

    }

    @Test
    public void instantiatesACompanyWithMembers() throws Exception {

        assertNotNull(distributionCalculatorService.getCompany());
        assertNotNull(distributionCalculatorService.getCompany().getName());

        distributionCalculatorService.getCompany().getOwners()
                .forEach(owner -> {
                    assertNotNull(owner.getName());
                });
    }

    @Test
    public void calculatesTotalPayments() throws Exception {

        String results = distributionCalculatorService.getTotalPayout();
        Assertions.assertThat(results).contains("payoutByShareClass");
        Assertions.assertThat(results).contains("payoutByIndividual");

    }

}
