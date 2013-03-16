package roark.drivers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import roark.jelenium.*;
import roark.utilities.data.*;
public class TestSuiteDriver {
	static Logger logger = Logger.getLogger(TestSuiteDriver.class);
	public static void main(String[] args) throws Exception  {
		PropertyConfigurator.configure("log4j.properties");
		testSuiteDriver("firefox");
		//testSuiteDriver("googlechrome");
	}
	
	public static void testSuiteDriver(String browserName){
		try{ 
			
			/* Note: You have to change the rootfolderpath in environment variables class package roark.utilities.data 
			 * In EnvironmentVariables.xml (Configuration folder in TAFdemo framework folder) , the browser exes path and browser driver exes path need to be changed
			 */
			
			ExcelUtilities util = new ExcelUtilities();
			TestSuite appts= new TestSuite();
			//appts = util.createTestSuiteByAppID("VTiger");
			//appts = util.createTestSuiteByTestcaseType(tcType)
			appts = util.createTestSuiteByExecutionQue("YES");
			appts.setSuiteName("VTiger_Testsuite");
			Map<String, List<TestcaseStep>> testcaseQue = util.readTestcases(appts.getTestSuiteInfo());
			appts.setTestcaseQue(testcaseQue);
			Map<String, List<Map<String, String>>> tdSets = util.readTestdata(appts.getTestSuiteInfo());
			appts.setTestdataSets(tdSets);
			List<String> locAppIDs = appts.getAppIDList();
			Map<String, List<Map<String, String>>> locSets = util.readLocators(locAppIDs);
			appts.setLocatorSets(locSets);
			appts.setRunTimeData(new HashMap<String, Map<String, String>>());
			appts.setExecBrowserName(browserName);
			appts.runAllTestcases();
			appts.generateTestReport();
		}catch (Exception e){
			logger.error("Exception in Testsuite driver-"+ e.getMessage());
			//e.printStackTrace();
		}
	}
	
}