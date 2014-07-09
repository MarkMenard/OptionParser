package optionparser;

import java.util.Optional;

public abstract class Option {
	private String flag;
	private Optional<String> rawValue;
	
	public Option (String flag, Optional<String> rawValue) {
		this.flag = flag;
		this.rawValue = rawValue;
	}
	
	public Boolean isValid () {
		return false;
	}
	
	@SuppressWarnings("rawtypes")
	public Optional getValue () {
		return Optional.ofNullable(null);
	}
	
	// Properties

	public abstract String getType();

	public String getFlag() {
		return flag;
	}
	
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public Optional<String> getRawValue() {
		return rawValue;
	}
	
	public void setRawValue(Optional<String> rawValue) {
		this.rawValue = rawValue;
	}

}
