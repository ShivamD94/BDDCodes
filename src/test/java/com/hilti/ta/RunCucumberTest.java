package com.hilti.ta;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = { "target/test-classes/features" },
		glue = { "com.hilti.ta.steps"},
		plugin = {"html:target/cukes", "json:target/cucumber-report.json", "junit:target/cucumber-report.xml", "pretty"},
		strict = true,

		tags = { "@Smoke" },
		dryRun = true ,
		monochrome = true)
public class RunCucumberTest extends AbstractTestNGCucumberTests {
}

//testautomation-interview-project/src/test/java/com/hilti/ta/steps/