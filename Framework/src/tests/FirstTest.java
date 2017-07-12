package tests;

import pages.FirstPage;
import utils.BaseClass;
import utils.CommonUtils;

public class FirstTest {

	public static void main(String args[]){
		
		BaseClass.openBrowser("firefoxMac");
		CommonUtils.navigateToURL("https://www.google.com");
		CommonUtils.getPageTitle();
		System.out.println(CommonUtils.getCurrentURL());
		CommonUtils.click(FirstPage.searchGoogleButton);
		BaseClass.closeBrowser();
	}
	
}
