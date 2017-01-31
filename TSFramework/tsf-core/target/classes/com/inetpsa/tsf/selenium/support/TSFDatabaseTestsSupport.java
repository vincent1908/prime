package com.inetpsa.tsf.selenium.support;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Classe InitTests.
 * Specifique LTP => faire une interface dans Comon et donner une implémentation LTP et STS
 * @author u118157
 */
public final class TSFDatabaseTestsSupport {
	
	/** Logger. */
	private static Log log = LogFactory.getLog(TSFDatabaseTestsSupport.class);

	/** Liste des packages ou il faut chercher les fichiers DDL. */
	protected static final List DEFAULT_LIST_PACKAGES = new ArrayList();

	/** Init de la liste des packages ou il faut chercher les fichiers DDL par défaut */
	static {
		DEFAULT_LIST_PACKAGES.add("com/inetpsa/ltp");
		DEFAULT_LIST_PACKAGES.add("com/inetpsa/gan");
	}

	/** User oracle. */
	private static final String USER_ORACLE = "pyt01_m1";
	
	/** Password oracle. */
	private static final String PASSWD_ORACLE = "pyt01_m1";

	/** User hsql. */
	public static final String USER_HSQL= "SA";
	
	/** Password hsql. */
	private static final String PASSWD_HSQL = "SA";

	/** User h2. */
	public static final String USER_H2= "SA";
	
	/** Password h2. */
	private static final String PASSWD_H2 = "SA";


	/** Type ORACLE. */
	public static final String BASE_ORACLE = "ORACLE";
	
	/** Type HSQL. */
	public static final String BASE_HSQL = "HSQL";	
	
	/** Type H2. */
	public static final String BASE_H2 = "H2";	

	/** Driver ORACLE. */
	private static final String DRIVER_ORACLE = "oracle.jdbc.driver.OracleDriver";	

	/** Driver HSQL. */
	public static final String DRIVER_HSQL = "org.hsqldb.jdbcDriver";	

	/** Driver H2. */
	public static final String DRIVER_H2 = "org.h2.Driver";	

	/** Manager ddl. */
	////private static ConvertDDL convertDDL ;

	/** HSQLDB hsqldb. */
	////private static HSQLDB hsqldb;

	/** Property ltp.dataBase.type */
	public static final String PROPERTY_DATABASE_TYPE = "ltp.dataBase.type";
	/** Property ltp.dataBase.name */
	public static final String PROPERTY_DATABASE_NAME = "ltp.dataBase.name";
	/** Property ltp.dataBase.machine */
	public static final String PROPERTY_DATABASE_MACHINE = "ltp.dataBase.machine";
	/** Property ltp.dataBase.port */
	public static final String PROPERTY_DATABASE_PORT = "ltp.dataBase.port";
	/** Property ltp.dataBase.hsql.mode */
	public static final String PROPERTY_DATABASE_HSQL_MODE = "ltp.dataBase.hsql.mode";
	/** Property ltp.dataBase.hsql.autoStart */
	public static final String PROPERTY_DATABASE_HSQL_AUTOSTART = "ltp.dataBase.hsql.autoStart";
	/** Property ltp.dataBase.hsql.cleanOnStartUp */
	public static final String PROPERTY_DATABASE_HSQL_CLEAN_ON_STARTUP = "ltp.dataBase.hsql.cleanOnStartUp";

	/** Type de base de données. */
	private static String databaseType;
	
	/** Nom de l'instance oracle. */
	private static String database;
	
	/** Nom de la machine oracle. */
	private static String machine;
	
	/** Port de connection oracle. */
	private static String port;

	/** Mode de la base : mémoire ou pas
	 * HSQLDB.HSQLDB_MODE_MEMORY_TABLES
	 * HSQLDB.HSQLDB_MODE_CACHED_TABLES
	 */
	private static String databaseHsqlMode;
	
	/** Démarrage automatique de la base de données HSQL ou non. */
	private static boolean databaseHsqlAutoStart;
	
	/** Vidage en automatique de la base HSQL au démarrage. */
	private static boolean databaseHsqlCleanOnStartUp;

	/**
	 * Constructeur de Tests.
	 */
	private TSFDatabaseTestsSupport() {
		super();
	}

	/**
	 * Initialisation de la connexion.
	 *
	 * @throws Exception : Exception générale
	 */
	public static void initConnection() throws Exception {		
		initConnection(null);
	}

	/**
	 * Initialisation des propriétés de la connexion.
	 *
	 * @throws Exception : Exception générale
	 */
	private static void initProperties() throws Exception {
		/** Refresh properties */
		databaseType = System.getProperty(PROPERTY_DATABASE_TYPE, BASE_HSQL);
		database = System.getProperty(PROPERTY_DATABASE_NAME, "DATABASE_TEST");
		machine = System.getProperty(PROPERTY_DATABASE_MACHINE, "localhost");
		port = System.getProperty(PROPERTY_DATABASE_PORT, "9123");

		///databaseHsqlMode = System.getProperty(PROPERTY_DATABASE_HSQL_MODE, HSQLDB.HSQLDB_MODE_CACHED_TABLES);
		databaseHsqlAutoStart = Boolean.valueOf(System.getProperty(PROPERTY_DATABASE_HSQL_AUTOSTART, "true")).booleanValue();
		databaseHsqlCleanOnStartUp = Boolean.valueOf(System.getProperty(PROPERTY_DATABASE_HSQL_CLEAN_ON_STARTUP, "true")).booleanValue();

	}

	/**
	 * Initialisation de la connexion.
	 *
	 * @param listPackagesTransmis List
	 * @throws Exception : Exception générale
	 */
	public static void initConnection(List listPackagesTransmis) throws Exception {
		startDatabase(listPackagesTransmis);
	}

	/**
	 * Démarrage de la base de données.
	 *
	 * @param listPackagesTransmis List
	 * @throws Exception : Exception générale
	 */
	public static void startDatabase(List listPackagesTransmis) throws Exception {
		initProperties();

		String driver = null;
		String url = null;
		String user = null;
		String pwd = null;

		if(BASE_ORACLE.equals(databaseType)){
			String oracleUrl = "jdbc:oracle:thin:@" + machine + ":" + port + ":" + database;
			driver = DRIVER_ORACLE;
			url = oracleUrl;
			user = USER_ORACLE;
			pwd = PASSWD_ORACLE;
		}else if(BASE_HSQL.equals(databaseType)){
			String hsqlUrl = "jdbc:hsqldb:hsql://" + machine + ":"	+ port + "/" + database;
			driver = DRIVER_HSQL;
			url = hsqlUrl;
			user = USER_HSQL;
			pwd = PASSWD_HSQL;
		}else if(BASE_H2.equals(databaseType)){
			String h2Url = "jdbc:h2:tcp://" + machine + ":"	+ port + "/" + database;
			driver = DRIVER_H2;
			url = h2Url;
			user = USER_H2;
			pwd = PASSWD_H2;
		}else {
			throw new Exception("Type de base non géré !");
		}  

		boolean initDDL = false;
//		if(hsqldb == null && BASE_HSQL.equals(databaseType) && databaseHsqlAutoStart){
//			log.warn("DEMARRAGE DE LA BASE " + databaseType + " : " + url + " avec le driver " + driver);
//
//			hsqldb = new HSQLDB(machine, database, Integer.valueOf(port).intValue());
//			hsqldb.setDbMode(databaseHsqlMode);
//			hsqldb.start();
//
//			/** Permet d'arrêter la base lors du dernier test 
//			 * Lorsque la JVM s'arrête
//			 */
//			Runtime.getRuntime().addShutdownHook(new Thread() {
//
//				public void run() {
//					log.warn("STOP DATABASE");
//
//					try {
//						hsqldb.stop();
//					} catch (DDLException e) {
//						e.printStackTrace();
//					}
//				}
//
//			});
//
//			initDDL = true;		
//		}
//
//		LTPTestCase.initJdbc(driver, url, user, pwd, true);
//
//		if(initDDL && BASE_HSQL.equals(databaseType)){		
//			initDatabaseWithDdl(listPackagesTransmis, url, user, pwd);
//		}
	}

	/**
	 * Initialisation de la structure de la base de données.
	 *
	 * @param listPackagesTransmis Collection
	 * @param url String
	 * @param user String
	 * @param pwd String
	 * @throws DDLException DDLException
	 */
	private static void initDatabaseWithDdl(Collection listPackagesTransmis,String url, 
			String user, String pwd) { ////throws DDLException {
		List listPackages = new ArrayList();
		listPackages.addAll(DEFAULT_LIST_PACKAGES);
		if(CollectionUtils.isNotEmpty(listPackagesTransmis)) {
			listPackages.addAll(listPackagesTransmis);
		} 

		/** On ne recrée la base que lors des tests en HSQL
		 * sinon on considère qu'elle est correcte sur ORACLE
		 */
//		if(convertDDL == null) {
//			convertDDL = new ConvertDDL();
//
//			DDLOptionsInputClassPath input = new DDLOptionsInputClassPath();
//			input.setPackagesNames(listPackages);
//			
//			DDLOptionsOutputDB output = new DDLOptionsOutputDB();
//			output.setOutputUrl(url);
//			output.setOutputUsername(user);
//			output.setOutputPassword(pwd);
//			output.setOutputSchemaPattern("PUBLIC");
//			
//			convertDDL.process(input, output);
//		}
	}

	/**
	 * Accesseur sur databaseHsqlAutoStart.
	 *
	 * @return boolean
	 */
	public static boolean isDatabaseHsqlAutoStart() {
		return databaseHsqlAutoStart;
	}

	/**
	 * Accesseur sur databaseHsqlCleanOnStartUp.
	 *
	 * @return boolean
	 */
	public static boolean isDatabaseHsqlCleanOnStartUp() {
		return databaseHsqlCleanOnStartUp;
	}

	/**
	 * Accesseur sur databaseType.
	 *
	 * @return String
	 */
	public static String getDatabaseType() {
		return databaseType;
	}

	/**
	 * Accesseur sur database.
	 *
	 * @return String
	 */
	public static String getDatabase() {
		return database;
	}

	/**
	 * Accesseur sur machine.
	 *
	 * @return String
	 */
	public static String getMachine() {
		return machine;
	}

	/**
	 * Accesseur sur port.
	 *
	 * @return String
	 */
	public static String getPort() {
		return port;
	}

}
