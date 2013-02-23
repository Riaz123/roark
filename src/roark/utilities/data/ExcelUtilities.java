package roark.utilities.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import roark.jelenium.*;
import roark.utilities.data.EnvironmentVariables;

public class ExcelUtilities {
	static Logger logger = Logger.getLogger(ExcelUtilities.class);
	public ExcelUtilities(){
		
	}
	
	public TestSuite createTestSuiteByTestcaseType(String tcType){
		TestSuite suite= new TestSuite();
		EnvironmentVariables ev = EnvironmentVariables.getInstance();
		List<String> tscolNames = new ArrayList<String>();
		//TestcaseID TestcaseName	ApplicationID	TestcaseType	ExecutionQue
        // use colDetails, if you want to add colType if the excel has data , that has other datatypes
		tscolNames.add("TestcaseID"); 
        tscolNames.add("TestcaseName");
        tscolNames.add("ApplicationID");
        tscolNames.add("TestcaseType");
        tscolNames.add("ExecutionQue");

		String tsfilePath = ev.getTestSuiteFilePathPrefix().trim()+ ev.getFileExtension(); // 
		logger.info("testsuiteFilepath::"+tsfilePath);
		String tssheetName=ev.getTestSuiteSheetName();
		String tsqWhereClause= "TestcaseType='"+tcType + "'"; 
		try {
			suite = this.createTestSuite(tsfilePath,tssheetName ,tscolNames ,tsqWhereClause);
			logger.info("createTestSuiteByTestcaseType is done");
		} catch (Exception e) {
			logger.error("Exception in createTestSuiteByTestcaseType \n stacktraceInfo::"+ e.getMessage());
		}
		return suite;
	}
	
	public TestSuite createTestSuiteByExecutionQue(String queStatus){
		
		TestSuite suite= new TestSuite();
		EnvironmentVariables ev = EnvironmentVariables.getInstance();
		List<String> tscolNames = new ArrayList<String>();
		//TestcaseID TestcaseName	ApplicationID	TestcaseType	ExecutionQue
        // use colDetails, if you want to add colType if the excel has data , that has other datatypes
		tscolNames.add("TestcaseID"); 
        tscolNames.add("TestcaseName");
        tscolNames.add("ApplicationID");
        tscolNames.add("TestcaseType");
        tscolNames.add("ExecutionQue");

		String tsfilePath = ev.getTestSuiteFilePathPrefix().trim()+ ev.getFileExtension(); // 
		logger.info("testsuiteFilepath::"+tsfilePath);
		String tssheetName=ev.getTestSuiteSheetName();
		String tsqWhereClause= "ExecutionQue='"+queStatus+"'"; 
		try {
			suite = this.createTestSuite(tsfilePath,tssheetName ,tscolNames ,tsqWhereClause);
			logger.info("createTestSuiteByExecutionQue is done");
		} catch (Exception e) {
			logger.error("Exception in createTestSuiteByExecutionQue \n stacktraceInfo::"+ e.getMessage());
		}
		return suite;
	}
	public TestSuite createTestSuiteByAppID(String appID){
		TestSuite testsuite= new TestSuite();
		EnvironmentVariables ev = EnvironmentVariables.getInstance();
		List<String> tscolNames = new ArrayList<String>();
        tscolNames.add("TestcaseID");                                           
        tscolNames.add("TestcaseName");
        tscolNames.add("ApplicationID");
        tscolNames.add("TestcaseType");
        tscolNames.add("ExecutionQue");

		String tsfilePath = ev.getTestSuiteFilePathPrefix().trim()+ ev.getFileExtension(); // 
		String tssheetName=ev.getTestSuiteSheetName();
		String tsqWhereClause="ApplicationID='"+appID+"'"; 
		try {
			testsuite = this.createTestSuite(tsfilePath,tssheetName ,tscolNames , tsqWhereClause);
		} catch (Exception e) {
			logger.error("Exception in createTestSuiteByAppID \n stacktraceInfo::"+ e.getMessage());
			//e.printStackTrace();
		}
		return testsuite;
	}
	private TestSuite createTestSuite(String tsfilePath, String tssheetName,List<String> tscolNames, String tsqWhereClause) {
		
		TestSuite testSuite = new TestSuite();
		Connection conn1 = null;
		Statement stmnt = null;

		try
			{
				//Class.forName( "sun.jdbc.odbc.JdbcOdbcDriver" );
				conn1 = DriverManager.getConnection( "jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};DBQ="+tsfilePath+"");
				stmnt = conn1.createStatement();
				String suiteQuery ;
				if (tsqWhereClause.equals("")==false){
					suiteQuery = "select * from ["+tssheetName+"$] where "+tsqWhereClause+";";
				}
				else{
					suiteQuery = "select * from ["+tssheetName+"$];";
				}
				ResultSet tSuiteTable = stmnt.executeQuery(suiteQuery);
				Map<String, Map<String, String>> testSuiteInfo = new LinkedHashMap<String, Map<String, String>>();
				
				while( tSuiteTable.next() )
						{
					    	Map<String, String> testcaseProps = new HashMap<String, String>();
					    	for(String tscolName : tscolNames){
					    		if(! tscolName.equals("TestcaseID")){
					    			testcaseProps.put(tscolName, tSuiteTable.getString(tscolName));
					    		}
					    	}
						testSuiteInfo.put(tSuiteTable.getString("TestcaseID"), testcaseProps);
						}
				testSuite.setTestSuiteInfo(testSuiteInfo);
				logger.info("TestSuiteInfo added ::"+ testSuite.getTestSuiteInfo().keySet().toString());
				
			}catch(Exception e){
				logger.error("Exception in createTestSuite \n stacktraceInfo::"+e.getMessage());
				//e.printStackTrace();
		}finally{
			try {
				stmnt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			try {
				conn1.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		return testSuite;
	
	}
	
	public Map<String, List<TestcaseStep>> readTestcases(Map<String, Map<String, String>> testSuiteInfo ) {
		EnvironmentVariables ev = EnvironmentVariables.getInstance();
		//TestcaseID	StepID	Keyword	ApplicationID	FieldName	TestData	CheckPoint
		Map<String, List<TestcaseStep>> testCaseInfo = new LinkedHashMap<String, List<TestcaseStep>>();

		for (String tcID : testSuiteInfo.keySet()) {
			logger.info("reading Testcase ::"+tcID);
		    String currAppID = testSuiteInfo.get(tcID).get("ApplicationID");
		    String testcasesFilePath= ev.getTestcasesFilePathPrefix() +currAppID+ ev.getFileExtension() ;
			String tcsheetName = ev.getTestcasesSheetName();
			Connection conn1 = null;
			Statement stmnt = null;
			try
				{
					//Class.forName( "sun.jdbc.odbc.JdbcOdbcDriver" );
					conn1 = DriverManager.getConnection( "jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};DBQ="+testcasesFilePath+"");
					stmnt = conn1.createStatement();
					logger.info("TestcaseID ::"+tcID);
					String tcQuery = "select * from ["+tcsheetName+"$] where TestcaseID='"+tcID+"';";
					ResultSet tStepTable = stmnt.executeQuery(tcQuery);
					while( tStepTable.next() )
						{	
							String currKeyword = tStepTable.getString("Keyword");
							if (currKeyword.equalsIgnoreCase("ExecuteTestComponent")==false ){
								TestcaseStep step = new TestcaseStep();
								step.setTestcaseID(tcID);
								step.setStepID(tStepTable.getString("StepID"));
								step.setFieldName(tStepTable.getString("FieldName"));
								step.setApplicationID(tStepTable.getString("ApplicationID"));
								step.setKeyword(currKeyword);
								step.setTestDataType(tStepTable.getString("TestdataType"));
								step.setTestDataName(tStepTable.getString("Testdata"));
								step.setCheckPoint(tStepTable.getString("Checkpoint"));
	
								if(testCaseInfo.get(tcID)== null){
									List<TestcaseStep> steps = new ArrayList<TestcaseStep>();
									steps.add(step);
									testCaseInfo.put(tcID, steps);
									logger.info("Added a new Testcase - \n" +tcID  );
									logger.info("Added a new TestcaseStep \n"+step.getStepID());
								}else{
									List<TestcaseStep> steps = testCaseInfo.get(tcID);
									steps.add(step);
									logger.info("Updating the Testcase - \n" +tcID  );
									logger.info("Added a new TestcaseStep \n"+step.getStepID());
								}
							}else{
								String tcomID= tStepTable.getString("FieldName");
								String appID =  tStepTable.getString("ApplicationID");
								String stepID =  tStepTable.getString("StepID");
								logger.info("Keyword is ExecuteTestComponent @stepID:"+stepID + "  in Testcase, ID:"+tcID);
								logger.info("TestComponentID::"+tcomID );
								testCaseInfo=this.readTestcaseStepsFromComponent(appID, tcID, stepID, tcomID, testCaseInfo);
							}
						}
					tStepTable.close();
				}catch(Exception e){
					//e.printStackTrace();
					logger.error("Exception in readTestcases \n stacktraceInfo::"+ e.getMessage());
				}finally{
					try {
						stmnt.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					}
					try {
						conn1.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					}
				}
		}
		return testCaseInfo;
	}
	
	public Map<String, List<TestcaseStep>> readTestcaseStepsFromComponent(String appID, String testcaseID, String testcaseStepID,String testcomponentID,Map<String, List<TestcaseStep>> testCaseInfo){
		EnvironmentVariables ev = EnvironmentVariables.getInstance();
		String tcofilePath=ev.getTestComponentsFilePathPrefix().trim()+appID.trim()+  ev.getFileExtension();
		String tcosheetName= ev.getTestComponentsSheetName().trim();
		Connection conn = null;
		Statement stmnt = null;
		try
		{
			conn = DriverManager.getConnection( "jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};DBQ="+tcofilePath+"");
	 		stmnt = conn.createStatement();
	 		String tcoQuery = "select * from ["+tcosheetName+"$] where TestComponentID='"+testcomponentID+"';";
			ResultSet tcoStepTable = stmnt.executeQuery(tcoQuery);
			while(tcoStepTable.next()){
				String tcoKeyword= tcoStepTable.getString("Keyword");
				logger.info("tcoKeyword::"+tcoKeyword);
				if(tcoKeyword.equalsIgnoreCase("ExecuteTestComponent")==false){
					TestcaseStep step = new TestcaseStep();
					step.setTestcaseID(testcaseID);
					step.setStepID(testcaseStepID+ "."+tcoStepTable.getString("StepID"));
					step.setApplicationID(tcoStepTable.getString("ApplicationID"));
					step.setFieldName(tcoStepTable.getString("FieldName"));
					step.setKeyword(tcoKeyword);
					step.setTestDataType(tcoStepTable.getString("TestdataType"));
					step.setTestDataName(tcoStepTable.getString("Testdata"));
					step.setCheckPoint(tcoStepTable.getString("Checkpoint"));
						if(testCaseInfo.get(testcaseID)== null){
							List<TestcaseStep> steps = new ArrayList<TestcaseStep>();
							steps.add(step);
							testCaseInfo.put(testcaseID, steps);
							logger.info("Added a new Testcase - \n" +testcaseID  );
							logger.info("Added a new TestcaseStep \n"+step.getStepID());
						}else{
							List<TestcaseStep> steps = testCaseInfo.get(testcaseID);
							steps.add(step);
							logger.info("Updating the Testcase - \n" +testcaseID  );
							logger.info("Added a new TestcaseStep \n"+step.getStepID());
						}
				}else{
					String tcomID= tcoStepTable.getString("FieldName");
					String tcoappID =  tcoStepTable.getString("ApplicationID");
					String tcostepID =  tcoStepTable.getString("StepID");
					testCaseInfo=this.readTestcaseStepsFromComponent( tcoappID, testcaseID, testcaseStepID+ "."+tcostepID , tcomID, testCaseInfo);
				}
			}
		}catch(Exception e){
			//e.printStackTrace();
			logger.error("Exception in readTestcaseStepsFromComponent - "+e.getMessage());
		}finally{
			try{
				stmnt.close();
				conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return testCaseInfo;

	}
	public Map<String, List<Map<String, String>>> readTestdata( Map<String, Map<String, String>> testSuiteInfo) {
		EnvironmentVariables ev = EnvironmentVariables.getInstance();
		Map<String, List<Map<String, String>>> testdataSets= new LinkedHashMap<String, List<Map<String, String>>>();

		for (String tcID : testSuiteInfo.keySet()) {
			Connection conn1 = null;
			Statement stmnt = null;
			String tdsheetName= ev.getTestDataSheetName();
		    String currAppID = testSuiteInfo.get(tcID).get("ApplicationID");
		    String tdfilePath=   ev.getTestDataFilePathPrefix()+currAppID+ ev.getFileExtension();
			try
			{
				ResultSet tdStepTable = null;
				conn1 = DriverManager.getConnection( "jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};DBQ="+tdfilePath+"");
		 		stmnt = conn1.createStatement();
		 		String tdQuery = "select * from ["+tdsheetName+"$] where TestcaseID='"+tcID+"';";
				tdStepTable = stmnt.executeQuery(tdQuery);
				List<Map<String, String>> testdataset = new ArrayList<Map<String, String>>();
	
				while(tdStepTable.next()){
					if(testdataSets.get(tcID)== null){
						
						logger.info("reading Testdata- adding testdata records to a new test case:"+tcID);
					    Map<String, String> testdataRecord = new HashMap<String, String>();
					    testdataRecord.put("TestcaseID", tdStepTable.getString("TestcaseID"));
					    testdataRecord.put("TestdataName", tdStepTable.getString("TestdataName"));
					    testdataRecord.put("TestdataValue", tdStepTable.getString("TestdataValue"));
					    testdataset.add(testdataRecord);
					    logger.info("Added testdata set - \n"+testdataRecord.toString() );
					    testdataSets.put(tcID, testdataset);
						
					}else{
						logger.info("reading Testdata - Updating test data records to an existing test case:"+tcID);
					    Map<String, String> testdataRecord = new HashMap<String, String>();
					    testdataRecord.put("TestcaseID", tdStepTable.getString("TestcaseID"));
					    testdataRecord.put("TestdataName", tdStepTable.getString("TestdataName"));
					    testdataRecord.put("TestdataValue", tdStepTable.getString("TestdataValue"));	
					    logger.info("Added testdata set - \n"+testdataRecord.toString() );
					    testdataSets.get(tcID).add(testdataRecord);
					}
				}
				tdStepTable.close();
			}catch(Exception e){
				//e.printStackTrace();
				logger.error("Exception in readTestdata  \n stacktraceInfo::"+e.getMessage());
			}finally{
				try {
					stmnt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
				try {
					conn1.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}
		}
		return testdataSets;
		}
	public Map<String, List<Map<String, String>>> readLocators(List<String> locAppIDs){
		Map<String, List<Map<String, String>>> locatorSets= new LinkedHashMap<String, List<Map<String, String>>>();
		if(locAppIDs.size()>0){
			EnvironmentVariables ev = EnvironmentVariables.getInstance();
			for (String appID : locAppIDs) {
				Connection conn1 = null;
				Statement stmnt = null;
				String locsheetName= ev.getFieldDefnSheetName();
			    String locfilePath= ev.getFieldDefnFilePathPrefix()+appID+ ev.getFileExtension();
				try
				{
					ResultSet locTable = null;
					conn1 = DriverManager.getConnection( "jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};DBQ="+locfilePath+"");
			 		stmnt = conn1.createStatement();
			 		String locQuery = "select * from ["+locsheetName+"$];";
					locTable = stmnt.executeQuery(locQuery);
					List<Map<String, String>> locset = new ArrayList<Map<String, String>>();
		
					while(locTable.next()){
						if(locatorSets.get(appID)== null){
							
							logger.info("reading LOCATORS- adding locators records to a new applicationID:"+appID);
						    Map<String, String> locRecord = new HashMap<String, String>();
						    locRecord.put("ScreenName", locTable.getString("ScreenName").trim());
						    locRecord.put("FieldName", locTable.getString("FieldName").trim());
						    locRecord.put("FieldDefinition", locTable.getString("FieldDefinition").trim());
						    locset.add(locRecord);
						    locatorSets.put(appID, locset);
							
						}else{
							logger.info("reading LOCATORS - Updating  locators records to a new applicationID:"+appID);
						    Map<String, String> locRecord = new HashMap<String, String>();
						    locRecord.put("ScreenName", locTable.getString("ScreenName").trim());
						    locRecord.put("FieldName", locTable.getString("FieldName").trim());
						    locRecord.put("FieldDefinition", locTable.getString("FieldDefinition").trim());
						    locatorSets.get(appID).add(locRecord);
						}
					}
		
				}catch(Exception e){
					logger.error("Exception in reading locators \n stacktraceInfo::"+e.getMessage());
				}finally{
					try {
						stmnt.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					}
					try {
						conn1.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					}
				}
			}

		}else{
			logger.error("Error in reading Locators - appIDs List contains"+ locAppIDs.size() + " appIDs");
		}
				return locatorSets;
		}


	
}
