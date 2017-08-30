
 
import java.io.StringWriter;
import javax.xml.namespace.QName;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPBody;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.NodeList;
 
public class demo {
 
 // reference variables
  public static SOAPConnectionFactory soapConnectionFactory;
  public static SOAPConnection soapConnection ;
  public static MessageFactory messageFactory;
  public static SOAPMessage soapMessage;
  public static SOAPPart soapPart;
  public static SOAPEnvelope soapEnvelope;
  public static SOAPMessage soapResponseMessage;
  
  @Before
  public void setUp(){
         
     try {
     //object initialization
          soapConnectionFactory = SOAPConnectionFactory.newInstance();
          soapConnection = soapConnectionFactory.createConnection();
                 
          messageFactory = MessageFactory.newInstance();
          soapMessage = messageFactory.createMessage();
  
          soapPart = soapMessage.getSOAPPart();                
          soapEnvelope = soapPart.getEnvelope();
                 
     } catch (UnsupportedOperationException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
     } catch (SOAPException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
     }
         
  }
 
  @Test
  public void degreeTofahrenheit() {
 
     try {
      	 demofunction.sendreq("54","degreeCelsius","degreeFahrenheit");
    	 demofunction.getResponse();
 
     } catch (Exception e) {
          System.out.println(e.getMessage());
     }
  }
          
}