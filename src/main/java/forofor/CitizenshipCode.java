package forofor;

import forofor.PropertyExtractor.PropertyException;


public enum CitizenshipCode {
	SACitizen(0),
	PermanentResident(1);
	
	private int id;

	private CitizenshipCode(int id) {
		this.id = id;
	}
	
	public static CitizenshipCode byID(int id) throws PropertyException {
		for(CitizenshipCode code : CitizenshipCode.values()) {
			if(code.id == id) {
				return code;
			}
		}
		throw new PropertyException("4", "Invalid Citizenship Code: " + id);
	}
}