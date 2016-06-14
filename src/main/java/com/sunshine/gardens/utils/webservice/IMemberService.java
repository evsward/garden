/**
 * IMemberService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.sunshine.gardens.utils.webservice;

public interface IMemberService extends java.rmi.Remote {
    public java.lang.String userLogin(java.lang.String loginXml) throws java.rmi.RemoteException;
    public java.lang.String resetPassword(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String activation(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String register(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String memberProfileUpdate(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getMemberInfo(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String forgetPassword(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getPointInfo(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getBalanceInfo(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String checkProfile(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String bindVip(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String memberExchange(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String stayIn(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getConsumeAccount(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String salesIncomeQuery(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String vipClassUp(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String consumptionDetail(java.lang.String xml) throws java.rmi.RemoteException;
}
