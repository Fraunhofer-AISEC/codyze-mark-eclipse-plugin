package de.fraunhofer.aisec.mark.tests;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.HashMap;
import java.util.Map.Entry;

import org.junit.Test;

import de.fraunhofer.aisec.mark.XtextParser;
import de.fraunhofer.aisec.mark.markDsl.MarkModel;

/**
 * Unit test for the MARK standalone parser.
 * 
 * @author julian
 *
 */
public class XtextParserTest {

	@Test
	public void test() {
		XtextParser parser = new XtextParser();
		parser.addMarkFile(new File("../../examples/Test/Rules.mark"));
		HashMap<String, MarkModel> result = parser.parse();
		assertNotNull(result);
		for (Entry<String, MarkModel> entry : result.entrySet()) {
			System.out.println(entry.getKey());
			XtextParser.dump(entry.getValue());
		}
	}

}
