package de.fhg.aisec.mark;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

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

/**
 * A standalone parser for .mark files.
 * 
 * Standalone means that this parser does not need an Eclipse/Xtext environment to operate.
 * 
 * @author dennis, julian
 *
 */
public class XtextParser {

  @Inject
  private XtextResourceSet resourceSet;

  public XtextParser() {
    StandaloneSetup saS = new org.eclipse.emf.mwe.utils.StandaloneSetup();
    saS.setPlatformUri("./");
    saS.setScanClassPath(false);

    Injector injector = new MarkDslStandaloneSetup().createInjectorAndDoEMFRegistration();
    injector.injectMembers(this);
    resourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
  }

  /**
   * Dumps an EObject to stdout.
   * 
   * @param mod_
   */
  public static void dump(EObject mod_) {
    System.out.println(dump(mod_, "\t"));
  }

  private static String dump(EObject mod_, String indent) {
    if (mod_ == null) {
      return "";
    }
    String res = indent + mod_.toString().replaceFirst(".*[.]impl[.](.*)Impl[^(]*", "$1 ");

    for (EObject a : mod_.eCrossReferences()) {
      res += " references " + a.toString().replaceFirst(".*[.]impl[.](.*)Impl[^(]*", "$1 ");
    }
    res += "\n";
    for (EObject f : mod_.eContents()) {
      res += dump(f, indent + "\t");
    }
    return res;
  }

  /**
   * Adds a MARK file to the parser.
   * 
   * Use this method to register all MARK files that need to be parsed, then call {@code parse()}.
   * @param f
   */
  public void addMarkFile(File f) {
    this.resourceSet.getResource(URI.createFileURI(f.getAbsolutePath()), true);
  }

  public void printResourceList() {
    for (Iterator<Resource> iterator = resourceSet.getResources().iterator(); iterator.hasNext();) {
      Resource resource = iterator.next();
      System.out.println(resource.getURI().segment(resource.getURI().segmentCount() - 1));
      if (resource.getErrors().isEmpty()) {
        dump(resource.getContents().get(0));
      } else {
        System.out.println("\t<<< HAS ERRORS >>>");
        for (Diagnostic diagnostic : resource.getErrors()) {
          System.out.println("\tl" + diagnostic.getLine() + ": " + diagnostic.getMessage());
        }
      }
    }
  }

  public void dumpModel() {
    System.out.println("a");
  }

  /**
   * Resolves all loaded MARK files.
   * 
   * The files must be registered by {@code addMarkFile()} before.
   * 
   * @return
   */
  public HashMap<String, MarkModel> parse() {
    EcoreUtil.resolveAll(this.resourceSet);

    HashMap<String, MarkModel> models = new HashMap<String, MarkModel>();
    for (Resource r : this.resourceSet.getResources()) {
      EObject model = (MarkModel) r.getContents().get(0);
      models.put(r.getURI().toFileString(), (MarkModel) model);
    }
    return models;
  }

  /**
   * Just for testing.
   */
  public static void main(String... args) throws IOException {
    XtextParser p = new XtextParser();

    // Add two MARK files
    //p.addMarkFile(new File("../../examples/Test/Rules.mark"));
    //p.addMarkFile(new File("../../examples/Test/AES.mark"));
    p.addMarkFile(new File("../../examples/PoC_MS1/JCA_Cipher.mark"));
    

    // Parse them
    HashMap<String, MarkModel> parse = p.parse();

    // Dump their model
    for (Entry<String, MarkModel> par : parse.entrySet()) {
      dump(par.getValue());
    }

    // Dump their errors (if any)
    p.printResourceList();
  }
}
