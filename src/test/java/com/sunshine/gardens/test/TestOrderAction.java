package com.sunshine.gardens.test;

import org.junit.Assert;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

public class TestOrderAction extends JunitActionBase {  
//    @Test
    public void testAdd() throws Exception {  
    MockHttpServletRequest request = new MockHttpServletRequest();  
        MockHttpServletResponse response = new MockHttpServletResponse();  
        request.setServletPath("/order/add");  
        request.addParameter("id", "1002");  
        request.addParameter("date", "2010-12-30");  
        request.setMethod("POST");  
        // 执行URI对应的action  
        final ModelAndView mav = this.excuteAction(request, response);  
        // Assert logic  
        Assert.assertEquals("order/add", mav.getViewName());  
        String msg=(String)request.getAttribute("msg");  
        System.out.println(msg);  
    }  
}