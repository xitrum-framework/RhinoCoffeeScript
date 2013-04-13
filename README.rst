How to create CoffeeScript.class
--------------------------------

1. Download coffee-script.js (the core compile) from http://coffeescript.org/
2. Precompile with Rhino 1.7R4:

::

  org.mozilla.javascript.tools.jsc.Main.main(
    "-opt 9 -nosource -o CoffeeScript -package tv.cntt.rhino coffee-script.js".split(" ")
  )

Tell me to update if a new version of Rhino has been released or the above
options can be improved.

Usage
-----

Maven:

* Group: tv.cntt
* Artifact: CoffeeScript.class
* Version: 1.6.2 (= latest version of CoffeeScript)

Tell me to update if a new version of CoffeeScript has been released.

This library contains only one file CoffeeScript.class, without no dependency.
Scala example:

::

  import scala.util.control.NonFatal
  import org.mozilla.javascript._  // Your project must include Rhino dependency
  import tv.cntt.rhino.CoffeeScript

  def compile(coffeeScript: String, bare: Boolean): Option[String] = {
    val ctx = Context.enter()
    try {
      val scope  = ctx.initStandardObjects()
      val script = new CoffeeScript
      script.exec(ctx, scope)

      val obj  = scope.get("CoffeeScript", scope).asInstanceOf[NativeObject]
      val fun  = obj.get("compile", scope).asInstanceOf[Function]
      val opts = ctx.evaluateString(scope, "({bare: %b})".format(bare), null, 1, null)

      val javaScript = fun.call(ctx, scope, obj, Array(coffeeScript, opts)).asInstanceOf[String]
      Some(javaScript)
    } catch {
      case NonFatal(e) =>
        println(e)
        None
    } finally {
      Context.exit()
    }
  }

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
