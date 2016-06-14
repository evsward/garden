/**
 * MemberServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.sunshine.gardens.utils.webservice;

import com.dance.core.utils.spring.SpringContextHolder;
import com.sunshine.gardens.utils.MyPropertyPlaceholderConfigurer;

public class MemberServiceLocator extends org.apache.axis.client.Service implements
		com.sunshine.gardens.utils.webservice.MemberService {
	private final static MyPropertyPlaceholderConfigurer configrue = SpringContextHolder.getBean("propertyPlaceholder");

	public MemberServiceLocator() {
	}

	public MemberServiceLocator(org.apache.axis.EngineConfiguration config) {
		super(config);
	}

	public MemberServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName)
			throws javax.xml.rpc.ServiceException {
		super(wsdlLoc, sName);
	}

	// Use to get a proxy class for MemberServiceHttpEndpoint
	private java.lang.String MemberServiceHttpEndpoint_address = configrue.getProp("vipMemberUrl");

	public java.lang.String getMemberServiceHttpEndpointAddress() {
		return MemberServiceHttpEndpoint_address;
	}

	// The WSDD service name defaults to the port name.
	private java.lang.String MemberServiceHttpEndpointWSDDServiceName = "MemberServiceHttpEndpoint";

	public java.lang.String getMemberServiceHttpEndpointWSDDServiceName() {
		return MemberServiceHttpEndpointWSDDServiceName;
	}

	public void setMemberServiceHttpEndpointWSDDServiceName(java.lang.String name) {
		MemberServiceHttpEndpointWSDDServiceName = name;
	}

	public com.sunshine.gardens.utils.webservice.IMemberService getMemberServiceHttpEndpoint()
			throws javax.xml.rpc.ServiceException {
		java.net.URL endpoint;
		try {
			endpoint = new java.net.URL(MemberServiceHttpEndpoint_address);
		} catch (java.net.MalformedURLException e) {
			throw new javax.xml.rpc.ServiceException(e);
		}
		return getMemberServiceHttpEndpoint(endpoint);
	}

	public com.sunshine.gardens.utils.webservice.IMemberService getMemberServiceHttpEndpoint(java.net.URL portAddress)
			throws javax.xml.rpc.ServiceException {
		try {
			com.sunshine.gardens.utils.webservice.MemberServiceHttpEndpointStub _stub = new com.sunshine.gardens.utils.webservice.MemberServiceHttpEndpointStub(
					portAddress, this);
			_stub.setPortName(getMemberServiceHttpEndpointWSDDServiceName());
			return _stub;
		} catch (org.apache.axis.AxisFault e) {
			return null;
		}
	}

	public void setMemberServiceHttpEndpointEndpointAddress(java.lang.String address) {
		MemberServiceHttpEndpoint_address = address;
	}

	/**
	 * For the given interface, get the stub implementation. If this service has
	 * no port for the given interface, then ServiceException is thrown.
	 */
	public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
		try {
			if (com.sunshine.gardens.utils.webservice.IMemberService.class.isAssignableFrom(serviceEndpointInterface)) {
				com.sunshine.gardens.utils.webservice.MemberServiceHttpEndpointStub _stub = new com.sunshine.gardens.utils.webservice.MemberServiceHttpEndpointStub(
						new java.net.URL(MemberServiceHttpEndpoint_address), this);
				_stub.setPortName(getMemberServiceHttpEndpointWSDDServiceName());
				return _stub;
			}
		} catch (java.lang.Throwable t) {
			throw new javax.xml.rpc.ServiceException(t);
		}
		throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  "
				+ (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
	}

	/**
	 * For the given interface, get the stub implementation. If this service has
	 * no port for the given interface, then ServiceException is thrown.
	 */
	public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface)
			throws javax.xml.rpc.ServiceException {
		if (portName == null) {
			return getPort(serviceEndpointInterface);
		}
		java.lang.String inputPortName = portName.getLocalPart();
		if ("MemberServiceHttpEndpoint".equals(inputPortName)) {
			return getMemberServiceHttpEndpoint();
		} else {
			java.rmi.Remote _stub = getPort(serviceEndpointInterface);
			((org.apache.axis.client.Stub) _stub).setPortName(portName);
			return _stub;
		}
	}

	public javax.xml.namespace.QName getServiceName() {
		return new javax.xml.namespace.QName("http://tempuri.org/", "MemberService");
	}

	private java.util.HashSet ports = null;

	public java.util.Iterator getPorts() {
		if (ports == null) {
			ports = new java.util.HashSet();
			ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "MemberServiceHttpEndpoint"));
		}
		return ports.iterator();
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(java.lang.String portName, java.lang.String address)
			throws javax.xml.rpc.ServiceException {

		if ("MemberServiceHttpEndpoint".equals(portName)) {
			setMemberServiceHttpEndpointEndpointAddress(address);
		} else { // Unknown Port Name
			throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
		}
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address)
			throws javax.xml.rpc.ServiceException {
		setEndpointAddress(portName.getLocalPart(), address);
	}

}
