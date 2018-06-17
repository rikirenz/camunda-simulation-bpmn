package util;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class BpmnPreprocesser {

	private final static Logger LOGGER = Logger.getLogger("ENSO-APP");
	private Document bpmnDocument;
    private XPathFactory xPathfactory;
    private XPath xpath;
    
	/**
	 * Constructor BpmnPreprocesser
	 *
	 * @param bpmnDocumentInput Bpmb input document 
	 */
	public BpmnPreprocesser(Document bpmnDocumentInput) {
	    this.xPathfactory = XPathFactory.newInstance();
	    this.bpmnDocument = bpmnDocumentInput;
	    this.xpath = xPathfactory.newXPath();
	}
	
	/**
	 * Get the processed bpmn 
	 *
	 * @return bpmnDocument Bpmn processed document 
	 */
	public Document getProcessedBpmn () {
		// process the document
		convertTaskToUserTask();
		convertBoundaryEventsToMessageBoundaryEvents();
		convertConditionExpressionWithCustomCode();

		return bpmnDocument;
	}

		
	
	/**
	 * Convert all the task elements in the bpmnDocument to user tasks
	 */
	private void convertTaskToUserTask() {
		try {
			XPathExpression expr = xpath.compile("//*[local-name()='task']");
	        NodeList nodeTaskslist = (NodeList) expr.evaluate(this.bpmnDocument, XPathConstants.NODESET);
	        // check if there is a node inside with a message in case put the message name in the list
	        for (int i = 0; i < nodeTaskslist.getLength(); i++) {
	      	  this.bpmnDocument.renameNode((Element) nodeTaskslist.item(i), "http://www.omg.org/spec/BPMN/20100524/MODEL", "bpmn:userTask");
	        }			
		} catch (Exception ex) {
			
		}
	}
	

	/**
	 * Convert all the boundary events elements in the bpmnDocument to message boundary events
	 */
	private void convertBoundaryEventsToMessageBoundaryEvents() {
		try {
            // store all the task ids
            Set<String> taskIds = new HashSet<String>();
            XPathExpression expr = xpath.compile("//*[local-name()='userTask']");
            NodeList nodeTaskslist = (NodeList) expr.evaluate(this.bpmnDocument, XPathConstants.NODESET);
            for (int i = 0; i < nodeTaskslist.getLength(); i++) {
          	  taskIds.add(((Element) nodeTaskslist.item(i)).getAttribute("id"));
            }

            // extract all the boundaries events
            expr = xpath.compile("//*[local-name()='boundaryEvent']");
            NodeList nodeBoundaryEventsList = (NodeList) expr.evaluate(this.bpmnDocument, XPathConstants.NODESET);

            // check if there is a node inside with a message in case put the message name in the list
            for (int i = 0; i < nodeBoundaryEventsList.getLength(); i++) {

              // Is the boundary element attached to a task? 
          	  Element currElement = (Element) nodeBoundaryEventsList.item(i);
          	  if (!taskIds.contains(currElement.getAttribute("attachedToRef"))) continue; 

          	  // create a message node
  	          // <bpmn:message id="Message_123" name="Message_123" />             	  
          	  Element nd = this.bpmnDocument.createElementNS("http://www.omg.org/spec/BPMN/20100524/MODEL", "bpmn:message");
          	  String messageId = "messageBoundaryEvent" + currElement.getAttribute("attachedToRef");
          	  nd.setAttribute("id", messageId);
          	  nd.setAttribute("name", messageId);
          	  this.bpmnDocument.getFirstChild().insertBefore(nd, this.bpmnDocument.getFirstChild().getFirstChild());

          	  // convert the event to a message event
          	  // remove all elements except outgoings
          	  NodeList childNodes = currElement.getChildNodes();
          	  for (int j = childNodes.getLength()-1; j >= 0 ; j--) {
          		  if (!childNodes.item(j).getNodeName().equals("bpmn:outgoing")) {  
              		  currElement.removeChild(childNodes.item(j));            			  
          		  }
          	  }
             
          	  // add message
              Element messageEventDef = this.bpmnDocument.createElementNS("http://www.omg.org/spec/BPMN/20100524/MODEL", "bpmn:messageEventDefinition");
          	  messageEventDef.setAttribute("messageRef", messageId);
          	  currElement.appendChild(messageEventDef);       
            }		
		} catch (Exception ex) {
			
		}
	}

	
	/**
	 * Convert all the ConditionExpression elements in the bpmnDocument replacing them with custom code
	 */
	private void convertConditionExpressionWithCustomCode() {
		try {
	        XPathExpression expr = xpath.compile("//*[local-name()='conditionExpression']");
	        NodeList nl = (NodeList) expr.evaluate(this.bpmnDocument, XPathConstants.NODESET);
	        for (int i = 0; i < nl.getLength(); i++) {
	            ((Element) nl.item(i)).setNodeValue("<![CDATA[import util.Util\r\n\r\n" +
	                "Util.booleanValueFlow(\"" + ((Element) nl.item(i).getParentNode()).getAttribute("id") + "\");]]>"
	            );
	        }			
		} catch (Exception ex) {
			
		}
	}

}
