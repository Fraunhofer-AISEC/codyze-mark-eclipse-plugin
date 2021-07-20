# MARK Eclipse Plugin

_MARK_ is a domain specific language for writing rules for the static code analysis tool [_Codyze_](/Fraunhofer-AISEC/codyze). It stands for "Modellierungssprache für Anforderungen und Richtlinien der Kryptografie" (Modeling Language for Cryptography Requirements and Guidelines).

This project holds several dependent Eclipse projects, implementing the following features of the MARK language:

* an Xtext _grammar_, defining the MARK language
* an Xtext _code generator_ that creates Crymlin/Gremlin out of MARK files
* an _Eclipse plugin_ for syntax highlighting and autocompletion, and more
* a _language server_ that can be used to support MARK in IDEs with LSP support
* _Unit tests_ for the language and the code generator

In all projects there are basically only two essential files:

* `de.fraunhofer.aisec.mark/src/de/fraunhofer/aisec/mark/MarkDsl.xtext` is the Xtext language grammar of MARK
* `de.fraunhoder.aisec.mark/src/de/fraunhofer/aisec/mark/generator/MarkDslGenerator.xtend` is the code generation template


# Installation

Go to the release page and download 


# Playing around

The `examples` folder contains Xtext projects with MARK examples. You can use them to test the current syntax implemented in MARK. Feel free to add your own examples.

To use these examples, you have to start the `MARK.product`. It starts a new Eclipse instance with its own workspace. In the new Eclipse instance you can import the Xtext projects from the `examples` folder as *Existing Projects into Workspace*. Make sure to deselect the option *Copy projects into workspace* if you want to commit your changes. By deselecting this option the Xtext project folder is used directly.

If you want to start your own examples, you may have to copy your project from the instance's workspace into the `examples` folder. Afterwards, you remove your project from Eclipse and import it from the `examples` folder. Ensure again to deselect the *Copy projects into Workspace* option to be able to commit your changes later on.


# Development FAQ

## How to build

We added Maven wrapper to the project. You won't need an installed Maven installation to run Maven commands.

You need Java 11.


### With Maven

Simply run:

```
./mvnw clean install
```

### With Eclipse

To make reasonable use of these projects, you should start with the "Eclipse IDE for Java and DSL Developers" package. You can find the download at [Eclipse](https://www.eclipse.org/). Alternatively, you have to install the Xtext SDK in your Eclipse instance.


>__NOTE:__
>The project defines specific dependency versions. You may have to install the appropriate version to resolve the correct dependencies. We have tested with 2018-12 and 2020-03.

To build the project in Eclipse, click on the green launch configurations button and run _"GenerateMarkDsk Language Infrastructure"_. This will create all the auto-generated language files of the DSL and the code generator.

Now, to see the DSL plugins in action, launch _"MARK.product"_ This will spawn a new instance of Eclipse with the MARK plugins installed. Within that new instance, create a new project and in that project create a new file with the extension `.mark`. You will be asked if the project should become an Xtext project. Confirm the dialog and you will have autocompletion and all the other nice features.

## Bumping version numbers

The parent project uses the tycho-versions-plugin to update the version number across all (sub-)projects.

For a new SNAPSHOT version use:
```bash
# change into de.fraunhofer.aisec.mark.parent
./mvnw org.eclipse.tycho:tycho-versions-plugin:set-version -DnewVersion=X.Y.Z-SNAPSHOT -Dtycho.mode=maven
```
