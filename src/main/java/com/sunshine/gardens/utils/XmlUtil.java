package com.sunshine.gardens.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.xml.sax.SAXException;

import com.dance.core.utils.spring.SpringContextHolder;

/**
 * XML文档处理类
 * 
 * @author liuwb.edward
 * 
 */
public class XmlUtil {
	private static Log log = LogFactory.getLog(XmlUtil.class);
	private final static MyPropertyPlaceholderConfigurer configrue = SpringContextHolder.getBean("propertyPlaceholder");

	/**
	 * 通过文件地址获得Document对象
	 * 
	 * @param xmlContent
	 *            XML内容
	 * @return
	 * @throws DocumentException
	 * @throws UnsupportedEncodingException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public static Document getDoucument(String xmlContent) throws DocumentException, UnsupportedEncodingException {
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(new ByteArrayInputStream(xmlContent.getBytes("UTF-8")));
		return document;
	}

	public static Document getDoucument(String xmlContent, String charSet) throws DocumentException,
			UnsupportedEncodingException {
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(new ByteArrayInputStream(xmlContent.getBytes(StringUtils.defaultString(
				charSet, "UTF-8"))));
		return document;
	}

	/**
	 * 判断字符串是否是xml格式
	 * 
	 * @param xmlContent
	 * @param charSet
	 * @return
	 */
	public static boolean isXmlStr(String xmlContent, String charSet) {
		Document document = null;
		try {
			document = getDoucument(xmlContent, charSet);
		} catch (Exception e) {
			log.error(e);
		}
		if (document == null) {
			return false;
		}
		return true;
	}

	/**
	 * 判断字符串是否是xml格式
	 * 
	 * @param xmlContent
	 * @param charSet
	 * @return
	 */
	public static boolean isXmlStr(String xmlContent) {
		return isXmlStr(xmlContent, null);
	}

	/**
	 * 获得子节点集合
	 * 
	 * @param root
	 *            Document root对象
	 * @param childNodeName
	 *            需要查找到的Node对象名称 可使用格式如"data/products/product"
	 */
	@SuppressWarnings("unchecked")
	public static List<Node> getChildNodeListByName(Document root, String childNodeName) {
		return root.selectNodes(childNodeName);
	}

	/**
	 * 获得子节点的Text，如果没有这个节点，返回null
	 * 
	 * @param root
	 *            Document root对象
	 * @param childNodeName
	 *            需要查找到的Node对象名称 可使用格式如"data/products/product"
	 */
	public static String getChildNodeTextByName(Document root, String childNodeName) {
		Node childNode = root.selectSingleNode(childNodeName);
		if (childNode == null) {
			return null;
		}
		return getNodeText(childNode);
	}

	/**
	 * 获得子节点集合 如果父节点名称与参数传递的父节点名称相同,那么返回子节点集合,否则返回NULL
	 * 
	 * @param parentNode
	 *            父节点对象
	 * @param parentNodeName
	 *            父节点名称
	 * @param childNodeName
	 *            需要获得的子节点名称 可使用格式如"data/products/product"
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Node> getChildNodeListByName(Node parentNode, String childNodeName) {
		try {
			return parentNode.selectNodes(childNodeName);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获得指定子节点的值 如果父节点名称与参数传递的父节点名称相同，那么返回指定的子节点的值，否则返回NULL
	 * 
	 * @param parentNode
	 *            父节点对象
	 * @param parentNodeName
	 *            父节点名称
	 * @param childNodeName
	 *            需要获得值的子节点名称
	 * @return
	 */
	public static String getChildNodeText(Node node, String childNodeName) {
		try {
			return node.selectSingleNode(childNodeName).getText();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获得节点的值 如果节点名称与参数传递的节点名称相同，那么返回节点的值，否则返回NULL
	 * 
	 * @param node
	 *            节点对象
	 * @param nodeName
	 *            节点名称
	 * @return
	 */
	public static String getNodeText(Node node) {
		try {
			return node.getText();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 通过子节点名称与子节点的值获得子节点的父节点
	 * 
	 * @param rootNode
	 *            根节点
	 * @param childNodeText
	 *            子节点的值
	 * @param childNodeName
	 *            子节点名称
	 * @return
	 */
	public static Node getParentByChildNodeText(Node rootNode, String childNodeName, String childNodeText) {
		List<Node> nodeList = getChildNodeListByName(rootNode.getDocument(), childNodeName);
		log.debug("nodeList.size() = " + nodeList.size());
		log.debug("childNodeText = " + childNodeText);
		for (Node node : nodeList) {
			log.debug("node.getText = " + node.getText());
			if (childNodeText.equalsIgnoreCase(node.getText())) {
				return node.getParent();
			}
		}

		return null;
	}

	/**
	 * 获得节点内属性值
	 * 
	 * @param node
	 * @param keyName
	 * @return
	 */
	public static String getNodeAttributeValue(Node node, String keyName) {
		try {
			return node.selectSingleNode(keyName).getText();
		} catch (Exception e) {
			return null;
		}
	}

	public static void main(String[] args) throws Exception {
		String xmlStr = "<?xml version='1.0' encoding='utf-8'?><Response xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:xsd='http://www.w3.org/2001/XMLSchema'><Head><transcode>10</transcode><systype>1013</systype><retcode>0</retcode><retmsg>成功获取返回信息</retmsg></Head><Body><ResponseBodyRmtype><Rmtype><rmtype>BS1</rmtype><descript>别墅二室二厅</descript><descript1 /></Rmtype><Rmtype><rmtype>BS2</rmtype><descript>别墅三室两厅</descript><descript1 /></Rmtype><Rmtype><rmtype>BS3</rmtype><descript>别墅四室二厅</descript><descript1 /></Rmtype><Rmtype><rmtype>BS4</rmtype><descript>别墅五室二厅</descript><descript1 /></Rmtype><Rmtype><rmtype>JD1</rmtype><descript>酒店1-3层</descript><descript1 /></Rmtype><Rmtype><rmtype>JD2</rmtype><descript>酒店4-6层单人</descript><descript1 /></Rmtype><Rmtype><rmtype>JF1</rmtype><descript>酒店复式大床</descript><descript1 /></Rmtype><Rmtype><rmtype>JF2</rmtype><descript>酒店复式双床</descript><descript1 /></Rmtype><Rmtype><rmtype>JT</rmtype><descript>酒店套房</descript><descript1 /></Rmtype><Rmtype><rmtype>WB1</rmtype><descript>湖畔别墅1</descript><descript1 /></Rmtype><Rmtype><rmtype>WB2</rmtype><descript>湖畔别墅2</descript><descript1 /></Rmtype><Rmtype><rmtype>JD3</rmtype><descript>酒店标间大床</descript><descript1 /></Rmtype><Rmtype><rmtype>HF</rmtype><descript>花园酒店复式</descript><descript1 /></Rmtype><Rmtype><rmtype>HDT</rmtype><descript>花园酒店度假套房</descript><descript1 /></Rmtype><Rmtype><rmtype>HT</rmtype><descript>花园酒店套房</descript><descript1 /></Rmtype><Rmtype><rmtype>HB1</rmtype><descript>花园酒店标间一层</descript><descript1 /></Rmtype><Rmtype><rmtype>HB2</rmtype><descript>花园酒店标间二层</descript><descript1 /></Rmtype><Rmtype><rmtype>JF3</rmtype><descript>酒店复式儿童房</descript><descript1 /></Rmtype><Rmtype><rmtype>JF4</rmtype><descript>酒店复式麻将房</descript><descript1 /></Rmtype><Rmtype><rmtype>GY2</rmtype><descript>公寓二室一厅</descript><descript1 /></Rmtype><Rmtype><rmtype>GY3</rmtype><descript>公寓三室一厅</descript><descript1 /></Rmtype><Rmtype><rmtype>HS1</rmtype><descript>会所1</descript><descript1 /></Rmtype><Rmtype><rmtype>HS2</rmtype><descript>会所2</descript><descript1 /></Rmtype><responsecode>1</responsecode><responsemsg>成功</responsemsg></ResponseBodyRmtype></Body></Response>";
		Map<String, String> map = xmlStr2MapParent(xmlStr);
		for (Map.Entry<String, String> entry : map.entrySet()) {
			System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
		}
	}

	/**
	 * 递归方式查找指定节点
	 * 
	 * @param root
	 *            根节点
	 * @param ElementName
	 *            要查找的节点名称，不区分大小写
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Element findElementByName(Element root, String ElementName) {
		Iterator<Element> it = root.elementIterator();
		if (!it.hasNext()) {
			return null;
		}
		for (Element el; it.hasNext();) {
			el = it.next();
			if (ElementName.equalsIgnoreCase(el.getName())) {
				return el;
			} else {
				Element _el = findElementByName(el, ElementName);
				if (_el == null) {
					continue;
				}
				return _el;
			}
		}
		return null;
	}

	/**
	 * 此方法可解析任意xml文件 map key为 父节点的名称+节点名称
	 * 
	 * @param xmlStr
	 * @return
	 */
	public static Map<String, String> xmlStr2MapParent(String xmlStr) {
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(new StringReader(xmlStr));
			Element rootElement = document.getRootElement();
			getElementValue(rootElement, resultMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	@SuppressWarnings("unchecked")
	private static void getElementValue(Element e, Map<String, String> map) {
		Iterator<Element> it = (Iterator<Element>) e.elementIterator();
		if (it.hasNext()) {
			while (it.hasNext()) {
				getElementValue(it.next(), map);
			}
		} else {

			String k = e.getName();
			String v = e.getText();

			Element parentElement = e.getParent();
			map.put(parentElement.getName() + k, v);
		}
	}

	/**
	 * 解析字符串xml，为Map<String, Object>
	 * 
	 * @param xmlStr
	 * @return
	 */
	public static Map<String, Object> xmlStr2MapObjParent(String xmlStr) {
		log.info("接收XML，数据信息：" + xmlStr);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(new StringReader(xmlStr));
			Element rootElement = document.getRootElement();
			getElementObjValue(rootElement, resultMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	@SuppressWarnings("unchecked")
	private static void getElementObjValue(Element e, Map<String, Object> map) {
		Iterator<Element> it = (Iterator<Element>) e.elementIterator();
		if (it.hasNext()) {
			while (it.hasNext()) {
				getElementObjValue(it.next(), map);
			}
		} else {

			String k = e.getName();
			String v = e.getText();
			map.put(k, v);
		}
	}

	public static String queryWSDict(String code) {
		StringBuffer xml = new StringBuffer();
		xml.append(xmlWSHeadStr(1041));
		xml.append("<Body><code>");
		xml.append(code);
		xml.append("</code></Body></Request>");
		return xml.toString();
	}

	public static String xmlWSHeadStr(int systype) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer xml = new StringBuffer();
		xml.append("<Request><Head><transcode>");
		xml.append(configrue.getProp("transcode"));
		xml.append("</transcode><reqtime>");
		xml.append(sdf.format(new Date()));
		xml.append("</reqtime><systype>");
		xml.append(systype);
		xml.append("</systype><username>");
		xml.append(configrue.getProp("username"));
		xml.append("</username><password>");
		xml.append(configrue.getProp("password"));
		xml.append("</password></Head>");
		return xml.toString();
	}
	
}
