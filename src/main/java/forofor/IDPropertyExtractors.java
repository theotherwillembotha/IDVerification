package forofor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import forofor.PropertyExtractor.Extract;
import forofor.PropertyExtractor.PropertyException;

public enum IDPropertyExtractors {
	Validation(input -> {
		// the ID number should be 13 digits long.
		if(!input.matches("[0-9]{13}")) {
			throw new PropertyException("1", "Invalid ID Number. Expected to be 13 digits.");
		}
		
		// calculate the checksum
		AtomicBoolean odd = new AtomicBoolean(false);
		int checksum = Arrays.stream(input.split("|"))		// split the digits.
			.mapToInt(Integer::parseInt)					// convert them to integers.
			.map(val -> {
				odd.set(!odd.get());						// flip the state.
				return odd.get() 
					? val 					// odd, add val
					: (val < 5) 			
						? val*2 			// even and smaller than 5, multiply by 2.
						: val*2-9 ;			// even and larger or equal to 5, multiply by 2, subtract 9
			})
			.sum();
		
		if(checksum % 10 != 0) {
			throw new PropertyException("2", "Invalid ID Number. Checksum Incorrect");
		}
		
		// everything is okay.
		return Arrays.asList();
	}),
	
	DateOfBirth(input -> {
		// the digits 0 to 6 is the birthdate - The assumption is that no one is older than 100 years.
		try{
			LocalDateTime birth = LocalDateTime.parse("19" + input.substring(0,6) + "00", DateTimeFormatter.ofPattern("yyyyMMddHH"));
			
			if(birth.plus(100, ChronoUnit.YEARS).isBefore(LocalDateTime.now())) {
				birth = birth.plus(100, ChronoUnit.YEARS);
			}
			return Arrays.asList(
				new Extract("Birthdate", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(birth)),
				new Extract("age", String.valueOf(ChronoUnit.YEARS.between(birth, LocalDateTime.now())))
			);
		}
		catch (DateTimeParseException e) {
			throw new PropertyException("3", "Invalid Birthdate: " + e.getCause().getMessage());
		}
	}),
	
	Gender(input -> {
		// digit 6 indicates the gender. 0-4 = female. 5-9 indicates male.
		return Arrays.asList(
			new Extract("gender", Integer.parseInt(input.substring(6,7)) < 5 ? "Female" : "Male")
		);
	}),
	
	SequenceNumber(input -> {
		// digit 7 to 9 indicates the sequence number.
		return Arrays.asList(
			new Extract("sequence", String.valueOf(Integer.parseInt(input.substring(7,9))))
		);
	}),
	
	Citizenship(input -> {
		// digit 10 indicates the citizenship. 0 = SA Citizen. 1 = Resident. 
		return Arrays.asList(
			new Extract("citizenship", CitizenshipCode.byID(Integer.parseInt(input.substring(10,11))).name())
		);
	});
	
	private PropertyExtractor extractor;
	
	private IDPropertyExtractors(PropertyExtractor extractor) {
		this.extractor = extractor;
	}

	public List<Extract> extract(String input) throws PropertyException{
		return this.extractor.extract(input);
	}
}