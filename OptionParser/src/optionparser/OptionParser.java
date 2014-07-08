package optionparser;

import java.util.*;

public class OptionParser {
	
	private final String optionsFromCommandLine;
	private final Set<String> validFlags = new HashSet<String> ();
	
	public OptionParser (String optionsFromCommandLine, ParserConfig config) {
		this.optionsFromCommandLine = optionsFromCommandLine;
		config.configure(this);
	}

	public Object getValue (String flag) {
		return optionsFromCommandLine.contains("-" + flag);
	}
		
	public void option (String flag) {
		validFlags.add(flag);
	}
	
	public Boolean hasConfiguredFlag (String flag) {
		return validFlags.contains(flag);
	}
}
