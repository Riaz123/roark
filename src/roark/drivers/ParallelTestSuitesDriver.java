package roark.drivers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
 
public class ParallelTestSuitesDriver {
 
    public static void main(String[] args) {
    	ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new TestSuites("QueStatus", "YES", "GOOGLECHROME"));
        executor.execute(new TestSuites("TestcaseType", "SMOKE", "FIREFOX"));
        //executor.execute(new TestSuites("GOOGLECHROME"));
        executor.execute(new TestSuites("TestcaseType", "SMOKE", "FIREFOX"));


        //executor.execute(new TestSuites("GOOGLECHROME"));
        
        System.out.println("Starting shutdown...");
        executor.shutdown();
 
        try {
            executor.awaitTermination(500, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            System.out.println("Interrupted...");
        }
 
        System.out.println("All done!");
 
    }
}