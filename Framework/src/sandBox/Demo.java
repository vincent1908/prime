package sandBox;

public class Demo {

	public static void main(String[] args) {
		
		String driverPath = "//lib//chromedriver.exe";
		String workingDirectory = System.getProperty("user.dir" +"//"+ driverPath);
		System.out.println(workingDirectory);
	}

}
