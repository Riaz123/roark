package roark.utilities.reporting;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class NewThreadLogger extends Thread{

    private Logger d_logger;

	public void getLogger(){
	  	long tid = Thread.currentThread().getId();
	  	d_logger = Logger.getLogger("roark.utilities.reporting.NewThreadLogger"+tid);
	  	String logFileName = "doSomething"+tid+".log";

	  	Properties prop = new Properties();
	  	prop.setProperty("roark.utilities.reporting.NewThreadLogger"+tid,"INFO, WORKLOG");
	  	prop.setProperty("log4j.appender.WORKLOG","org.apache.log4j.FileAppender");
	  	prop.setProperty("log4j.appender.WORKLOG.File", logFileName);
	  	prop.setProperty("log4j.appender.WORKLOG.layout","org.apache.log4j.PatternLayout");
	  	prop.setProperty("log4j.appender.WORKLOG.layout.ConversionPattern","%d %c{1} - %m%n");
	  	prop.setProperty("log4j.appender.WORKLOG.Threshold","INFO"); 
	  	PropertyConfigurator.configure(prop);
	}
	
}
