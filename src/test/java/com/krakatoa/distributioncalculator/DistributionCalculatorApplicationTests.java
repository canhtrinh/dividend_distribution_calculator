package com.krakatoa.distributioncalculator;

import com.krakatoa.distributioncalculator.controllers.CommandLineController;
import com.krakatoa.distributioncalculator.services.DistributionCalculatorService;
import org.assertj.core.api.Assertions;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.system.OutputCaptureRule;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.atLeast;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(args = "--cashDistributionAmount=1000")
class DistributionCalculatorApplicationTests {

	@Rule
	OutputCaptureRule outputCapture = new OutputCaptureRule();

	@Autowired
	ApplicationArguments applicationArguments;

	@SpyBean
    DistributionCalculatorService distributionCalculatorService;

	@Autowired
    CommandLineController commandLineController;


	@Test
	void assertServiceWillRun() throws Exception {

		commandLineController.run(applicationArguments);
		Mockito.verify(distributionCalculatorService, atLeast(1)).setRemainingAmtToDistribute(anyDouble());
		Mockito.verify(distributionCalculatorService, atLeast(1)).getTotalPayout();

	}

	@Ignore
//	@Test
	public void shouldGenerateResultFiles() throws Exception {

		commandLineController.run(applicationArguments);
		Assertions.assertThat(outputCapture).contains("payoutByShareClass");
		Assertions.assertThat(outputCapture).contains("payoutByIndividual");

	}

}
