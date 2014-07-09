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
		OptionParser parser = new OptionParser (Arrays.asList("-v", "-nfoo", "-f1.0", "-i2"), p -> { 
			p.option("v");
			p.option("p");
			p.option("n", "String");
			p.option("s", "String");
			p.option("f", "Float");
			p.option("i", "Integer");
		});
		
		assertTrue((Boolean) parser.getValue("v").get());
		assertFalse((Boolean) parser.getValue("p").get());
		assertEquals("foo", (String) parser.getValue("n").get());
		parser.getValue("s").ifPresent(arg -> fail("We shouldn't be able to get here because -s isn't in the ARGV collection."));
		assertEquals(1.0f, parser.getValue("f").get());
		assertEquals(2, parser.getValue("i").get());
	}

	@Test
	public void returns_empty_optional_for_undefined_option () {
		OptionParser parser = new OptionParser (Arrays.asList("-ffoo"), p -> p.option("v"));
		assertFalse(parser.getValue("f").isPresent());
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
	public void a_string_option_arg_must_have_content () {
		OptionParser parser = new OptionParser (Arrays.asList("-f"), p -> p.option("f", "String"));
		assertFalse(parser.isValid());
	}

	@Test
	public void a_string_option_arg_is_valid_when_it_has_content () {
		OptionParser parser = new OptionParser (Arrays.asList("-ffoo"), p -> p.option("f", "String"));
		assertTrue(parser.isValid());
	}

	@Test
	public void a_string_option_is_valid_when_the_arg_is_not_present () {
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
	
	// Integer Options

	@Test
	public void an_integer_option_must_have_content () {
		OptionParser parser = new OptionParser (Arrays.asList("-f"), p -> p.option("f", "Integer"));
		assertEquals(false, parser.isValid());
	}

	@Test
	public void an_integer_option_must_be_an_integer () {
		OptionParser parser = new OptionParser (Arrays.asList("-ffoo"), p -> p.option("f", "Integer"));
		assertEquals(false, parser.isValid());
	}
	
	@Test
	public void an_integer_option_is_valid_when_the_arg_is_not_present () {
		OptionParser parser = new OptionParser (emptyArgs, p -> p.option("f", "Integer"));
		assertEquals(true, parser.isValid());
	}
	
	@Test
	public void an_integer_option_can_return_an_integer_value () {
		OptionParser parser = new OptionParser (Arrays.asList("-f100"), p -> p.option("f", "Integer"));
		assertEquals(100, parser.getValue("f").get());
	}
	
	@Test
	public void an_integer_option_returns_an_empty_optional_when_absent () {
		OptionParser parser = new OptionParser (emptyArgs, p -> p.option("f", "Integer"));
		assertEquals(false, parser.getValue("f").isPresent());
	}
	
	// Float options
	
	@Test
	public void a_float_option_can_return_an_float_value () {
		OptionParser parser = new OptionParser (Arrays.asList("-f1.0"), p -> p.option("f", "Float"));
		assertEquals(1.0f, parser.getValue("f").get());
	}

}
