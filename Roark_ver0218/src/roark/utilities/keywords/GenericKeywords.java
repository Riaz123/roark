package roark.utilities.keywords;


import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import roark.jelenium.*;

public class GenericKeywords {
	static Logger logger = Logger.getLogger(GenericKeywords.class);
	public WebDriver webdriver;

	public WebDriver getWebdriver() {
		return webdriver;
	}

	public void setWebdriver(WebDriver webdriver) {
		this.webdriver = webdriver;
	}
	
	
	GenericKeywords(){
		
	} 
	
	public int navigateToUrl( TestcaseStep testcaseStep) {
		int exitCode;
		logger.info("url :"+testcaseStep.getTestDataValue());
		try{
			if(testcaseStep.getTestDataValue().equalsIgnoreCase("TESTDATA_NOTFOUND")==false){
				this.getWebdriver().get(testcaseStep.getTestDataValue());
				exitCode=0; //operation success
			}else{
				exitCode=2; // testdata error
			}
		}catch(Exception e){
			logger.info("Exception in navigateToUrl - \n"+e.getMessage());
			exitCode=4;
		}
		if(exitCode==0){
			testcaseStep.setStepResult("PASS");
		}else{
			testcaseStep.setStepResult("FAIL");
			testcaseStep.setErrorCode(exitCode);
		}
		return exitCode;
	}

	public int closeCurrentWindow(TestcaseStep testcaseStep) {
		int exitCode;
		try{
			this.getWebdriver().close();
			logger.info("Closed the current window");
			exitCode=0;
		}catch(Exception e){
			//e.printStackTrace();
			logger.error("Exception in closeCurrentWindow \n StacktraceInfo::"+e.getMessage());
			exitCode=4;
		}
		if(exitCode==0){
			testcaseStep.setStepResult("PASS");
		}else{
			testcaseStep.setStepResult("FAIL");
			testcaseStep.setErrorCode(exitCode);
		}
		return exitCode;
	}

	public int clickOKinAlertDialog(TestcaseStep testcaseStep) {
		int exitCode;
		try{
			if(this.getAlert()!=null){
				try{
					this.getAlert().accept();
					exitCode=0;
				}catch(Exception e){
					exitCode=1;
				}
			}else{
				exitCode= 10; // Alert dialog is null
			}
		}catch(Exception e){
			//e.printStackTrace();
			logger.error("Exception in clickOKinAlertDialog \n StacktraceInfo::"+e.getMessage());
			exitCode=4;
		}
		if(exitCode==0){
			testcaseStep.setStepResult("PASS");
		}else{
			testcaseStep.setStepResult("FAIL");
			testcaseStep.setErrorCode(exitCode);
		}
		return exitCode;		
	}
	
	private boolean isAlertPresent(){
		boolean isAlertPresent;
		try{
			WebDriverWait waiter = new WebDriverWait(this.getWebdriver(), 30);
			Alert x = waiter.until(ExpectedConditions.alertIsPresent());
			isAlertPresent=true;
		}catch(Exception e){
			//e.printStackTrace();
			logger.error("Exception in isAlertPresent -"+ e.getMessage());
			isAlertPresent=false;
		}
		return isAlertPresent;
	
	}
	
	private Alert getAlert(){
		Alert alertDialog;
		try{
			if(this.isAlertPresent()==true){
				alertDialog= this.getWebdriver().switchTo().alert();
			}else{
				alertDialog=null;
			}
		}catch(Exception e){
			//e.printStackTrace();
			logger.error("Exception in getAlert \n StacktraceInfo::"+e.getMessage());
			alertDialog=null;
		}
		return alertDialog;
	}

	public int clickCancelButtonInAlertDialog(TestcaseStep testcaseStep) {
		int exitCode;
		try{
			if(this.getAlert()!=null){
				try{
					this.getAlert().dismiss();
					exitCode=0;
				}catch(Exception e){
					exitCode=1;
				}
			}else{
				exitCode= 10; // Alert dialog is null
			}
		}catch(Exception e){
			//e.printStackTrace();
			logger.error("Exception in clickCancelButtonInAlertDialog \n StacktraceInfo::"+e.getMessage());
			exitCode=4;
		}
		if(exitCode==0){
			testcaseStep.setStepResult("PASS");
		}else{
			testcaseStep.setStepResult("FAIL");
			testcaseStep.setErrorCode(exitCode);
		}
		return exitCode;			
	}

	public int verifyAlertPresent(TestcaseStep testcaseStep) {
		int exitCode;
		try{
			if(this.getAlert()!=null){
				exitCode=0;
			}else{
				exitCode= 1; 
			}
		}catch(Exception e){
			//e.printStackTrace();
			logger.error("Exception in verifyAlertPresent \n StacktraceInfo::"+e.getMessage());
			exitCode=4;
		}
		if(exitCode==0){
			testcaseStep.setStepResult("PASS");
		}else{
			testcaseStep.setStepResult("FAIL");
			testcaseStep.setErrorCode(exitCode);
		}
		return exitCode;			
	}
	

	public int verifyAlertNotPresent(TestcaseStep testcaseStep) {
		int exitCode;
		try{
			if(this.getAlert()!=null){
				exitCode=1;
			}else{
				exitCode= 0; 
			}
		}catch(Exception e){
			//e.printStackTrace();
			logger.error("Exception in verifyAlertNotPresent \n StacktraceInfo::"+e.getMessage());
			exitCode=4;
		}
		if(exitCode==0){
			testcaseStep.setStepResult("PASS");
		}else{
			testcaseStep.setStepResult("FAIL");
			testcaseStep.setErrorCode(exitCode);
		}
		return exitCode;			
	}

	public int verifyAlertMessage(TestcaseStep testcaseStep) {
		int exitCode;
		try{
			if( this.getAlert()!=null){
				String expectedAlertMsg = testcaseStep.getTestDataValue().trim();
				if(expectedAlertMsg.equalsIgnoreCase("TESTDATA_NOTFOUND")==false){
					try{
						String actualAlertMsg=this.getAlert().getText().trim();
						if(actualAlertMsg.equalsIgnoreCase(expectedAlertMsg)==true){
							exitCode=0;
						}else{
							exitCode=5;
						}
					}catch(Exception e){
						exitCode=1; // operation level exception
					}
				}else{
					exitCode=2; // testdata level error
				}
			}else{
				exitCode= 10; // alert not found
			}
		}catch(Exception e){
			exitCode=4;
			logger.error("Exception in verifyAlertMessage \n StacktraceInfo::"+e.getMessage());
		}
		if(exitCode==0){
			testcaseStep.setStepResult("PASS");
		}else{
			testcaseStep.setStepResult("FAIL");
			testcaseStep.setErrorCode(exitCode);
		}
		return exitCode;			
	}

	public int verifyAlertMessageContains(TestcaseStep testcaseStep) {
		int exitCode;
		try{
			if( this.getAlert()!=null){
				String expectedAlertMsg = testcaseStep.getTestDataValue().trim();
				if(expectedAlertMsg.equalsIgnoreCase("TESTDATA_NOTFOUND")==false){
					try{
						String actualAlertMsg=this.getAlert().getText().trim();
						if(actualAlertMsg.contains(expectedAlertMsg)==true){
							exitCode=0;
						}else{
							exitCode=5;
						}
					}catch(Exception e){
						exitCode=1; // operation level exception
					}
				}else{
					exitCode=2; // testdata level error
				}
			}else{
				exitCode= 10; // alert not found
			}
		}catch(Exception e){
			exitCode=4;
			logger.error("Exception in verifyAlertMessageContains \n StacktraceInfo::"+e.getMessage());
		}
		if(exitCode==0){
			testcaseStep.setStepResult("PASS");
		}else{
			testcaseStep.setStepResult("FAIL");
			testcaseStep.setErrorCode(exitCode);
		}
		return exitCode;			

	}

	public void clickLinkByTextValue(TestcaseStep testcaseStep) {
		// TODO Auto-generated method stub
		
	}
}

