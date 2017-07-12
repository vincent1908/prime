package utils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.runner.Result;

public class Reporter {
	

	private static List<Result> details;
	private static final String resultPlaceholder = "<!-- INSERT_RESULTS -->";
	private static final String templatePath = "//Users//Gregoryvincent//Documents//Selenium//report_template.html";
			
	public Reporter() {
	}
	
	public static void initialize() {
		details = new ArrayList<Result>();
	}
	
	public static void report(String actualValue,String expectedValue) {
		if(actualValue.equals(expectedValue)) {
			Result r = new Result();
			details.add(r);
		} else {
			Result r = new Result();
			details.add(r);
		}
	}
	
	public static void showResults() {
		for (int i = 0;i < details.size();i++) {
	//		System.out.println("Result " + Integer.toString(i) + ": " + ((Object) details.get(i)).getResult());
		}
	}
	
	public static void writeResults() {
		try {
			String reportIn = new String(Files.readAllBytes(Paths.get(templatePath)));
			for (int i = 0; i < details.size();i++) {
				reportIn = reportIn.replaceFirst(resultPlaceholder,"<tr><td>" + Integer.toString(i+1) + "</td><td>" + resultPlaceholder);
			}
			
			String currentDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
			String reportPath = "//Users//Gregoryvincent//Documents//Selenium//report_" + currentDate + ".html";
			Files.write(Paths.get(reportPath),reportIn.getBytes(),StandardOpenOption.CREATE);
			
		} catch (Exception e) {
			System.out.println("Error when writing report file:\n" + e.toString());
		}
	}

}
