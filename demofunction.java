import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.xml.*;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.MimeHeaders;
//import javax.xml.soap.Node;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.junit.Assert;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

public class demofunction extends demo {



	public static void sendreq(String Temperature, String FromUnit, String ToUnit) throws Exception {
		soapEnvelope.addNamespaceDeclaration("web",
				"http://www.webserviceX.NET/");

		// change header's attribute
		MimeHeaders mimeHeader = soapMessage.getMimeHeaders();
		mimeHeader.setHeader("SOAPAction",                          
				"http://www.webserviceX.NET/ConvertTemp");

		SOAPBody soapBody = soapEnvelope.getBody();
		QName bodyName = new QName("http://www.webserviceX.NET/",
				"ConvertTemp", "web");
		SOAPBodyElement bodyElement =
				soapBody.addBodyElement(bodyName);

		QName n = new QName("http://www.webserviceX.NET/",
				"Temperature","web");
		QName n1 = new QName("http://www.webserviceX.NET/",
				"FromUnit","web");
		QName n2 = new QName("http://www.webserviceX.NET/",
				"ToUnit", "web");

		bodyElement.addChildElement(n).addTextNode(Temperature);
		bodyElement.addChildElement(n1).addTextNode(FromUnit);
		bodyElement.addChildElement(n2).addTextNode(ToUnit);

		// Save message
		soapMessage.saveChanges();

		// View input
		System.out.println("Soap request:\n");
		soapMessage.writeTo(System.out);

		// Sending request
		String endUrl =
				"http://www.webservicex.net/ConvertTemperature.asmx ";
		soapResponseMessage = soapConnection.
				call(soapMessage, endUrl);
	}



	public static void getResponse() throws Exception {
		// View the output
		System.out.println("XML response\n");

		// Create transformer
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer = transformerFactory.newTransformer();

		// Get reply content
		Source source = soapResponseMessage.getSOAPPart().getContent();
		StringWriter writer = new StringWriter();
		StreamResult sResult = new StreamResult(writer);
		transformer.transform(source, sResult);
		String result1 = writer.toString();
		System.out.println(result1);
		getFullNameFromXml(result1, "ConvertTempResult");
		
		soapConnection.close();
	}

	public static Document loadXMLString(String source) throws Exception
	{
		DocumentBuilderFactory dbf =DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(source));

		return db.parse(is);
	}
	public static List<String> getFullNameFromXml(String response, String tagName) throws Exception {
		Document xmlDoc = loadXMLString(response);
		NodeList nodeList = xmlDoc.getElementsByTagName(tagName);
		List<String> ids = new ArrayList<String>(nodeList.getLength());
		for(int i=0;i<nodeList.getLength(); i++) {
			Node x = nodeList.item(i);
			ids.add(x.getFirstChild().getNodeValue());             
			System.out.println(tagName+" "+nodeList.item(i).getFirstChild().getNodeValue());
		}
		return ids;
	}

}


