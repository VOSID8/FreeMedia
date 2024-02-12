file:///C:/Projects/webcrawler/src/main/scala/Main.scala
### java.lang.NoClassDefFoundError: sourcecode/Name

occurred in the presentation compiler.

action parameters:
offset: 1582
uri: file:///C:/Projects/webcrawler/src/main/scala/Main.scala
text:
```scala

import zio._

import zio.http.Header.{AccessControlAllowMethods, AccessControlAllowOrigin,AccessControlAllowHeaders, Origin}
import zio.http.Middleware.{CorsConfig, cors}
import zio.http._
import zio.http.template._

object Main extends ZIOAppDefault {

  val port: Int = 9000

  val config: CorsConfig =
  CorsConfig(
    allowedOrigin = {
      case origin @ Origin.Value(_, host, _) if host == "http://localhost:5500" => Some(AccessControlAllowOrigin.Specific(origin))
      case _                                                             => None
    },
    allowedMethods = AccessControlAllowMethods(Method.PUT, Method.DELETE,Method.OPTIONS),
    allowedHeaders = AccessControlAllowHeaders("*")
  )
  val app: HttpApp[Any] = Routes(
    Method.GET / "store" -> handler { (req: Request) =>
      Response.text("hii")},
    Method.POST / "store" -> handler { (req: Request) =>
      Response.text("wtf")}
  ).toHttpApp @@ cors(config)

   def app2: HttpApp[Any] = {
    // Html response takes in a `Html` instance.
    Handler.html {

      // Support for default Html tags
      html(
        // Support for child nodes
        head(
          title("ZIO Http"),
        ),
        body(
          div(
            // Support for css class names
            css := "container" :: "text-align-left" :: Nil,
            h1("Hello World"),
            ul(
              // Support for inline css
              styles := Seq("list-style" -> "none"),
              <input type="text" id="textInput" placeholder="Type something..."></i@@,
              li(
                a(href := "/hello/world/again", "Hello World Again"),
              ),

              // Support for Seq of Html elements
              (2 to 10) map { i =>
                li(
                  a(href := s"/hello/world/i", s"Hello World $i"),
                )
              },
            ),
          ),
        ),
      )
    }
  }.toHttpApp

  val seeds =
    Set(URLL.make("https://www."+"nytimes"+".com/india").get)

  val shouldCrawl = (_: URLL) => true

  val processResult =
    (url: URLL, _: String) => Console.printLine(url.toString).orDie
  
  val program =
    for {
      _ <- Server.serve(app2).provide(Server.defaultWithPort(port))
      fiber <- WebCrawler.run(seeds, shouldCrawl, processResult).fork
      _     <- Console.readLine("Press Enter to exit!")
      _     <- fiber.interrupt
    } yield ExitCode.success

  val run =
    program.provide(
      WebCrawler.live,
      Web.live
    )
}
```



#### Error stacktrace:

```
scala.meta.internal.tokenizers.XmlParser$Xml$.UnpStart(XmlParser.scala:48)
	scala.meta.internal.tokenizers.XmlParser$Xml$.Unparsed(XmlParser.scala:47)
	scala.meta.internal.tokenizers.XmlParser$Xml$.XmlContent(XmlParser.scala:43)
	scala.meta.internal.tokenizers.XmlParser.$anonfun$XmlExpr$1(XmlParser.scala:24)
	scala.meta.shaded.internal.fastparse.internal.RepImpls$.rec$4(RepImpls.scala:226)
	scala.meta.shaded.internal.fastparse.internal.RepImpls$.rep$extension(RepImpls.scala:266)
	scala.meta.shaded.internal.fastparse.package$ByNameOps$.rep$extension(package.scala:202)
	scala.meta.internal.tokenizers.XmlParser.XmlExpr(XmlParser.scala:24)
	scala.meta.internal.tokenizers.LegacyScanner.$anonfun$getXml$2(LegacyScanner.scala:932)
	scala.meta.shaded.internal.fastparse.SharedPackageDefs.parseInputRaw(SharedPackageDefs.scala:69)
	scala.meta.shaded.internal.fastparse.SharedPackageDefs.parseInputRaw$(SharedPackageDefs.scala:45)
	scala.meta.shaded.internal.fastparse.package$.parseInputRaw(package.scala:6)
	scala.meta.shaded.internal.fastparse.Parsed$Extra.trace(Parsed.scala:139)
	scala.meta.shaded.internal.fastparse.Parsed$Extra.traced(Parsed.scala:118)
	scala.meta.internal.tokenizers.LegacyScanner.getXml(LegacyScanner.scala:936)
	scala.meta.internal.tokenizers.LegacyScanner.fetchLT$1(LegacyScanner.scala:295)
	scala.meta.internal.tokenizers.LegacyScanner.fetchToken(LegacyScanner.scala:303)
	scala.meta.internal.tokenizers.LegacyScanner.nextToken(LegacyScanner.scala:211)
	scala.meta.internal.tokenizers.LegacyScanner.foreach(LegacyScanner.scala:1011)
	scala.meta.internal.tokenizers.ScalametaTokenizer.uncachedTokenize(ScalametaTokenizer.scala:24)
	scala.meta.internal.tokenizers.ScalametaTokenizer.$anonfun$tokenize$1(ScalametaTokenizer.scala:17)
	scala.collection.concurrent.TrieMap.getOrElseUpdate(TrieMap.scala:962)
	scala.meta.internal.tokenizers.ScalametaTokenizer.tokenize(ScalametaTokenizer.scala:17)
	scala.meta.internal.tokenizers.ScalametaTokenizer$$anon$2.apply(ScalametaTokenizer.scala:332)
	scala.meta.tokenizers.Api$XtensionTokenizeDialectInput.tokenize(Api.scala:25)
	scala.meta.tokenizers.Api$XtensionTokenizeInputLike.tokenize(Api.scala:14)
	scala.meta.internal.pc.completions.KeywordsCompletions$.reverseTokens$lzyINIT1$1(KeywordsCompletions.scala:50)
	scala.meta.internal.pc.completions.KeywordsCompletions$.reverseTokens$1(KeywordsCompletions.scala:54)
	scala.meta.internal.pc.completions.KeywordsCompletions$.contribute(KeywordsCompletions.scala:56)
	scala.meta.internal.pc.completions.Completions.completions(Completions.scala:187)
	scala.meta.internal.pc.completions.CompletionProvider.completions(CompletionProvider.scala:86)
	scala.meta.internal.pc.ScalaPresentationCompiler.complete$$anonfun$1(ScalaPresentationCompiler.scala:136)
```
#### Short summary: 

java.lang.NoClassDefFoundError: sourcecode/Name