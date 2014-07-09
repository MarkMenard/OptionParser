package optionparser;

import java.util.Optional;

public class IntegerOption extends Option {

	public IntegerOption () {
	}

	public IntegerOption(String flag, Optional<String> rawValue) {
		super(flag, rawValue);
	}

	@Override
	public String getType() {
		return "Integer";
	}
	
	public Boolean isValid () {
		if (getRawValue().isPresent()) {
			String rawString = getRawValue().get();
			try {
				return rawString.length() > 2 && isInteger(rawString.substring(2));
			} catch (NumberFormatException e) {
				return false;
			}
		} else {
			return true;
		}
	}
	
	public Optional getValue () {
		return isValid() && getRawValue().isPresent() && isInteger(getRawValue().get().substring(2)) ? 
				Optional.of(Integer.valueOf(getRawValue().get().substring(2))) : 
					Optional.ofNullable(null);
	}
	
	private Boolean isInteger (String rawString) {
		try {
			Integer.valueOf(rawString) ;
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
