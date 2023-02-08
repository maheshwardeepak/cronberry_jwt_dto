package com.cronberry.utility;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.attribute.PosixFilePermission;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;

import com.cronberry.service.constants.CronberryEnums.OperatorEnum;
import com.cronberry.service.constants.UIConstants;


public class UtilityData {



	private static Logger logger = LogManager.getLogger(UtilityData.class);

	public static String generateOTP() {
		Random r = new Random();
		int otp = r.nextInt((UIConstants.OTPFOURTIMENINE - UIConstants.OTPFOURTIMEONE) + UIConstants.ONE)
				+ UIConstants.OTPFOURTIMEONE;
		return Integer.toString(otp);
	}

	public static Calendar getInstance() {
		return Calendar.getInstance();
	}
	
	public static boolean compareOperators(OperatorEnum enumValue, String val) {
        return enumValue.toString().equalsIgnoreCase(val);
    }
	
	public static Integer convertToInt(String val) {
        return Integer.valueOf(val);
    }

	@Async
	public static void sendMultipleMailsFromCronberry(JavaMailSender javaMailSender, String email[], String subject,
			String body) {
		try {
			MimeMessageHelper helper;
			MimeMessage message = javaMailSender.createMimeMessage();
			helper = new MimeMessageHelper(message);

			helper.setFrom(UIConstants.EMAIL_FROM_ADDRESS);
			helper.setSubject(subject);
			helper.setText(body, true);
			helper.setTo(email);
			javaMailSender.send(message);
		} catch (Exception e) {
			logger.error(UIConstants.EXCEPTION + UIConstants.COLON, e);
		}
	}

	@Async
	public static void sendForgotPasswordMailFromCronberry(JavaMailSender javaMailSender, String email, String subject,
			String logo, String linkUrl, String firstName) {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper;
		helper = new MimeMessageHelper(message);
		try {
			InternetAddress internetAddress = new InternetAddress("noreply@cronberry.com",
					"cronberry");
			helper.setFrom(internetAddress);
			helper.setTo(email);
			helper.setSubject(subject);	
			helper.setText("link for forgot password "+firstName+ "  : "+linkUrl);
//			VelocityEngine ve = new VelocityEngine();
//			ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
//			ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
//			ve.init();
//			Template template = ve.getTemplate(UIConstants.FORGOT_PASSWORD_EMAIL_TEMPLATE_PATH);
//			VelocityContext velocityContext = new VelocityContext();
//			velocityContext.put("logoUrl", logo);
//			velocityContext.put("linkUrl", linkUrl);
//			velocityContext.put("firstName", firstName);
//			StringWriter stringWriter = new StringWriter();
//			template.merge(velocityContext, stringWriter);
//			helper.setText(stringWriter.toString(), true);
			javaMailSender.send(message);
		} catch (Exception e) {
			logger.error(UIConstants.EXCEPTION + UIConstants.COLON, e);
		}
	}
	
//	@Value("${emailFromAddress}")
//	public  String emailFromAddress ;
//	
//	@Value("${emailFromAlias}")
//	public  String emailFromAlias ;
	

	@Async
	public static void sendOTPMailFromCronberry(JavaMailSender javaMailSender, String email, String subject, String otp,
			String logo) {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper;
		helper = new MimeMessageHelper(message);
		try {
			InternetAddress internetAddress = new InternetAddress("noreply@cronberry.com",
					"cronberry");
			helper.setFrom(internetAddress);
			helper.setTo(email);
			helper.setSubject(subject);	
			helper.setText("hello otp for varification "+otp);
			

//			VelocityEngine ve = new VelocityEngine();
//			ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
//			ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
//			ve.init();
//			org.apache.velocity.Template template = ve.getTemplate("/templates/otp-email-template.vm");
//			VelocityContext velocityContext = new VelocityContext();
//			velocityContext.put("otp", otp);
//			velocityContext.put("logoUrl", logo);
//			StringWriter stringWriter = new StringWriter();
//			template.merge(velocityContext, stringWriter);
//			helper.setText(stringWriter.toString(), true);
			javaMailSender.send(message);
		} catch (Exception e) {
			logger.error(UIConstants.EXCEPTION + UIConstants.COLON, e);
		}
	}

//	@Async
//	public static void sendPlanExpireMailFromCronberry(JavaMailSender javaMailSender, String email, String subject,
//			String firstName, String tmpPath) {
//		MimeMessage message = javaMailSender.createMimeMessage();
//		MimeMessageHelper helper;
//		helper = new MimeMessageHelper(message);
//		try {
//			InternetAddress internetAddress = new InternetAddress(UIConstants.EMAIL_FROM_ADDRESS,
//					UIConstants.EMAIL_FROM_ALIAS);
//			helper.setFrom(internetAddress);
//			helper.setTo(email);
//			helper.setSubject(subject);
//			VelocityEngine ve = new VelocityEngine();
//			ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
//			ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
//			ve.init();
//			Template template = ve.getTemplate(tmpPath);
//			VelocityContext velocityContext = new VelocityContext();
//			velocityContext.put("name", firstName);
//			StringWriter stringWriter = new StringWriter();
//			template.merge(velocityContext, stringWriter);
//			helper.setText(stringWriter.toString(), true);
//			javaMailSender.send(message);
//		} catch (MessagingException | UnsupportedEncodingException e) {
//			logger.error(UIConstants.EXCEPTION + UIConstants.COLON, e);
//		}
//	}

//	@Async
//	public static void sendOTPMailFromCronberry(JavaMailSender javaMailSender, String email, String subject, String otp,
//			String logo) {
//		MimeMessage message = javaMailSender.createMimeMessage();
//		MimeMessageHelper helper;
//		helper = new MimeMessageHelper(message);
//		try {
//			InternetAddress internetAddress = new InternetAddress(UIConstants.EMAIL_FROM_ADDRESS,
//					UIConstants.EMAIL_FROM_ALIAS);
//			helper.setFrom(internetAddress);
//			helper.setTo(email);
//			helper.setSubject(subject);
//			VelocityEngine ve = new VelocityEngine();
//			ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
//			ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
//			ve.init();
//			Template template = ve.getTemplate(UIConstants.OTP_EMAIL_TEMPLATE_PATH);
//			VelocityContext velocityContext = new VelocityContext();
//			velocityContext.put("otp", otp);
//			velocityContext.put("logoUrl", logo);
//			StringWriter stringWriter = new StringWriter();
//			template.merge(velocityContext, stringWriter);
//			helper.setText(stringWriter.toString(), true);
//			javaMailSender.send(message);
//		} catch (MessagingException | UnsupportedEncodingException e) {
//			logger.error(UIConstants.EXCEPTION + UIConstants.COLON, e);
//		}
//	}
//
//	public static char[] generatePassword(int length) throws CronberryException {
//		String values = UIConstants.CAPITAL_CHARS + UIConstants.SMALL_CHARS + UIConstants.NUMBERS;
//		Random random = new Random();
//		char[] password = new char[length];
//		for (int i = 0; i < length; i++) {
//			password[i] = values.charAt(random.nextInt(values.length()));
//		}
//		return password;
//	}
//
//	public static BCryptPasswordEncoder getBcryptPasswordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//
//	@SuppressWarnings("deprecation")
//	public static Date getStartDate() {
//		Date date = new Date();
//		Date newDate = (Date) date.clone();
//		newDate.setHours(0);
//		newDate.setMinutes(0);
//		newDate.setSeconds(0);
//		newDate.setTime(newDate.getTime() - newDate.getTime() % 1000);
//		return newDate;
//	}
//
//	public static Date getPreviousDate() {
//		Date date = new Date();
//		Date dateBefore = new Date(date.getTime() - 1 * 24 * 3600 * 1000l);
//		return dateBefore;
//	}
//
//	public static Date getYearlyDate() {
//		Calendar calendar = UtilityData.getInstance();
//		calendar.setTime(UtilityData.getStartDate());
//		calendar.add(Calendar.YEAR, 1);
//		Date date = calendar.getTime();
//		return date;
//	}
//
//	public static Date getMonthlyDate() {
//		Calendar calendar = UtilityData.getInstance();
//		calendar.setTime(UtilityData.getStartDate());
//		calendar.add(Calendar.MONTH, 1);
//		Date date = calendar.getTime();
//		return date;
//	}
//
//	public static void isFolderExist(String path) {
//		File file = new File(path);
//		file.mkdirs();
//		Path pathD = Paths.get(path);
//		Set<PosixFilePermission> perms = UtilityData.FilePermission();
//		try {
//			Files.setPosixFilePermissions(pathD, perms);
//		} catch (IOException e) {
//			logger.error(UIConstants.EXCEPTION + UIConstants.COLON, e);
//		}
//	}
//
//	public static byte[] decodeImage(String img) {
//		return Base64.decodeBase64(img);
//	}
//
//	public static String javaMailSender(String emailUser, String emailPassword, String emailServerPort,
//			String emailServerIp, String email, String subject, String body, String emailFromAddress,
//			String emailFromAlias) {
//		String response = UIConstants.ENPTY;
//		JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
//		javaMailSenderImpl.setHost(emailServerIp);
//		javaMailSenderImpl.setPort(Integer.parseInt(emailServerPort));
//		javaMailSenderImpl.setUsername(emailUser);
//		javaMailSenderImpl.setPassword(emailPassword);
//		setMailSenderProperties(javaMailSenderImpl);
//		response = sendTestEmail(javaMailSenderImpl, email, subject, body, emailFromAddress, emailFromAlias);
//		return response;
//	}
//
//	@Async
//	private static String sendTestEmail(JavaMailSender javaMailSender, String email, String subject, String body,
//			String emailFromAddress, String emailFromAlias) {
//		String response = UIConstants.ENPTY;
//		try {
//			InternetAddress internetAddress = new InternetAddress(emailFromAddress, emailFromAlias);
//			MimeMessage message = javaMailSender.createMimeMessage();
//			MimeMessageHelper helper = new MimeMessageHelper(message, true);
//			helper.setSubject(subject);
//			helper.setText(body);
//			helper.setTo(email);
//			helper.setFrom(internetAddress);
//			javaMailSender.send(message);
//			response = UIConstants.SUCCESS;
//		} catch (Exception e) {
//			e.printStackTrace();
//			response = UIConstants.EMAIL_SENDING_FAILED;
//		}
//		return response;
//	}
//
//	private static void setMailSenderProperties(JavaMailSenderImpl javaMailSenderImpl) {
//		Properties props = javaMailSenderImpl.getJavaMailProperties();
//		props.put("mail.transport.protocol", "smtp");
//		props.put("mail.smtp.auth", true);
//		props.put("mail.smtp.starttls.enable", true);
//		props.put("mail.debug", true);
//	}
//
//	public static String sendMobilePushNotification(String fcmKey, String fcmSenderid, String deviceId, String message,
//			String title, String deepLink, String notificationImage, String notificationIcon) throws IOException {
//		String response = UIConstants.ENPTY;
//		try {
//			URL url = new URL(fcmSenderid);
//			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//			conn.setUseCaches(false);
//			conn.setDoInput(true);
//			conn.setDoOutput(true);
//			conn.setRequestMethod(UIConstants.POST_REQUEST);
//			conn.setRequestProperty(UIConstants.AUTHORIZATION, UIConstants.KEY_EQ + fcmKey);
//			conn.setRequestProperty(UIConstants.CONTENT_TYPE_FORM, UIConstants.CONTENT_TYPE_JSON);
//			JSONObject json = new JSONObject();
//			json.put(UIConstants.TO, deviceId.trim());
//			JSONObject info = new JSONObject();
//			info.put(UIConstants.TITLE, title);
//			info.put(UIConstants.BODY, message);
//			info.put(UIConstants.ICON, notificationIcon);
//			info.put(UIConstants.IMAGE, notificationImage);
//			info.put(UIConstants.DEEP_LINK, deepLink);
//			info.put(UIConstants.CLICK_ACTION, "redirect");
//			info.put(UIConstants.SOUND, UIConstants.DEFAULT);
//			json.put(UIConstants.NOTIFICATION, info);
//
//			JSONPObject data = new JSONObject();
//			data.put(UIConstants.ANDROID_CHANNEL_ID, "");
//			data.put(UIConstants.TITLE, title);
//			data.put(UIConstants.MESSAGE, message);
//			data.put(UIConstants.ICON, notificationIcon);
//			data.put(UIConstants.IMAGE, notificationImage);
//			data.put(UIConstants.DEEP_LINK, deepLink);
//			info.put(UIConstants.CLICK_ACTION, "redirect");
//			data.put(UIConstants.SOUND, UIConstants.DEFAULT);
//			json.put(UIConstants.DATA, data);
//			logger.debug("TEST===" + json.toString());
//			try {
//				OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
//				wr.write(json.toString());
//				wr.flush();
//				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
//				String output = br.readLine();
//				JSONObject jsonObject = new JSONObject(output);
//				Integer result = jsonObject.getInt(UIConstants.SUCCESSFULL);
//				if (result == UIConstants.INT_ONE) {
//					JSONArray jsonResult = jsonObject.getJSONArray("results");
//					JSONObject objects = jsonResult.getJSONObject(0);
//					String messageId = objects.getString("message_id");
//					logger.debug(messageId);
//					response = UIConstants.SUCCESS;
//				} else {
//					JSONArray jsonResult = jsonObject.getJSONArray("results");
//					JSONObject objects = jsonResult.getJSONObject(0);
//					String error = objects.getString("error");
//					logger.debug(error);
//					response = UIConstants.NOTIFICATION_FAILED;
//				}
//			} catch (Exception e) {
//				logger.error(UIConstants.EXCEPTION + UIConstants.COLON, e);
//				response = UIConstants.NOTIFICATION_FAILED;
//			}
//		} catch (Exception e) {
//			logger.error(UIConstants.EXCEPTION + UIConstants.COLON, e);
//			response = UIConstants.NOTIFICATION_FAILED;
//		}
//		return response;
//	}
//
//	public static String sendWebPushNotification(String fcmKey, String fcmSenderid, String fcmToken, String message,
//			String title, String deepLink, String notificationImage, String notificationIcon) throws IOException {
//		String response = UIConstants.ENPTY;
//		try {
//			URL url = new URL(fcmSenderid);
//			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//			conn.setUseCaches(false);
//			conn.setDoInput(true);
//			conn.setDoOutput(true);
//			conn.setRequestMethod(UIConstants.POST_REQUEST);
//			conn.setRequestProperty(UIConstants.AUTHORIZATION, UIConstants.KEY_EQ + fcmKey);
//			conn.setRequestProperty(UIConstants.CONTENT_TYPE_FORM, UIConstants.CONTENT_TYPE_JSON);
//			JSONObject json = new JSONObject();
//			json.put(UIConstants.TO, fcmToken.trim());
//			JSONObject info = new JSONObject();
//			info.put(UIConstants.TITLE, title);
//			info.put(UIConstants.BODY, message);
//			info.put(UIConstants.ICON, notificationIcon);
//			info.put(UIConstants.IMAGE, notificationImage);
//			info.put(UIConstants.CLICK_ACTION, deepLink);
//			info.put(UIConstants.SOUND, UIConstants.DEFAULT);
//			json.put(UIConstants.NOTIFICATION, info);
//			try {
//				OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
//				wr.write(json.toString());
//				wr.flush();
//				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
//				String output = br.readLine();
//				JSONObject jsonObject = new JSONObject(output);
//				Integer result = jsonObject.getInt(UIConstants.SUCCESSFULL);
//				if (result == UIConstants.INT_ONE) {
//					response = UIConstants.SUCCESS;
//				} else {
//					response = UIConstants.NOTIFICATION_FAILED;
//				}
//			} catch (Exception e) {
//				logger.error(UIConstants.EXCEPTION + UIConstants.COLON, e);
//				response = UIConstants.NOTIFICATION_FAILED;
//			}
//		} catch (Exception e) {
//			logger.error(UIConstants.EXCEPTION + UIConstants.COLON, e);
//			response = UIConstants.NOTIFICATION_FAILED;
//		}
//		return response;
//	}
//
//	public static void sendMail(String to[], String subject, String body, String username, String password, String host,
//			String port) {
//
//		JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
//		javaMailSenderImpl.setHost(host);
//		javaMailSenderImpl.setPort(Integer.parseInt(port));
//		javaMailSenderImpl.setUsername(username);
//		javaMailSenderImpl.setPassword(password);
//		setMailSenderProperties(javaMailSenderImpl);
//		sendMultipleMailsFromCronberry(javaMailSenderImpl, to, subject, body);
//	}
//
//	public static String simpleDateFormat() {
//		Date date = new Date();
//		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
//		return formatter.format(date);
//	}
//
//	public static SimpleDateFormat getSimpleDateFormat() {
//		return new SimpleDateFormat(UIConstants.DATE_FORMAT);
//	}
//
//	@Async
//	public static void sendCustomerEnquiryEmail(String name, String email, String phoneNo, String companyName,
//			String message, String city, int leadCount, String emailFromAddress, String password, String emailToAddress,
//			String logo) {
//		JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
//		javaMailSenderImpl.setHost("smtp.gmail.com");
//		javaMailSenderImpl.setPort(587);
//		javaMailSenderImpl.setUsername(emailFromAddress);
//		javaMailSenderImpl.setPassword(password);
//		setMailSenderProperties(javaMailSenderImpl);
//		sendCustomerEnquiryMail(javaMailSenderImpl, name, email, phoneNo, companyName, message, city, leadCount,
//				emailFromAddress, emailToAddress);
//		sendCustomerEnquiryMailToUser(javaMailSenderImpl, name, email, emailFromAddress, logo);
//	}
//
//	private static void sendCustomerEnquiryMailToUser(JavaMailSenderImpl javaMailSender, String name, String email,
//			String emailFromAddress, String logo) {
//		MimeMessage message = javaMailSender.createMimeMessage();
//		MimeMessageHelper helper;
//		helper = new MimeMessageHelper(message);
//		try {
//			InternetAddress internetAddress = new InternetAddress(emailFromAddress);
//			helper.setTo(email);
//			helper.setFrom(internetAddress);
//			helper.setSubject(UIConstants.AUTO_REPLY_ENQUIRY_EMAIL_SUBJECT);
//			VelocityEngine ve = new VelocityEngine();
//			ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
//			ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
//			ve.init();
//			Template template = ve.getTemplate(UIConstants.AUTO_REPLY_ENQUIRY_EMAIL_TEMPLATE_PATH);
//			VelocityContext velocityContext = new VelocityContext();
//			velocityContext.put("logoUrl", logo);
//			velocityContext.put("name", name);
//			StringWriter stringWriter = new StringWriter();
//			template.merge(velocityContext, stringWriter);
//			helper.setText(stringWriter.toString(), true);
//			javaMailSender.send(message);
//		} catch (MessagingException e) {
//			logger.error(UIConstants.EXCEPTION + UIConstants.COLON, e);
//		}
//
//	}
//
//	private static void sendCustomerEnquiryMail(JavaMailSender javaMailSender, String name, String email,
//			String phoneNo, String companyName, String message, String city, int leadCount, String emailFromAddress,
//			String emailToAddress) {
//		try {
//			InternetAddress internetAddress = new InternetAddress(emailFromAddress);
//			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
//			helper.setText("name :" + name + "\n" + "phoneNo :" + phoneNo + "\n" + "companyName :" + companyName + "\n"
//					+ "email :" + email + "\n" + "city :" + city + "\n" + "leadCount :" + leadCount + "\n" + "message ;"
//					+ message);
//			helper.setTo(emailToAddress);
//			helper.setFrom(internetAddress);
//			javaMailSender.send(mimeMessage);
//		} catch (Exception e) {
//			logger.error(UIConstants.EXCEPTION + UIConstants.COLON, e);
//		}
//	}
//
//	public static String sendEmail(String emailUser, String emailPassword, String emailServerPort, String emailServerIp,
//			String email, String subject, String message, String emailFromAddress, String emailFromAlias) {
//		String response = UIConstants.ENPTY;
//		Properties properties = setMailProperties(emailServerPort);
//		Session mailSession = Session.getDefaultInstance(properties);
//		MimeMessage mimeMessage = new MimeMessage(mailSession);
//		try {
//			mimeMessage.setFrom(new InternetAddress(emailFromAddress));
//			mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
//			mimeMessage.setSubject(subject);
//			mimeMessage.setContent(message, "text/html");
//			Transport transport = mailSession.getTransport();
//			transport.connect(emailServerIp, emailUser, emailPassword);
//			transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
//			response = UIConstants.SUCCESS;
//			transport.close();
//		} catch (Exception exception) {
//			logger.error(UIConstants.EXCEPTION + UIConstants.COLON, exception);
//			response = UIConstants.FAILED;
//		}
//		return response;
//	}
//
//	private static Properties setMailProperties(String emailServerPort) {
//		Properties props = System.getProperties();
//		props.put("mail.transport.protocol", "smtp");
//		props.put("mail.smtp.port", emailServerPort);
//		props.put("mail.smtp.starttls.enable", "true");
//		props.put("mail.smtp.auth", "true");
//		return props;
//	}

	// public static String sendEmailViaSendGridEmailViaWebApi(String sendgridApi,
	// String emailFromAddress, String email,
	// String subject, String message) {
	// String result = UIConstants.ENPTY;
	// SendGrid sendgrid = new SendGrid(sendgridApi);
	// Email emailObj = new Email();
	// emailObj.addTo(email);
	// emailObj.setFrom(emailFromAddress);
	// emailObj.setSubject(subject);
	// emailObj.setText(message);
	// try {
	// Response response = sendgrid.send(emailObj);
	// if (response.getCode() == UIConstants.INT_TWO_HUNDRED) {
	// result = UIConstants.SUCCESS;
	// } else {
	// result = UIConstants.FAILED;
	// }
	// } catch (Exception exception) {
	// exception.printStackTrace();
	// result = UIConstants.FAILED;
	// }
	// return result;
	// }

	public static String getstartTime() {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();
		String startTime = dateTimeFormatter.format(now);
		return startTime + UIConstants.ENPTY + UIConstants.HHMMSS;
	}

	public static String getendTime() {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:00");
		LocalDateTime now = LocalDateTime.now();
		String endTime = dateTimeFormatter.format(now);
		return endTime;
	}

	public static String fetchSegmentId(Long segId) {
		String segNameInitial = "SEG-";
		segNameInitial = segNameInitial + segId;
		return segNameInitial;
	}

	public static double roundDouble(double d, int places) {

		BigDecimal bigDecimal = new BigDecimal(Double.toString(d));
		bigDecimal = bigDecimal.setScale(places, RoundingMode.HALF_UP);
		return bigDecimal.doubleValue();
	}

	public static String generateEncodedVal(String value) {
		long now = Instant.now().toEpochMilli();
		String keyNameWithTime = value + now;
		String encodedVal = java.util.Base64.getEncoder().encodeToString(keyNameWithTime.getBytes());
		return encodedVal;
	}

	public static String getClientIp(HttpServletRequest request) {
		String remoteAddr = "";
		if (request != null) {
			remoteAddr = request.getHeader("x-forwarded-for");
			if (remoteAddr == null || "".equals(remoteAddr)) {
				remoteAddr = request.getRemoteAddr();
			}
		}
		return remoteAddr;
	}

	public static boolean validateJavaDate(String strDate) {
		if (strDate.trim().equals("")) {
			return false;
		} else {
			SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdfrmt.setLenient(false);
			try {
				Date javaDate = sdfrmt.parse(strDate);
			} catch (ParseException e) {
				return false;
			}
			return true;
		}
	}

	public static int diffrenceBetweenDates(String fromDate, String toDate) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date startDate = sdf.parse(fromDate);
		Date endDate = sdf.parse(toDate);

		// DecimalFormat crunchifyFormatter = new DecimalFormat("###,###");

		// getTime() returns the number of milliseconds since January 1, 1970, 00:00:00
		// GMT represented by this Date object
		long diff = endDate.getTime() - startDate.getTime();

		int diffDays = (int) (diff / (24 * 60 * 60 * 1000));

		return diffDays;
		// System.out.println("difference between days: " + diffDays);
		//
		// int diffhours = (int) (diff / (60 * 60 * 1000));
		// System.out.println("difference between hours: " +
		// crunchifyFormatter.format(diffhours));
		//
		// int diffmin = (int) (diff / (60 * 1000));
		// System.out.println("difference between minutues: " +
		// crunchifyFormatter.format(diffmin));
		//
		// int diffsec = (int) (diff / (1000));
		// System.out.println("difference between seconds: " +
		// crunchifyFormatter.format(diffsec));
		//
		// System.out.println("difference between milliseconds: " +
		// crunchifyFormatter.format(diff));

	}

	public static String getPrintStackTrace(Exception ex) {
		StackTraceElement[] stack = ex.getStackTrace();
		StringBuilder _builder = new StringBuilder(ex.toString() + ex.getMessage() + "\n");
		for (StackTraceElement _stackTrace : stack) {
			_builder.append("\tat " + _stackTrace + "\n");
		}
		return _builder.toString();
	}

	public static String convertDate(Date fromDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(fromDate);
	}

	public static String parseDate(String inputDate) {

		String outputDate = null;
		String[] possibleDateFormats = { "yyyy.MM.dd G 'at' HH:mm:ss z", "EEE, MMM d, ''yy", "h:mm a",
				"hh 'o''clock' a, zzzz", "K:mm a, z", "yyyyy.MMMMM.dd GGG hh:mm aaa", "EEE, d MMM yyyy HH:mm:ss Z",
				"yyMMddHHmmssZ", "yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", "YYYY-'W'ww-u",
				"EEE, dd MMM yyyy HH:mm:ss z", "EEE, dd MMM yyyy HH:mm zzzz", "yyyy-MM-dd'T'HH:mm:ssZ",
				"yyyy-MM-dd'T'HH:mm:ss.SSSzzzz", "yyyy-MM-dd'T'HH:mm:sszzzz", "yyyy-MM-dd'T'HH:mm:ss z",
				"yyyy-MM-dd'T'HH:mm:ssz", "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd'T'HHmmss.SSSz", "yyyy-MM-dd", "yyyyMMdd",
				"dd/MM/yy", "dd/MM/yyyy", "dd-MMM-yyyy", "dd/MMM/yyyy", "dd MMM yyyy", "dd MMMMM yyyy", "dd MMM, yyyy",
				"dd MMMMM, yyyy", "dd MMM yyyy HH:mm:ss", "dd MMMMM yyyy HH:mm:ss", "dd MMM, yyyy HH:mm:ss",
				"dd MMMMM, yyyy HH:mm:ss", "yyyy-MM-dd HH:mm:ss", "dd-MM-yyyy HH:mm:ss", "dd-MMM-yyyy HH:mm:ss",
				"dd-MMMMM-yyyy HH:mm:ss", "dd-MMMMM-yyyy hh:mm:ss a", "dd/MMM/yyyy HH:mm:ss", "yyyy/MM/dd HH:mm:ss",
				"yyyy.MM.dd HH:mm:ss", "yyyy/MMMMM/dd HH:mm:ss", "yyyy-MMMMM-dd HH:mm:ss", "MMM/dd/yyyy HH:mm:ss",
				"MMMMM-dd-yyyy HH:mm:ss", "dd-MMMMM-yyyy hh:mm a", "HH:mm", "dd/MM/yyyy HH:mm",
				"dd/MMMMM/yyyy hh:mm:ss a", "yyyy-MM-dd, HH:mm:ss", "yyyy-MM-dd, hh:mm:ss" };

		try {

			outputDate = convertDate(DateUtils.parseDateStrictly(inputDate, possibleDateFormats));

		} catch (ParseException e) {
			logger.error(UIConstants.EXCEPTION + UIConstants.COLON, e);
			outputDate = null;
		}

		return outputDate;

	}

	public static Set<PosixFilePermission> FilePermission() {
		Set<PosixFilePermission> perms = new HashSet<PosixFilePermission>();
		perms.add(PosixFilePermission.OWNER_READ);
		perms.add(PosixFilePermission.OWNER_WRITE);
		perms.add(PosixFilePermission.OWNER_EXECUTE);
		perms.add(PosixFilePermission.GROUP_READ);
		perms.add(PosixFilePermission.GROUP_WRITE);
		perms.add(PosixFilePermission.GROUP_EXECUTE);
		perms.add(PosixFilePermission.OTHERS_READ);
		perms.add(PosixFilePermission.OTHERS_WRITE);
		perms.add(PosixFilePermission.OTHERS_EXECUTE);
		return perms;
	}

//	public static String convertStreamToString(String url, String data, HttpResponse response, Logger logger)
//			throws IOException {
//
//		if (logger != null) {
//			logger.info(UIConstants.SENDING_REQUEST_URL + url);
//			logger.info(UIConstants.PARAMETERS + data);
//			logger.info(UIConstants.RESPONSE_CODE + response.getStatusLine().getStatusCode());
//		}
//
//		org.apache.http.HttpEntity entity = response.getEntity();
//
//		if (entity == null) {
//			return null;
//		}
//		// A Simple JSON Response Read
//		InputStream instream = null;
//		StringBuilder sb = null;
//		try {
//			instream = entity.getContent();
//			BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
//			sb = new StringBuilder();
//			String line = null;
//
//			while ((line = reader.readLine()) != null) {
//				sb.append(line + "\n");
//			}
//		} catch (IOException e) {
//			logger.error(UIConstants.EXCEPTION + UIConstants.COLON, e);
//		} finally {
//			instream.close();
//		}
//		return sb.toString();
//
//	}
//
	public static boolean isValidEmailAddress(String email) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			result = false;
		}
		return result;
	}

//	public static String sendWpPushNotification(WpPushLogDTO wpPushObj, String body) throws IOException {
//
//		String response = UIConstants.ENPTY;
//		try {
//			URL url = new URL(
//					"https://" + wpPushObj.getWpPushConfiguration().getInstanceId() + ".cronberry.com/sendMessage");
//			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//			conn.setUseCaches(false);
//			conn.setDoInput(true);
//			conn.setDoOutput(true);
//			conn.setConnectTimeout(60000);
//			conn.setRequestMethod(UIConstants.POST_REQUEST);
//
//			conn.setRequestProperty(UIConstants.USER_ID,
//					AES.encryptUserId(wpPushObj.getWpPushConfiguration().getUserId()));
//			conn.setRequestProperty(UIConstants.PROJECT_ID,
//					AES.encryptUserId(wpPushObj.getWpPushConfiguration().getProjectId()));
//			conn.setRequestProperty(UIConstants.CONTENT_TYPE_FORM, UIConstants.CONTENT_TYPE_JSON);
//			JSONObject inputJson = new JSONObject(body);
//
//			try {
//				OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
//				wr.write(inputJson.toString());
//				wr.flush();
//				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
//				String output = br.readLine();
//
//				JSONObject res = new JSONObject(output);
//
//				response = UIConstants.SUCCESS;
//			} catch (Exception e) {
//				e.printStackTrace();
//				response = UIConstants.SMS_SENDING_FAILED;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			response = UIConstants.SMS_SENDING_FAILED;
//		}
//		return response;
//
//	}

	public static void main(String[] args) {
		System.out.println(parseDate(""));
		System.out.println(parseDate("2021.02.02 15:56:00"));
		System.out.println(parseDate("2021/December/02 15:56:00"));
		System.out.println(parseDate("2021-December-02 15:56:00"));

		System.out.println(parseDate("Dec/02/2021 15:56:00"));
		System.out.println(parseDate("June-02-2021 15:56:00"));
		System.out.println(parseDate("02-June-2021 03:56 PM"));

		System.out.println(parseDate("14:56"));
		System.out.println(parseDate("02/02/2021 14:56"));
		System.out.println(parseDate("02/July/2021 01:00:00 PM"));

	}
}