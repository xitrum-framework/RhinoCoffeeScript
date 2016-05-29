This Java library provides CoffeeScript.class precompiled with
`Rhino <https://developer.mozilla.org/en-US/docs/Mozilla/Projects/Rhino/Download_Rhino>`_.
This allows you to speed up the compiling of CoffeeScript snippets to JavaScript.
For example, it usually takes several seconds to load coffee-script.js and use
it to compile a simple CoffeeScript snippet. This can be reduced to several
milliseconds when using the precompiled CoffeeScript.class.

Usage
-----

Maven:

* Group: tv.cntt
* Artifact: rhinocoffeescript
* Version: 1.10.0 (= version of CoffeeScript)

Please `create a new issue <https://github.com/xitrum-framework/RhinoCoffeeScript/issues>`_
to report if you see that a new version of CoffeeScript has been released, but
there's no corresponding version of rhinocoffeescript.

This library only includes Rhino dependency. You can use the precompiled class
``tv.cntt.rhinocoffeescript.CoffeeScript`` or the utility class
``tv.cntt.rhinocoffeescript.Compiler``.

Scala example:

::

  import javax.script.ScriptException
  import tv.cntt.rhinocoffeescript.Compiler

  object CoffeeScriptCompiler {
    def compile(coffeeScript: String): Option[String] = {
      try {
        val javaScript = Compiler.compile(coffeeScript)
        Some(javaScript)
      } catch {
        case e: ScriptException =>
          val line   = e.getLineNumber
          val column = e.getColumnNumber
          println("CoffeeScript syntax error at %d:%d".format(line, column))
          None
      }
    }
  }

You can also cache the generated JavaScript snippet using local cache
(you can easily `implement one with LinkedHashMap <http://www.java-blog.com/creating-simple-cache-java-linkedhashmap-anonymous-class>`_)
or distributed cache (like `Hazelcast <http://www.hazelcast.com/>`_).

How CoffeeScript.class is created
---------------------------------

1. Download coffee-script.js (the core compile) from http://coffeescript.org/
2. Precompile with Rhino 1.7.7.1:

::

  org.mozilla.javascript.tools.jsc.Main.main(
    "-opt 9 -nosource -o CoffeeScript -package tv.cntt.rhinocoffeescript coffee-script.js".split(" ")
  )

Please `create a new issue <https://github.com/xitrum-framework/RhinoCoffeeScript/issues>`_
to report if a new version of Rhino has been released or the above options can
be improved.

About the options when creating CoffeeScript.class
--------------------------------------------------

::

  org.mozilla.javascript.tools.jsc.Main.main(Array("-h"))

gives:

::

  Usage: java org.mozilla.javascript.tools.jsc.Main [OPTION]... SOURCE...
  Valid options are:
    -version VERSION   Use the specified language version.
                         VERSION should be one of 100|110|120|130|140|150|160|170.
    -opt LEVEL         Use optimization with the specified level.
                         LEVEL should be one of 0..9.
    -debug, -g         Include debug information.
    -nosource          Do not include source to function objects.
                         It makes f.toString() useless and violates ECMAScript
                         standard but makes generated classes smaller and
                         saves memory.
    -o CLASSNAME       Use specified name as the last component of the main
                         generated class name. When specified, only one script
                         SOURCE is allowed. If omitted, it defaults to source
                         name with stripped .js suffix.
    -package PACKAGE   Place generated classes in the specified package.
    -d DIRECTORY       Use DIRECTORY as destination directory for generated
                         classes. If omitted, it defaults to parent directory
                         of SOURCE.
    -encoding charset  Sets the character encoding of the source files.
    -extends CLASS     The main generated class will extend the specified
                         class CLASS.
    -implements INTERFACE1,INTERFACE2,... The main generated class will
                         implement the specified list of interfaces.
    -main-method-class CLASS Specify the class name used for main method
                         implementation. The class must have a method matching
                         "public static void main(Script sc, String[] args)"
    -observe-instruction-count Generate code that contains callbacks to
                         accumulate counts of executed instructions. Code
                         compiled with this flag can be monitored using
                         Context.setInstructionObserverThreshold.
    -help, --help, -h  Print this help and exit.
