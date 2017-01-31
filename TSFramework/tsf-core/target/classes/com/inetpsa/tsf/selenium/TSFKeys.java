package com.inetpsa.tsf.selenium;



import java.awt.event.KeyEvent;

import org.openqa.selenium.Keys;

/**
 * TSFKeys
 * @author e452276
 */
public enum TSFKeys {

    /** Number key 0*/
    NUM0         ("0", 0x30),
    /** Number key 1*/
    NUM1         ("1", 0x31),
    /** Number key 2*/
    NUM2         ("2", 0x32),
    /** Number key 3*/
    NUM3         ("3", 0x33),
    /** Number key 4*/
    NUM4         ("4", 0x34),
    /** Number key 5*/
    NUM5         ("5", 0x35),
    /** Number key 6*/
    NUM6         ("6", 0x36),
    /** Number key 7*/
    NUM7         ("7", 0x37),
    /** Number key 8*/
    NUM8         ("8", 0x38),
    /** Number key 9*/
    NUM9         ("9", 0x39),
    
    /** Control key CANCEL*/
	CANCEL		(Keys.CANCEL, 		KeyEvent.VK_CANCEL),
	/** Control key HELP*/
	HELP		(Keys.HELP, 		KeyEvent.VK_HELP),
	/** Control key BACK_SPACE*/
	BACK_SPACE	(Keys.BACK_SPACE, 	KeyEvent.VK_BACK_SPACE),
	/** Control key TAB*/
	TAB			(Keys.TAB, 			KeyEvent.VK_TAB),
	/** Control key CLEAR*/
	CLEAR		(Keys.CLEAR, 		KeyEvent.VK_CLEAR),
	/** Control key ENTER*/
	ENTER		(Keys.ENTER, 		KeyEvent.VK_ENTER),
	/** Control key SHIFT*/
	SHIFT		(Keys.SHIFT, 		KeyEvent.VK_SHIFT),
	/** Control key CONTROL*/
	CONTROL		(Keys.CONTROL, 		KeyEvent.VK_CONTROL),
	/** Control key ALT*/
	ALT			(Keys.ALT, 			KeyEvent.VK_ALT),
	/** Control key PAUSE*/
	PAUSE		(Keys.PAUSE, 		KeyEvent.VK_PAUSE),
	/** Control key ESCAPE*/
	ESCAPE		(Keys.ESCAPE, 		KeyEvent.VK_ESCAPE),
	/** Control key SPACE*/
	SPACE		(Keys.SPACE, 		KeyEvent.VK_SPACE),
	/** Control key PAGE_UP*/
	PAGE_UP		(Keys.PAGE_UP, 		KeyEvent.VK_PAGE_UP),
	/** Control key PAGE_DOWN*/
	PAGE_DOWN	(Keys.PAGE_DOWN, 	KeyEvent.VK_PAGE_DOWN),
	/** Control key END*/
	END			(Keys.END, 			KeyEvent.VK_END),
	/** Control key HOME*/
	HOME		(Keys.HOME, 		KeyEvent.VK_HOME),
	/** Control key LEFT*/
	LEFT		(Keys.LEFT, 		KeyEvent.VK_LEFT),
	/** Control key UP*/
	UP			(Keys.UP, 			KeyEvent.VK_UP),
	/** Control key RIGHT*/
	RIGHT		(Keys.RIGHT, 		KeyEvent.VK_RIGHT),
	/** Control key DOWN*/
	DOWN        (Keys.DOWN, 		KeyEvent.VK_DOWN),
	/** Control key INSERT*/
	INSERT      (Keys.INSERT, 		KeyEvent.VK_INSERT),
	/** Control key DELETE*/
	DELETE		(Keys.DELETE, 		KeyEvent.VK_DELETE),
	/** Control key SEMICOLON*/
	SEMICOLON	(Keys.SEMICOLON, 	KeyEvent.VK_SEMICOLON),
	/** Control key EQUALS*/
	EQUALS      (Keys.EQUALS, 		KeyEvent.VK_EQUALS),
	
	/** Number pad key 0*/
	NUMPAD0      (Keys.NUMPAD0,     KeyEvent.VK_NUMPAD0),
	/** Number pad key 1*/
	NUMPAD1      (Keys.NUMPAD1,     KeyEvent.VK_NUMPAD1),
	/** Number pad key 2*/
	NUMPAD2      (Keys.NUMPAD2,     KeyEvent.VK_NUMPAD2),
	/** Number pad key 3*/
	NUMPAD3      (Keys.NUMPAD3,     KeyEvent.VK_NUMPAD3),
	/** Number pad key 4*/
	NUMPAD4      (Keys.NUMPAD4,     KeyEvent.VK_NUMPAD4),
	/** Number pad key 5*/
	NUMPAD5      (Keys.NUMPAD5,     KeyEvent.VK_NUMPAD5),
	/** Number pad key 6*/
	NUMPAD6      (Keys.NUMPAD6,     KeyEvent.VK_NUMPAD6),
	/** Number pad key 7*/
	NUMPAD7      (Keys.NUMPAD7,     KeyEvent.VK_NUMPAD7),
	/** Number pad key 8*/
	NUMPAD8      (Keys.NUMPAD8,     KeyEvent.VK_NUMPAD8),
	/** Number pad key 9*/
	NUMPAD9      (Keys.NUMPAD9,     KeyEvent.VK_NUMPAD9),
	
	/** Number pad key MULTIPLY*/
	MULTIPLY     (Keys.MULTIPLY, 	KeyEvent.VK_MULTIPLY),
	/** Number pad key ADD*/
	ADD          (Keys.ADD, 		KeyEvent.VK_ADD),
	/** Number pad key SEPARATOR*/
	SEPARATOR    (Keys.SEPARATOR, 	KeyEvent.VK_SEPARATER),
	/** Number pad key SUBTRACT*/
	SUBTRACT     (Keys.SUBTRACT, 	KeyEvent.VK_SUBTRACT),
	/** Number pad key DECIMAL*/
	DECIMAL      (Keys.DECIMAL, 	KeyEvent.VK_DECIMAL),
	/** Number pad key DIVIDE*/
	DIVIDE       (Keys.DIVIDE, 		KeyEvent.VK_DIVIDE),
	/** Number pad key META*/
	META         ("\uE03D",         KeyEvent.VK_META),
	
	/** Function key F1*/
	F1           (Keys.F1,  KeyEvent.VK_F1),
	/** Function key F2*/
	F2           (Keys.F2,  KeyEvent.VK_F2),
	/** Function key F3*/
	F3           (Keys.F3,  KeyEvent.VK_F3),
	/** Function key F4*/
	F4           (Keys.F4,  KeyEvent.VK_F4),
	/** Function key F5*/
	F5           (Keys.F5,  KeyEvent.VK_F5),
	/** Function key F6*/
	F6           (Keys.F6,  KeyEvent.VK_F6),
	/** Function key F7*/
	F7           (Keys.F7,  KeyEvent.VK_F7),
	/** Function key F8*/
	F8           (Keys.F8,  KeyEvent.VK_F8),
	/** Function key F9*/
	F9           (Keys.F9,  KeyEvent.VK_F9),
	/** Function key F10*/
	F10          (Keys.F10, KeyEvent.VK_F10),
	/** Function key F11*/
	F11          (Keys.F11, KeyEvent.VK_F11),
	/** Function key F12*/
	F12          (Keys.F12, KeyEvent.VK_F12),
	
	/** Alpha key A*/
    A			("A", KeyEvent.VK_A),
    /** Alpha key B*/
    B			("B", KeyEvent.VK_B), 
    /** Alpha key C*/
    C			("C", KeyEvent.VK_C), 
    /** Alpha key D*/
    D			("D", KeyEvent.VK_D), 
    /** Alpha key E*/
    E			("E", KeyEvent.VK_E), 
    /** Alpha key F*/
    F			("F", KeyEvent.VK_F),
    /** Alpha key G*/
    G			("G", KeyEvent.VK_G), 
    /** Alpha key H*/
    H			("H", KeyEvent.VK_H), 
    /** Alpha key I*/
    I			("I", KeyEvent.VK_I), 
    /** Alpha key J*/
    J			("J", KeyEvent.VK_J), 
    /** Alpha key K*/
    K			("K", KeyEvent.VK_K), 
    /** Alpha key L*/
    L			("L", KeyEvent.VK_L), 
    /** Alpha key M*/
    M			("M", KeyEvent.VK_M), 
    /** Alpha key N*/
    N			("N", KeyEvent.VK_N), 
    /** Alpha key O*/
    O			("O", KeyEvent.VK_O), 
    /** Alpha key P*/
    P			("P", KeyEvent.VK_P),
    /** Alpha key Q*/
    Q			("Q", KeyEvent.VK_Q), 
    /** Alpha key R*/
    R			("R", KeyEvent.VK_R), 
    /** Alpha key S*/
    S			("S", KeyEvent.VK_S), 
    /** Alpha key T*/
    T			("T", KeyEvent.VK_T), 
    /** Alpha key U*/
    U			("U", KeyEvent.VK_U), 
    /** Alpha key V*/
    V			("V", KeyEvent.VK_V), 
    /** Alpha key W*/
    W			("W", KeyEvent.VK_W), 
    /** Alpha key X*/
    X			("X", KeyEvent.VK_X),
    /** Alpha key Y*/
    Y			("Y", KeyEvent.VK_Y), 
    /** Alpha key Z*/
    Z			("Z", KeyEvent.VK_Z),
	
    /** Alpha key a*/
    L_A			("a", 0x61),
    /** Alpha key b*/
    L_B			("b", 0x62),
    /** Alpha key c*/
    L_C			("c", 0x63),
    /** Alpha key d*/
    L_D			("d", 0x64), 
    /** Alpha key e*/
    L_E			("e", 0x65), 
    /** Alpha key f*/
    L_F			("f", 0x66), 
    /** Alpha key g*/
    L_G			("g", 0x67), 
    /** Alpha key h*/
    L_H			("h", 0x68), 
    /** Alpha key i*/
    L_I			("i", 0x69), 
    /** Alpha key j*/
    L_J			("j", 0x6a), 
    /** Alpha key k*/
    L_K			("k", 0x6b), 
    /** Alpha key l*/
    L_L			("l", 0x6c), 
    /** Alpha key m*/
    L_M			("m", 0x6d), 
    /** Alpha key n*/
    L_N			("n", 0x6e), 
    /** Alpha key o*/
    L_O			("o", 0x6f), 
    /** Alpha key p*/
    L_P			("p", 0x70), 
    /** Alpha key q*/
    L_Q			("q", 0x71), 
    /** Alpha key r*/
    L_R			("r", 0x72), 
    /** Alpha key s*/
    L_S			("s", 0x73), 
    /** Alpha key t*/
    L_T			("t", 0x74), 
    /** Alpha key u*/
    L_U			("u", 0x75), 
    /** Alpha key v*/
    L_V			("v", 0x76), 
    /** Alpha key w*/
    L_W			("w", 0x77), 
    /** Alpha key x*/
    L_X			("x", 0x78), 
    /** Alpha key y*/
    L_Y			("y", 0x79), 
    /** Alpha key z*/
    L_Z			("z", 0x7a);
	
    /** Key of WebDriver*/
	private final CharSequence keyWebDriver;
	/** Key of Selenium*/
	private final int keySelenium;
	
	/**
	 * Construction
	 * @param keyWebDriver CharSequence
	 * @param keySelenium int
	 */
	private TSFKeys(CharSequence keyWebDriver, int keySelenium) {
		this.keyWebDriver = keyWebDriver;
		this.keySelenium = keySelenium;
	}

	/**
	 * Get the Key when using WebDriver
	 * @return keyWebDriver
	 */
	public CharSequence getKeyWebDriver() {
		return keyWebDriver;
	}

	/**
     * Get the Key when using selenium
     * @return keySelenium
     */
	public int getKeySelenium() {
		return keySelenium;
	}
}