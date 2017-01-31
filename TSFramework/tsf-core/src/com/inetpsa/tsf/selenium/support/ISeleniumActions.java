package com.inetpsa.tsf.selenium.support;

import java.util.List;

import com.inetpsa.tsf.selenium.TSFKeys;

/**
 * ISeleniumActions
 * @author e365712
 */
public interface ISeleniumActions {
	
	/**
	 * disable log
	 */
	void disableLog();
	
	/**
	 * Clicks on a link, button, checkbox or radio button. If the click action
	 * causes a new page to load (like a link usually does), call
	 * waitForPageToLoad.
	 * 
	 * @param locator
	 *            an element locator
	 */
	void click(String locator);

	/**
	 * Move the focus to the specified element; for example, if the element is
	 * an input field, move the cursor to that field.
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a>
	 */
	void focus(String locator);

	/**
	 * Simulates a user pressing and releasing a key.
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a>
	 * @param keySequence
	 *            Either be a string(
	 *            "\" followed by the numeric keycode  of the key to be pressed, normally the ASCII value of that key), or a single  character. For example: "
	 *            w", "\119".
	 *            
	 * @deprecated use only for selenium1.X
	 */
	@Deprecated
	void keyPress(String locator, String keySequence);
	
	/**
	 * Simulates a user pressing and releasing a key.
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a>
	 * @param key
	 *            For example: "w", "TSFKeys.W".
	 */
	void keyPress(String locator, TSFKeys key);

	/**
	 * Press the shift key and hold it down until doShiftUp() is called or a new
	 * page is loaded.
	 * 
	 * @deprecated use only for selenium1.X
	 */
	@Deprecated
	void shiftKeyDown();

	/** Release the shift key. 
	 * 
	 * @deprecated use only for selenium1.X
	 */
	@Deprecated
	void shiftKeyUp();

	/**
	 * Press the alt key and hold it down until doAltUp() is called or a new
	 * page is loaded.
	 * 
	 * @deprecated use only for selenium1.X
	 */
	@Deprecated
	void altKeyDown();

	/** Release the alt key. 
	 * 
	 * @deprecated use only for selenium1.X
	 */
	@Deprecated
	void altKeyUp();

	/**
	 * Press the control key and hold it down until doControlUp() is called or a
	 * new page is loaded.
	 * @deprecated use only for selenium1.X
	 */
	@Deprecated
	void controlKeyDown();

	/** Release the control key.
	 * 
	 *  @deprecated use only for selenium1.X
	 */
	@Deprecated
	void controlKeyUp();

	/**
	 * Simulates a user pressing a key (without releasing it yet).
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a>
	 * @param keySequence
	 *            Either be a string(
	 *            "\" followed by the numeric keycode  of the key to be pressed, normally the ASCII value of that key), or a single  character. For example: "
	 *            w", "\119".
	 *            
	 * @deprecated use only for selenium1.X
	 */
	@Deprecated
	void keyDown(String locator, String keySequence);
	
	/**
	 * Simulates a user pressing a key (without releasing it yet).
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a>
	 * @param key
	 *            For example: "w", "TSFKeys.W".
	 * @deprecated use only for selenium1.X
	 */
	@Deprecated    
	void keyDown(String locator, TSFKeys key);

	/**
	 * Simulates a user releasing a key.
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a>
	 * @param keySequence
	 *            Either be a string(
	 *            "\" followed by the numeric keycode  of the key to be pressed, normally the ASCII value of that key), or a single  character. For example: "
	 *            w", "\119".
	 *            
	 * @deprecated use only for selenium1.X
	 */
	@Deprecated	
	void keyUp(String locator, String keySequence);
	
	/**
	 * Simulates a user releasing a key.
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a>
	 * @param key
	 *            For example: "w", "TSFKeys.W".
	 * @deprecated use only for selenium1.X
	 */
	@Deprecated    
	void keyUp(String locator, TSFKeys key);

	/**
	 * Simulates a user hovering a mouse over the specified element.
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a>
	 */
	void mouseOver(String locator);
	
	/**
	 * Simulates a user hovering a mouse out the specified element.
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a>
	 */
	void mouseOut(String locator);
	
	/**
	 * Simulates a user pressing the left mouse button (without releasing it
	 * yet) on the specified element.
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a>
	 */
	void mouseDown(String locator);

	/**
	 * Sets the value of an input field, as though you typed it in.
	 * 
	 * <p>
	 * Can also be used to set the value of combo boxes, check boxes, etc. In
	 * these cases, value should be the value of the option selected, not the
	 * visible text.
	 * </p>
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a>
	 * @param value
	 *            the value to type
	 */
	void type(String locator, String value);

	/**
	 * Simulates keystroke events on the specified element, as though you typed
	 * the value key-by-key.
	 * 
	 * <p>
	 * This is a convenience method for calling keyDown, keyUp, keyPress for
	 * every character in the specified string; this is useful for dynamic UI
	 * widgets (like auto-completing combo boxes) that require explicit key
	 * events.
	 * </p>
	 * <p>
	 * Unlike the simple "type" command, which forces the specified value into
	 * the page directly, this command may or may not have any visible effect,
	 * even in cases where typing keys would normally have a visible effect. For
	 * example, if you use "typeKeys" on a form element, you may or may not see
	 * the results of what you typed in the field.
	 * </p>
	 * <p>
	 * In some cases, you may need to use the simple "type" command to set the
	 * value of the field and then the "typeKeys" command to send the keystroke
	 * events corresponding to what you just typed.
	 * </p>
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a>
	 * @param value
	 *            the value to type
	 */
	void typeKeys(String locator, String value);

	/**
	 * Check a toggle-button (checkbox/radio)
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a>
	 */
	void check(String locator);

	/**
	 * Uncheck a toggle-button (checkbox/radio)
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a>
	 */
	void uncheck(String locator);

	/**
	 * Select an option from a drop-down using an option locator.
	 * 
	 * <p>
	 * Option locators provide different ways of specifying options of an HTML
	 * Select element (e.g. for selecting a specific option, or for asserting
	 * that the selected option satisfies a specification). There are several
	 * forms of Select Option Locator.
	 * </p>
	 * <ul>
	 * <li><strong>label</strong>=<em>labelPattern</em>: matches options based
	 * on their labels, i.e. the visible text. (This is the default.)
	 * <ul * class="first last simple">
	 * <li>label=regexp:^[Oo]ther</li>
	 * </ul>
	 * </li>
	 * <li><strong>value</strong>=<em>valuePattern</em>: matches options based
	 * on their values.
	 * <ul class="first last simple">
	 * <li>value=other</li>
	 * </ul>
	 * </li>
	 * <li><strong>id</strong>=<em>id</em>:
	 * 
	 * matches options based on their ids.
	 * <ul class="first last simple">
	 * <li>
	 * id=option1</li>
	 * </ul>
	 * </li>
	 * <li><strong>index</strong>=<em>index</em>: matches an option based on its
	 * index (offset from zero).
	 * <ul * class="first last simple">
	 * <li>index=2</li>
	 * </ul>
	 * </li>
	 * </ul>
	 * <p>
	 * If no option locator prefix is provided, the default behaviour is to
	 * match on <strong>label</strong>.
	 * </p>
	 * 
	 * @param selectLocator
	 *            an <a href="#locators">element locator</a> identifying a
	 *            drop-down menu
	 * @param optionLocator
	 *            an option locator (a label by default)
	 */
	void select(String selectLocator, String optionLocator);

	/**
	 * Opens an URL in the test frame. This accepts both relative and absolute
	 * URLs.
	 * 
	 * The "open" command waits for the page to load before proceeding, ie. the
	 * "AndWait" suffix is implicit.
	 * 
	 * <em>Note</em>: The URL must be on the same domain as the runner HTML due
	 * to security restrictions in the browser (Same Origin Policy). If you need
	 * to open an URL on another domain, use the Selenium Server to start a new
	 * browser session on that domain.
	 * 
	 * @param url
	 *            the URL to open; may be relative or absolute
	 */
	void open(String url);

	/**
	 * Selects a popup window using a window locator; once a popup window has
	 * been selected, all commands go to that window. To select the main window
	 * again, use null as the target.
	 * 
	 * <p>
	 * 
	 * Window locators provide different ways of specifying the window String:
	 * by title, by internal JavaScript "name," or by JavaScript variable.
	 * </p>
	 * <ul>
	 * <li><strong>title</strong>=<em>My Special Window</em>: Finds the window
	 * using the text that appears in the title bar. Be careful; two windows can
	 * share the same title. If that happens, this locator will just pick one.</li>
	 * <li><strong>name</strong>=<em>myWindow</em>: Finds the window using its
	 * internal JavaScript "name" property. This is the second parameter
	 * "windowName" passed to the JavaScript method window.open(url, windowName,
	 * windowFeatures, replaceFlag) (which Selenium intercepts).</li>
	 * <li><strong>var</strong>=<em>variableName</em>: Some pop-up windows are
	 * unnamed (anonymous), but are associated with a JavaScript variable name
	 * in the current application window, e.g. "window.foo = window.open(url);".
	 * In those cases, you can open the window using "var=foo".</li>
	 * </ul>
	 * <p>
	 * If no window locator prefix is provided, we'll try to guess what you mean
	 * like this:
	 * </p>
	 * <p>
	 * 1.) if windowID is null, (or the string "null") then it is assumed the
	 * user is referring to the original window instantiated by the browser).
	 * </p>
	 * <p>
	 * 2.) if the value of the "windowID" parameter is a JavaScript variable
	 * name in the current application window, then it is assumed that this
	 * variable contains the return value from a call to the JavaScript
	 * window.open() method.
	 * </p>
	 * <p>
	 * 3.) Otherwise, selenium looks in a hash it maintains that maps string
	 * names to window "names".
	 * </p>
	 * <p>
	 * 4.) If <em>that</em> fails, we'll try looping over all of the known
	 * windows to try to find the appropriate "title". Since "title" is not
	 * necessarily unique, this may have unexpected behavior.
	 * </p>
	 * <p>
	 * If you're having trouble figuring out the name of a window that you want
	 * to manipulate, look at the Selenium log messages which identify the names
	 * of windows created via window.open (and therefore intercepted by
	 * Selenium). You will see messages like the following for each window as it
	 * is opened:
	 * </p>
	 * <p>
	 * <code>debug: window.open call intercepted; window ID (which you can use with selectWindow()) is "myNewWindow"</code>
	 * </p>
	 * <p>
	 * In some cases, Selenium will be unable to intercept a call to window.open
	 * (if the call occurs during or before the "onLoad" event, for example).
	 * (This is bug SEL-339.) In those cases, you can force Selenium to notice
	 * the open window's name by using the Selenium openWindow command, using an
	 * empty (blank) url, like this: openWindow("", "myFunnyWindow").
	 * </p>
	 * 
	 * @param windowID
	 *            the JavaScript window ID of the window to select
	 */
	void selectWindow(String windowID);

	/**
	 * Selects a frame within the current window. (You may invoke this command
	 * multiple times to select nested frames.) To select the parent frame, use
	 * "relative=parent" as a locator; to select the top frame, use
	 * "relative=top". You can also select a frame by its 0-based index number;
	 * select the first frame with "index=0", or the third frame with "index=2".
	 * 
	 * <p>
	 * You may also use a DOM expression to identify the frame you want
	 * directly, like this: <code>dom=frames["main"].frames["subframe"]</code>
	 * </p>
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a> identifying a frame
	 *            or iframe
	 */
	void selectFrame(String locator);

	/** Simulates the user clicking the "back" button on their browser. */
	void goBack();

	/**
	 * Gets the entire text of the page.
	 * 
	 * @return the entire text of the page
	 */
	String getBodyText();

	/**
	 * Gets the (whitespace-trimmed) value of an input field (or anything else
	 * with a value parameter). For checkbox/radio elements, the value will be
	 * "on" or "off" depending on whether the element is checked or not.
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a>
	 * @return the element value, or "on/off" for checkbox/radio elements
	 */
	String getValue(String locator);

	/**
	 * Gets the text of an element. This works for any element that contains
	 * text. This command uses either the textContent (Mozilla-like browsers) or
	 * the innerText (IE-like browsers) of the element, which is the rendered
	 * text shown to the user.
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a>
	 * @return the text of the element
	 */
	String getText(String locator);

	/**
	 * Briefly changes the backgroundColor of the specified element yellow.
	 * Useful for debugging.
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a>
	 */
	void highlight(String locator);

	/**
	 * Gets the result of evaluating the specified JavaScript snippet. The
	 * snippet may have multiple lines, but only the result of the last line
	 * will be returned.
	 * 
	 * <p>
	 * Note that, by default, the snippet will run in the context of the
	 * "selenium" String itself, so <code>this</code> will refer to the Selenium
	 * String. Use <code>window</code> to refer to the window of your
	 * application, e.g. <code>window.document.getElementById('foo')</code>
	 * </p>
	 * <p>
	 * If you need to use a locator to refer to a single element in your
	 * application page, you can use
	 * <code>this.browserbot.findElement("id=foo")</code> where "id=foo" is your
	 * locator.
	 * </p>
	 * 
	 * @param script
	 *            the JavaScript snippet to run
	 * @return the results of evaluating the snippet
	 */
	Object getEval(String script);

	/**
	 * Gets whether a toggle-button (checkbox/radio) is checked. Fails if the
	 * specified element doesn't exist or isn't a toggle-button.
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a> pointing to a
	 *            checkbox or radio button
	 * @return true if the checkbox is checked, false otherwise
	 */
	boolean isChecked(String locator);

	/**
	 * Gets the text from a cell of a table. The cellAddress syntax
	 * tableLocator.row.column, where row and column start at 0.
	 * 
	 * @param tableCellAddress
	 *            a cell address, e.g. "foo.1.4"
	 * @return the text from the specified cell
	 */
	String getTable(String tableCellAddress);

	/**
	 * Gets the value of an element attribute. The value of the attribute may
	 * differ across browsers (this is the case for the "style" attribute, for
	 * example).
	 * 
	 * @param locator
	 *            an element locator followed by an @ sign and then the name of
	 *            the attribute, e.g. "foo@bar"
	 * @return the value of the specified attribute
	 */
	String getAttribute(String locator);

	/**
	 * Gets the value of an element attribute. The value of the attribute may
	 * differ across browsers (this is the case for the "style" attribute, for
	 * example).
	 * 
	 * @param locator
	 *            an element locator followed by an @ sign and then the name of
	 *            the attribute, e.g. "foo@bar"
	 * @param attributeLocator
	 *            an element locator followed by an @ sign and then the name of
	 *            the attribute, e.g. "foo@bar"
	 * @return the value of the specified attribute
	 */
	String getAttribute(String locator, String attributeLocator);

	/**
	 * Verifies that the specified text pattern appears somewhere on the
	 * rendered page shown to the user.
	 * 
	 * @param pattern
	 *            a <a href="#patterns">pattern</a> to match with the text of
	 *            the page
	 * @return true if the pattern matches the text, false otherwise
	 */
	boolean isTextPresent(String pattern);

	/**
	 * Verifies that the specified element is somewhere on the page.
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a>
	 * @return true if the element is present, false otherwise
	 */
	boolean isElementPresent(String locator);
	
	/**
	 * Verifies that the specified element is somewhere on the page.
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a>
	 * @param hasLog
	 *            if the selenium use log or not       
	 * @return true if the element is present, false otherwise
	 */
	boolean isElementPresent(String locator, boolean hasLog);

	/**
	 * Determines if the specified element is visible. An element can be
	 * rendered invisible by setting the CSS "visibility" property to "hidden",
	 * or the "display" property to "none", either for the element itself or one
	 * if its ancestors. This method will fail if the element is not present.
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a>
	 * @return true if the specified element is visible, false otherwise
	 */
	boolean isVisible(String locator);

	/**
	 * Determines whether the specified input element is editable, ie hasn't
	 * been disabled. This method will fail if the specified element isn't an
	 * input element.
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a>
	 * @return true if the input element is editable, false otherwise
	 */
	boolean isEditable(String locator);

	/**
	 * Returns the entire HTML source between the opening and closing "html"
	 * tags.
	 * 
	 * @return the entire HTML source
	 */
	String getHtmlSource();

	/**
	 * Retrieves the horizontal position of an element
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a> pointing to an
	 *            element OR an element itself
	 * @return of pixels from the edge of the frame.
	 */
	Number getElementPositionLeft(String locator);

	/**
	 * Retrieves the vertical position of an element
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a> pointing to an
	 *            element OR an element itself
	 * @return of pixels from the edge of the frame.
	 */
	Number getElementPositionTop(String locator);

	/**
	 * Retrieves the width of an element
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a> pointing to an
	 *            element
	 * @return width of an element in pixels
	 */
	Number getElementWidth(String locator);

	/**
	 * Retrieves the height of an element
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a> pointing to an
	 *            element
	 * @return height of an element in pixels
	 */
	Number getElementHeight(String locator);

	/**
	 * Returns the number of nodes that match the specified xpath, eg. "//table"
	 * would give the number of tables.
	 * 
	 * @param xpath
	 *            the xpath expression to evaluate. do NOT wrap this expression
	 *            in a 'count()' function; we will do that for you.
	 * @return the number of nodes that match the specified xpath
	 */
	Number getXpathCount(String xpath);

	/**
	 * Waits for a new page to load.
	 * 
	 * <p>
	 * You can use this command instead of the "AndWait" suffixes,
	 * "clickAndWait", "selectAndWait", "typeAndWait" etc. (which are only
	 * available in the JS API).
	 * </p>
	 * <p>
	 * Selenium constantly keeps track of new pages loading, and sets a
	 * "newPageLoaded" flag when it first notices a page load. Running any other
	 * Selenium command after turns the flag to false. Hence, if you want to
	 * wait for a page to load, you must wait immediately after a Selenium
	 * command that caused a page-load.
	 * </p>
	 * 
	 * @param timeout
	 *            a timeout in milliseconds, after which this command will
	 *            return with an error
	 */
	void waitForPageToLoad(String timeout);
	
	/**
	 * Saves the entire contents of the current window canvas to a PNG file.
	 * Contrast this with the captureScreenshot command, which captures the
	 * contents of the OS viewport (i.e. whatever is currently being displayed
	 * on the monitor), and is implemented in the RC only. Currently this only
	 * works in Firefox when running in chrome mode, and in IE non-HTA using the
	 * EXPERIMENTAL "Snapsie" utility. The Firefox implementation is mostly
	 * borrowed from the Screengrab! Firefox extension. Please see
	 * http://www.screengrab.org and http://snapsie.sourceforge.net/ for
	 * details.
	 * 
	 * @param filename
	 *            the path to the file to persist the screenshot as. No filename
	 *            extension will be appended by default. Directories will not be
	 *            created if they do not exist, and an exception will be thrown,
	 *            possibly by native code.
	 * @param kwargs
	 *            a kwargs string that modifies the way the screenshot is
	 *            captured. Example: "background=#CCFFDD" . Currently valid
	 *            options:
	 *            <dl>
	 *            <dt>background</dt>
	 *            <dd>the background CSS for the HTML document. This may be
	 *            useful to set for capturing screenshots of less-than-ideal
	 *            layouts, for example where absolute positioning causes the
	 *            calculation of the canvas dimension to fail and a black
	 *            background is exposed (possibly obscuring black text).</dd>
	 *            </dl>
	 */
	void captureEntirePageScreenshot(String filename,
			String kwargs);

	/**
	 * Captures a PNG screenshot to the specified file.
	 * 
	 * @param filename
	 *            the absolute path to the file to be written, e.g.
	 *            "c:\blah\screenshot.png"
	 */
	void captureScreenshot(String filename);

	/**
	 * Simulates a user pressing and releasing a key by sending a native
	 * operating system keystroke. This function uses the java.awt.Robot class
	 * to send a keystroke; this more accurately simulates typing a key on the
	 * keyboard. It does not honor settings from the shiftKeyDown,
	 * controlKeyDown, altKeyDown and metaKeyDown commands, and does not target
	 * any particular HTML element. To send a keystroke to a particular element,
	 * focus on the element first before running this command.
	 * 
	 * @param keycode
	 *            an integer keycode number corresponding to a
	 *            java.awt.event.KeyEvent; note that Java keycodes are NOT the
	 *            same thing as JavaScript keycodes!
	 *            
	 * @deprecated use only for selenium1.X
	 */
	@Deprecated
	void keyPressNative(String keycode);
	
	/**
	 * Simulates a user pressing and releasing a key by sending a native
	 * operating system keystroke. 
	 * 
	 * @param key
	 *            For example: "w", "TSFKeys.W".
	 */
	void keyPressNative(TSFKeys key);
	
	/**
	 * Simulates a user pressing a key (without releasing it yet) by sending a
	 * native operating system keystroke. This function uses the java.awt.Robot
	 * class to send a keystroke; this more accurately simulates typing a key on
	 * the keyboard. It does not honor settings from the shiftKeyDown,
	 * controlKeyDown, altKeyDown and metaKeyDown commands, and does not target
	 * any particular HTML element. To send a keystroke to a particular element,
	 * focus on the element first before running this command.
	 * 
	 * @param keycode
	 *            an integer keycode number corresponding to a
	 *            java.awt.event.KeyEvent; note that Java keycodes are NOT the
	 *            same thing as JavaScript keycodes!
	 *            
	 * @deprecated use only for selenium1.X
	 */
	@Deprecated
	void keyDownNative(String keycode);
	
	/**
	 * Simulates a user pressing a key (without releasing it yet) by sending a native
	 * operating system keystroke. 
	 * 
	 * @param key
	 *            For example: "w", "TSFKeys.W".
	 */
	void keyDownNative(TSFKeys key);

	/**
	 * Simulates a user releasing a key by sending a native operating system
	 * keystroke. This function uses the java.awt.Robot class to send a
	 * keystroke; this more accurately simulates typing a key on the keyboard.
	 * It does not honor settings from the shiftKeyDown, controlKeyDown,
	 * altKeyDown and metaKeyDown commands, and does not target any particular
	 * HTML element. To send a keystroke to a particular element, focus on the
	 * element first before running this command.
	 * 
	 * @param keycode
	 *            an integer keycode number corresponding to a
	 *            java.awt.event.KeyEvent; note that Java keycodes are NOT the
	 *            same thing as JavaScript keycodes!
	 *            
	 * @deprecated use only for selenium1.X
	 */
	@Deprecated
	void keyUpNative(String keycode);
	
	/**
	 * Simulates a user releasing a key by sending a native
	 * operating system keystroke. 
	 * 
	 * @param key
	 *            For example: "w", "TSFKeys.W".
	 */
	void keyUpNative(TSFKeys key);
	
	/**
	 * Gets the title of the current page.
	 * 
	 * @return the title of the current page
	 */
	String getTitle();
	
	/**
	 * Select the default frame
	 */
	void selectDefaultFrame();
	
	/**
	 * Get alert
	 * 
	 * @return the content of alert
	 */
	String getAlert();
	
	/**
	 * Wait the specified condition occur on the page.(webdriver can't used the same script)
	 * @param script String
	 * @param timeout String
	 */
	void waitForCondition(String script, String timeout);
	
	/**
	 * Get all windowIds
	 * @return list of window's ids;
	 */
	List<String> getAllWindowIds();
	
	/**
     * Get all windowTitless
     * @return list of window's titles;
     */
    List<String> getAllWindowTitles();
	
	/**
	 * Combination key
	 * @param controlKey include ctrl, shift, alt key (Now only shift key OK)
	 * @param normalkey include a-z 0-9
	 */
	void combinationKey(TSFKeys controlKey, TSFKeys normalkey);
	
	/**
     * Combination key
     * @param controlKeyA include ctrl, shift, alt key (Now only shift key OK)
     * @param controlKeyB include ctrl, shift, alt key (Now only shift key OK)
     * @param normalkey include a-z 0-9
     */
    void combinationKey(TSFKeys controlKeyA, TSFKeys controlKeyB, TSFKeys normalkey);
    
    /**
     * Use Js
     * @param script js script
     * @param element webelement
     * @return Object
     */
    Object getEval(String script, Object element);
}
