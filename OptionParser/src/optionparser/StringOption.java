package optionparser;

import java.util.Optional;

public class StringOption extends Option {
	public StringOption (String flag, Optional<String> rawValue) {
		super(flag, rawValue);
	}

	public String getType () {
		return "Boolean";
	}
	
	public Boolean isValid () {
		if (getRawValue().isPresent()) {
			return getRawValue().get().length() > 2;
		} else {
			return true;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public Optional getValue () {
		if (getRawValue().isPresent()) {
			return Optional.of(getRawValue().get().substring(2));
		} else {
			return getRawValue();
		}
	}
}
