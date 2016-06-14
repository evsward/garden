package com.sunshine.gardens.utils;

import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.axis.encoding.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dance.core.utils.spring.SpringContextHolder;

/**
 * 字符串 DESede(3DES) 加密 ECB模式/使用PKCS7方式填充不足位,目前给的密钥是192位 3DES（即Triple
 * DES）是DES向AES过渡的加密算法（1999年，NIST将3-DES指定为过渡的
 * 加密标准），是DES的一个更安全的变形。它以DES为基本模块，通过组合分组方法设计出分组加
 * 密算法，其具体实现如下：设Ek()和Dk()代表DES算法的加密和解密过程，K代表DES算法使用的 密钥，P代表明文，C代表密表，这样，
 * 3DES加密过程为：C=Ek3(Dk2(Ek1(P))) 3DES解密过程为：P=Dk1((EK2(Dk3(C)))
 * 
 * @param args在java中调用sun公司提供的3DES加密解密算法时，需要使
 *            用到$JAVA_HOME/jre/lib/目录下如下的4个jar包： jce.jar
 *            security/US_export_policy.jar security/local_policy.jar
 *            ext/sunjce_provider.jar
 * 
 * @author LiuWenbin
 * @2015年11月28日
 */
public class ThreeDES {
	private final static Log logger = LogFactory.getLog(ThreeDES.class);
	private final static MyPropertyPlaceholderConfigurer configrue = SpringContextHolder.getBean("propertyPlaceholder");
	private final static String Algorithm = "DESede"; // 定义加密算法,可用DES,DESede,Blowfish

	/**
	 * 
	 * @param keybyte
	 *            加密密钥，长度为24字节
	 * @param src
	 *            被加密的数据缓冲区（源）
	 * @return
	 */
	private static byte[] encryptMode(byte[] keybyte, byte[] src) {
		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
			// 加密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return c1.doFinal(src);// 在单一方面的加密或解密
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param keybyte
	 *            加密密钥，长度为24字节
	 * @param src
	 *            加密后的缓冲区
	 * @return
	 */
	private static byte[] decryptMode(byte[] keybyte, byte[] src) {
		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
			// 解密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	/**
	 * 字符串转字节码
	 * 
	 * @return
	 */
	private static byte[] hex() {
		String key = configrue.getProp("order.qrcode.key");
		String f = DigestUtils.md5Hex(key);
		byte[] bkeys = new String(f).getBytes();
		byte[] enk = new byte[24];
		for (int i = 0; i < 24; i++) {
			enk[i] = bkeys[i];
		}
		return enk;
	}

	/**
	 * 加密字符串
	 * 
	 * @param srcStr
	 * @return 加密后的字符串
	 */
	@SuppressWarnings("restriction")
	public static String threeDesCrypt(String srcStr) {
		byte[] enk = hex();
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		byte[] encoded = encryptMode(enk, srcStr.getBytes());
		String result = Base64.encode(encoded);
		logger.info("加密后：" + result);
		return result;
	}

	/**
	 * 解密字符串
	 * 
	 * @param srcStr
	 * @return 解密后的字符串
	 */
	@SuppressWarnings("restriction")
	public static String threeDesDecrypt(String srcStr) {
		logger.info("元字符串：" + srcStr);
		byte[] enk = hex();
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		byte[] reqSrcStr = Base64.decode(srcStr);
		byte[] srcBytes = decryptMode(enk, reqSrcStr);
		return new String(srcBytes);
	}
	
	public static String encryptThreeDESECB(String src) throws Exception {
		String key = configrue.getProp("order.qrcode.key");
		DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
		SecretKey securekey = keyFactory.generateSecret(dks);

		Cipher cipher = Cipher.getInstance("DESede");
		cipher.init(Cipher.ENCRYPT_MODE, securekey);
		byte[] b = cipher.doFinal(src.getBytes());

		return Base64.encode(b);

	}

	public static String decryptThreeDESECB(String src) throws Exception {
		// --通过base64,将字符串转成byte数组
		// BASE64Decoder decoder = new BASE64Decoder();
		String key = configrue.getProp("order.qrcode.key");
		byte[] bytesrc = Base64.decode(src);
		// --解密的key
		DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
		SecretKey securekey = keyFactory.generateSecret(dks);

		// --Chipher对象解密
		Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, securekey);
		byte[] retByte = cipher.doFinal(bytesrc);

		return new String(retByte);
	}

	public static void main(String[] args) {
		System.out.println(threeDesDecrypt("HfEuoZw4ETMHXJC3eupVMmvhxrGTq3tTlWdlLltgaRYIYl4sAwn+wKomZWe0GT6R"));
	}
}
