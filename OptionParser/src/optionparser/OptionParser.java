package optionparser;

import java.util.*;
import java.util.function.Consumer;

public class OptionParser {
	
	private final List<String> argsFromCommandLine;
	private final Map<String, Option> optionsMap = new HashMap<> ();
	private final Map<String, String> configuredFlags = new HashMap<String, String> ();
	private static final Optional<String> NULL_OPTIONAL = Optional.ofNullable(null);
	
	public OptionParser (List<String> argsFromCommandLine, Consumer<OptionParser> config) {
		config.accept(this);
		this.argsFromCommandLine = argsFromCommandLine;
		setupCommandLineOptions();
	}

	public void option (String flag) {
		option(flag, "Boolean");
	}
	
	public void option (String flag, String type) {
		configuredFlags.put(flag, type);
	}
	
	@SuppressWarnings("rawtypes")
	public Optional getValue (String flag) {
		return optionsMap.containsKey(flag) ? optionsMap.get(flag).getValue() : NULL_OPTIONAL;
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
			try {
				Option option = (Option) Class.forName("optionparser." + type + "Option").newInstance();
				option.setFlag(flag);
				option.setRawValue(getValueForFlag(flag));
				optionsMap.put(flag, option);
			} catch (Exception e) {
				throw new RuntimeException ("ERROR: Could not handle option " + flag + " with type " + type, e);
			}
		});
	}
}
