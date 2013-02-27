package roark.jelenium;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import roark.jelenium.TestcaseStep;
import roark.utilities.data.EnvironmentVariables;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class TestSuite {
	static Logger logger = Logger.getLogger(TestSuite.class);
	private String suiteName;
	private Map<String, String> runSettings = new HashMap<String, String>() ;
	private WebDriver webappdriver ;
	private Map<String, Map<String, String>> testSuiteInfo = new LinkedHashMap<String, Map<String, String>>();
	private Map<String, List<TestcaseStep>> testcaseQue ;
	private Map<String, List<Map<String, String>>> locatorSets= new LinkedHashMap<String, List<Map<String, String>>>();
	private Map<String, List<Map<String, String>>> testdataSets =new LinkedHashMap<String, List<Map<String, String>>>();
	private String execBrowserName;
	private Map<String, Map<String, String>> runTimeData = new LinkedHashMap<String, Map<String, String>>(); 
	public enum BrowserType{
		FIREFOX, GOOGLECHROME,  SAFARI, INTERNETEXPLORER;
	}
	public TestSuite(){
		
	}


	public String getSuiteName() {
		return suiteName;
	}

	public void setSuiteName(String suiteName) {
		this.suiteName = suiteName;
	}


	public Map<String, String> getRunSettings() {
		return runSettings;
	}


	public void setRunSettings(Map<String, String> runSettings) {
		this.runSettings = runSettings;
	}


	public WebDriver getWebappdriver() {
		return webappdriver;
	}


	public void setWebappdriver(WebDriver webappdriver) {
		this.webappdriver = webappdriver;
	}

	public Map<String, Map<String, String>> getTestSuiteInfo() {
		return testSuiteInfo;
	}

	public void setTestSuiteInfo(Map<String, Map<String, String>> testSuiteInfo) {
		this.testSuiteInfo = testSuiteInfo;
	}

	public Map<String, List<TestcaseStep>> getTestcaseQue() {
		return testcaseQue;
	}

	public void setTestcaseQue(Map<String, List<TestcaseStep>> testcaseQue) {
		this.testcaseQue = testcaseQue;
	}


	public Map<String, List<Map<String, String>>> getLocatorSets() {
		return locatorSets;
	}


	public void setLocatorSets(Map<String, List<Map<String, String>>> locatorSets) {
		this.locatorSets = locatorSets;
		this.updateLocatorValues();
		logger.info("LocatorSets are set");
	}

	private void updateLocatorValues() {
		boolean locatorFound=false;

		try{
			Map<String, List<TestcaseStep>> testcaseSteps = this.getTestcaseQue();
			for(String tcID : testcaseSteps.keySet()){
				for(TestcaseStep tcStep: testcaseSteps.get(tcID)){
					String fieldName =tcStep.getFieldName();
					if(fieldName.equalsIgnoreCase("NA")==false){
						for(Map<String , String> locatorSet: this.getLocatorSets().get(tcStep.getApplicationID())){
							if(locatorSet.get("FieldName").equalsIgnoreCase(fieldName)==true){
								tcStep.setFieldDefinition(locatorSet.get("FieldDefinition"));
								locatorFound=true ;
								break;
							}else{
								locatorFound=false ;
							}
						}
						if(locatorFound==false){
							tcStep.setFieldName(fieldName);
							tcStep.setFieldDefinition("LOCATOR_NOT_FOUND");
							logger.error("Locator not found for "+fieldName);
						}
					}else{
						tcStep.setFieldName("NOT_APPLICABLE");
						tcStep.setFieldDefinition("NOT_APPLICABLE");
						locatorFound=true ;
					}

				}
			}
			logger.info("DONE :: updateLocatorValues");

		}catch(Exception e){
			logger.error("Exception in updateLocatorValues - \n stacktraceInfo::"+e.getMessage());
			//e.printStackTrace();
		}
		
	}

	/**
	 * @return the testdataSets
	 */
	public Map<String, List<Map<String, String>>> getTestdataSets() {
		return testdataSets;
	}

	/**
	 * @param testdataSets the testdataSets to set
	 */
	public void setTestdataSets(Map<String, List<Map<String, String>>> testdataSets) {
		this.testdataSets = testdataSets;
		logger.info("DONE :: setTestdataSets");
		this.updateTestdataValues();
	}

	private void updateTestdataValues() {
		try{
			EnvironmentVariables ev = EnvironmentVariables.getInstance();
			Map<String, List<Map<String, String>>> tdSets = this.getTestdataSets();
			Map<String, List<TestcaseStep>> testcaseSteps = this.getTestcaseQue();
			for(String tcID:testcaseSteps.keySet() ){
				List<Map<String, String>> tdSet = tdSets.get(tcID);
				for(TestcaseStep tcStep: testcaseSteps.get(tcID)){
					String testdataValue;
					try{
						String tdType = tcStep.getTestDataType();
						if(tdType.equalsIgnoreCase("NA")==false){
							logger.info("UpdateTestdata value - \n TestcaseID:"+ tcID + " , StepID:"+tcStep.getStepID());
							logger.info("TestdataName::" + tcStep.getTestDataName()+ " , TestdataType::"+tdType);
							if(tdType.toUpperCase().equals("XLPARAMETER")==true){
								try{
									boolean tdnameFound = false;
									for(Map<String, String> tdRow: tdSet){
										try{
											if(tdRow.get("TestdataName").trim().equalsIgnoreCase(tcStep.getTestDataName().trim())==true){
												testdataValue=tdRow.get("TestdataValue").trim();
												tcStep.setTestDataValue(testdataValue);
												logger.info("TestdataName -"+tcStep.getTestDataName() +  " is set to ::"+tdRow.get("TestdataValue"));
												tdnameFound=true;
												break;
											}else{
												tdnameFound=false;
											}
										}catch(Exception e){
											tdnameFound=false;
											logger.error("Exception in fetching testdatavalue for "+tcStep.getTestDataName() + "\n "+e.getMessage());
										}
									}
									if(tdnameFound==false){
										logger.error("TestdataName is not found in the testdataSets ");
										testdataValue="TESTDATA_NOTFOUND";
										tcStep.setTestDataValue(testdataValue);
									}
								}catch(Exception e){
									logger.error("Exception in updateTestdataValues for \n testdataname::"+tcStep.getTestDataName() +
											"  in TestcaseID::"+ tcStep.getTestcaseID() + " at stepId:"+tcStep.getStepID());
									logger.error("\n StacktraceInfo::"+e.getMessage());
									testdataValue="TESTDATA_NOTFOUND";
									tcStep.setTestDataValue(testdataValue);
								}
							
							}else if(tdType.toUpperCase().equals("ENVIRONMENTVARIABLE")==true){
								try{
									logger.info("tdType is ENVIRONMENTVARIABLE @step#"+tcStep.getStepID() + " in TestcaseID#"+tcStep.getTestcaseID());
									String evValue =ev.getTestParameterValue(tcStep.getApplicationID(), tcStep.getTestDataName());
									if(evValue.equals("NOT_FOUND")==false){
										tcStep.setTestDataValue(evValue);
										logger.info("testdatavalue is set by env Variable- "+evValue);
									}else{
										logger.info("testdatavalue is not set by env Variable- "+evValue);
										tcStep.setTestDataValue("TESTDATA_NOTFOUND");
									}
								}catch(Exception e){
									logger.error("Exception in updateTestdataValues for \n testdataname::"+tcStep.getTestDataName() +
											"  in TestcaseID::"+ tcStep.getTestcaseID() + " at stepId:"+tcStep.getStepID());
									logger.error("\n StacktraceInfo::"+e.getMessage());
									tcStep.setTestDataValue("TESTDATA_NOTFOUND");

								}
							}else if(tdType.toUpperCase().equals("RUNTIMEVARIABLE")==true ){
								logger.info("testdataType is runtimevariable, will be updated at teststep level");
								if(tcStep.getKeyword().startsWith("Store")==true){
									tcStep.setTestDataValue(tcStep.getTestDataName());
								}else{
									tcStep.setTestDataValue("TOBEUPDATED_RUNTIME");
								}
								
							}else{
								tcStep.setTestDataType("INVALID");
								logger.error("Invalid testdataType error \n testdataname::"+tcStep.getTestDataName() +
										"  in TestcaseID::"+ tcStep.getTestcaseID() + " at stepId:"+tcStep.getStepID());
								tcStep.setTestDataValue("TESTDATA_NOTFOUND");
							}
						}else{
							tcStep.setTestDataType("NOT_APPLICABLE");
							tcStep.setTestDataValue("NOT_APPLICABLE");
							logger.info("Testdata is not applicable  in TestcaseID::"+ tcStep.getTestcaseID() + " at stepId:"+tcStep.getStepID());
						}
					}catch(Exception e){
						logger.error("Exception in updateTestdataValues for \n testdataname::"+tcStep.getTestDataName() +
								"  in TestcaseID::"+ tcStep.getTestcaseID() + " at stepId:"+tcStep.getStepID());
						logger.error("\n StacktraceInfo::"+e.getMessage());
						testdataValue="TESTDATA_NOTFOUND";
						tcStep.setTestDataValue(testdataValue);
					}

				}
			}
			logger.info("DONE :: updateTestdataValues");

		}catch(Exception e){
			logger.error("Exception in updateTestdataValues - \n stackTraceInfo::"+e.getMessage());

		}
		
	}

	public void runAllTestcases() {
		logger.info("TestcaseQue - Count "+ getTestcaseQue().keySet().size());
		
		try{
			for(String tcID : getTestcaseQue().keySet() ){
				logger.info("Running Testcase ::"+tcID);
				for(TestcaseStep tcStep : getTestcaseQue().get(tcID)){
					tcStep.setWebappdriver(getWebappdriver());
					tcStep.setRunTimeData(runTimeData);
					tcStep.run();
				}
				logger.info("Execution completed for testcaseID::"+ tcID);
			}
			logger.info("Execution completed for TestSuite::"+ this.getTestcaseQue().keySet().toString());
			endSession();
		}catch(Exception e){
			//e.printStackTrace();
			logger.info("Exception in runAllTestcases - \n stacktrace ::"+e.getMessage());
		}
		
		
	}

	private void endSession() {
		try{
			getWebappdriver().close();
			//TODO :: ending session needs to handle multiple windows too if present 
			logger.info("Webdriver session associated with the TestSuite "+this.getSuiteName()+  " has been terminated - "+this.getWebappdriver());
		}catch(Exception e){
			logger.error("Exception in ending webdriver  session - "+e.getMessage() + "\n"+this.getWebappdriver());
		}
	}


	public void generateTestReport() {
		try{
		for(String tcID:this.getTestcaseQue().keySet() ){
			logger.info("Testcase report ::"+tcID);
			for(TestcaseStep tcStep : this.getTestcaseQue().get(tcID)){
				try{
					if(tcStep.getStepResult().equalsIgnoreCase("FAIL")==true){
						logger.info("StepID::"+tcStep.getStepID() + " - Result ::"+ tcStep.getStepResult() + " , errorCode::"+tcStep.getErrorCode());
					}else{
						logger.info("StepID::"+tcStep.getStepID() + " - Result ::"+ tcStep.getStepResult());
					}
				}catch(Exception e){
					logger.info("Exception occurred while returning stepResult for StepID:"+tcStep.getStepID() +
							" in  TestcaseID:"+ tcStep.getTestcaseID());
					
				}
			}
		}
		}catch(Exception e){
			//e.printStackTrace();
			logger.error("Exception in runAllTestcases -"+e.getMessage());
		}		
	}


	public String getExecBrowserName() {
		return execBrowserName;
	}


	public void setExecBrowserName(String execBrowserName) {
		this.execBrowserName = execBrowserName;
		EnvironmentVariables ev= EnvironmentVariables.getInstance();
		String exepath =ev.getBrowserEXEpath(execBrowserName, "");
		if(exepath.equalsIgnoreCase("NOT_FOUND")==false){
			try{
				switch (BrowserType.valueOf(this.getExecBrowserName().toUpperCase())) {
				case INTERNETEXPLORER:
					try{
						if(ev.getBrowserDriverEXEpath("INTERNETEXPLORER" ,"","").equalsIgnoreCase("NOT_FOUND")==false){
							System.setProperty("webdriver.ie.driver", ev.getBrowserDriverEXEpath("INTERNETEXPLORER" ,"",""));
							this.setWebappdriver(new InternetExplorerDriver());
							logger.info("InternetExplorerDriver is set for webdriver object");
						}else{
							logger.error("BrowserDriverexe not found for the browser - INTERNETEXPLORER");
						}
					}catch(Exception e){
						//e.printStackTrace();
						logger.error("BrowserDriverexe not found for the browser - INTERNETEXPLORER");
					}

		        break;
		        case FIREFOX:
		        	try{
		        		FirefoxProfile profile = new FirefoxProfile();
			        	this.setWebappdriver(new FirefoxDriver(new FirefoxBinary(new File(exepath)), profile));
			        	this.getWebappdriver().manage().window().maximize();
						logger.info("FirefoxDriver is set for webdriver object");
		        	}catch(Exception e){
						logger.error("Exception in initiating browser session - FIREFOX \n stacktrace ::"+e.getMessage());
		        	}
		        	
		        break;
		        case GOOGLECHROME:
		        	try{
			        	System.setProperty("webdriver.chrome.driver", ev.getBrowserDriverEXEpath("GOOGLECHROME", "", ""));
			        	this.setWebappdriver(new ChromeDriver());
			        	this.getWebappdriver().manage().window().maximize();
						logger.info("ChromeDriver is set for webdriver object");
		        	}catch(Exception e){
						logger.error("Exception in initiating browser session - GOOGLECHROME \n stacktrace ::"+e.getMessage());
		        	}
		        break;
		        default:
		        	try{
		        	FirefoxProfile fprofile = new FirefoxProfile();
		        	this.setWebappdriver(new FirefoxDriver(new FirefoxBinary(new File(exepath)), fprofile));
		        	this.getWebappdriver().manage().window().maximize();
					logger.info("Default FirefoxDriver is set for webdriver object");
		        	}catch(Exception e){
						logger.error("Exception in initiating browser session - FIREFOX \n stacktrace ::"+e.getMessage());
		        	}
		         break;
				}
				
				this.getWebappdriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			}catch(Exception e){
				logger.error("Exception in initiating a browser session , browserName::"+execBrowserName);
				//e.printStackTrace();
			}
			
		}else{
			logger.error("Browser EXE path is not found - Error in starting the session, browserName::"+execBrowserName);
		}
		
	}

	public Map<String, Map<String, String>> getRunTimeData() {
		return runTimeData;
	}

	public void setRunTimeData(Map<String,Map<String, String>> runTimeData) {
		this.runTimeData = runTimeData;
	}




	/**
	 * @return the appIDList
	 */
	public List<String> getAppIDList() {
		LinkedList<String> appIDsList = new LinkedList<String>();
		try{
			Map<String, List<TestcaseStep>> ctestcaseQue = this.getTestcaseQue();
			if(ctestcaseQue.keySet().size()>0){
				for(String tcID : ctestcaseQue.keySet()){
					try{
						List<TestcaseStep> tcSteps = ctestcaseQue.get(tcID);
						for(TestcaseStep tcStep :tcSteps){
							String appID = tcStep.getApplicationID().trim();
							if(appIDsList.contains(appID)==false){
								appIDsList.add(appID);
							}
						}
					}catch(Exception e){
						logger.error("Exception in adding ApplicationIDs for the suite");
					}
				}
				
			}else{
				logger.error("The testsuite has zero testcases in the que");
			}
			
		}catch(Exception e){
			logger.error("Exception in getting applicationIDs list for the testsuite object");
		}
		return appIDsList;
	}
	

}
