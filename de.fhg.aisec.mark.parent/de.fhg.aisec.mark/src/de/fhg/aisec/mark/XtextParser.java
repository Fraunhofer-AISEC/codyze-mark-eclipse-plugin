package de.fhg.aisec.mark;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.mwe.utils.StandaloneSetup;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;

import com.google.inject.Inject;
import com.google.inject.Injector;

import de.fhg.aisec.mark.markDsl.MarkModel;

public class XtextParser {
	
	@Inject
    private XtextResourceSet resourceSet;
 
    public XtextParser() {
        setupParser();
    }
 
    private void setupParser() {        
    	StandaloneSetup saS = new org.eclipse.emf.mwe.utils.StandaloneSetup();
    	saS.setPlatformUri("../");
    	saS.setScanClassPath(false);
    	
        Injector injector = new MarkDslStandaloneSetup().createInjectorAndDoEMFRegistration();
        injector.injectMembers(this);
        resourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
        
	    /*
	    boolean err = result.hasSyntaxErrors();
	    EObject eRoot = result.getRootASTElement();
	    System.out.println(dump(eRoot, ""));
	    */
    }
    
    public static void dump(EObject mod_) {
    	System.out.println(dump(mod_, "\t"));
    }
    
    public static String dump(EObject mod_, String indent) {
        String res = indent + mod_.toString().replaceFirst (".*[.]impl[.](.*)Impl[^(]*", "$1 ");

		for ( EObject a :mod_.eCrossReferences())
            res += " references " + a.toString().replaceFirst (".*[.]impl[.](.*)Impl[^(]*", "$1 ");
        res += "\n";
        for (EObject f :mod_.eContents()) {
            res += dump(f, indent + "\t");
        }
        return res;
    }

 
    public void addResource(File f) {
    	resourceSet.getResource(URI.createFileURI(f.getAbsolutePath()), false);
    }
    
    public void printResourceList() {
    	for (Iterator<Resource> iterator = resourceSet.getResources().iterator(); iterator.hasNext();) {
			Resource resource = iterator.next();
			System.out.println(resource.getURI().segment(resource.getURI().segmentCount()-1));
			if (resource.getErrors().isEmpty()) {
				dump(resource.getContents().get(0));
			} else {
				System.out.println("\t<<< HAS ERRORS >>>");
				for (Diagnostic diagnostic : resource.getErrors()) {
					System.out.println("\tl" + diagnostic.getLine() + ": "+ diagnostic.getMessage());
				}
			}
		}
    }
    
    public void dumpModel() {
    	System.out.println("a");
    }

    
    public MarkModel parse(File markFile) {
    	Resource resource = resourceSet.getResource(URI.createFileURI(markFile.getAbsolutePath()), true);
        EcoreUtil.resolveAll(resource);
        return (MarkModel) resource.getContents().get(0);
    }
    
    /**
     * Just for testing.
     */
    public static void main(String... args) throws IOException {
    	XtextParser p = new XtextParser();
    	
    	File[] directories = new File("../../examples/Test/").listFiles(File::isFile);
    	for (File file : directories) {
			if (file.getName().endsWith(".mark")) {
				//p.addResource(file);
			}
		}
    	
    	//p.printResourceList();
    	
    	//p.dumpModel();
    	
    	EObject parse = p.parse(new File("/home/julian/workspace/2018-11-bsi-secure-crypto-lib-tool/code/mark-crymlin-eclipse-plugin/examples/Test/Rules.mark"));
//    	EObject parse = p.parse("/home/user/projects/bsi-secure-crypto/code/mark-crymlin-eclipse-plugin/examples/Test/CTR.mark");
    	dump(parse);
    	
    	p.printResourceList();
    }
}
