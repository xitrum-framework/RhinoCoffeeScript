package tv.cntt.rhinocoffeescript;

import javax.script.ScriptException;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.JavaScriptException;
import org.mozilla.javascript.NativeObject;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

// See https://github.com/scalate/scalate/blob/scala_2.10/scalate-core/src/main/scala/org/fusesource/scalate/filter/CoffeeScriptFilter.scala
class Compiler {
    public static String compile(String coffeeScript) throws ScriptException {
        Context ctx = Context.enter();
        try {
            Scriptable scope  = ctx.initStandardObjects();
            Script     script = (Script) new CoffeeScript();
            script.exec(ctx, scope);

            NativeObject obj  = (NativeObject) scope.get("CoffeeScript", scope);
            Function     fun  = (Function) obj.get("compile", scope);
            Object       opts = ctx.evaluateString(scope, "({bare: true})", null, 1, null);

            String javaScript = (String) fun.call(ctx, scope, obj, new Object[] {coffeeScript, opts});
            return javaScript;
        } catch (JavaScriptException e) {
            // Extract line and column of the error location in the CoffeeScript.
            // The below code is guessed from:
            // - Experimenting http://coffeescript.org/ with Chrome JavaScript Console
            // - https://code.google.com/p/wiquery/source/browse/trunk/src/main/java/org/mozilla/javascript/NativeError.java?spec=svn1010&r=1010
            Scriptable syntaxError = (Scriptable) e.getValue();
            Scriptable location    = (Scriptable) ScriptableObject.getProperty(syntaxError, "location");
            Double     firstLine   = (Double) ScriptableObject.getProperty(location, "first_line");
            Double     firstColumn = (Double) ScriptableObject.getProperty(location, "first_column");

            throw new ScriptException("CoffeeScript syntax error", "", firstLine.intValue(), firstColumn.intValue());
        } finally {
            Context.exit();
        }
    }
}
