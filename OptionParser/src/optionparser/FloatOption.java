package optionparser;

import java.util.Optional;

public class FloatOption extends Option {

	public FloatOption () {
	}

	public FloatOption (String flag, Optional<String> rawValue) {
		super(flag, rawValue);
	}

	@Override
	public String getType() {
		return "Float";
	}
	
	public Boolean isValid () {
		if (getRawValue().isPresent()) {
			String rawString = getRawValue().get();
			try {
				return rawString.length() > 2 && isFloat(rawString.substring(2));
			} catch (NumberFormatException e) {
				return false;
			}
		} else {
			return true;
		}
	}
	
	public Optional getValue () {
		return isValid() && getRawValue().isPresent() && isFloat(getRawValue().get().substring(2)) ? 
				Optional.of(Float.valueOf(getRawValue().get().substring(2))) : 
					Optional.ofNullable(null);
	}
	
	private Boolean isFloat (String rawString) {
		try {
			Float.valueOf(rawString) ;
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
