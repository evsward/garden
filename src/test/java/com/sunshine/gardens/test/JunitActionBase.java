package com.sunshine.gardens.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.servlet.ModelAndView;
@ContextConfiguration
public class JunitActionBase {

//	private static HandlerMapping handlerMapping;
//	private static HandlerAdapter handlerAdapter;

//	/**
//	 * 读取spring3 MVC配置文件
//	 */
//	@BeforeClass
//	public static void setUp() {
//		if (handlerMapping == null) {
//			String[] configs = { "file:src/springConfig/springMVC.xml" };
//			XmlWebApplicationContext context = new XmlWebApplicationContext();
//			context.setConfigLocations(configs);
//			MockServletContext msc = new MockServletContext();
//			context.setServletContext(msc);
//			context.refresh();
//			msc.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, context);
//			handlerMapping = (HandlerMapping) context.getBean(DefaultAnnotationHandlerMapping.class);
//			handlerAdapter = (HandlerAdapter) context
//					.getBean(context.getBeanNamesForType(AnnotationMethodHandlerAdapter.class)[0]);
//		}
//	}

	/**
	 * 执行request对象请求的action
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView excuteAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		HandlerExecutionChain chain = handlerMapping.getHandler(request);
//		final ModelAndView model = handlerAdapter.handle(request, response, chain.getHandler());
		return null;
	}
}
