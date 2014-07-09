package optionparser;

import java.util.Optional;

public class BooleanOption extends Option {
	public BooleanOption (String flag, Optional<String> rawValue) {
		super(flag, rawValue);
	}
	
	public Optional getValue () {
		return getRawValue().isPresent() ? Optional.of(new Boolean (true)) : Optional.of(new Boolean (false));
	}
	
	public Boolean isValid () {
		return true;
	}
	
	public String getType () {
		return "Boolean";
	}

}
