package roark.jelenium;

import java.util.List;

public class Testcase {

	private List<TestcaseStep>	testcaseSteps;
	private String	testcaseResult;
	private String	testcaseType;
	private String	applicationID;
	
	public Testcase(){
		
	}

	/**
	 * @return the testcaseSteps
	 */
	public List<TestcaseStep> getTestcaseSteps() {
		return testcaseSteps;
	}

	/**
	 * @param testcaseSteps the testcaseSteps to set
	 */
	public void setTestcaseSteps(List<TestcaseStep> testcaseSteps) {
		this.testcaseSteps = testcaseSteps;
	}

	/**
	 * @return the testcaseResult
	 */
	public String getTestcaseResult() {
		return testcaseResult;
	}

	/**
	 * @param testcaseResult the testcaseResult to set
	 */
	public void setTestcaseResult(String testcaseResult) {
		this.testcaseResult = testcaseResult;
	}

	/**
	 * @return the testcaseType
	 */
	public String getTestcaseType() {
		return testcaseType;
	}

	/**
	 * @param testcaseType the testcaseType to set
	 */
	public void setTestcaseType(String testcaseType) {
		this.testcaseType = testcaseType;
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

}
