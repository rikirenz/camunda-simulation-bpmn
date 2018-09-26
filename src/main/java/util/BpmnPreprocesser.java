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
		addListenerTointermediateEvents();
		addClassToUserTaskAlreadyPresent();
		convertTaskToUserTask();
		convertManualTaskToUserTask();
		convertBoundaryEventsToMessageBoundaryEvents();
		convertStartSubPorcessEventsToMessageStartEvents();
		convertConditionExpressionWithCustomCode();
		return bpmnDocument;
	}

		

	/**
	 * Clean all the user-task elements in order to make them compatible with the simulation
	 */
	private void addClassToUserTaskAlreadyPresent() {
		try {
			XPathExpression expr = xpath.compile("//*[local-name()='userTask']");
	        NodeList nodeTaskslist = (NodeList) expr.evaluate(this.bpmnDocument, XPathConstants.NODESET);
	        // check if there is a node inside with a message in case put the message name in the list
	        for (int i = 0; i < nodeTaskslist.getLength(); i++) {
	        	// add listener
	        	Element ndWrapper = this.bpmnDocument.createElementNS("http://www.omg.org/spec/BPMN/20100524/MODEL", "semantic:extensionElements");
				Element ndExecutionListeners = this.bpmnDocument.createElementNS("http://camunda.org/schema/1.0/bpmn", "camunda:executionListener");
				ndExecutionListeners.setAttribute("class", "executionlisteners.TaskListener");
				ndExecutionListeners.setAttribute("event", "start");
				ndExecutionListeners.removeAttribute("implementation");
				ndWrapper.appendChild(ndExecutionListeners);			
				nodeTaskslist.item(i).insertBefore(ndWrapper, nodeTaskslist.item(i).getFirstChild());		
	        }
		} catch (Exception ex) {
			ex.printStackTrace();
		}
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
	        	// add listener
	        	Element ndWrapper = this.bpmnDocument.createElementNS("http://www.omg.org/spec/BPMN/20100524/MODEL", "semantic:extensionElements");
				Element ndExecutionListeners = this.bpmnDocument.createElementNS("http://camunda.org/schema/1.0/bpmn", "camunda:executionListener");
				ndExecutionListeners.setAttribute("class", "executionlisteners.TaskListener");
				ndExecutionListeners.setAttribute("event", "start");
				ndWrapper.appendChild(ndExecutionListeners);			
				nodeTaskslist.item(i).insertBefore(ndWrapper, nodeTaskslist.item(i).getFirstChild());				
				// convert the node type
	        	this.bpmnDocument.renameNode((Element) nodeTaskslist.item(i), "http://www.omg.org/spec/BPMN/20100524/MODEL", "bpmn:userTask");
	        }
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Convert all the task elements in the bpmnDocument to user tasks
	 */
	private void convertManualTaskToUserTask() {
		try {
			XPathExpression expr = xpath.compile("//*[local-name()='manualTask']");
	        NodeList nodeTaskslist = (NodeList) expr.evaluate(this.bpmnDocument, XPathConstants.NODESET);
	        // check if there is a node inside with a message in case put the message name in the list
	        for (int i = 0; i < nodeTaskslist.getLength(); i++) {
	        	// add listener
	        	Element ndWrapper = this.bpmnDocument.createElementNS("http://www.omg.org/spec/BPMN/20100524/MODEL", "semantic:extensionElements");
				Element ndExecutionListeners = this.bpmnDocument.createElementNS("http://camunda.org/schema/1.0/bpmn", "camunda:executionListener");
				ndExecutionListeners.setAttribute("class", "executionlisteners.TaskListener");
				ndExecutionListeners.setAttribute("event", "start");
				ndWrapper.appendChild(ndExecutionListeners);			
				nodeTaskslist.item(i).insertBefore(ndWrapper, nodeTaskslist.item(i).getFirstChild());				
				// convert the node type
	        	this.bpmnDocument.renameNode((Element) nodeTaskslist.item(i), "http://www.omg.org/spec/BPMN/20100524/MODEL", "bpmn:userTask");
	        }
		} catch (Exception ex) {
      	  ex.printStackTrace();
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
			ex.printStackTrace();
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
	        	((Element) nl.item(i)).setAttribute("language", "groovy");
	        	nl.item(i).setTextContent("import util.Util\r\n\r\n" +
						"Util.booleanValueFlow(\"" + ((Element) nl.item(i).getParentNode()).getAttribute("id") + "\");"
	            );	            
	        }
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Add listener to intermediate catch and throw events
	 */	
	private void addListenerTointermediateEvents() {
		try {
			// add listeners to intermediateThrowEvent nodes
	        XPathExpression expr = xpath.compile("//*[local-name()='intermediateThrowEvent']");
	        NodeList nl = (NodeList) expr.evaluate(this.bpmnDocument, XPathConstants.NODESET);
	        for (int i = 0; i < nl.getLength(); i++) {	        
	        	Element ndWrapper = this.bpmnDocument.createElementNS("http://www.omg.org/spec/BPMN/20100524/MODEL", "semantic:extensionElements");
				Element ndExecutionListeners = this.bpmnDocument.createElementNS("http://camunda.org/schema/1.0/bpmn", "camunda:executionListener");
				ndExecutionListeners.setAttribute("class", "executionlisteners.EventListener");
				ndExecutionListeners.setAttribute("event", "start");
				ndWrapper.appendChild(ndExecutionListeners);			
				nl.item(i).insertBefore(ndWrapper, nl.item(i).getFirstChild());          
	        }

	        // add listeners to intermediateCatchEvent nodes
	        expr = xpath.compile("//*[local-name()='intermediateCatchEvent']");
	        nl = (NodeList) expr.evaluate(this.bpmnDocument, XPathConstants.NODESET);
	        for (int i = 0; i < nl.getLength(); i++) {	        
	        	Element ndWrapper = this.bpmnDocument.createElementNS("http://www.omg.org/spec/BPMN/20100524/MODEL", "semantic:extensionElements");
				Element ndExecutionListeners = this.bpmnDocument.createElementNS("http://camunda.org/schema/1.0/bpmn", "camunda:executionListener");
				ndExecutionListeners.setAttribute("class", "executionlisteners.EventListener");
				ndExecutionListeners.setAttribute("event", "start");
				ndWrapper.appendChild(ndExecutionListeners);			
				nl.item(i).insertBefore(ndWrapper, nl.item(i).getFirstChild());          
	        }	        
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	/**
	 * Convert all the start event in the sub process to start message events 
	 */	
	private void convertStartSubPorcessEventsToMessageStartEvents() {
		try {
			// get all the subprocess nodes
	        XPathExpression expr = xpath.compile("//*[local-name()='subProcess']//*[local-name()='subProcess']//*[local-name()='startEvent']");
	        NodeList starEventsNodeList = (NodeList) expr.evaluate(this.bpmnDocument, XPathConstants.NODESET);

        	for (int j = 0; j < starEventsNodeList.getLength(); j++) {
        		// verify if the start event is already a message event	        	
        		Element currElement = (Element) starEventsNodeList.item(j);
        		NodeList startEventChildNodes = currElement.getChildNodes();
        		boolean isAMessageEvent = false;
        		for (int k = 0; k < startEventChildNodes.getLength(); k++) {
        			
        			if (startEventChildNodes.item(k).getNodeName().equals("messageEventDefinition")) {
        				isAMessageEvent = true;
        				break;
        			}
        		}

        		if (isAMessageEvent) continue; 	        		

            	Element nd = this.bpmnDocument.createElementNS("http://www.omg.org/spec/BPMN/20100524/MODEL", "bpmn:message");
            	String messageId = "messageStartEvent" + currElement.getAttribute("id") + j;
            	nd.setAttribute("id", messageId);
            	nd.setAttribute("name", messageId);
            	this.bpmnDocument.getFirstChild().insertBefore(nd, this.bpmnDocument.getFirstChild().getFirstChild());
            	    
        		
        		// remove all elements except outgoings
				for (int k = startEventChildNodes.getLength()-1; k >= 0 ; k--) {
					if (!startEventChildNodes.item(k).getNodeName().equals("bpmn:outgoing")) {  
						currElement.removeChild(startEventChildNodes.item(k));            			  
					}
				}
        	
	          	// add message
	            Element messageEventDef = this.bpmnDocument.createElementNS("http://www.omg.org/spec/BPMN/20100524/MODEL", "bpmn:messageEventDefinition");
	          	messageEventDef.setAttribute("messageRef", messageId);
	          	currElement.appendChild(messageEventDef);
        		
        	}	        	
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}}
