package roark.utilities.data;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class EnvironmentVariables {
	static Logger logger = Logger.getLogger(EnvironmentVariables.class);

/*	private String	rootFolderPath;
*/	private String	fileExtension;
	private String 	logFileExtension;
	private String	testSuiteFilePathPrefix;
	private String	testcasesFilePathPrefix;
	private String 	testComponentsFilePathPrefix;
	private String	testDataFilePathPrefix;
	private String 	fieldDefnFilePathPrefix;
	private String 	testReportsFilePathPrefix;
	private String 	executionLogFilePathPrefix;
	private String 	snapShotFilePathPrefix;
	private String	testSuiteSheetName;
	private String	testcasesSheetName;
	private String 	testComponentsSheetName;
	private String	testDataSheetName;
	private String 	fieldDefnSheetName;
	private String 	envVariablesXMLfilepath;
	

	private static EnvironmentVariables instance;
	EnvironmentVariables(){
		
	} 
	public static synchronized EnvironmentVariables getInstance(){
	  if (instance == null)
	  {
	  instance = new EnvironmentVariables();
	  instance.setFolderSettings();
	  }
	  return instance;
	  }

	/*
	 * This method takes @param rootFolderPath to set the folder settings
	 * Assumptions : 
	 * 		1. The folders for various test artifacts are assumed to be sub-folders of the root folder
	 * 		2. The folder names are as assumed in the method, should match the actual folder structure and names
	 * 		3. The filenames have only prefixes and are derived based on the ApplicationID during execution
	 * 		4. The filepaths will be constructed during the exeuction using the prefix and appID and file extension
	 * 					
	 */
	public void setFolderSettings(){
		try{
			String rootFolderPath= "./TAF_Demo";
			this.setFileExtension(".xls");
		    this.setLogFileExtension(".txt");
		    
		    String testSuiteFoldername= "TestSuite";
		    String testSuiteFolderpath = rootFolderPath + "\\" + testSuiteFoldername ;
		    String testSuiteFilenamePrefix ="TestSuite";
		    String testSuiteFilepathPrefix = testSuiteFolderpath + "\\" +testSuiteFilenamePrefix;
		    this.setTestSuiteFilePathPrefix(testSuiteFilepathPrefix);
		    this.setTestSuiteSheetName("TestSuite");
		    
		    String testcasesFoldername="Testcases";
		    String testcasesFolderpath = rootFolderPath +"\\"+testcasesFoldername;
		    String testcasesFilenamePrefix = "Testcases";
		    String testcasesFilePathPrefix = testcasesFolderpath+ "\\"+testcasesFilenamePrefix;
		    this.setTestcasesFilePathPrefix(testcasesFilePathPrefix);
		    this.setTestcasesSheetName("Testcases");
		    
		    String testComFoldername= "TestComponents";
		    String testComFolderpath = rootFolderPath + "\\" + testComFoldername ;
		    String testComFilenamePrefix ="TestComponents";
		    String testComponentsFilePathPrefix = testComFolderpath+ "\\"+testComFilenamePrefix;
		    this.setTestComponentsFilePathPrefix(testComponentsFilePathPrefix);
		    this.setTestComponentsSheetName("TestComponents");
	
		    
		    String testDataFoldername = "Testdata";
		    String testDataFolderpath = rootFolderPath +"\\"+testDataFoldername;
		    String testDataFilenamePrefix = "Testdata";
		    String testDataFilePathPrefix = testDataFolderpath+ "\\"+testDataFilenamePrefix;
		    this.setTestDataFilePathPrefix(testDataFilePathPrefix);
		    this.setTestDataSheetName("Testdata");
		    
		    String fieldDefnFoldername = "FieldDefinitions";
		    String fieldDefnFolderpath = rootFolderPath +"\\"+fieldDefnFoldername;
		    String fieldDefnFilenamePrefix = "FieldDefinitions";
		    String fieldDefnFilePathPrefix= fieldDefnFolderpath + "\\"+fieldDefnFilenamePrefix;
		    this.setFieldDefnFilePathPrefix(fieldDefnFilePathPrefix);
		    this.setFieldDefnSheetName("FieldDefinitions");
		    
		    String testReportsFoldername = "TestReports";
		    String testReportsFolderpath = rootFolderPath +"\\"+testReportsFoldername;
		    String testReportsFilenamePrefix = "TestReport_"; 
		    String testReportsFilePathPrefix= testReportsFolderpath+ "\\"+ testReportsFilenamePrefix;
		    this.setTestReportsFilePathPrefix(testReportsFilePathPrefix);
		    
		    String snapShotsFoldername = "Snapshots";
		    String snapShotsFolderpath = rootFolderPath +"\\"+testReportsFoldername +"\\"+snapShotsFoldername;
		    String snapShotsFilenamePrefix = "SnapShot_"; 
		    String snapShotFilePathPrefix= snapShotsFolderpath+ "\\"+ snapShotsFilenamePrefix;
		    this.setSnapShotFilePathPrefix(snapShotFilePathPrefix);
	
		    String execLogsFoldername = "ExecutionLogs";
		    String execLogsFolderpath = rootFolderPath +"\\"+execLogsFoldername;
		    String execLogsFilenamePrefix = "ExecutionLog_"; 
		    String execLogsFilePathPrefix = execLogsFolderpath+ "\\"+ execLogsFilenamePrefix;
		    this.setExecutionLogFilePathPrefix(execLogsFilePathPrefix);
		    
		    String envVariablesFoldername = "Configuration";
		    String envVariablesFolderpath = rootFolderPath +"\\"+envVariablesFoldername;
		    String envVariablesFileName = "EnvironmentVariables.xml"; 
		    this.setEnvVariablesXMLfilepath(envVariablesFolderpath + "\\"+envVariablesFileName);
			logger.info("DONE :: setFolderSettings");

		}catch(Exception e){
			//e.printStackTrace();
			logger.error("Exception in setFolderSettings - \n stacktraceInfo::"+e.getMessage());
		}

	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public String getTestSuiteFilePathPrefix() {
		return testSuiteFilePathPrefix;
	}

	public void setTestSuiteFilePathPrefix(String testSuiteFilePathPrefix) {
		this.testSuiteFilePathPrefix = testSuiteFilePathPrefix;
	}

	public String getTestcasesFilePathPrefix() {
		return testcasesFilePathPrefix;
	}

	public void setTestcasesFilePathPrefix(String testcasesFilePathPrefix) {
		this.testcasesFilePathPrefix = testcasesFilePathPrefix;
	}

	public String getTestComponentsFilePathPrefix() {
		return testComponentsFilePathPrefix;
	}

	public void setTestComponentsFilePathPrefix(String testComponentsFilePathPrefix) {
		this.testComponentsFilePathPrefix = testComponentsFilePathPrefix;
	}

	public String getTestDataFilePathPrefix() {
		return testDataFilePathPrefix;
	}

	public void setTestDataFilePathPrefix(String testDataFilePathPrefix) {
		this.testDataFilePathPrefix = testDataFilePathPrefix;
	}

	public String getFieldDefnFilePathPrefix() {
		return fieldDefnFilePathPrefix;
	}

	public void setFieldDefnFilePathPrefix(String fieldDefnFilePathPrefix) {
		this.fieldDefnFilePathPrefix = fieldDefnFilePathPrefix;
	}
	public String getTestReportsFilePathPrefix() {
		return testReportsFilePathPrefix;
	}
	public void setTestReportsFilePathPrefix(String testReportsFilePathPrefix) {
		this.testReportsFilePathPrefix = testReportsFilePathPrefix;
	}
	public String getExecutionLogFilePathPrefix() {
		return executionLogFilePathPrefix;
	}
	public void setExecutionLogFilePathPrefix(String executionLogFilePathPrefix) {
		this.executionLogFilePathPrefix = executionLogFilePathPrefix;
	}
	public String getLogFileExtension() {
		return logFileExtension;
	}
	public void setLogFileExtension(String logFileExtension) {
		this.logFileExtension = logFileExtension;
	}

	public String getSnapShotFilePathPrefix() {
		return snapShotFilePathPrefix;
	}

	public void setSnapShotFilePathPrefix(String snapShotFilePathPrefix) {
		this.snapShotFilePathPrefix = snapShotFilePathPrefix;
	}

	public String getTestSuiteSheetName() {
		return testSuiteSheetName;
	}

	public void setTestSuiteSheetName(String testSuiteSheetName) {
		this.testSuiteSheetName = testSuiteSheetName;
	}

	public String getTestcasesSheetName() {
		return testcasesSheetName;
	}

	public void setTestcasesSheetName(String testcasesSheetName) {
		this.testcasesSheetName = testcasesSheetName;
	}

	public String getTestComponentsSheetName() {
		return testComponentsSheetName;
	}


	public void setTestComponentsSheetName(String testComponentsSheetName) {
		this.testComponentsSheetName = testComponentsSheetName;
	}


	public String getTestDataSheetName() {
		return testDataSheetName;
	}

	public void setTestDataSheetName(String testDataSheetName) {
		this.testDataSheetName = testDataSheetName;
	}


	public String getFieldDefnSheetName() {
		return fieldDefnSheetName;
	}

	public void setFieldDefnSheetName(String fieldDefnSheetName) {
		this.fieldDefnSheetName = fieldDefnSheetName;
	}

	public String getTestParameterValue (String appID, String variableName){
		String envVariableValue = "" ;
			try{
				DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
				//Getting the instance of DocumentBuilderFactory 
				domFactory.setNamespaceAware(true);
				//true if the parser produced will provide support for XML namespaces; 
				DocumentBuilder builder = domFactory.newDocumentBuilder();
				//Creating document builder
				String xmlFilePath=  this.getEnvVariablesXMLfilepath().trim(); //"D:\\workspace\\Roark_ver2\\TAF_Demo\\Configuration\\EnvironmentVariables.xml";
				logger.info("xmlFilePath:"+this.getEnvVariablesXMLfilepath().trim());
				Document doc = builder.parse(xmlFilePath);
				XPath xpath = XPathFactory.newInstance().newXPath();
				//getting instance of xPath
				XPathExpression expr = xpath.compile("//Application[@ApplicationID='"+appID+"']/TestParameters/Parameter");
				Object result = expr.evaluate(doc, XPathConstants.NODESET);
				NodeList nodes = (NodeList) result;
				if(nodes.getLength()!=0){
					 for (int i = 0; i < nodes.getLength(); i++) {
				    	 try{
				    		 if(nodes.item(i).getAttributes().item(0).getNodeValue().equalsIgnoreCase(variableName)==true){
				    			 envVariableValue =nodes.item(i).getAttributes().item(2).getNodeValue();
				    			 break;
				    		 }else{
					    		 envVariableValue="NOT_FOUND";
				    		 }
				    	 }catch(Exception e){
				    		 //e.printStackTrace();
				    		 envVariableValue="NOT_FOUND";
				    	 }
					 }
				}else{
		   		 envVariableValue="NOT_FOUND";
				}
			}catch(Exception e){
				//e.printStackTrace();
				envVariableValue="NOT_FOUND";
				logger.error("Exception in getTestParameterValue - \n stacktraceInfo::"+e.getMessage());
			}
		return envVariableValue;
	}
	
	public String getBrowserEXEpath (String browserName, String browserVersion){
		String browserEXEpath = "" ;
			try{
				DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
				//Getting the instance of DocumentBuilderFactory 
				domFactory.setNamespaceAware(true);
				//true if the parser produced will provide support for XML namespaces; 
				DocumentBuilder builder = domFactory.newDocumentBuilder();
				//Creating document builder
				String xmlFilePath=  this.getEnvVariablesXMLfilepath().trim(); //"D:\\workspace\\Roark_ver2\\TAF_Demo\\Configuration\\EnvironmentVariables.xml";
				Document doc = builder.parse(xmlFilePath);
				XPath xpath = XPathFactory.newInstance().newXPath();
				//getting instance of xPath
				XPathExpression expr = xpath.compile("//Browsers/browser[@name='"+browserName.toUpperCase().trim()+"']");
				Object result = expr.evaluate(doc, XPathConstants.NODESET);
				NodeList nodes = (NodeList) result;
				if(nodes.getLength()!=0){
					 for (int i = 0; i < nodes.getLength(); i++) {
						 try{
							  int attriLen = nodes.item(i).getAttributes().getLength();
							  for(int j=0;j<attriLen; j++){
								  if(nodes.item(i).getAttributes().item(j).getNodeName().equalsIgnoreCase("exePath")==true){
									  browserEXEpath= nodes.item(i).getAttributes().item(j).getNodeValue();
									  logger.info("browserEXEpath:"+browserEXEpath);
									  break;
								  }
							  }

						 }catch(Exception e){
							 browserEXEpath= "NOT_FOUND";
							 logger.error("Exception in getBrowserEXEpath - \n stacktraceInfo::"+e.getMessage());
						 }
					 }
				}else{
					browserEXEpath="NOT_FOUND";
					logger.error("browserEXEpath is set to NOT_FOUND ");
				}
			}catch(Exception e){
				//e.printStackTrace();
				browserEXEpath="NOT_FOUND";
				logger.error("Exception in getBrowserEXEpath - \n stacktraceInfo::"+e.getMessage());
			}
			if(browserEXEpath.equals("")== true){
				browserEXEpath="NOT_FOUND";
			}
		return browserEXEpath;
	}
	public String getEnvVariablesXMLfilepath() {
		return envVariablesXMLfilepath;
	}
	public void setEnvVariablesXMLfilepath(String envVariablesXMLfilepath) {
		this.envVariablesXMLfilepath = envVariablesXMLfilepath;
	}
	public String getBrowserDriverEXEpath(String browserName, String browserVersion, String browserType) {
		String browserDriverEXEpath = ""  ;
		try{
			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
			//Getting the instance of DocumentBuilderFactory 
			domFactory.setNamespaceAware(true);
			//true if the parser produced will provide support for XML namespaces; 
			DocumentBuilder builder = domFactory.newDocumentBuilder();
			//Creating document builder
			String xmlFilePath=  this.getEnvVariablesXMLfilepath().trim(); //"D:\\workspace\\Roark_ver2\\TAF_Demo\\Configuration\\EnvironmentVariables.xml";
			Document doc = builder.parse(xmlFilePath);
			XPath xpath = XPathFactory.newInstance().newXPath();
			//getting instance of xPath
			XPathExpression expr = xpath.compile("//BrowserDrivers/browserDriver[@name='"+browserName.toUpperCase().trim()+"']");
			Object result = expr.evaluate(doc, XPathConstants.NODESET);
			NodeList nodes = (NodeList) result;
			if(nodes.getLength()!=0){
				 for (int i = 0; i < nodes.getLength(); i++) {
					 try{
						  int attriLen = nodes.item(i).getAttributes().getLength();
						  for(int j=0;j<attriLen; j++){
							  if(nodes.item(i).getAttributes().item(j).getNodeName().equalsIgnoreCase("exePath")==true){
								  browserDriverEXEpath= nodes.item(i).getAttributes().item(j).getNodeValue();
								  logger.info("BrowserDriverEXEpath:"+browserDriverEXEpath);
								  break;
							  }
						  }

					 }catch(Exception e){
						 browserDriverEXEpath= "NOT_FOUND";
						logger.error("Exception in getbrowserDriverEXEpath - \n stacktraceInfo::"+e.getMessage());

					 }
				 }
			}else{
				browserDriverEXEpath="NOT_FOUND";
			}
		}catch(Exception e){
			//e.printStackTrace();
			browserDriverEXEpath="NOT_FOUND";
		}
		if(browserDriverEXEpath.equals("")== true){
			browserDriverEXEpath="NOT_FOUND";
		}
	return browserDriverEXEpath;
	}
	
}
