package forofor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Application {
	
	public static void main(String[] args) throws IOException {
		IDValidationService idValidator = new IDValidationService();
		
		while(true) {
			System.out.println("Enter a ID Number to validate (q to exit)");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String input = reader.readLine().trim();
			if(input.equalsIgnoreCase("q")) {
				break;
			}
			System.out.println(JsonMapper.toJSON(idValidator.validate(input)));
		}
		System.out.println("Goodbye");
	}

}
