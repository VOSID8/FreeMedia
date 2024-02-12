error id: file:///C:/Projects/webcrawler/src/main/scala/Main.scala:[268..275) in Input.VirtualFile("file:///C:/Projects/webcrawler/src/main/scala/Main.scala", "
import zio._
import play.api._
import play.api.mvc._

// import zio.http.Header.{AccessControlAllowMethods, AccessControlAllowOrigin,AccessControlAllowHeaders, Origin}
// import zio.http.Middleware.{CorsConfig, cors}
// import zio.http._

@Singleton
class  extends ZIOAppDefault  {

  val port: Int = 9000
  val seeds =
    Set(URLL.make("https://www."+"nytimes"+".com/india").get)

  val shouldCrawl = (_: URLL) => true

  val processResult =
    (url: URLL, _: String) => Console.printLine(url.toString).orDie
  
  val program =
    for {
      //_ <- Server.serve(app2).provide(Server.defaultWithPort(port))
      fiber <- WebCrawler.run(seeds, shouldCrawl, processResult).fork
      _     <- Console.readLine("Press Enter to exit!")
      _     <- fiber.interrupt
    } yield ExitCode.success

  override val run =
    program.provide(
      WebCrawler.live,
      Web.live
    )
}

// val config: CorsConfig =
  // CorsConfig(
  //   allowedOrigin = {
  //     case origin @ Origin.Value(_, host, _) if host == "http://localhost:5500" => Some(AccessControlAllowOrigin.Specific(origin))
  //     case _                                                             => None
  //   },
  //   allowedMethods = AccessControlAllowMethods(Method.PUT, Method.DELETE,Method.OPTIONS),
  //   allowedHeaders = AccessControlAllowHeaders("*")
  // )
  // val app: HttpApp[Any] = Routes(
  //   Method.GET / "store" -> handler { (req: Request) =>
  //     Response.text("hii")},
  //   Method.POST / "store" -> handler { (req: Request) =>
  //     Response.text("wtf")}
  // ).toHttpApp @@ cors(config)")
file:///C:/Projects/webcrawler/src/main/scala/Main.scala
file:///C:/Projects/webcrawler/src/main/scala/Main.scala:11: error: expected identifier; obtained extends
class  extends ZIOAppDefault  {
       ^
#### Short summary: 

expected identifier; obtained extends