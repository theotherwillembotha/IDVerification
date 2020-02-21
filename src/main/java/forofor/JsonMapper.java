package forofor;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonMapper {
	
	private static ObjectMapper objectMapper;

	static {
		objectMapper = new ObjectMapper();
		objectMapper.setVisibility(objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
			.withFieldVisibility(Visibility.ANY)
			.withGetterVisibility(Visibility.NONE)
			.withSetterVisibility(Visibility.NONE)
			.withCreatorVisibility(Visibility.NONE)
		);
		
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		objectMapper.setSerializationInclusion(Include.NON_NULL);
	}

	public static String toJSON(Object response) {
		try { return objectMapper.writeValueAsString(response); } 
		catch (JsonProcessingException e) { throw new RuntimeException(e); }
	}
}
