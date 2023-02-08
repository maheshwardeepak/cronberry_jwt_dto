package com.cronberry.utility;

import java.io.IOException;


import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * The Class CustomUserIdDeserializer.
 */
@SuppressWarnings("deprecation")
public class CustomUserIdDeserializer extends JsonDeserializer<Long> {

	private Logger logger = LogManager.getLogger(CustomUserIdDeserializer.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fasterxml.jackson.databind.JsonDeserializer#deserialize(com.fasterxml
	 * .jackson.core.JsonParser,
	 * com.fasterxml.jackson.databind.DeserializationContext)
	 */
	@Override
	public Long deserialize(JsonParser parser, DeserializationContext context)
			throws IOException, JsonProcessingException {
		logger.info("deserializedeserializedeserializedeserializedeserializedeserialize");
		JsonNode node = parser.getCodec().readTree(parser);
		Long decryptedUserId = 0l;
		String unescapedString = StringEscapeUtils.unescapeJava(node.toString());
		unescapedString = unescapedString.substring(1, unescapedString.length() - 1);

		decryptedUserId = AES.decryptUserId(unescapedString);
		return decryptedUserId;

	}

}
