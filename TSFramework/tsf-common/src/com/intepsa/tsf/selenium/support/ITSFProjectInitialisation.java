package com.intepsa.tsf.selenium.support;

public interface ITSFProjectInitialisation {
	
	void initDatabaseConnexion() throws Exception;
	
	void initDatabaseContent() throws Exception;
	
	void initServices() throws Exception;
	
	void cleanDatabase() throws Exception;
	
	void isUseDatabase() throws Exception;
}
