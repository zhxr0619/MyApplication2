package com.by.dalitek.modelhouse.util;

public class MyFunction {
	/**
	 * 字节数组转字符串
	 * */
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * 校验码
	 * @param cmd
	 */
	public static void setCheckSum(byte[] cmd) {
		int checkSum = 0;
		for (int i = 0; i < cmd.length - 1; i++) {
			checkSum = ((checkSum + cmd[i]) & 255);
		}
		checkSum = (256 - checkSum) % 256;
		if (checkSum < 128) {
			cmd[cmd.length - 1] = (byte) checkSum;
		} else {
			cmd[cmd.length - 1] = (byte) (checkSum - 256);
		}
	}

//	//校验函数
//	public static String CheckSum(String str) {
//		// TODO Auto-generated method stub
//		if (str.length() != 14) {
//			return "";
//		}
//		int total = 0;
//		String tempstr = "";
//		int i = 0;
//		for (i = 0; i < 14;) {
//			tempstr = str.substring(i, i + 2);
//			total += (256 - Integer.valueOf(tempstr, 16));
//			// System.out.println(total);
//			i = i + 2;
//		}
//		String check = Integer.toHexString(total % 256);
//		// System.out.println(check);
//		if (check.length() != 2) {
//			check = "0" + check;
//		}
//		return str + check.toUpperCase();
//	}
//

	//校验函数
	public static String CheckSum(String str) {
		// TODO Auto-generated method stub
//			if (str.length() != 14) {
//				return "";
//			}
		int total = 0;
		String tempstr = "";
		int i = 0;
		for (i = 0; i < str.length();) {
			tempstr = str.substring(i, i + 2);
			total += (256 - Integer.valueOf(tempstr, 16));
			// System.out.println(total);
			i = i + 2;
		}
		String check = Integer.toHexString(total % 256);
		// System.out.println(check);
		if (check.length() != 2) {
			check = "0" + check;
		}
		return str + check.toUpperCase();
	}

	/**
	 * 十六进制字符串转换成bytes
	 *
	 * @param hexString
	 * @return
	 */
	public static byte[] HexString2Bytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

//	public static byte[] HexString2Bytes(String src) {
//
//		if (src == null || src.equals("")) {
//			return null;
//		}
//		byte[] ret = new byte[8];
//		byte[] tmp = src.getBytes();
//		for (int i = 0; i < 8; i++) {
//			ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
//		}
//		return ret;
//	}
//	private static byte uniteBytes(byte src0, byte src1) {
//
//		byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 }))
//				.byteValue();
//		_b0 = (byte) (_b0 << 4);
//		byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 }))
//				.byteValue();
//		byte ret = (byte) (_b0 ^ _b1);
//		return ret;
//	}

	// 亮度转换为01-255
	public static int HexToDec(int a) {
		int Dec;
		Dec = (255 * 100 - (254 * a)) / 100;
		return Dec;

	}

	// 值转亮度
	public static int DecToHex(int a) {
		int Hex;
		Hex = (255 - a) * 100 / 254;
		return Hex;
	}

}
