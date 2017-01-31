package com.inetpsa.tsf.selenium.support;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import junit.framework.AssertionFailedError;

import org.apache.commons.lang.StringUtils;
import org.junit.Rule;
import org.junit.rules.TestName;

import com.inetpsa.tsf.selenium.TSFConstant;
import com.inetpsa.tsf.selenium.TSFSeleniumConfig;
import com.thoughtworks.selenium.SeleneseTestBase;

/**
 * To deal with the data, such as annotation, properties file and so on
 * @author e353062
 */
public class TSFAbstractSupport extends SeleneseTestBase {

	/**
	 * name TestName
	 */
	@Rule 
	public TestName name = new TestName();
	
	/** Logger. */
	private static final Logger LOG = Logger.getLogger("TSF");
	
	/**
	 * Get the values defined in properties file to substitute for the defaule value of selenium
	 * @param isBatchMode :
	 * @throws IllegalAccessException exception
	 */
	protected void parametrizeTestByProperties(boolean isBatchMode) throws IllegalAccessException {
		InputStream in = null;
		String path = null;
		try {
			String filePath = System.getProperty("user.dir") + System.getProperty("property.path");
			in = new BufferedInputStream(new FileInputStream(filePath));
			Properties p = new Properties();
			p.load(in);
			if(!p.isEmpty()) {
				Set<Object> keys = p.keySet();
				String key = null;
				for (Object keyObj : keys) {
					key = keyObj.toString();
					setValueIntoField(key, p.getProperty(key), isBatchMode);
				}
			}
		} catch (FileNotFoundException e) {
			LOG.log(Level.WARNING, "Cannot find the file [" + path + TSFConstant.PROPERTIES_FILE_NAME + "]", e);
		} catch (IOException e) {
			final String msg = "Cannot load test data from resource "+ e.toString();
			LOG.log(Level.WARNING, msg, e);
			fail(msg);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				final String msg = "Cannot load test data from resource "+ e.toString();
				LOG.log(Level.WARNING, msg, e);
				fail(msg);
			}
		}
	}
	
	/**
	 * Check if annotation existed in testCase class or method, get the value of annotation to substitute for default value of selenium
	 * @param isBatchMode : 
	 * @throws IOException exception
	 */
	protected void parametrizeTestByAnnotation(boolean isBatchMode) throws IOException {
		Class<?> clazz = this.getClass();
		TSFSeleniumConfig annotation = null;
		//Deal with the annotation on class
		try {
    		if (clazz.isAnnotationPresent(TSFSeleniumConfig.class)) {
    			annotation = clazz.getAnnotation(TSFSeleniumConfig.class);
    			processDataByAnnotation(annotation,isBatchMode);
    		}
    		//Deal with the annotation on methods
    		Method[] methods = clazz.getDeclaredMethods();
    		String methodName = null;
    		for (Method method : methods) {
    			if (method.getModifiers() == Method.DECLARED) {
    				methodName = method.getName();
    				if (methodName.startsWith(name.getMethodName())) {
    					if (method.isAnnotationPresent(TSFSeleniumConfig.class)) {
    						annotation = method.getAnnotation(TSFSeleniumConfig.class);
    						processDataByAnnotation(annotation, isBatchMode);
    					}
    				}
    			}
    		}
		} catch (IllegalAccessException e) {
			final String msg = "Cannot process annotation " + e.toString();
			LOG.log(Level.WARNING, msg, e);
			fail(msg);
		} catch (InvocationTargetException e) {
			final String msg = "Cannot invocate the method from class '" + annotation.getClass().getSimpleName() + "'" + e.toString();
			LOG.log(Level.WARNING, msg, e);
			fail(msg);
		}
	}
	
	/**
	 * processDataByAnnotation
	 * @param annotation TSFSeleniumConfig
	 * @param isBatchMode boolean
	 * @throws InvocationTargetException exception
	 * @throws IllegalAccessException exception
	 * @throws IOException exception
	 */
	private void processDataByAnnotation(TSFSeleniumConfig annotation, boolean isBatchMode) throws IllegalAccessException, InvocationTargetException, IOException {
		if (annotation == null) {
			return;
		}
		Method[] methods = annotation.getClass().getDeclaredMethods();
		String methodName = null;
		Object valueObj = null;
		for (Method method : methods) {
			boolean isExisted = false;
			methodName = method.getName();
			for (String mtdName : TSFConstant.USELESS_ANNOTATION_METHODS) {
				if(methodName.equals(mtdName)) {
					isExisted = true;
					break;
				}
			}
			
			if(!isExisted) {
				valueObj = method.invoke(annotation);
				setValueIntoField(methodName, valueObj, isBatchMode);
			}
		}
	}
	
	/**
	 * Set the value into filed
	 * @param filedName :
	 * @param fieldValueObj :
	 * @param isBatchMode :
	 * @throws IllegalAccessException error
	 * @throws IOException error
	 */
	private void setValueIntoField(String filedName, Object fieldValueObj, boolean isBatchMode) throws IllegalAccessException, IOException {
		Field field = findPublicField(this.getClass(), filedName);
		if(field != null) {
			Object fieldValue = parseAttributeValue(filedName, fieldValueObj, field.getType());
			List<String> uselessMethods = new ArrayList<String>();
			if(isBatchMode){
				uselessMethods = Arrays.asList(TSFConstant.UNUSED_ATTRUBYTE);
			}
			if(fieldValue != null && StringUtils.isNotBlank(fieldValue.toString()) 
					&& !uselessMethods.contains(field.getName())) {
				field.set(this, fieldValueObj);
			}
		} else {
			throw new IOException("Cannot find the public field '" + filedName + "'");
		}
	}
	
	/**
     * Helper method to find a public field of matching type for
     * <code>fieldName</code>. If no field with that name is found, this
     * method returns <code>null</code>. If a field is found but does not
     * have a valid type, this method throws an <code>AssertionFailedError</code>
     * with appropriate error message.
     *
     * @param testClass the test class
     * @param fieldName the field name
     * @return the field
     */
    private Field findPublicField(Class testClass, String fieldName) {
        try {
            Field field = testClass.getField(fieldName);
            if (!isValidAttributeType(field.getType())) {
                fail("Cannot set value for attribute '" + fieldName + 
                     "': Public member field has incompatible type '" +
                     field.getType().getName() + "'");
            }
            return field;
        } catch (NoSuchFieldException e) {
			LOG.log(Level.WARNING, "no such field in file" + fieldName, e);
            return null;
        }
    }
    
    /**
     * Check if <code>type</code> is a type compatible with parametrization.
     *
     * @param type the type
     * @return true, if is valid attribute type
     */
    private boolean isValidAttributeType(Class type) {
        if (!type.equals(Void.TYPE)) {
        	return true;
        }
        
        if (Boolean.class.equals(type) || Character.class.equals(type)
            || Byte.class.equals(type) || Short.class.equals(type)
            || Integer.class.equals(type) || Long.class.equals(type)
            || Float.class.equals(type) || Double.class.equals(type)
            || String.class.equals(type)) {
        	return true;
        }
        
        // Check if type has a public constructor taking a string as an argument.
        final Class[] argTypes = { String.class };
        try {
            type.getConstructor(argTypes);
            return true;
        } catch (NoSuchMethodException e) {
        	LOG.log(Level.WARNING, "no such method when run isValidAttributeType", e);
            return false;
        }
    }
    
    /**
     * Helper method to parse a string into an object of the desired type.
     * For primitive types, this method returns the appropriate object wrapper.
     *
     * @param name the name
     * @param valueObj the value
     * @param type the type
     * @return the object
     */
  //CHECKSTYLE:OFF (Return count is 12)
    private Object parseAttributeValue(String name, Object valueObj, Class type) {
  //CHECKSTYLE:ON
        try {
            if (valueObj == null || StringUtils.isEmpty(valueObj.toString())) {
            	return valueObj;
            }
            String valueStr = valueObj.toString();
            
            if (Boolean.TYPE.equals(type) || Boolean.class.equals(type)) {
                return Boolean.valueOf(valueStr);
            } else if (Character.TYPE.equals(type) || Character.class.equals(type)) {
                if (valueStr.length() != 1) {
                    fail("Cannot set attribute '" + name + "' to value '" +
                         valueObj + "': Value too long.");
                }
                return new Character(valueStr.charAt(0));
            } else if (Byte.TYPE.equals(type) || Byte.class.equals(type)) {
                return Byte.valueOf(valueStr);
            } else if (Short.TYPE.equals(type) || Short.class.equals(type)) {
                return Short.valueOf(valueStr);
            } else if (Integer.TYPE.equals(type) || Integer.class.equals(type)) {
                return Integer.valueOf(valueStr);
            } else if (Long.TYPE.equals(type) || Long.class.equals(type)) {
                return Long.valueOf(valueStr);
            } else if (Float.TYPE.equals(type) || Float.class.equals(type)) {
                return Float.valueOf(valueStr);
            } else if (Double.TYPE.equals(type) || Double.class.equals(type)) {
                return Double.valueOf(valueStr);
            } else if (String.class.equals(type)) {
                return valueStr;
            } else {
                final Class[] argTypes = { String.class };
                Constructor<Object> c = type.getConstructor(argTypes);
                final Object[] arg = { valueObj };
                return c.newInstance(arg);
            }
        } catch (AssertionFailedError e) { 
            throw e;
        } catch (Exception e) {
        	final String msg = "Cannot set attribute '" + name + "' to value '" + valueObj + "': " + e.getMessage();
        	LOG.log(Level.WARNING, msg, e);
            fail(msg);
            return null;
        }
    }

}
