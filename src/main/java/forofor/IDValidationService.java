package forofor;

import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

import forofor.IDValidationResponse.IDValidationResponseError;
import forofor.IDValidationResponse.IDValidationResponseHeader;
import forofor.PropertyExtractor.PropertyException;

public class IDValidationService {
	
	public IDValidationResponse validate(String id) throws PropertyException{
		IDValidationResponse response = new IDValidationResponse(new IDValidationResponseHeader("3.0", new Date(), "ID Validation Message"));
		
		try {
			response.setPayload(Arrays.stream(IDPropertyExtractors.values())
				.flatMap(extractor -> extractor.extract(id).stream())
				.collect(Collectors.toMap(
					extract -> extract.key(),
					extract -> extract.value()
				))
			);
		}
		catch(PropertyException e) {
			response.setError(new IDValidationResponseError(e.getCode(), e.getMessage()));
		}
		
		return response;
	}

}
