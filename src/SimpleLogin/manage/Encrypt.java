package SimpleLogin.manage;

import java.security.MessageDigest;

public class Encrypt {
	public static String encryptData(String str) {
		StringBuilder sb = new StringBuilder();
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(str.getBytes());
			byte[] md5encrypt = md5.digest();

			for (int i = 0; i < md5encrypt.length; i++) {
				sb.append(Integer.toHexString((int) md5encrypt[i] & 0xFF));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
