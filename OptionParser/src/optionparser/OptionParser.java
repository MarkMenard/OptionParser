package optionparser;

import java.util.*;

public class OptionParser {
	
	private final Map<String, CommandLineArgument> commandLineArgumentsByFlag = new HashMap<> ();
	private final Map<String, String> configuredFlags = new HashMap<String, String> ();
	private static final String STRING = "String";
	private static final String BOOLEAN = "Boolean";
	
	public OptionParser (List<String> argsFromCommandLine, ParserConfig config) {
		config.configure(this);
		setupCommandLineArguments(argsFromCommandLine);
	}

	public void option (String flag) {
		option(flag, BOOLEAN);
	}
	
	public void option (String flag, String type) {
		configuredFlags.put(flag, type);
	}
	
	@SuppressWarnings("rawtypes")
	public Optional getValue (String flag) {
		if (configuredFlags.get(flag) == STRING) {
			return getValueForFlag(flag);
		} else if (configuredFlags.get(flag) == BOOLEAN) {
			return Optional.of(commandLineArgumentsByFlag.containsKey(flag));
		}
		return Optional.ofNullable(null);
	}
		
	public Boolean hasConfiguredFlag (String flag) {
		return configuredFlags.keySet().contains(flag);
	}
	
	public String getTypeForFlag (String flag) {
		return configuredFlags.get(flag);
	}
	
	public Boolean isValid () {
		return commandLineArgumentsByFlag.values().stream()
			.filter(arg -> arg.getType() == STRING)
			.allMatch(arg -> arg.getRawValue() != null && arg.getRawValue().length() > 0);
	}

	private Optional<String> getValueForFlag (String flag) {
		CommandLineArgument arg = commandLineArgumentsByFlag.get(flag);
		
		return arg != null ? Optional.of(arg.getRawValue()) : Optional.ofNullable(null);
		
	}
	
	private void setupCommandLineArguments(List<String> rawArgsFromCommandLine) {
		List<CommandLineArgument> commandLineArguments = new ArrayList<> ();

		rawArgsFromCommandLine.stream()
			.filter(arg -> arg.length() >= 2)
			.forEach((arg) -> {
				String flag = arg.substring(1, 2);
				String value = arg.length() > 2 ? arg.substring(2) : null;
				commandLineArguments.add(new CommandLineArgument(flag, value));
			});
		
		configuredFlags.keySet().forEach(flag -> {
			commandLineArguments.stream()
				.filter(cla -> cla.getFlag().equals(flag))
				.findFirst()
				.ifPresent(cla -> cla.setType(configuredFlags.get(flag)));
		});
		
		commandLineArguments.forEach(cla -> commandLineArgumentsByFlag.put(cla.getFlag(), cla));
	}
	
	public class CommandLineArgument {
		private String flag;
		private String rawValue;
		private String type;
		
		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public CommandLineArgument (String flag, String rawValue) {
			this.flag = flag;
			this.rawValue = rawValue;
		}

		public String getFlag() {
			return flag;
		}
		
		public void setFlag(String flag) {
			this.flag = flag;
		}
		
		public String getRawValue() {
			return rawValue;
		}
		
		public void setRawValue(String rawValue) {
			this.rawValue = rawValue;
		}
	}
}
