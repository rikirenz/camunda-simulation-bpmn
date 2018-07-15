package util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import bpsimWrappers.ControlParametersWrapper;
import bpsimWrappers.CostParametersWrapper;
import bpsimWrappers.ParametersWrapper;


public class Util {

	protected final static Logger LOGGER = Logger.getLogger("ENSO-APP");
	private static Random random = new Random();

	public static ParametersWrapper retriveParamaterType(String id, Class<?> clsType) {
		// Search all the parameters for a given element
		try {
			ArrayList<Object> bpsimObjects = BpsimCollection.taskObjects.get(id);
			for (Object currObject : bpsimObjects) {
				if (clsType.isInstance(currObject)) {
					return (ParametersWrapper) currObject;					
				}
			}
			throw new Exception();
		} catch (Exception ex) {		
			LOGGER.info("There are no Bpsim objects defined for this element.");
			return null;					
		}
	}

	public static boolean booleanValueFlow(String elementId) {
		try {
			ControlParametersWrapper controlParameters = (ControlParametersWrapper) retriveParamaterType(elementId, ControlParametersWrapper.class);			 
			// if there is no object exception with distribution or boolean 
			if (controlParameters == null ) throw new Exception("The Parameters for the out flow:" + elementId + " are not well defined.");
			if (controlParameters.getProbability() != null) return random.nextBoolean();
			throw new Exception("The Parameters for the out-flow element:" + elementId + " are not well defined.");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return true;
		}
	}
	
	public static BpmnModelInstance loadBpmnProcess(Document bpmnDocument) {
		try {
	        // @todo refactor
	        TransformerFactory tf = TransformerFactory.newInstance();
	        Transformer transformer = tf.newTransformer();
	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();              
	        Result outputTarget = new StreamResult(outputStream);
	        transformer.transform(new DOMSource(bpmnDocument),  outputTarget);
	        InputStream is = new ByteArrayInputStream(outputStream.toByteArray());
			
			return Bpmn.readModelFromStream(is);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public static String convertDocumnetToString(Document doc) {
		try {
			java.io.StringWriter sw = new java.io.StringWriter();
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer;
			transformer = tf.newTransformer();
	        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
	        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");	
	        transformer.transform(new DOMSource(doc), new StreamResult(sw));
			
	        return sw.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}		
	}
	
	
	public static void writeStringToFile(String content, String filename) {
		try {
			PrintWriter out = new PrintWriter(filename);
			out.println(content);
		    out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
    public static String convertNodeToString(Node node) {
        if (node == null) {
            throw new IllegalArgumentException("node is null.");
        }

        try {
            // Remove unwanted whitespaces
            node.normalize();
            XPath xpath = XPathFactory.newInstance().newXPath();
            XPathExpression expr = xpath.compile("//text()[normalize-space()='']");
            NodeList nodeList = (NodeList) expr.evaluate(node, XPathConstants.NODESET);

            for (int i = 0; i < nodeList.getLength(); ++i) {
                Node nd = nodeList.item(i);
                nd.getParentNode().removeChild(nd);
            }

            // Create and setup transformer
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
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


