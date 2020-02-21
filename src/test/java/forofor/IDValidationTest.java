package forofor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import forofor.IDValidationResponse.IDValidationResponseError;

public class IDValidationTest {
	
	private static IDValidationService idValidationService;

	@BeforeAll
	public static void setup() {
		idValidationService = new IDValidationService();
	}
	
	
	@Test
	public void pass_Male_SACitizen_39(){
		Map<String, String> payload = idValidationService.validate("8005065002082").payload();
		assertEquals("Male", payload.get("gender"));
		assertEquals("SACitizen", payload.get("citizenship"));
		assertEquals("1980-05-06", payload.get("Birthdate") );
		assertEquals("39", payload.get("age"));
	}
	
	@Test
	public void pass_Female_Resident_15(){
		Map<String, String> payload = idValidationService.validate("0406084006187").payload();
		assertEquals("Female", payload.get("gender"));
		assertEquals("PermanentResident", payload.get("citizenship"));
		assertEquals("2004-06-08", payload.get("Birthdate"));
		assertEquals("15", payload.get("age"));
	}
	

	@Test
	public void fail_InvalidLength(){
		IDValidationResponseError error = idValidationService.validate("406084006180").error();
		assertEquals("1", error.code());
		assertEquals("Invalid ID Number. Expected to be 13 digits.", error.description());
	}
	
	@Test
	public void fail_InvalidCharacters(){
		IDValidationResponseError error = idValidationService.validate("4X06084006180").error();
		assertEquals("1", error.code());
		assertEquals("Invalid ID Number. Expected to be 13 digits.", error.description());
	}
	
	@Test
	public void fail_InvalidChecksum(){
		IDValidationResponseError error = idValidationService.validate("4406084006180").error();
		assertEquals("2", error.code());
		assertEquals("Invalid ID Number. Checksum Incorrect", error.description());
	}

	@Test
	public void fail_InvalidBirthdate(){
		IDValidationResponseError error = idValidationService.validate("4455084006180").error();
		assertEquals("3", error.code());
		assertEquals("Invalid Birthdate: Invalid value for MonthOfYear (valid values 1 - 12): 55", error.description());
	}
	

	@Test
	public void fail_CitizenCode(){
		IDValidationResponseError error = idValidationService.validate("4405084006680").error();
		assertEquals("4", error.code());
		assertEquals("Invalid Citizenship Code: 6", error.description());
	}

	@Test
	public void verifyPayloadSerialization(){
		String json = JsonMapper.toJSON(idValidationService.validate("8005065002082"));
		assertTrue(json.contains("\"Payload\":{\"sequence\":\"0\",\"gender\":\"Male\",\"citizenship\":\"SACitizen\",\"Birthdate\":\"1980-05-06\",\"age\":\"39\"}"));
		assertFalse(json.contains("\"Error\":{"));
	}

	@Test
	public void verifyErrorSerialization(){
		String json = JsonMapper.toJSON(idValidationService.validate("406084006180"));
		assertTrue(json.contains("\"Error\":{\"Code\":\"1\",\"Description\":\"Invalid ID Number. Expected to be 13 digits.\"}"));
		assertFalse(json.contains("\"Payload\":{"));
	}
	
}
