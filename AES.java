
package com.cronberry.utility;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//import com.alcodes.cronberry.app.constants.UIConstants;
//import com.amazonaws.services.ec2.AmazonEC2Async;
//import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
//import com.amazonaws.services.ec2.model.Instance;
//import com.amazonaws.services.ec2.model.InstanceStateName;
//import com.amazonaws.services.ec2.model.Reservation;
import com.cronberry.service.constants.UIConstants;
public class AES {

	private static SecretKeySpec secretKey;
	private static byte[] key;

	private static Logger logger = LogManager.getLogger(AES.class);

	public static void setKey(String myKey) {
		MessageDigest sha = null;
		try {
			key = myKey.getBytes("UTF-8");
			sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16);
			secretKey = new SecretKeySpec(key, "AES");
		} catch (NoSuchAlgorithmException e) {
			logger.error(UIConstants.EXCEPTION + UIConstants.COLON, e);
		} catch (UnsupportedEncodingException e) {
			logger.error(UIConstants.EXCEPTION + UIConstants.COLON, e);
		}
	}

	public static String encrypt(String strToEncrypt, String secret) {
		try {
			setKey(secret);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
		} catch (Exception e) {
			logger.error(UIConstants.EXCEPTION + UIConstants.COLON, e);
		}
		return null;
	}

	public static String decrypt(String strToDecrypt, String secret) {
		try {
			setKey(secret);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
		} catch (Exception e) {
			logger.error(UIConstants.EXCEPTION + UIConstants.COLON, e);
		}
		return null;
	}

	public static String encryptUserId(long userId) {

		try {
			if (userId == 0) {
				return null;
			}

			String value = String.valueOf(userId);

			String key = UIConstants.ENCRYPT_KEY;
			String initVector = UIConstants.ENCRYPT_VECTOR;
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(UIConstants.UTF_8));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(UIConstants.UTF_8), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

			value = Base64.getEncoder().encodeToString(cipher.doFinal(value.getBytes("UTF-8")));
			return value.replace(UIConstants.SLASH, UIConstants.AMPERSENT);
		} catch (IOException | NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException
				| InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
			logger.error(UIConstants.EXCEPTION + UIConstants.COLON, e);
		}
		return null;
	}

	public static long decryptUserId(String encrypted) {
		try {
			if (encrypted == null || encrypted.isEmpty() || encrypted.equals("0")) {
				return 0;
			}
			try {
				encrypted = encrypted.replace(UIConstants.AMPERSENT, UIConstants.SLASH);
				String key = UIConstants.ENCRYPT_KEY;
				String initVector = UIConstants.ENCRYPT_VECTOR;
				IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(UIConstants.UTF_8));
				SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(UIConstants.UTF_8), "AES");

				Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
				cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

				byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));

				return Long.valueOf(new String(original));
			} catch (IOException | NoSuchPaddingException | NoSuchAlgorithmException
					| InvalidAlgorithmParameterException | InvalidKeyException | BadPaddingException
					| IllegalBlockSizeException e) {
				throw new RuntimeException(e);
			}
		} catch (Exception e) {
			logger.error(UIConstants.EXCEPTION + UIConstants.COLON + e.getMessage());
		}
		return 0;
	}

//	public static String isUrl(String url) {
//		URLValidator urlValidator = new URLValidator();
//		if (urlValidator.isValid(url, null)) {
//			return url;
//		} else {
//			return "http://google.com/404";
//		}
//
//	}

	public static String encryptPasswordMD5(String password) {
		try {
			return DatatypeConverter
					.printHexBinary(MessageDigest.getInstance("MD5").digest(password.trim().getBytes()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return password;
	}

	// public static void main(String[] args)
	// throws EncryptedDocumentException, InvalidFormatException, IOException,
	// InterruptedException {
	//

	// System.out.println(decryptUserId("ENO4SwxEUwHuBiDln9IKkg=="));
	//
	// // String msg="Welcome to cronberry 7775072822";
	// // String proxyIp="";
	// // String serverId="i-0d87ec742ab590084";
	// //
	// // System.out.println(encryptPasswordMD5(msg+proxyIp+serverId+10));
	//
	// String str = null;
	// // System.out.println(str.length());
	// if (!StringUtils.isEmpty(str) && str.length() > 3500) {
	// str = str.substring(0, 3500);
	// }
	// str = StringEscapeUtils.escapeEcmaScript(str);
	// System.out.println(str);

	//
	// // BasicAWSCredentials awsCredentials = new
	// // BasicAWSCredentials("AKIAS5HC54QYWMMLHD7D",
	// // "gCOKKA31C/nQyhTAG6deznnUXwuFjQg8klda1fBo");
	// // AWSStaticCredentialsProvider provider = new
	// // AWSStaticCredentialsProvider(awsCredentials);
	// // AmazonEC2Async amazonEC2 =
	// // AmazonEC2AsyncClientBuilder.standard().withCredentials(provider)
	// // .withRegion(Regions.AP_SOUTH_1).build();
	// //
	// // StartInstancesRequest startInstancesRequest = new StartInstancesRequest()
	// // .withInstanceIds("i-00d14d3927f9431b8");
	// // StartInstancesResult instancesResult =
	// // amazonEC2.startInstances(startInstancesRequest);
	// //
	// // String ip = null;
	// // ip = getServerIp(amazonEC2, ip, 0);
	// //
	// // System.out.println("instance ip= " + ip);
	//
	// }

//	private static String getServerIp(AmazonEC2Async amazonEC2, String ip, int i) throws InterruptedException {
//		Thread.sleep(30000);
//		DescribeInstancesRequest describeInstancesRequest = new DescribeInstancesRequest()
//				.withInstanceIds("i-00d14d3927f9431b8");
//
//		for (reservation reservation : amazonEC2.describeInstances(describeInstancesRequest).getReservations()) {
//			for (Instance instance : reservation.getInstances()) {
//				if (instance.getState().getName().equals(InstanceStateName.Running.toString())) {
//					ip = instance.getPublicIpAddress();
//
//					break;
//				}
//			}
//		}
//
//		if (ip == null && i < 3) {
//			ip = getServerIp(amazonEC2, ip, 0);
//		}
//		return ip;
//	}
//
	public static String encodeValue(String value) {

		return value.replace(UIConstants.SLASH, UIConstants.AMPERSENT);

	}

	public static String decodeValue(String value) {
		return value.replace(UIConstants.AMPERSENT, UIConstants.SLASH);
	}
}