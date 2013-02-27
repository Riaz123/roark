package roark.drivers;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import roark.jelenium.TestSuite;
import roark.jelenium.TestcaseStep;
import roark.utilities.data.ExcelUtilities;


public class TestSuites implements Runnable {
	private static Logger logger = Logger.getLogger(TestSuites.class);
	private String suiteType;
	private String suiteSelection;
	private String browserName;

    
    public  TestSuites(String suiteType , String suiteSelection, String browserName){
    	this.suiteType= suiteType;
    	this.suiteSelection= suiteSelection;
    	this.browserName= browserName;
		
	}


	@Override
	public void run() {
		PropertyConfigurator.configure("log4j.properties");
		if(this.suiteType.equalsIgnoreCase("ApplicationID")==true){
			this.testSuiteByAppID(this.suiteSelection, this.browserName);
		}else if(this.suiteType.equalsIgnoreCase("TestcaseType")==true){
			this.testSuiteByTestcaseType(this.suiteSelection, this.browserName);
		}else if(this.suiteType.equalsIgnoreCase("QueStatus")==true){
			this.testSuiteByExecutionQue(this.suiteSelection, this.browserName);
		}else{
			this.testSuiteByExecutionQue("YES", this.browserName);
		}
		
	}
	
	public void testSuiteByAppID(String appID, String browserName){
		
		try{ 
			
			/* Note: You have to change the rootfolderpath in environment variables class package roark.utilities.data 
			 * In EnvironmentVariables.xml (Configuration folder in TAFdemo framework folder) , the browser exes path and browser driver exes path need to be changed
			 */
			//PropertyConfigurator.configure("log4j.properties");
			ExcelUtilities util = new ExcelUtilities();
			TestSuite appts= new TestSuite();
			appts = util.createTestSuiteByAppID(appID);
			appts.setSuiteName(appID+ "_Testsuite");
				
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
	
	public void testSuiteByTestcaseType(String testcaseType, String browserName){
		try{ 
				
				/* Note: You have to change the rootfolderpath in environment variables class package roark.utilities.data 
				 * In EnvironmentVariables.xml (Configuration folder in TAFdemo framework folder) , the browser exes path and browser driver exes path need to be changed
				 */
				//PropertyConfigurator.configure("log4j.properties");
				ExcelUtilities util = new ExcelUtilities();
				TestSuite typets= new TestSuite();
				typets = util.createTestSuiteByTestcaseType(testcaseType);
				
				typets.setSuiteName(testcaseType+ "_Testsuite");
					
				Map<String, List<TestcaseStep>> testcaseQue = util.readTestcases(typets.getTestSuiteInfo());
				typets.setTestcaseQue(testcaseQue);
				Map<String, List<Map<String, String>>> tdSets = util.readTestdata(typets.getTestSuiteInfo());
				typets.setTestdataSets(tdSets);
				List<String> locAppIDs = typets.getAppIDList();
				Map<String, List<Map<String, String>>> locSets = util.readLocators(locAppIDs);
				typets.setLocatorSets(locSets);
				typets.setRunTimeData(new HashMap<String, Map<String, String>>());
				typets.setExecBrowserName(browserName);
				typets.runAllTestcases();
				typets.generateTestReport();
			}catch (Exception e){
				logger.error("Exception in Testsuite driver-"+ e.getMessage());
				//e.printStackTrace();
			}		
		
	}
	
	public void testSuiteByExecutionQue(String queStatus, String browserName){
		try{ 
			
			/* Note: You have to change the rootfolderpath in environment variables class package roark.utilities.data 
			 * In EnvironmentVariables.xml (Configuration folder in TAFdemo framework folder) , the browser exes path and browser driver exes path need to be changed
			 */
			//PropertyConfigurator.configure("log4j.properties");
			ExcelUtilities util = new ExcelUtilities();
			TestSuite quets= new TestSuite();
			quets = util.createTestSuiteByExecutionQue(queStatus);
			
			quets.setSuiteName(queStatus+ "_Testsuite");
			Map<String, List<TestcaseStep>> testcaseQue = util.readTestcases(quets.getTestSuiteInfo());
			quets.setTestcaseQue(testcaseQue);
			Map<String, List<Map<String, String>>> tdSets = util.readTestdata(quets.getTestSuiteInfo());
			quets.setTestdataSets(tdSets);
			List<String> locAppIDs = quets.getAppIDList();
			Map<String, List<Map<String, String>>> locSets = util.readLocators(locAppIDs);
			quets.setLocatorSets(locSets);
			quets.setRunTimeData(new HashMap<String, Map<String, String>>());
			quets.setExecBrowserName(browserName);
			quets.runAllTestcases();
			quets.generateTestReport();
		}catch (Exception e){
			logger.error("Exception in Testsuite driver-"+ e.getMessage());
			//e.printStackTrace();
		}		
	}
}