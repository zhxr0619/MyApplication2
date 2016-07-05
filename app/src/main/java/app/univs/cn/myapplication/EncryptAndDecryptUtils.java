package app.univs.cn.myapplication;

import android.content.Context;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

/**
 * Created by zxr on 2015/11/17.
 */
public class EncryptAndDecryptUtils {
    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";
    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "SHA1WithRSA";
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 245;
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 256;
    /**
     * 获取公钥的key
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";
    /**
     * 获取私钥的key
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    static String publicKey;
    static String privateKey;
    private static RSAPublicKey PA_PUBLIC_KEY;
    private static RSAPrivateKey PA_PRIVATE_KEY;//模拟合作商私钥

    static {
        try {
            Map<String, Object> keyMap = genKeyPair();
            publicKey = getPublicKey(keyMap);
            privateKey = getPrivateKey(keyMap);
            System.err.println("公钥: \n\r" + publicKey);
            System.err.println("私钥： \n\r" + privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>
     * 生成密钥对(公钥和私钥)
     * </p>
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Object> genKeyPair() throws Exception {
        Context mContext = HaoBuyInit.getInstance().getmContext();
        //读取公钥和私钥
        InputStream publicIn = mContext.getResources().getAssets().open("gx_SEC_PUBKies.key");
        InputStream privateIn = mContext.getResources().getAssets().open("gx_SEC_PRIKies.pfx");
        //读取合作商公钥
        InputStream paPublicIn = mContext.getResources().getAssets().open("pa_SEC_PUBKies.key");
        //模拟读取合作商私钥
        InputStream paPrivateIn = mContext.getResources().getAssets().open("pa_SEC_PRIKies.pfx");

        if (paPublicIn == null || paPrivateIn == null) {
            Log.e("PartnerPubKeySecurity", "未找到合作商密钥，请检查！");
        }

        PA_PUBLIC_KEY = (RSAPublicKey) BaseSecuret.readKey(paPublicIn);
        PA_PRIVATE_KEY = (RSAPrivateKey) BaseSecuret.readKey(paPrivateIn);
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, PA_PUBLIC_KEY);
        keyMap.put(PRIVATE_KEY, PA_PRIVATE_KEY);
        return keyMap;
    }

    public static void test(String source1) throws Exception {
        System.err.println("公钥加密——私钥解密");
        String source = "这是一行没有任何意义的文字，你看完了等于没看，不是吗？";
        System.out.println("\r加密前文字：\r\n" + source);
        byte[] data = source.getBytes();
        byte[] encodedData = encryptByPublicKey(data, PA_PUBLIC_KEY);
        System.out.println("加密后文字：\r\n" + Base64Utils.encode(encodedData));
        byte[] decodedData = decryptByPrivateKey(encodedData, PA_PRIVATE_KEY);
        String target = new String(decodedData);
        System.out.println("解密后文字: \r\n" + target);


        System.out.println("\r加密前文字：\r\n" + source1);
        byte[] data1 = source1.getBytes();
        byte[] encodedData1 = encryptByPublicKey(data1, PA_PUBLIC_KEY);
        System.out.println("加密后文字：\r\n" + Base64Utils.encode(encodedData1));

        String sign = sign(encodeSHA512(encodedData1), PA_PRIVATE_KEY);
        System.out.println("签名: \r\n" + sign);
        //验签
        boolean isSign = verify(encodeSHA512(encodedData1), PA_PUBLIC_KEY, sign);
        System.out.println("验签：" + isSign);
        byte[] decodedData1 = decryptByPrivateKey(encodedData1, PA_PRIVATE_KEY);
        String target1 = new String(decodedData1);
        System.out.println("解密后文字: \r\n" + target1);
    }

    /**
     * 获取密文、签名
     *
     * @param source
     * @return
     * @throws Exception
     */
    public static Map<String, String> getEncryptAndSign(String source) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        System.out.println("\r加密前文字：\r\n" + source);
        byte[] data1 = source.getBytes();
        byte[] encodedData1 = encryptByPublicKey(data1, PA_PUBLIC_KEY);
        String encrypt = Base64Utils.encode(encodedData1);
        System.out.println("加密后文字：\r\n" + encrypt);
        String sign = sign(encodeSHA512(encodedData1), PA_PRIVATE_KEY);
        System.out.println("签名: \r\n" + sign);
        map.put(HaoConstants.ENCRYPT_FLAG, encrypt);
        map.put(HaoConstants.SIGN_FLAG, sign);
        return map;
    }

    /**
     * 验签
     *
     * @param encrypt
     * @param sign
     * @return
     * @throws Exception
     */
    public static boolean checkSign(String encrypt, String sign) throws Exception {
        byte[] encodedData1 = Base64Utils.decode(encrypt);
        return verify(encodeSHA512(encodedData1), PA_PUBLIC_KEY, sign);
    }

    /**
     * 解密 获取明文
     *
     * @param encrypt
     * @return
     * @throws Exception
     */
    public static String getDecrypt(String encrypt) throws Exception {
        byte[] encodedData1 = Base64Utils.decode(encrypt);
        byte[] decodedData1 = decryptByPrivateKey(encodedData1, PA_PRIVATE_KEY);
        String decrypt = new String(decodedData1);
        System.out.println("解密后文字: \r\n" + decrypt);
        return decrypt;
    }

    /**
     * <p>
     * 公钥加密
     * </p>
     *
     * @param data      源数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, RSAPublicKey publicKey)
            throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /**
     * <P>
     * 私钥解密
     * </p>
     *
     * @param encryptedData 已加密数据
     * @param privateKey    私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, RSAPrivateKey privateKey)
            throws Exception {

        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * <p>
     * 用私钥对信息生成数字签名
     * </p>
     *
     * @param data       已加密数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, RSAPrivateKey privateKey) throws Exception {
//        byte[] keyBytes = Base64Utils.decode(privateKey);

        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(data);
        return Base64Utils.encode(signature.sign());
    }

    /**
     * <p>
     * 校验数字签名
     * </p>
     *
     * @param data      已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @param sign      数字签名
     * @return
     * @throws Exception
     */
    public static boolean verify(byte[] data, RSAPublicKey publicKey, String sign)
            throws Exception {
//        byte[] keyBytes = Base64Utils.decode(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey.getEncoded());
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);
        signature.update(data);
        return signature.verify(Base64Utils.decode(sign));
    }

    /**
     * SHA-512消息摘要计算
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encodeSHA512(byte[] data) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        byte[] digestData = md.digest(data);
        return digestData;
    }

    /**
     * <p>
     * 获取私钥
     * </p>
     *
     * @param keyMap 密钥对
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return Base64Utils.encode(key.getEncoded());
    }

    /**
     * <p>
     * 获取公钥
     * </p>
     *
     * @param keyMap 密钥对
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return Base64Utils.encode(key.getEncoded());
    }
}
