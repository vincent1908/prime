package tests;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import utils.TestBase;

public class Test1 extends TestBase {
	int indexSI = 1;

	String testcaseName = this.getClass().getSimpleName();

	String methodName;
	
	Boolean testCaseStatus = false;

	@Test
	public void t() throws Exception {

		methodName = new Object() {}.getClass().getEnclosingMethod().getName();

		System.out.println(testcaseName + "..."+methodName);

		createDir();
		// createInstanceOfClass("Test2");

		int a = 5;

		if (a == 5) {

			
		}
		
	}
	
	public void v() throws Exception {

		methodName = new Object() {}.getClass().getEnclosingMethod().getName();

		System.out.println(testcaseName + "..."+methodName);

		createDir();
		// createInstanceOfClass("Test2");

		int a = 5;

		if (a == 5) {

			
		}
		
	}
		
		@AfterTest
		public void ReportingTest() throws Exception{
			
			updateResult(indexSI++, testcaseName, "pass", methodName);

			
		}

	

	// void createInstanceOfClass(String className) throws
	// ClassNotFoundException, InstantiationException, IllegalAccessException{
	//
	//
	// Class classTemp = Class.forName(className);
	//
	// Object obj =classTemp.newInstance();
	// System.out.println(obj);
	//
	//
	//
	// }
}
