package optionparser;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

public class OptionParserTest {

	private final List<String> emptyArgs = new ArrayList<> ();
	
	/**
	 * This tests serves as an example of how to use the OptionParser.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void multiple_options () {
		OptionParser parser = new OptionParser (Arrays.asList("-v", "-ffoo", "-p"), p -> { 
			p.option("f", "String");
			p.option("v");
			p.option("p");
			p.option("s", "String");
		});
		
		parser.getValue("s").ifPresent(arg -> fail("We shouldn't be able to get here because -s isn't in the ARGV collection."));
		assertTrue((Boolean) parser.getValue("v").get());
		assertTrue((Boolean) parser.getValue("p").get());
		assertEquals("foo", (String) parser.getValue("f").get());
		
	}

	@Test
	public void returns_empty_optional_for_undefined_option () {
		OptionParser parser = new OptionParser (Arrays.asList("-ffoo"), p -> p.option("v"));
		assertFalse(parser.getValue("f").isPresent());
	}
	
	@Test
	public void can_define_a_boolean_option () {
		OptionParser parser = new OptionParser (emptyArgs, p -> p.option("v"));
		assertTrue(parser.hasConfiguredFlag("v"));
		assertEquals("Boolean", parser.getTypeForFlag("v"));
	}
	
	@Test
	public void boolean_option_is_false_when_arg_is_absent () {
		OptionParser parser = new OptionParser (emptyArgs, p -> p.option("v"));
		assertEquals(false, parser.getValue("v").get());
	}
	
	@Test
	public void boolean_option_is_true_when_arg_is_present () {
		OptionParser parser = new OptionParser (Arrays.asList("-v"), p -> p.option("v"));
		assertEquals(true, parser.getValue("v").get());
	}
	
	// String options
	
	@Test
	public void can_define_a_string_option () {
		OptionParser parser = new OptionParser (emptyArgs, p -> p.option("f", "String"));
		assertTrue(parser.hasConfiguredFlag("f"));
		assertEquals("String", parser.getTypeForFlag("f"));
	}
	
	@Test
	public void is_invalid_when_a_string_option_does_not_have_content () {
		OptionParser parser = new OptionParser (Arrays.asList("-f"), p -> p.option("f", "String"));
		assertFalse(parser.isValid());
	}

	@Test
	public void is_valid_when_a_string_arg_has_content () {
		OptionParser parser = new OptionParser (Arrays.asList("-ffoo"), p -> p.option("f", "String"));
		assertTrue(parser.isValid());
	}

	@Test
	public void is_valid_when_a_string_arg_is_not_present () {
		OptionParser parser = new OptionParser (Arrays.asList(""), p -> p.option("f", "String"));
		assertTrue(parser.isValid());
	}

	@Test
	public void can_return_string_arg_value () {
		OptionParser parser = new OptionParser (Arrays.asList("-ffoo"), p -> p.option("f", "String"));
		assertEquals("foo", parser.getValue("f").get());
	}
	
	@Test
	public void get_value_for_absent_string_arg_returns_an_empty_optional () {
		OptionParser parser = new OptionParser (emptyArgs, p -> p.option("f", "String"));
		assertEquals(false, parser.getValue("f").isPresent());
	}
	

}
