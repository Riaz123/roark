package roark.drivers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;

import roark.jelenium.TestSuite;
import roark.jelenium.TestcaseStep;
import roark.utilities.data.ExcelUtilities;


public class CBT_ParallelDriver {
	static Logger logger = Logger.getLogger(CBT_ParallelDriver.class);


	  @DataProvider(parallel = true)
	  public Object[][] datasets() {
	    return new Object[][] {
	      new Object[] {  "FIREFOX" },
	      new Object[] { "FIREFOX" },
	    };
	  }
	  @Test(dataProvider="datasets")
	  public  void suiteDriver(String browserName){
		  	long tid = Thread.currentThread().getId();
		  	logger.info("ThreadID::"+tid);
		    PropertyConfigurator.configure("log4j.properties");
			try{ 
				testSuiteDriver(browserName);
			}catch(Exception e){
				logger.error("Exception in suiteDriver - "+e.getMessage());
			}
	  }
		
	  public  void testSuiteDriver(String browserName){
				try{ 
					
					/* Note: You have to change the rootfolderpath in package roark.utilities.data 
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
					e.printStackTrace();
				}
		}
}
