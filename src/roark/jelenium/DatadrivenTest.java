package roark.jelenium;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import roark.utilities.data.EnvironmentVariables;

public class DatadrivenTest {
	static Logger logger = Logger.getLogger(TestSuite.class);
	private String testcaseID;
	private List<TestcaseStep> testcaseSteps;
	private List<Map<String, String>> testdataTable = new LinkedList<Map<String,String>>();
	private TestSuite testSuite;
	
	public DatadrivenTest(){
		
	}

	public String getTestcaseID() {
		return testcaseID;
	}

	public void setTestcaseID(String testcaseID) {
		this.testcaseID = testcaseID;
		logger.info("testcaseID is set");

	}

	public List<TestcaseStep> getTestcaseSteps() {
		return testcaseSteps;
	}

	public void setTestcaseSteps(List<TestcaseStep> testcaseSteps) {
		this.testcaseSteps = testcaseSteps;
		logger.info("testcaseSteps are set");

	}

	public List<Map<String, String>> getTestdataTable() {
		return testdataTable;
	}

	public void setTestdataTable(List<Map<String, String>> testdataTable) {
		this.testdataTable = testdataTable;
	}

	public TestSuite getTestSuite() {
		return testSuite;
	}

	public void setTestSuite(TestSuite testSuite) {
		this.testSuite = testSuite;
	}

	public void runOnAllrows() {
		try{
			logger.info("Executing datadriven test :"+this.getTestcaseID());
			List<TestcaseStep> steps =getTestSuite().getTestcaseQue().get(getTestcaseID());
			//String appID = testSuite.getTestSuiteInfo().get(getTestcaseID()).get("ApplicationID");
			List<Map<String, String>> testdataSets= getTestdataTable();
			int iterCount=0;
			int totalIterations= testdataSets.size();
			logger.info("totalIterations :"+totalIterations);
			for(Map<String, String> testDatarow : testdataSets ){
				iterCount++;
				logger.info("Executing iteration -"+iterCount );
				logger.info("testDatarow::\n"+ testDatarow.toString());
				logger.info("Step Count:"+steps.size());
				int stepCount=0;
				for(TestcaseStep step: steps ){
					stepCount++;
					logger.info("Running step -"+stepCount + "\n keyword:"+ step.getKeyword() 
						+ "  Fieldname:"+ step.getFieldName() + " TestdataType:"+step.getTestDataType());
					if(step.getTestDataType().equalsIgnoreCase("NA")==false){
						updateTestdataValue(step, testDatarow);
						
					}else{
						step.setTestDataType("NOT_APPLICABLE");
						step.setTestDataValue("NOT_APPLICABLE");
					}
					step.setWebappdriver(getTestSuite().getWebappdriver());
					step.setRunTimeData(getTestSuite().getRunTimeData());
					step.run();
				}
				logger.info("Completed iteration - "+iterCount);

			}
			logger.info("Completed "+totalIterations + " iterations");
			
		}catch(Exception e){
			logger.error("Exception in running data driven test case -"+getTestcaseID() + "\n"+e.getMessage() );
		}
		
	}

	private void updateTestdataValue(TestcaseStep tcStep , Map<String, String> testdataRow) {
		String testdataValue;
		logger.info("UpdateTestdata value - \n TestcaseID:"+ tcStep.getTestcaseID() + " , StepID:"+tcStep.getStepID());
		logger.info("TestdataName::" + tcStep.getTestDataName()+ " , TestdataType::"+tcStep.getTestDataType());
		String tdType = tcStep.getTestDataType();
		if(tdType.toUpperCase().equals("XLPARAMETER")==true){
			try{
				testdataValue = testdataRow.get(tcStep.getTestDataName().toUpperCase());
				tcStep.setTestDataValue(testdataValue);
				logger.info("For step#"+tcStep.getStepID() + " in testcase - "+ tcStep.getTestcaseID() + " , test data name "+ tcStep.getTestDataName()+ " is updated with value:"+testdataValue);
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
				EnvironmentVariables ev = EnvironmentVariables.getInstance();
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
			
	}
	
	
	


}
