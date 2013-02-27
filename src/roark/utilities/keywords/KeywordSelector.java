package roark.utilities.keywords;

import java.util.Map;

import org.apache.log4j.Logger;

import roark.jelenium.TestSuite;
import roark.jelenium.TestcaseStep;

public class KeywordSelector {
	private String	keywordType;
	static Logger logger = Logger.getLogger(KeywordSelector.class);



	public KeywordSelector(){
		
	} 


	public enum keyWordType {
		GENERIC, CUSTOM, WEBUI, FILEHANDLING;
		}
	public enum genericKeywords{
		LAUNCH, GOTOURL, GOBACK, REFRESHPAGE, RELOADPAGE, SWITCHTOWINDOWBYINDEX, SWITCHTOWINDOWBYTITLE, SWITCHTOWINDOWBYTITLECONTAINS, SWITCHTOALERT, ACCEPTALERT, 
		DISMISSALERT, STOREALERTMSG, VERIFYALERTMESSAGE, VERIFYALERTMESSAGECONTAINS, VERIFYALERTPRESENT, VERIFYWINDOWPRESENT, VERIFYTEXTNOTPRESENT,
		WAIT, WAITFORALERT, WAITFORWINDOW, WAITFORPAGETOLOAD, SWITCHTOFRAMEBYINDEX, SWITCHTOFRAMEBYXPATH, SWITCHTOFRAMEBYTITLE, SWITCHTOFRAMEBYID,
		VERIFYTITLE, VERIFYTITLECONTAINS, VERIFYTEXTPRESENT, CLICKLINKBYTEXT, CLICKLINKBYPARTIALTEXT, CLICKELEMENTBYTEXT, CLICKELEMENTBYPARTIALTEXT,
		CLOSEWINDOWBYINDEX, CLOSEWINDOWBYTITLE, CLOSEWINDOWBYTITLECONTAINS, CLOSEWINDOW, CLOSEALLWINDOWS, SWITCHTODEFAULTCONTENT, VERIFYLINKSCOUNT, 
		VERIFYOSNAME, VERIFYBROWSERNAME, VERIFYBROWSERVERSION, VERIFYOSVERSION, VERIFYIPADDRESS, VERIFYALERTNOTPRESENT
		;
	}
	public enum customKeywords{
		CHOOSEDATEFROMDATEPICKER, DRAGANDDROP, VERTICALSCROLL, HORIZONTALSCROLL;
		
	}
	public enum webUIKeywords{
		ENTER, VERIFYVALUE, SELECTBYLABEL, SELECTBYINDEX, SELECTBYVALUE, VERIFYELEMENTPRESENT,VERIFYELEMENTNOTPRESENT, CLICK,  MOUSEOVER,
		STORETEXT, CHECK, UNCHECK, SUBMIT,   WAITTILLENABLED, VERIFYITEMPRESENT,  VERIFYITEMSCOUNT, VERIFYROWCOUNT, 
		VERIFYCOLUMNCOUNT, VERIFYTEXT, VERIFYCELLTEXT,  DEFAULTCONTENT, RADIOBYTEXT,VERIFYSELECTEDITEM,
		VERIFYSELECTED, VERIFYNOTSELECTED, VERIFYENABLED, VERIFYDISABLED, VERIFYTEXTCONTAINS,
		VERIFYPARTOFTEXT,  VERIFYPROPERTY,  STOREVALUE, SENDKEYS, CLICKVIAJS, CLICKLINKINTABLE,
		VERIFYASCENDINGINTABLE, VERIFYELEMENTSPRESENT, VERIFYDESCENDINGINTABLE, VERIFYALLITEMS, CLEAR,  CLICKANDWAIT, WAITFORELEMENT  ;
		
	}
	public void executeKeyword( TestcaseStep testcaseStep) {
		
		String keyword= testcaseStep.getKeyword();
		logger.info("Executing StepID : "+testcaseStep.getStepID());
		String fieldName = testcaseStep.getFieldName();
		if(fieldName.equalsIgnoreCase("NOT_APPLICABLE")==false){
			if(keyword.equalsIgnoreCase("CUSTOMKEYWORD")==false){
				this.setKeywordType("WEBUI");
			}else{
				this.setKeywordType("CUSTOM");
			}
		}else{
			this.setKeywordType("GENERIC");
		}
		logger.info("KeywordType is set to " + this.getKeywordType() );

		
		logger.info("Step Details - \n"+ testcaseStep.getStepDetails());
		switch (keyWordType.valueOf(this.getKeywordType().toUpperCase())) {
		case CUSTOM:
			break;
		case FILEHANDLING:
			break;
		case GENERIC:
			GenericKeywords genKeywords = new GenericKeywords(); 
			genKeywords.setWebdriver(testcaseStep.getWebappdriver());
			switch (genericKeywords.valueOf(keyword.toUpperCase())) {
			case ACCEPTALERT:
				try{
					genKeywords.clickOKinAlertDialog(testcaseStep);
				}catch(Exception e){
					//e.printStackTrace();
					logger.error("Exception in the keyword "+keyword + "\n StackTraceInfo::" + e.getMessage() );
				}
				break;
			
			case CLICKELEMENTBYPARTIALTEXT:
			break;
			case CLICKELEMENTBYTEXT:
				break;
			case CLICKLINKBYPARTIALTEXT:
				break;
			case CLICKLINKBYTEXT:
				try{
					genKeywords.clickLinkByTextValue(testcaseStep);
				}catch(Exception e){
					logger.error("Exception in the keyword "+keyword + "\n StackTraceInfo::" + e.getMessage() );
				}
				break;
			case CLOSEALLWINDOWS:
				break;
			case CLOSEWINDOW:
				try{
					genKeywords.closeCurrentWindow(testcaseStep);
				}catch(Exception e){
					logger.error("Exception in the keyword "+keyword + "\n StackTraceInfo::" + e.getMessage() );
					//e.printStackTrace();
				}
				break;
			case CLOSEWINDOWBYINDEX:
				break;
			case CLOSEWINDOWBYTITLE:
				break;
			case CLOSEWINDOWBYTITLECONTAINS:
				break;
			case DISMISSALERT:
				try{
					genKeywords.clickCancelButtonInAlertDialog(testcaseStep);
				}catch(Exception e){
					logger.error("Exception in the keyword "+keyword + "\n StackTraceInfo::" + e.getMessage() );
					//e.printStackTrace();
				}
				break;
			case GOBACK:
				break;
			case GOTOURL:
				try{
					genKeywords.navigateToUrl(testcaseStep);
				}catch(Exception e){
					logger.error("Exception in the keyword "+keyword + "\n StackTraceInfo::" + e.getMessage() );
					//e.printStackTrace();				
				}
				break;
			case LAUNCH:
				try{
					genKeywords.navigateToUrl(testcaseStep);
				}catch(Exception e){
					logger.error("Exception in the keyword "+keyword + "\n StackTraceInfo::" + e.getMessage() );
					//e.printStackTrace();				
				}
				break;
			case REFRESHPAGE:
				break;
			case RELOADPAGE:
				break;
			case STOREALERTMSG:
				break;
			case SWITCHTOALERT:
				break;
			case SWITCHTODEFAULTCONTENT:
				break;
			case SWITCHTOFRAMEBYID:
				break;
			case SWITCHTOFRAMEBYINDEX:
				break;
			case SWITCHTOFRAMEBYTITLE:
				break;
			case SWITCHTOFRAMEBYXPATH:
				break;
			case SWITCHTOWINDOWBYINDEX:
				break;
			case SWITCHTOWINDOWBYTITLE:
				break;
			case SWITCHTOWINDOWBYTITLECONTAINS:
				break;
			case VERIFYALERTMESSAGE:
				try{
					genKeywords.verifyAlertMessage(testcaseStep);
				}catch(Exception e){
					logger.error("Exception in the keyword "+keyword + "\n StackTraceInfo::" + e.getMessage() );
					//e.printStackTrace();				
				}
				break;
			case VERIFYALERTMESSAGECONTAINS:
				try{
					genKeywords.verifyAlertMessageContains(testcaseStep);
				}catch(Exception e){
					logger.error("Exception in the keyword "+keyword + "\n StackTraceInfo::" + e.getMessage() );
					//e.printStackTrace();				
				}
				break;
			case VERIFYALERTPRESENT:
				try{
					genKeywords.verifyAlertPresent(testcaseStep);
				}catch(Exception e){
					logger.error("Exception in the keyword "+keyword + "\n StackTraceInfo::" + e.getMessage() );
					//e.printStackTrace();				
				}
				break;
			case VERIFYALERTNOTPRESENT:
				try{
					genKeywords.verifyAlertNotPresent(testcaseStep);
				}catch(Exception e){
					logger.error("Exception in the keyword "+keyword + "\n StackTraceInfo::" + e.getMessage() );
					//e.printStackTrace();				
				}
				break;
			case VERIFYBROWSERNAME:
				break;
			case VERIFYBROWSERVERSION:
				break;
			case VERIFYIPADDRESS:
				break;
			case VERIFYLINKSCOUNT:
				break;
			case VERIFYOSNAME:
				break;
			case VERIFYOSVERSION:
				break;
			case VERIFYTEXTNOTPRESENT:
				break;
			case VERIFYTEXTPRESENT:
				break;
			case VERIFYTITLE:
				break;
			case VERIFYTITLECONTAINS:
				break;
			case VERIFYWINDOWPRESENT:
				break;
			case WAIT:
				break;
			case WAITFORALERT:
				break;
			case WAITFORPAGETOLOAD:
				break;
			case WAITFORWINDOW:
				break;
			default:
				break;
			
			}
			break;
		case WEBUI:
			logger.info("switched to case - WEBUI in keywordSelector");
			WebUI ui_keywords=  new WebUI();
			ui_keywords.setWebDriver(testcaseStep.getWebappdriver());
			switch (webUIKeywords.valueOf(keyword.toUpperCase())) {
			case CLEAR:
				try{
					ui_keywords.clearDataInElement(testcaseStep);
				}catch(Exception e){
					logger.error("Exception in the keyword "+keyword + "\n StackTraceInfo::" + e.getMessage() );
					//e.printStackTrace();
				}
				break;

			case CHECK:
				try{
				ui_keywords.checkElement(testcaseStep);
				}catch(Exception e){
					logger.error("Exception in the keyword "+keyword + "\n StackTraceInfo::" + e.getMessage() );
					//e.printStackTrace();
				}
				break;
			case CLICK:
				try{
					ui_keywords.clickElement(testcaseStep);
				}catch(Exception e){
					logger.error("Exception in the keyword "+keyword + "\n StackTraceInfo::" + e.getMessage() );
					//e.printStackTrace();
				}	
				break;
			case CLICKANDWAIT:
				try{
					ui_keywords.clickElementAndWait(testcaseStep);
				}catch(Exception e){
					logger.error("Exception in the keyword "+keyword + "\n StackTraceInfo::" + e.getMessage() );
				}
			case CLICKLINKINTABLE:
				try{
					 //TODO  :: ui_keywords.cli
				}catch(Exception e){
					logger.error("Exception in the keyword "+keyword + "\n StackTraceInfo::" + e.getMessage() );
				}
				break;
			case CLICKVIAJS:
				try{
					ui_keywords.clickElementUsingJavaScript(testcaseStep);
				}catch(Exception e){
					logger.error("Exception in the keyword "+keyword + "\n StackTraceInfo::" + e.getMessage() );
					//e.printStackTrace();				
				}
				break;
			case DEFAULTCONTENT:
				try{
					 //TODO  :: ui_keywords.cli
				}catch(Exception e){
					logger.error("Exception in the keyword "+keyword + "\n StackTraceInfo::" + e.getMessage() );
				}
				break;
			case ENTER:
				try{
					ui_keywords.enterText(testcaseStep);
				}catch(Exception e){
					logger.error("Exception in the keyword "+keyword + "\n StackTraceInfo::" + e.getMessage() );
					//e.printStackTrace();
				}
				break;
			case MOUSEOVER:
				try{
					ui_keywords.mouseOver(testcaseStep);
				}catch(Exception e){
					logger.error("Exception in the keyword "+keyword + "\n StackTraceInfo::" + e.getMessage() );
					//e.printStackTrace();
				}
				break;
			case RADIOBYTEXT:
				try{
					 //TODO  :: ui_keywords.cli
				}catch(Exception e){
					logger.error("Exception in the keyword "+keyword + "\n StackTraceInfo::" + e.getMessage() );
				}
				break;
			case SELECTBYINDEX:
				try{
					ui_keywords.selectListByIndex(testcaseStep);
				}catch(Exception e){
					logger.error("Exception in the keyword "+keyword + "\n StackTraceInfo::" + e.getMessage());
					//e.printStackTrace();
				}
				break;
			case SELECTBYLABEL:
				try{
					ui_keywords.selectListByLabel(testcaseStep);
				}catch(Exception e){
					logger.error("Exception in the keyword "+keyword + "\n StackTraceInfo::" + e.getMessage());
					//e.printStackTrace();
				}
				break;
			case SELECTBYVALUE:
				try{
					ui_keywords.selectListByValue(testcaseStep);
				}catch(Exception e){
					logger.error("Exception in the keyword "+keyword + "\n StackTraceInfo::" + e.getMessage());
					//e.printStackTrace();
				}
				break;
			case SENDKEYS:
				try{
					ui_keywords.sendKeyboardInput(testcaseStep);
				}catch(Exception e){
					logger.error("Exception in the keyword "+keyword + "\n StackTraceInfo::" + e.getMessage());
					//e.printStackTrace();
				}
				break;
			case STORETEXT:
				try{
					ui_keywords.storeTextFromElement(testcaseStep);
				}catch(Exception e){
					logger.error("Exception in the keyword "+keyword + "\n StackTraceInfo::" + e.getMessage() );
					//e.printStackTrace();				
				}
				break;
			case STOREVALUE:
				try{
					String attributeName= "value";
					ui_keywords.storeAttributeValueFromElement(testcaseStep,  attributeName );
				}catch(Exception e){
					logger.error("Exception in the keyword "+keyword + "\n StackTraceInfo::" + e.getMessage() );
					//e.printStackTrace();				
				}
				break;
			case SUBMIT:
				try{
					 //TODO  :: ui_keywords.cli
				}catch(Exception e){
					logger.error("Exception in the keyword "+keyword + "\n StackTraceInfo::" + e.getMessage() );
				}
				break;
			case UNCHECK:
				try{
					 //TODO  :: ui_keywords.cli
				}catch(Exception e){
					logger.error("Exception in the keyword "+keyword + "\n StackTraceInfo::" + e.getMessage() );
				}
				break;
			case VERIFYALLITEMS:
				try{
					 //TODO  :: ui_keywords.cli
				}catch(Exception e){
					logger.error("Exception in the keyword "+keyword + "\n StackTraceInfo::" + e.getMessage() );
				}
				break;
			case VERIFYASCENDINGINTABLE:
				try{
					 //TODO  :: ui_keywords.cli
				}catch(Exception e){
					logger.error("Exception in the keyword "+keyword + "\n StackTraceInfo::" + e.getMessage() );
				}
				break;
			case VERIFYCELLTEXT:
				try{
					 //TODO  :: ui_keywords.cli
				}catch(Exception e){
					logger.error("Exception in the keyword "+keyword + "\n StackTraceInfo::" + e.getMessage() );
				}
				break;
			case VERIFYCOLUMNCOUNT:
				try{
					 //TODO  :: ui_keywords.cli
				}catch(Exception e){
					logger.error("Exception in the keyword "+keyword + "\n StackTraceInfo::" + e.getMessage() );
				}
				break;
			case VERIFYDESCENDINGINTABLE:
				try{
					 //TODO  :: ui_keywords.cli
				}catch(Exception e){
					logger.error("Exception in the keyword "+keyword + "\n StackTraceInfo::" + e.getMessage() );
				}
				break;
			case VERIFYDISABLED:
				try{
					 //TODO  :: ui_keywords.cli
				}catch(Exception e){
					logger.error("Exception in the keyword "+keyword + "\n StackTraceInfo::" + e.getMessage() );
				}
				break;
			case VERIFYELEMENTNOTPRESENT:
				try{
					 //TODO  :: ui_keywords.cli
				}catch(Exception e){
					logger.error("Exception in the keyword "+keyword + "\n StackTraceInfo::" + e.getMessage() );
				}
				break;
			case VERIFYELEMENTPRESENT:
				try{
					ui_keywords.verifyElementPresent(testcaseStep);
				}catch(Exception e){
					logger.error("Exception in the keyword "+keyword + "\n StackTraceInfo::" + e.getMessage() );
					//e.printStackTrace();				
				}
				break;
			case VERIFYELEMENTSPRESENT:
				try{
					 //TODO  :: ui_keywords.cli
				}catch(Exception e){
					logger.error("Exception in the keyword "+keyword + "\n StackTraceInfo::" + e.getMessage() );
				}
				break;
			case VERIFYENABLED:
				try{
					 //TODO  :: ui_keywords.cli
				}catch(Exception e){
					logger.error("Exception in the keyword "+keyword + "\n StackTraceInfo::" + e.getMessage() );
				}
				break;
			case VERIFYITEMPRESENT:
				try{
					 //TODO  :: ui_keywords.cli
				}catch(Exception e){
					logger.error("Exception in the keyword "+keyword + "\n StackTraceInfo::" + e.getMessage() );
				}
				break;
			case VERIFYITEMSCOUNT:
				try{
					 //TODO  :: ui_keywords.cli
				}catch(Exception e){
					logger.error("Exception in the keyword "+keyword + "\n StackTraceInfo::" + e.getMessage() );
				}
				break;
			case VERIFYNOTSELECTED:
				try{
					 //TODO  :: ui_keywords.cli
				}catch(Exception e){
					logger.error("Exception in the keyword "+keyword + "\n StackTraceInfo::" + e.getMessage() );
				}
				break;
			case VERIFYPARTOFTEXT:
				try{
					 //TODO  :: ui_keywords.cli
				}catch(Exception e){
					logger.error("Exception in the keyword "+keyword + "\n StackTraceInfo::" + e.getMessage() );
				}
				break;
			case VERIFYPROPERTY:
				try{
					 //TODO  :: ui_keywords.cli
				}catch(Exception e){
					logger.error("Exception in the keyword "+keyword + "\n StackTraceInfo::" + e.getMessage() );
				}
				break;
			case VERIFYROWCOUNT:
				try{
					 //TODO  :: ui_keywords.cli
				}catch(Exception e){
					logger.error("Exception in the keyword "+keyword + "\n StackTraceInfo::" + e.getMessage() );
				}
				break;
			case VERIFYSELECTED:
				try{
					 //TODO  :: ui_keywords.cli
				}catch(Exception e){
					logger.error("Exception in the keyword "+keyword + "\n StackTraceInfo::" + e.getMessage() );
				}
				break;
			case VERIFYSELECTEDITEM:
				try{
					 //TODO  :: ui_keywords.cli
				}catch(Exception e){
					logger.error("Exception in the keyword "+keyword + "\n StackTraceInfo::" + e.getMessage() );
				}
				break;
			case VERIFYTEXT:
				try{
					ui_keywords.verifyTextOnElement(testcaseStep);
				}catch(Exception e){
					logger.error("Exception in the keyword "+keyword + "\n StackTraceInfo::" + e.getMessage() );
					//e.printStackTrace();				
				}
				break;
			case VERIFYTEXTCONTAINS:
				try{
					ui_keywords.verifyPartialTextOnElement(testcaseStep);
				}catch(Exception e){
					logger.error("Exception in the keyword "+keyword + "\n StackTraceInfo::" + e.getMessage() );
					//e.printStackTrace();
				}	
				
				break;
			case VERIFYVALUE:
				try{
					ui_keywords.verifyValue(testcaseStep);
				}catch(Exception e){
					logger.error("Exception in the keyword "+keyword + "\n StackTraceInfo::" + e.getMessage() );
					//e.printStackTrace();
				}
				break;
			case WAITFORELEMENT:
				try{
					 //TODO  :: ui_keywords.cli
				}catch(Exception e){
					logger.error("Exception in the keyword "+keyword + "\n StackTraceInfo::" + e.getMessage() );
				}
				break;
			case WAITTILLENABLED:
				break;
			default:
				break;
			
			}

			break;
		default:
			break;
		}
		
		
		try{
			if(testcaseStep.getStepResult().equalsIgnoreCase("PASS")==true){
				logger.info("Step Result ::"+ testcaseStep.getStepResult());
			}else{
				logger.error("Step Result ::"+ testcaseStep.getStepResult() + " , ErrorCode ::"+ testcaseStep.getErrorCode());
			}
		}catch(Exception e){
			logger.error("Exception in fetching the stepResults - "+ e.getMessage());
		}
	}
	public String getKeywordType() {
		return keywordType;
	}
	public void setKeywordType(String keywordType) {
		this.keywordType = keywordType;
	}
	
	
	
}
