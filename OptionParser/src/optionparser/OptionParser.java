package optionparser;

import java.util.*;

public class OptionParser {
	
	private final List<String> argsFromCommandLine;
	private final Map<String, Option> optionsMap = new HashMap<> ();
	private final Map<String, String> configuredFlags = new HashMap<String, String> ();
	private static final String STRING = "String";
	private static final String BOOLEAN = "Boolean";
	private static final Optional<String> NULL_OPTIONAL = Optional.ofNullable(null);
	
	public OptionParser (List<String> argsFromCommandLine, ParserConfig config) {
		config.configure(this);
		this.argsFromCommandLine = argsFromCommandLine;
		setupCommandLineOptions();
	}

	public void option (String flag) {
		option(flag, BOOLEAN);
	}
	
	public void option (String flag, String type) {
		configuredFlags.put(flag, type);
	}
	
	@SuppressWarnings("rawtypes")
	public Optional getValue (String flag) {
		return optionsMap.containsKey(flag) ? optionsMap.get(flag).getValue() : NULL_OPTIONAL;
	}
		
	public Boolean hasConfiguredFlag (String flag) {
		return configuredFlags.keySet().contains(flag);
	}
	
	public String getTypeForFlag (String flag) {
		return configuredFlags.get(flag);
	}
	
	public Boolean isValid () {
		return optionsMap.values().stream()
			.allMatch(option -> option.isValid());
	}

	private Optional<String> getValueForFlag (String flag) {
		return argsFromCommandLine.stream()
				.filter(arg -> arg.startsWith("-" + flag))
				.findFirst();
	}
	
	private void setupCommandLineOptions () {
		configuredFlags.entrySet().forEach(entry -> {
			String flag = entry.getKey();
			String type = entry.getValue();
			
			if (type.equals(STRING)) {
				optionsMap.put(flag, new StringOption (flag, getValueForFlag(flag)));
			} else if (type.equals(BOOLEAN)) {
				optionsMap.put(flag, new BooleanOption (flag, getValueForFlag(flag)));
			} else {
				throw new RuntimeException ("ERROR: Attempt to create an option of an invalid type: " + type);
			}
		});
	}
}
