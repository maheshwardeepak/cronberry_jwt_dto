package com.cronberry.utility;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.cronberry.service.constants.UIConstants;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * The Class CustomUserIdSerializer.
 */
public class CustomUserIdSerializer extends JsonSerializer<Long> {

	private Logger logger = LogManager.getLogger(CustomUserIdSerializer.class);
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fasterxml.jackson.databind.JsonSerializer#serialize(java.lang.Object,
	 * com.fasterxml.jackson.core.JsonGenerator,
	 * com.fasterxml.jackson.databind.SerializerProvider)
	 */
	@Override
	public void serialize(Long userId, JsonGenerator generator, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		String encryptedUserId = null;
		try {
			encryptedUserId = AES.encryptUserId(userId);
		} catch (Exception e) {
			logger.error(UIConstants.EXCEPTION + UIConstants.COLON, e);
		}
		generator.writeString(encryptedUserId);
	}
}
