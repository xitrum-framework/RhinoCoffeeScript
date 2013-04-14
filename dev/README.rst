Generate CoffeeScript.class
---------------------------

Comment out the line ``autoScalaLibrary := false`` in build.sbt.

Run in ``sbt console``:

::

  org.mozilla.javascript.tools.jsc.Main.main(
    "-opt 9 -nosource -o CoffeeScript -package tv.cntt.rhinocoffeescript coffee-script.js".split(" ")
  )

``sbt compile`` then replace CoffeeScript.class:

::

  cp tv/cntt/rhinocoffeescript/CoffeeScript.class target/classes/tv/cntt/rhinocoffeescript/

Now we can ``sbt package``, ``sbt publish-local`` etc.

Publish to Sonatype
-------------------

See:
https://github.com/sbt/sbt.github.com/blob/gen-master/src/jekyll/using_sonatype.md

1. Copy content of
     dev/build.sbt.end   to the end of build.sbt
     dev/plugins.sbt.end to the end of project/plugins.sbt
2. Run ``sbt publish-signed``.
3. Login at https://oss.sonatype.org/ and from "Staging Repositories" select the
   newly published item, click "Close" then "Release".
