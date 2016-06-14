/**
 * IReservationService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.sunshine.gardens.utils.webservice;

public interface IReservationService extends java.rmi.Remote {
    public java.lang.String reservation(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String checkOut(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String rateQuery(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String rateQueryByCusNo(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getReservationDetail(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String resCancel(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String reservationPayment(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String reserveTypeAvail(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getRmrate(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String searchRoomType(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String checkCusNo(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String reservationCashment(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String reservationAccountment(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getCardnoConsumes(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String delCardnoConsumes(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String reservationAccountmentByCard(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getdic(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String guestAccountQuery(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String guestOrderQuery(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getRoomSta(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getRoomFutureSta(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String balanceToAraccnt(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String paramTest(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String selectRoomno(java.lang.String xml) throws java.rmi.RemoteException;
}
