package de.fhg.aisec.mark.tests;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.List;

import org.junit.Test;

import de.fhg.aisec.mark.XtextParser;
import de.fhg.aisec.mark.markDsl.MarkModel;

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
		parser.addMarkFile(new File("/home/julian/workspace/2018-11-bsi-secure-crypto-lib-tool/code/mark-crymlin-eclipse-plugin/examples/Test/Rules.mark"));
		List<MarkModel> result = parser.parse();
		assertNotNull(result);
		XtextParser.dump(result.get(0));
	}

}
