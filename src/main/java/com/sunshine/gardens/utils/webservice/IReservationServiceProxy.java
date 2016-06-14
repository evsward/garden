package com.sunshine.gardens.utils.webservice;

public class IReservationServiceProxy implements com.sunshine.gardens.utils.webservice.IReservationService {
  private String _endpoint = null;
  private com.sunshine.gardens.utils.webservice.IReservationService iReservationService = null;

  public IReservationServiceProxy() {
    _initIReservationServiceProxy();
  }
  
  public IReservationServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initIReservationServiceProxy();
  }
  
  private void _initIReservationServiceProxy() {
    try {
      iReservationService = (new com.sunshine.gardens.utils.webservice.ReservationServiceLocator()).getReservationServiceHttpEndpoint();
      if (iReservationService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)iReservationService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)iReservationService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (iReservationService != null)
      ((javax.xml.rpc.Stub)iReservationService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.sunshine.gardens.utils.webservice.IReservationService getIReservationService() {
    if (iReservationService == null)
      _initIReservationServiceProxy();
    return iReservationService;
  }
  
  public java.lang.String reservation(java.lang.String xml) throws java.rmi.RemoteException{
    if (iReservationService == null)
      _initIReservationServiceProxy();
    return iReservationService.reservation(xml);
  }
  
  public java.lang.String checkOut(java.lang.String xml) throws java.rmi.RemoteException{
    if (iReservationService == null)
      _initIReservationServiceProxy();
    return iReservationService.checkOut(xml);
  }
  
  public java.lang.String rateQuery(java.lang.String xml) throws java.rmi.RemoteException{
    if (iReservationService == null)
      _initIReservationServiceProxy();
    return iReservationService.rateQuery(xml);
  }
  
  public java.lang.String rateQueryByCusNo(java.lang.String xml) throws java.rmi.RemoteException{
    if (iReservationService == null)
      _initIReservationServiceProxy();
    return iReservationService.rateQueryByCusNo(xml);
  }
  
  public java.lang.String getReservationDetail(java.lang.String xml) throws java.rmi.RemoteException{
    if (iReservationService == null)
      _initIReservationServiceProxy();
    return iReservationService.getReservationDetail(xml);
  }
  
  public java.lang.String resCancel(java.lang.String xml) throws java.rmi.RemoteException{
    if (iReservationService == null)
      _initIReservationServiceProxy();
    return iReservationService.resCancel(xml);
  }
  
  public java.lang.String reservationPayment(java.lang.String xml) throws java.rmi.RemoteException{
    if (iReservationService == null)
      _initIReservationServiceProxy();
    return iReservationService.reservationPayment(xml);
  }
  
  public java.lang.String reserveTypeAvail(java.lang.String xml) throws java.rmi.RemoteException{
    if (iReservationService == null)
      _initIReservationServiceProxy();
    return iReservationService.reserveTypeAvail(xml);
  }
  
  public java.lang.String getRmrate(java.lang.String xml) throws java.rmi.RemoteException{
    if (iReservationService == null)
      _initIReservationServiceProxy();
    return iReservationService.getRmrate(xml);
  }
  
  public java.lang.String searchRoomType(java.lang.String xml) throws java.rmi.RemoteException{
    if (iReservationService == null)
      _initIReservationServiceProxy();
    return iReservationService.searchRoomType(xml);
  }
  
  public java.lang.String checkCusNo(java.lang.String xml) throws java.rmi.RemoteException{
    if (iReservationService == null)
      _initIReservationServiceProxy();
    return iReservationService.checkCusNo(xml);
  }
  
  public java.lang.String reservationCashment(java.lang.String xml) throws java.rmi.RemoteException{
    if (iReservationService == null)
      _initIReservationServiceProxy();
    return iReservationService.reservationCashment(xml);
  }
  
  public java.lang.String reservationAccountment(java.lang.String xml) throws java.rmi.RemoteException{
    if (iReservationService == null)
      _initIReservationServiceProxy();
    return iReservationService.reservationAccountment(xml);
  }
  
  public java.lang.String getCardnoConsumes(java.lang.String xml) throws java.rmi.RemoteException{
    if (iReservationService == null)
      _initIReservationServiceProxy();
    return iReservationService.getCardnoConsumes(xml);
  }
  
  public java.lang.String delCardnoConsumes(java.lang.String xml) throws java.rmi.RemoteException{
    if (iReservationService == null)
      _initIReservationServiceProxy();
    return iReservationService.delCardnoConsumes(xml);
  }
  
  public java.lang.String reservationAccountmentByCard(java.lang.String xml) throws java.rmi.RemoteException{
    if (iReservationService == null)
      _initIReservationServiceProxy();
    return iReservationService.reservationAccountmentByCard(xml);
  }
  
  public java.lang.String getdic(java.lang.String xml) throws java.rmi.RemoteException{
    if (iReservationService == null)
      _initIReservationServiceProxy();
    return iReservationService.getdic(xml);
  }
  
  public java.lang.String guestAccountQuery(java.lang.String xml) throws java.rmi.RemoteException{
    if (iReservationService == null)
      _initIReservationServiceProxy();
    return iReservationService.guestAccountQuery(xml);
  }
  
  public java.lang.String guestOrderQuery(java.lang.String xml) throws java.rmi.RemoteException{
    if (iReservationService == null)
      _initIReservationServiceProxy();
    return iReservationService.guestOrderQuery(xml);
  }
  
  public java.lang.String getRoomSta(java.lang.String xml) throws java.rmi.RemoteException{
    if (iReservationService == null)
      _initIReservationServiceProxy();
    return iReservationService.getRoomSta(xml);
  }
  
  public java.lang.String getRoomFutureSta(java.lang.String xml) throws java.rmi.RemoteException{
    if (iReservationService == null)
      _initIReservationServiceProxy();
    return iReservationService.getRoomFutureSta(xml);
  }
  
  public java.lang.String balanceToAraccnt(java.lang.String xml) throws java.rmi.RemoteException{
    if (iReservationService == null)
      _initIReservationServiceProxy();
    return iReservationService.balanceToAraccnt(xml);
  }
  
  public java.lang.String paramTest(java.lang.String xml) throws java.rmi.RemoteException{
    if (iReservationService == null)
      _initIReservationServiceProxy();
    return iReservationService.paramTest(xml);
  }
  
  public java.lang.String selectRoomno(java.lang.String xml) throws java.rmi.RemoteException{
    if (iReservationService == null)
      _initIReservationServiceProxy();
    return iReservationService.selectRoomno(xml);
  }
  
  
}