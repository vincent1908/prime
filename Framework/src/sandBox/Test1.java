package sandBox;

import java.io.IOException;

import org.testng.annotations.Test;

public class Test1 extends TestBase{
	int indexSI  = 1;
	
	@Test
	public void t() throws Exception{
	
		int a = 5;
		
		if(a==5){
			
			updateResult(indexSI, "t", "pass", "Test1");
		}
		
	}

}
