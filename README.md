# Modellierungssprache für Anforderungen und Richtlinien der Kryptografie (MARK)

This project holds several dependent eclipse projects, which implement the following features of the MARK language

* an _ANTLR grammar_, defining the MARK language
* an XText _code generator_ that creates Crymlin/Gremlin out of MARK files
* an _Eclipse plugin_ for syntax highlighting and autocompletion, and more
* A _Language Server (LSP)_ that can be used to support MARK in other IDEs, such as VS Code or Visual Studio (without the code generation part, though)
* _Unit tests_ for the language and the code generator

Do not let all the files confuse you. There are only two essential files:

* `de.fhg.aisec.mark.parent/de.fhg.aisec.mark/src/de/fhg/aisec/mark/MarkDsl.xtext` is the language grammar of MARK
* `de.fhg.aisec.mark.parent/de.fhg.aisec.mark/src/de/fhg/aisec/mark/generator/MarkDslGenerator.xtend` is the code generation template


# How to build

## With Maven

If you are a build server, you may simply run Maven in the `de.fhg.aisec.mark.parent` project:

```
cd de.fhg.aisec.mark.parent
mvn clean install
```

## With Eclipse

To make reasonable use of these projects, you will need the "Eclipse Photon IDE for Java and DSL Developers". Download it from here:

https://www.eclipse.org/downloads/packages/release/2018-12/r/eclipse-ide-java-and-dsl-developers


__NOTE: The Maven build is pinned to this specific stable release of Eclipse: S20181128170323 (see link for the respective update site below). It is STRONGLY recommended that you use the very same version as your IDE that is also used by the maven build. There are fragile dependencies between JDT (the Eclipse Java Dev Tools), Xtext, Xtend, and other components. If your IDE is using different versions than the Maven build you just bought your ticket to hell.__

* http://download.eclipse.org/tools/orbit/downloads/drops/S20181128170323/repository/

To build the project in Eclipse, click on the green launch configurations button and run _"GenerateMarkDsk Language Infrastructure"_. This will create all the auto-generated language files of the DSL and the code generator.

Now, to see the DSL plugins in action, launch _"MARK.product"_ This will spawn a new instance of Eclipse with the MARK plugins installed. Within that new instance, create a new project and in that project create a new file with the extension `.mark`. There you will have autocompletion and all the other nice features.
