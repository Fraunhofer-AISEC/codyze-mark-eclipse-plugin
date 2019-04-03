package de.fhg.aisec.mark.tests;

import static org.junit.Assert.assertNotNull;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;

import de.fhg.aisec.mark.XtextParser;

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
		EObject result = parser.parse(URI.createFileURI("/home/julian/workspace/2018-11-bsi-secure-crypto-lib-tool/code/mark-crymlin-eclipse-plugin/examples/Test/Rules.mark"));
		assertNotNull(result);
		XtextParser.dump(result);
	}

}
