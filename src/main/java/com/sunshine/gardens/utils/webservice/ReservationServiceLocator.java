/**
 * ReservationServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.sunshine.gardens.utils.webservice;

import com.dance.core.utils.spring.SpringContextHolder;
import com.sunshine.gardens.utils.MyPropertyPlaceholderConfigurer;

public class ReservationServiceLocator extends org.apache.axis.client.Service implements
		com.sunshine.gardens.utils.webservice.ReservationService {
	private final static MyPropertyPlaceholderConfigurer configrue = SpringContextHolder.getBean("propertyPlaceholder");

	public ReservationServiceLocator() {
	}

	public ReservationServiceLocator(org.apache.axis.EngineConfiguration config) {
		super(config);
	}

	public ReservationServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName)
			throws javax.xml.rpc.ServiceException {
		super(wsdlLoc, sName);
	}

	// Use to get a proxy class for ReservationServiceHttpEndpoint
	private java.lang.String ReservationServiceHttpEndpoint_address = configrue.getProp("reservationUrl");

	public java.lang.String getReservationServiceHttpEndpointAddress() {
		return ReservationServiceHttpEndpoint_address;
	}

	// The WSDD service name defaults to the port name.
	private java.lang.String ReservationServiceHttpEndpointWSDDServiceName = "ReservationServiceHttpEndpoint";

	public java.lang.String getReservationServiceHttpEndpointWSDDServiceName() {
		return ReservationServiceHttpEndpointWSDDServiceName;
	}

	public void setReservationServiceHttpEndpointWSDDServiceName(java.lang.String name) {
		ReservationServiceHttpEndpointWSDDServiceName = name;
	}

	public com.sunshine.gardens.utils.webservice.IReservationService getReservationServiceHttpEndpoint()
			throws javax.xml.rpc.ServiceException {
		java.net.URL endpoint;
		try {
			endpoint = new java.net.URL(ReservationServiceHttpEndpoint_address);
		} catch (java.net.MalformedURLException e) {
			throw new javax.xml.rpc.ServiceException(e);
		}
		return getReservationServiceHttpEndpoint(endpoint);
	}

	public com.sunshine.gardens.utils.webservice.IReservationService getReservationServiceHttpEndpoint(
			java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
		try {
			com.sunshine.gardens.utils.webservice.ReservationServiceHttpEndpointStub _stub = new com.sunshine.gardens.utils.webservice.ReservationServiceHttpEndpointStub(
					portAddress, this);
			_stub.setPortName(getReservationServiceHttpEndpointWSDDServiceName());
			return _stub;
		} catch (org.apache.axis.AxisFault e) {
			return null;
		}
	}

	public void setReservationServiceHttpEndpointEndpointAddress(java.lang.String address) {
		ReservationServiceHttpEndpoint_address = address;
	}

	/**
	 * For the given interface, get the stub implementation. If this service has
	 * no port for the given interface, then ServiceException is thrown.
	 */
	public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
		try {
			if (com.sunshine.gardens.utils.webservice.IReservationService.class
					.isAssignableFrom(serviceEndpointInterface)) {
				com.sunshine.gardens.utils.webservice.ReservationServiceHttpEndpointStub _stub = new com.sunshine.gardens.utils.webservice.ReservationServiceHttpEndpointStub(
						new java.net.URL(ReservationServiceHttpEndpoint_address), this);
				_stub.setPortName(getReservationServiceHttpEndpointWSDDServiceName());
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
		if ("ReservationServiceHttpEndpoint".equals(inputPortName)) {
			return getReservationServiceHttpEndpoint();
		} else {
			java.rmi.Remote _stub = getPort(serviceEndpointInterface);
			((org.apache.axis.client.Stub) _stub).setPortName(portName);
			return _stub;
		}
	}

	public javax.xml.namespace.QName getServiceName() {
		return new javax.xml.namespace.QName("http://tempuri.org/", "ReservationService");
	}

	private java.util.HashSet ports = null;

	public java.util.Iterator getPorts() {
		if (ports == null) {
			ports = new java.util.HashSet();
			ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "ReservationServiceHttpEndpoint"));
		}
		return ports.iterator();
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(java.lang.String portName, java.lang.String address)
			throws javax.xml.rpc.ServiceException {

		if ("ReservationServiceHttpEndpoint".equals(portName)) {
			setReservationServiceHttpEndpointEndpointAddress(address);
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
