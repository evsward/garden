package com.sunshine.gardens.utils.webservice;

public class IMemberServiceProxy implements com.sunshine.gardens.utils.webservice.IMemberService {
  private String _endpoint = null;
  private com.sunshine.gardens.utils.webservice.IMemberService iMemberService = null;

  public IMemberServiceProxy() {
    _initIMemberServiceProxy();
  }
  
  public IMemberServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initIMemberServiceProxy();
  }
  
  private void _initIMemberServiceProxy() {
    try {
      iMemberService = (new com.sunshine.gardens.utils.webservice.MemberServiceLocator()).getMemberServiceHttpEndpoint();
      if (iMemberService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)iMemberService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)iMemberService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (iMemberService != null)
      ((javax.xml.rpc.Stub)iMemberService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.sunshine.gardens.utils.webservice.IMemberService getIMemberService() {
    if (iMemberService == null)
      _initIMemberServiceProxy();
    return iMemberService;
  }
  
  public java.lang.String userLogin(java.lang.String loginXml) throws java.rmi.RemoteException{
    if (iMemberService == null)
      _initIMemberServiceProxy();
    return iMemberService.userLogin(loginXml);
  }
  
  public java.lang.String resetPassword(java.lang.String xml) throws java.rmi.RemoteException{
    if (iMemberService == null)
      _initIMemberServiceProxy();
    return iMemberService.resetPassword(xml);
  }
  
  public java.lang.String activation(java.lang.String xml) throws java.rmi.RemoteException{
    if (iMemberService == null)
      _initIMemberServiceProxy();
    return iMemberService.activation(xml);
  }
  
  public java.lang.String register(java.lang.String xml) throws java.rmi.RemoteException{
    if (iMemberService == null)
      _initIMemberServiceProxy();
    return iMemberService.register(xml);
  }
  
  public java.lang.String memberProfileUpdate(java.lang.String xml) throws java.rmi.RemoteException{
    if (iMemberService == null)
      _initIMemberServiceProxy();
    return iMemberService.memberProfileUpdate(xml);
  }
  
  public java.lang.String getMemberInfo(java.lang.String xml) throws java.rmi.RemoteException{
    if (iMemberService == null)
      _initIMemberServiceProxy();
    return iMemberService.getMemberInfo(xml);
  }
  
  public java.lang.String forgetPassword(java.lang.String xml) throws java.rmi.RemoteException{
    if (iMemberService == null)
      _initIMemberServiceProxy();
    return iMemberService.forgetPassword(xml);
  }
  
  public java.lang.String getPointInfo(java.lang.String xml) throws java.rmi.RemoteException{
    if (iMemberService == null)
      _initIMemberServiceProxy();
    return iMemberService.getPointInfo(xml);
  }
  
  public java.lang.String getBalanceInfo(java.lang.String xml) throws java.rmi.RemoteException{
    if (iMemberService == null)
      _initIMemberServiceProxy();
    return iMemberService.getBalanceInfo(xml);
  }
  
  public java.lang.String checkProfile(java.lang.String xml) throws java.rmi.RemoteException{
    if (iMemberService == null)
      _initIMemberServiceProxy();
    return iMemberService.checkProfile(xml);
  }
  
  public java.lang.String bindVip(java.lang.String xml) throws java.rmi.RemoteException{
    if (iMemberService == null)
      _initIMemberServiceProxy();
    return iMemberService.bindVip(xml);
  }
  
  public java.lang.String memberExchange(java.lang.String xml) throws java.rmi.RemoteException{
    if (iMemberService == null)
      _initIMemberServiceProxy();
    return iMemberService.memberExchange(xml);
  }
  
  public java.lang.String stayIn(java.lang.String xml) throws java.rmi.RemoteException{
    if (iMemberService == null)
      _initIMemberServiceProxy();
    return iMemberService.stayIn(xml);
  }
  
  public java.lang.String getConsumeAccount(java.lang.String xml) throws java.rmi.RemoteException{
    if (iMemberService == null)
      _initIMemberServiceProxy();
    return iMemberService.getConsumeAccount(xml);
  }
  
  public java.lang.String salesIncomeQuery(java.lang.String xml) throws java.rmi.RemoteException{
    if (iMemberService == null)
      _initIMemberServiceProxy();
    return iMemberService.salesIncomeQuery(xml);
  }
  
  public java.lang.String vipClassUp(java.lang.String xml) throws java.rmi.RemoteException{
    if (iMemberService == null)
      _initIMemberServiceProxy();
    return iMemberService.vipClassUp(xml);
  }
  
  public java.lang.String consumptionDetail(java.lang.String xml) throws java.rmi.RemoteException{
    if (iMemberService == null)
      _initIMemberServiceProxy();
    return iMemberService.consumptionDetail(xml);
  }
  
  
}