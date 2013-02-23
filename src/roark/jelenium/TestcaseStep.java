package roark.jelenium;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import roark.utilities.keywords.KeywordSelector;

public class TestcaseStep {
	static Logger logger = Logger.getLogger(TestcaseStep.class);
	private String	testcaseID;
	private String	stepID;
	private String	keyword;
	private String	applicationID;
	private String 	parentFieldName;
	private String	fieldName;
	private String	fieldDefinition;
	private String	testDataType;
	private String	testDataName;
	private String	testDataValue;
	private String	checkPoint;
	private String 	stepResult;
	private String	errDescription;
	private int 		errorCode;
	
	public TestcaseStep() {
		
	}

	/**
	 * @return the testcaseID
	 */
	public String getTestcaseID() {
		return testcaseID;
	}

	/**
	 * @param testcaseID the testcaseID to set
	 */
	public void setTestcaseID(String testcaseID) {
		this.testcaseID = testcaseID;
	}

	/**
	 * @return the stepID
	 */
	public String getStepID() {
		return stepID;
	}

	/**
	 * @param stepID the stepID to set
	 */
	public void setStepID(String stepID) {
		this.stepID = stepID;
	}

	/**
	 * @return the keyword
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * @param keyword the keyword to set
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	 * @return the applicationID
	 */
	public String getApplicationID() {
		return applicationID;
	}

	/**
	 * @param applicationID the applicationID to set
	 */
	public void setApplicationID(String applicationID) {
		this.applicationID = applicationID;
	}

	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * @param fieldName the fieldName to set
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * @return the fieldDefinition
	 */
	public String getFieldDefinition() {
		return fieldDefinition;
	}
	
	
	public String getStepDetails(){
		Map<String, String> stepDetails = new LinkedHashMap<String, String>();
		try{
			stepDetails.put("StepID", this.getStepID());
			stepDetails.put("FieldName", this.getFieldName());
			stepDetails.put("FieldDefinition", this.getFieldDefinition());
			stepDetails.put("Keyword", this.getKeyword());
			stepDetails.put("TestDataType", this.getTestDataType());
			stepDetails.put("TestDataName", this.getTestDataName());
			stepDetails.put("TestDataValue", this.getTestDataValue());
			stepDetails.put("Checkpoint", this.getCheckPoint());
		}catch(Exception e){
			//e.printStackTrace();
			logger.error("Exception in fetching stepDetails - \n StacktraceInfo::"+ e.getMessage());
		}
		return stepDetails.toString();
	}

	/**
	 * @param fieldDefinition the fieldDefinition to set
	 */
	public void setFieldDefinition(String fieldDefinition) {
		this.fieldDefinition = fieldDefinition;
	}

	/**
	 * @return the testDataType
	 */
	public String getTestDataType() {
		return testDataType;
	}

	/**
	 * @param testDataType the testDataType to set
	 */
	public void setTestDataType(String testDataType) {
		this.testDataType = testDataType;
	}

	/**
	 * @return the testDataName
	 */
	public String getTestDataName() {
		return testDataName;
	}

	/**
	 * @param testDataName the testDataName to set
	 */
	public void setTestDataName(String testDataName) {
		this.testDataName = testDataName;
	}

	/**
	 * @return the testDataValue
	 */
	public String getTestDataValue() {
		return testDataValue;
	}

	/**
	 * @param testDataValue the testDataValue to set
	 */
	public void setTestDataValue(String testDataValue) {
		this.testDataValue = testDataValue;
	}

	/**
	 * @return the checkPoint
	 */
	public String getCheckPoint() {
		return checkPoint;
	}

	/**
	 * @param checkPoint the checkPoint to set
	 */
	public void setCheckPoint(String checkPoint) {
		this.checkPoint = checkPoint;
	}

	/**
	 * @return the stepResult
	 */
	public String getStepResult() {
		return stepResult;
	}

	/**
	 * @param stepResult the stepResult to set
	 */
	public void setStepResult(String stepResult) {
		this.stepResult = stepResult;
	}

	/**
	 * @return the errDescription
	 */
	public String getErrDescription() {
		return errDescription;
	}

	/**
	 * @param errDescription the errDescription to set
	 */
	public void setErrDescription(String errDescription) {
		this.errDescription = errDescription;
	}

	public String getParentFieldName() {
		return parentFieldName;
	}

	public void setParentFieldName(String parentFieldName) {
		this.parentFieldName = parentFieldName;
	}

	public void run(TestSuite testSuite) {
		try{
			KeywordSelector keySel =new  KeywordSelector();
			keySel.executeKeyword(testSuite, this);
		}catch(Exception e){
			logger.error("Exception in run- TestcaseStep \n stacktraceInfo::"+e.getMessage());
			//e.printStackTrace();
		}
		
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}


}
