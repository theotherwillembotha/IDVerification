package forofor;

import java.util.List;

@FunctionalInterface
public interface PropertyExtractor{
	
	public List<Extract> extract(String input) throws PropertyException;
	
	
	public static class PropertyException extends RuntimeException {
		private static final long serialVersionUID = 1L;
		private String code;
		
		public PropertyException(String code, String message) {
			super(message);
			this.code = code;
		}

		public String getCode() {
			return this.code;
		}
	}
	
	public static class Extract{

		private String key;
		private String value;

		public Extract(String key, String value) {
			this.key = key;
			this.value = value;
		}
		
		public String key() {
			return key;
		}
		
		public String value() {
			return value;
		}
	}
	
}