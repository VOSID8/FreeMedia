file:///C:/Projects/webcrawler/src/main/scala/Main.scala
### dotty.tools.dotc.core.TypeError$$anon$1: Toplevel definition app is defined in
  C:/Projects/webcrawler/src/main/scala/Client.scala
and also in
  C:/Projects/webcrawler/src/main/scala/Main.scala
One of these files should be removed from the classpath.

occurred in the presentation compiler.

action parameters:
offset: 1024
uri: file:///C:/Projects/webcrawler/src/main/scala/Main.scala
text:
```scala

import zio._

import zio.http.Header.{AccessControlAllowMethods, AccessControlAllowOrigin,AccessControlAllowHeaders, Origin}
import zio.http.Middleware.{CorsConfig, cors}
import zio.http._

object Main extends ZIOAppDefault {

  val port: Int = 9000

  val config: CorsConfig =
    CorsConfig(
      allowedOrigin = {
        case origin @ Origin.Value(_, host, _) if host == "dev" => Some(AccessControlAllowOrigin.Specific(origin))
        case _                                                  => None
      },
      allowedMethods = AccessControlAllowMethods(Method.PUT, Method.DELETE),
      allowedHeaders =  AccessControlAllowHeaders("*"),
      //allowedHeaders =  Some(Set("*")),
    )

  // val corsConfig =
  //   CorsConfig(
  //     anyOrigin = true,
  //     anyMethod = true,
  //     allowedOrigins = s => s.equals("localhost:5500"),
  //     allowedHeaders =  Some(Set("*"))
  //     // allowedMethods = Some(Set(Method.GET, Method.POST))
  //   )

  val app: HttpApp[Any] = @@Routes(
    Method.GET / "fruits" / "a" -> handler { (req: Request) =>
      Response.text("URL:" + req.url.path.toString + " Headers: " + req.headers)
    },

    Method.POST / "fruits" / "a" -> handler { (req: Request) =>
      req.body.asString.map(Response.text(_))
    }
  ).toHttpApp @@ cors(config)

  val seeds =
    Set(URLL.make("https://www."+"nytimes"+".com/india").get)

  val shouldCrawl = (_: URLL) => true

  val processResult =
    (url: URLL, _: String) => Console.printLine(url.toString).orDie
  
  val program =
    for {
      _ <- Server.serve(app).provide(Server.default)
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

```
#### Short summary: 

dotty.tools.dotc.core.TypeError$$anon$1: Toplevel definition app is defined in
  C:/Projects/webcrawler/src/main/scala/Client.scala
and also in
  C:/Projects/webcrawler/src/main/scala/Main.scala
One of these files should be removed from the classpath.