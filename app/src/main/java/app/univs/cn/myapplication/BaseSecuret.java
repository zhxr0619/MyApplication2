package app.univs.cn.myapplication;

import android.util.Log;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class BaseSecuret {

	private static final int KEYSIZE = 512;
	private static final int ENCRYPT_BLOCK_SIZE=245;
	private static final int EDECRYPT_BLOCK_SIZE=256;
    
    /**  
     * 读取密钥 
     * @param
     * @return Key
     * @throws Exception
     */  
    public static Key readKey(InputStream in) throws Exception {
        ObjectInputStream oiskey=new ObjectInputStream(in);
        Key key=(Key)oiskey.readObject();
        oiskey.close();
        in.close();  
        return key;
    }

	
	/**
	 * 私钥加密
	 * @param privateKey
	 * @param
	 * @return
	 * @throws Exception
	 */
	public static byte[] rsaEncrypty(RSAPrivateKey privateKey,String plainText) throws Exception {
		if(privateKey==null||"".equals(privateKey)){
			throw new SecurityException("私钥不能为空，请检查私钥信息");
		}
		Cipher cipher=null;
		byte[] securityData=null;
		try{
			cipher= Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			for(int i=0;i<plainText.getBytes("UTF-8").length;i+=ENCRYPT_BLOCK_SIZE){
				byte[] securityRes=cipher.doFinal(subArray(plainText.getBytes("UTF-8"),i, i+ENCRYPT_BLOCK_SIZE));
				securityData=connArray(securityData, securityRes);
			}
			return securityData;
		}catch(NoSuchAlgorithmException e){
			throw new SecurityException("未找到该加密算法");
		}catch(NoSuchPaddingException e){
//			log.error(e.getMessage(),e);
			e.printStackTrace();
			return null;
		}catch(InvalidKeyException e){
			throw new SecurityException("非法私钥，请检查私钥信息");
		}catch (IllegalBlockSizeException e) {
            throw new SecurityException("明文长度非法，请检查明文长度");
        } catch (BadPaddingException e) {
            throw new SecurityException("明文数据已损坏，请检查明文数据信息");
        }
	}
	
	/**
	 * 公钥加密
	 * @param publicKey
	 * @param
	 * @return
	 * @throws Exception
	 */
	public static byte[] rsaEncrypty(RSAPublicKey publicKey,String plainText) throws Exception {
		if(publicKey==null||"".equals(publicKey)){
			throw new SecurityException("公钥不能为空，请检查公钥信息");
		}
		Cipher cipher=null;
		byte[] securityData=null;
		try{
			cipher= Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			for(int i=0;i<plainText.getBytes("UTF-8").length;i+=ENCRYPT_BLOCK_SIZE){
				byte[] securityRes=cipher.doFinal(subArray(plainText.getBytes("UTF-8"), i, i+ENCRYPT_BLOCK_SIZE));
				securityData=connArray(securityData, securityRes);
			}
			return securityData;
		}catch(NoSuchAlgorithmException e){
//			if(log.isErrorEnabled()){
//				log.error(e.getMessage(),e);
//			}else{
				e.printStackTrace();
				throw new SecurityException("未找到该加密算法");
//			}
		}catch(NoSuchPaddingException e){
//			if(log.isErrorEnabled()){
//				log.error(e.getMessage(),e);
//			}else{
				e.printStackTrace();
//			}
		}catch(InvalidKeyException e){
//			if(log.isErrorEnabled()){
//				log.error(e.getMessage(),e);
//			}else{
				e.printStackTrace();
				throw new SecurityException("非法公钥，请检查公钥信息");
//			}
		}catch (IllegalBlockSizeException e) {
//			if(log.isErrorEnabled()){
//				log.error(e.getMessage(),e);
//			}else{
				e.printStackTrace();
	            throw new SecurityException("明文长度非法，请检查明文长度");
//			}
        } catch (BadPaddingException e) {
//        	if(log.isErrorEnabled()){
//        		log.error(e.getMessage(),e);
//        	}else{
        		e.printStackTrace();
                throw new SecurityException("明文数据已损坏，请检查明文数据信息");
//        	}
        }
		return null;
	}

	/**
	 * 私钥解密
	 * @param privateKey
	 * @param securityData
	 * @return
	 * @throws Exception
	 */
	public static byte[] rsaDescrypt(RSAPrivateKey privateKey, byte[] securityData)throws Exception {
		if (privateKey == null||"".equals(privateKey)) {
//            throw new SecuretException("私钥不能为空, 请检查私钥信息");
			Log.e("BaseSecuret", "私钥不能为空, 请检查私钥信息");
        }
		if(securityData==null){
//			throw new SecuretException("密文不能为空, 请检查密文信息");
			Log.e("BaseSecuret", "密文不能为空, 请检查密文信息");
		}
		Cipher cipher=null;
		byte[] plainTextBytes=null;
		try{
			cipher= Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			for(int i=0;i<securityData.length;i+=EDECRYPT_BLOCK_SIZE){
				byte[] clearTextResult=cipher.doFinal(subArray(securityData, i, i+EDECRYPT_BLOCK_SIZE));
				plainTextBytes=connArray(plainTextBytes, clearTextResult);
			}
			return plainTextBytes;
		}catch (NoSuchAlgorithmException e) {
//			if(log.isErrorEnabled()){
//				log.error(e.getMessage(),e);
//			}else{
				e.printStackTrace();
				throw new Exception("未找到该解密算法");
//			}
        } catch (NoSuchPaddingException e) {
//        	if(log.isErrorEnabled()){
//        		log.error(e.getMessage(),e);
//        	}else{
        		e.printStackTrace();
//        	}
        } catch (InvalidKeyException e) {
//        	if(log.isErrorEnabled()){
//        		log.error(e.getMessage(),e);
//        	}else{
        		e.printStackTrace();
        		throw new Exception("非法私钥,请检查私钥信息");
//        	}
        } catch (IllegalBlockSizeException e) {
//        	if(log.isErrorEnabled()){
//        		log.error(e.getMessage(),e);
//        	}else{
        		e.printStackTrace();
                throw new Exception("密文长度非法，请检查密文数据长度");
//        	}
        } catch (BadPaddingException e) {
//        	if(log.isErrorEnabled()){
//        		log.error(e.getMessage(),e);
//        	}else{
        	    e.printStackTrace();
        		throw new Exception("密文数据已损坏，请检查密文数据信息");
//        	}
        }
		return null;
	}
    
    /**
     * SHA-512消息摘要计算
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encodeSHA512(byte[] data) throws Exception {
    	MessageDigest md= MessageDigest.getInstance("SHA-512");
    	byte[] digestData=md.digest(data);
    	return digestData;
    }

    /**
     * 截取数组，返回beginIndex至stopIndex之间部分
     * @param data
     * @param beginIndex
     * @param stopIndex
     * @return
     */
    public static byte[] subArray(byte[] data,int beginIndex,int stopIndex){
    	byte[] retrunByte=new byte[stopIndex-beginIndex];
    	if(data.length>=retrunByte.length){
    		if((data.length-beginIndex)>retrunByte.length){
    			System.arraycopy(data, beginIndex, retrunByte, 0, retrunByte.length);
    		}else{
    			System.arraycopy(data, beginIndex, retrunByte, 0, data.length - beginIndex);
    		}	
    	}else{
    		System.arraycopy(data, beginIndex, retrunByte, 0, data.length);
    	}   	
    	return retrunByte;
    }
    
    /**
     * 拼接数组，返回拼接后数组
     * @param arr1
     * @param arr2
     */
    public static byte[] connArray(byte[] arr1,byte[] arr2){
    	byte[] retrunByte=null;
    	if(arr1==null&&arr2!=null){
    		retrunByte=arr2;
    	}else if(arr2==null&&arr1!=null){
    		retrunByte=arr1;
    	}else if(arr1!=null&&arr2!=null) {
    		retrunByte=new byte[arr1.length+arr2.length];
        	System.arraycopy(arr1, 0, retrunByte, 0, arr1.length);
        	System.arraycopy(arr2, 0, retrunByte, arr1.length, arr2.length);
    	}
    	return retrunByte;
    }
}
