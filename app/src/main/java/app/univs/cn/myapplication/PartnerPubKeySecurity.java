package app.univs.cn.myapplication;

import android.content.Context;
import android.util.Log;

import org.apache.commons.codec1.binary.Base64;

import java.io.InputStream;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

public class PartnerPubKeySecurity {

	/** 获取密文和签名串
	 * @param plainText
	 * @return Map<String,String>
	 * @throws Exception
	 * return map  1,data:密文，signStr：签名串
	 */
	public static Map<String,String> getSignAndData(String plainText) throws Exception {
		Map<String,String> map=new HashMap<String, String>();
		byte[] sdata=encrypt(plainText, PA_PUBLIC_KEY);
		String data= Base64.encodeBase64String(sdata);
//		String data= Base64.encodeToString(sdata, BASE64_FLAG);
		byte[] digest=BaseSecuret.encodeSHA512(sdata);
		String signStr=Base64.encodeBase64String(doSign(digest));
//		String signStr=Base64.encodeToString(doSign(digest),BASE64_FLAG);
		map.put("data", data);
		map.put("signStr", signStr);
		return map;
	}
	
	/**
	 * 加签
	 * @param
	 * @return sign
	 * @throws Exception
	 */
	public static byte[] doSign(byte[] data) throws Exception {
		return RsaSignAndVerify.sign(data,GX_PRIVATE_KEY);
	}
	
	/**
	 * 验签
	 * @param data
	 * @param
	 * @return 
	 * @throws Exception
	 */
	public static boolean doVerify(String data,String sign) throws Exception {
		byte[] content=BaseSecuret.encodeSHA512(Base64.decodeBase64(data));
		byte[] signBytes=Base64.decodeBase64(sign);
		return RsaSignAndVerify.doCheck(content,signBytes,GX_PUBLIC_KEY);
	}
	
	/**
	 * RSA加密
	 * @param plainText
	 * @return 
	 * @throws Exception
	 */
	public static byte[] encrypt(String plainText,RSAKey rsaKey) throws Exception {
		byte[] securityContent=null;
		if(rsaKey instanceof RSAPrivateKey){
			securityContent=BaseSecuret.rsaEncrypty((RSAPrivateKey)rsaKey, plainText);
		}else if(rsaKey instanceof RSAPublicKey){
			securityContent=BaseSecuret.rsaEncrypty((RSAPublicKey)rsaKey, plainText);
		}
		return securityContent;
	}
	
	/**
	 * RSA解密
	 * @param data
	 * @return plainText
	 * @throws Exception
	 */
	public static String decrypt(String data) throws Exception {
		byte[] result=BaseSecuret.rsaDescrypt(PA_PRIVATE_KEY,Base64.decodeBase64(data));
		return new String(result);
	}
	
	/**
	 * 初始化公钥和私钥
	 */
	private static void initParam() throws Exception {
		Context mContext= HaoBuyInit.getInstance().getmContext();
		//读取公钥和私钥
		InputStream publicIn=mContext.getResources().getAssets().open("gx_SEC_PUBKies.key");
		InputStream privateIn=mContext.getResources().getAssets().open("gx_SEC_PRIKies.pfx");
		//读取合作商公钥
		InputStream paPublicIn=mContext.getResources().getAssets().open("pa_SEC_PUBKies.key");
		//模拟读取合作商私钥
		InputStream paPrivateIn=mContext.getResources().getAssets().open("pa_SEC_PRIKies.pfx");

//		if(publicIn==null||privateIn==null){
//			//若GX密钥文件不存在,则生成密钥文件
//			createAndSavekeys();
//		}
		if(paPublicIn==null||paPrivateIn==null){
			Log.e("PartnerPubKeySecurity", "未找到合作商密钥，请检查！");
		}
		try {
			GX_PUBLIC_KEY=(RSAPublicKey)BaseSecuret.readKey(publicIn);
			GX_PRIVATE_KEY=(RSAPrivateKey)BaseSecuret.readKey(privateIn);
			PA_PUBLIC_KEY=(RSAPublicKey)BaseSecuret.readKey(paPublicIn);
			PA_PRIVATE_KEY=(RSAPrivateKey)BaseSecuret.readKey(paPrivateIn);//模拟初始化合作商私钥
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static RSAPublicKey PA_PUBLIC_KEY;
	private static RSAPrivateKey PA_PRIVATE_KEY;//模拟合作商私钥
	private static RSAPublicKey GX_PUBLIC_KEY;
	private static RSAPrivateKey GX_PRIVATE_KEY;
	
	static{
		//初始化参
		try {
			initParam();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
