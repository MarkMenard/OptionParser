package optionparser;

import static org.junit.Assert.*;

import org.junit.Test;

public class OptionParserTest {

	@Test
	public void boolean_option_is_false_when_absent () {
		OptionParser parser = new OptionParser ("", new ParserConfig () {
			
			@Override
			public void configure (OptionParser parser) {
				parser.option("v");
			}
		});
		
		assertFalse((Boolean) parser.getValue("v"));
	}
	
	@Test
	public void boolean_option_is_true_when_present () {
		OptionParser parser = new OptionParser ("-v", new ParserConfig () {
			
			@Override
			public void configure (OptionParser parser) {
				parser.option("v");
			}
		});
		
		assertTrue((Boolean) parser.getValue("v"));
	}

	
	@Test
	public void can_define_a_boolean_option () {
		OptionParser parser = new OptionParser ("", new ParserConfig () {
			
			@Override
			public void configure (OptionParser parser) {
				parser.option("v");
			}
		});
		
		assertTrue(parser.hasConfiguredFlag("v"));
	}

}
