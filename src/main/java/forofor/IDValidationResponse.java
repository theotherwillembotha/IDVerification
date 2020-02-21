package forofor;

import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IDValidationResponse {
	
	public static class IDValidationResponseHeader{
		@JsonProperty("Version") private String version;
		@JsonProperty("TimeStamp") private Date timestamp;
		@JsonProperty("Message") private String message;

		private IDValidationResponseHeader() {}
		
		public IDValidationResponseHeader(String version, Date timeStamp, String message) {
			this();
			this.version = version;
			this.timestamp = timeStamp;
			this.message = message;
		}
	}
	
	public static class IDValidationResponseError{
		@JsonProperty("Code") private String code;
		@JsonProperty("Description") private String description;

		private IDValidationResponseError() {}
		
		public IDValidationResponseError(String code, String description) {
			this();
			this.code = code;
			this.description = description;
		}

		public String code() {
			return code;
		}
		
		public String description() {
			return description;
		}
	}
	
	@JsonProperty("Header") private IDValidationResponseHeader header;
	@JsonProperty("Error") private IDValidationResponseError error;
	@JsonProperty("Payload") private Map<String, String> payload;
	
	public IDValidationResponse(IDValidationResponseHeader header) {
		this.header = header;
	}

	public void setPayload(Map<String, String> payload) {
		this.error = null;
		this.payload = payload;
	}

	public void setError(IDValidationResponseError error) {
		this.error = error;
		this.payload = null;
	}
	
	public IDValidationResponseHeader header() {
		return header;
	}
	
	public IDValidationResponseError error() {
		return error;
	}
	
	public Map<String, String> payload() {
		return payload;
	}
}
