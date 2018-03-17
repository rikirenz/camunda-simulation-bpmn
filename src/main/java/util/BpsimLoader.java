package util;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Path;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import bpsim.BPSimData;

public class BpsimLoader {

	private BPSimData bpsimData;
	private Path xmlFilePath;
	
	public BpsimLoader(Path xmlFilePath) {
		this.xmlFilePath = xmlFilePath;
	}
	
	
	public BPSimData loadBpsimAnnotations() {
		try {
			JAXBContext jc = JAXBContext.newInstance(BPSimData.class);
	        Unmarshaller unmarshaller = jc.createUnmarshaller();
	        
	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder builder = factory.newDocumentBuilder();
	        Document doc = builder.parse(new File(this.xmlFilePath.toString()));
	        XPathFactory xPathfactory = XPathFactory.newInstance();
	        XPath xpath = xPathfactory.newXPath();
	        XPathExpression expr = xpath.compile("//*[local-name()='BPSimData']");	        
	        NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
	        if (nl.getLength() < 1) return null;
	        
	        ((Element) nl.item(0)).setAttribute("xmlns:bpsim","http://www.bpsim.org/schemas/2.0");
			String fileString = toString(nl.item(0));
			InputSource fileSource = new InputSource(new StringReader(fileString)); 
			return (BPSimData) unmarshaller.unmarshal(fileSource);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	private String toString(Node node) {
	    if (node == null) {
	        throw new IllegalArgumentException("node is null.");
	    }

	    try {
	        // Remove unwanted whitespaces
	        node.normalize();
	        XPath xpath = XPathFactory.newInstance().newXPath();
	        XPathExpression expr = xpath.compile("//text()[normalize-space()='']");
	        NodeList nodeList = (NodeList)expr.evaluate(node, XPathConstants.NODESET);

	        for (int i = 0; i < nodeList.getLength(); ++i) {
	            Node nd = nodeList.item(i);
	            nd.getParentNode().removeChild(nd);
	        }

	        // Create and setup transformer
	        Transformer transformer =  TransformerFactory.newInstance().newTransformer();
	        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	        StringWriter writer = new StringWriter();
	        transformer.transform(new DOMSource(node), new StreamResult(writer));
	        return writer.toString();
	    } catch (TransformerException e) {
	        throw new RuntimeException(e);
	    } catch (XPathExpressionException e) {
	        throw new RuntimeException(e);
	    }
	}

}
