package app.univs.cn.myapplication;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

public class RsaSignAndVerify {

//	private static Logger log=LoggerFactory.getLogger(RsaSignAndVerify.class);
	/** 
     * 签名算法 
     */  
    private static final String SIGN_ALGORITHMS = "SHA1WithRSA";
    
    /**
     * RSA签名
     * @param content
     * @param privateKey
     * @return
     */
    public static byte[] sign(byte[] content, RSAPrivateKey privateKey){
        try{
        	PKCS8EncodedKeySpec priPKCS8= new PKCS8EncodedKeySpec(privateKey.getEncoded());
            KeyFactory keyf= KeyFactory.getInstance("RSA");
            PrivateKey priKey= keyf.generatePrivate(priPKCS8);
            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
            signature.initSign(priKey);
            signature.update(content);
            byte[] signed = signature.sign();
            return signed;
        } catch (Exception e){
//        	if(log.isDebugEnabled()){
//                log.error(e.getMessage(),e);
//        	}else{
            	e.printStackTrace();
//        	}
           return null;
        }
    }
    
    /**
     * RSA验签
     * @param content
     * @param
     * @param publicKey
     * @return
     */
    public static boolean doCheck(byte[] content, byte[] signBytes, RSAPublicKey publicKey){
        try{
        	X509EncodedKeySpec pubX509=new X509EncodedKeySpec(publicKey.getEncoded());
        	KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        	PublicKey pubKey = keyFactory.generatePublic(pubX509);
            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
            signature.initVerify(pubKey);
            signature.update(content);
            boolean bverify = signature.verify(signBytes);
            return bverify;
        }catch (Exception e){
//        	if(log.isDebugEnabled()){
//                log.error(e.getMessage(),e);
//        	}else{
            	e.printStackTrace();
//        	}
        }
        return false;
    } 
}
