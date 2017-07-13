package tests;

import org.testng.annotations.Test;

import utils.TestBase;

public class Test1 extends TestBase {
	int indexSI = 1;

	@Test
	public void t() throws Exception {
		
		createDir();

		int a = 5;

		if (a == 5) {

			updateResult(indexSI, "t", "pass", "Test1");
		}

	}

}
