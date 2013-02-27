package roark.utilities.reporting;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.ErrorCode;

public class NewLogForTestSuiteFileAppender extends FileAppender {
public static final String date = "dd-MM-yyy HH:mm:ss";
public NewLogForTestSuiteFileAppender() {
}

public NewLogForTestSuiteFileAppender(Layout layout, String filename,
		boolean append, boolean bufferedIO, int bufferSize)
		throws IOException {
	super(layout, filename, append, bufferedIO, bufferSize);
}

public NewLogForTestSuiteFileAppender(Layout layout, String filename,
		boolean append) throws IOException {
	super(layout, filename, append);
}

public NewLogForTestSuiteFileAppender(Layout layout, String filename)
		throws IOException {
	super(layout, filename);
}

public void activateOptions() {
if (fileName != null) {
	try {
		fileName = getNewLogFileName();
		setFile(fileName, fileAppend, bufferedIO, bufferSize);
	} catch (Exception e) {
		errorHandler.error("Error while activating log options", e,
				ErrorCode.FILE_OPEN_FAILURE);
	}
}
}

private String getNewLogFileName() {
if (fileName != null) {
	final String DOT = ".";
	final String HIPHEN = "_";
	final File logFile = new File(fileName);
	final String fileName = logFile.getName();
	String newFileName = "";

	final int dotIndex = fileName.indexOf(DOT);
	if (dotIndex != -1) {
		// the file name has an extension. so, insert the time stamp
		// between the file name and the extension
	/*	newFileName = fileName.substring(0, dotIndex) + HIPHEN
				+ System.currentTimeMillis()+ DOT
				+ fileName.substring(dotIndex + 1);*/
		newFileName = fileName.substring(0, dotIndex) + HIPHEN
				+ System.currentTimeMillis()+ DOT
				+ fileName.substring(dotIndex + 1);
	} else {
		// the file name has no extension. So, just append the timestamp
		// at the end.
		//newFileName = fileName + HIPHEN + System.currentTimeMillis();
		newFileName = fileName + HIPHEN + this.getDateTime();
	}
	System.out.println("logFilePath:"+logFile.getParent() + File.separator + newFileName );
	return logFile.getParent() + File.separator + newFileName;
}
return null;
}


private String getDateTime(){
	
	Calendar calendar = Calendar.getInstance();
	SimpleDateFormat dateFormat = new SimpleDateFormat(date);
	String currDateTime = dateFormat.format(calendar.getTime());
	return currDateTime.replace(":", ".").replace(" ", "_");
	
}
}