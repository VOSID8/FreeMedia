error id: file:///C:/Projects/webcrawler/src/main/scala/Main.scala:[2848..2854) in Input.VirtualFile("file:///C:/Projects/webcrawler/src/main/scala/Main.scala", "
import zio._
import zio.http._
import zio.http.template._

// object Main extends ZIOAppDefault  {
//   val port: Int = 9000
//   val app2 = 
//     Routes(
//       Method.GET / "" -> handler { 
//         Handler.html {
//         html(
//           form(methodAttr := "GET",actionAttr := "/store",input(nameAttr:="news"),input(nameAttr:="topic"),button(typeAttr := "submit")),          
//         )}},
//       Method.GET / "store" -> handler { (req: Request) =>
//         val news = req.url.queryParams.map.get("news").get.asString
//         val topic = req.url.queryParams.map.get("topic").get.asString
//         val seeds = Set(URLL.make("https://www."+news+".com/"+topic).get)
//         println(news+" "+topic)
//         val shouldCrawl = (_: URLL) => true
//         val processResult = (url: URLL, _: String) => Console.printLine(url.toString).orDie
//         val program2 = 
//           for {
//             _ <- Console.printLine("meh")
//             fiber <- WebCrawler.run(seeds, shouldCrawl, processResult).provide(
//               WebCrawler.live,
//               Web.live).fork
//             _     <- Console.readLine("Press Enter to exit!")
//             _     <- fiber.interrupt
//           } yield()
//         val run = program2
//         Response.text("wtf")
//       }).toHttpApp

//   val program =
//     for {
//       entry <- Server.serve(app2).provide(Server.defaultWithPort(port))
//     } yield ExitCode.success
//   override val run = program
// }

// object Main extends ZIOAppDefault  {
//   val port: Int = 9000
//   val app2 = 
//     Routes(
//       Method.GET / "" -> handler { 
//         Handler.html {
//         html(
//           form(methodAttr := "GET",actionAttr := "/store",input(nameAttr:="news"),input(nameAttr:="topic"),button(typeAttr := "submit")),          
//         )}},
//       Method.GET / "store" -> handler { (req: Request) =>
//         val news = req.url.queryParams.map.get("news").get.asString
//         val topic = req.url.queryParams.map.get("topic").get.asString
//         println(news+" "+topic)
//         Response.text("wtf")
//       }).toHttpApp
//   val seeds = Set(URLL.make("https://www."+"nytimes"+".com/"+"hii").get)    
//   val shouldCrawl = (_: URLL) => true
//   val processResult = (url: URLL, _: String) => Console.printLine(url.toString).orDie

//   val program =
//     for {
//       entry <- Server.serve(app2).provide(Server.defaultWithPort(port)).fork
//       fiber <- WebCrawler.run(seeds, shouldCrawl, processResult).provide(
//               WebCrawler.live,
//               Web.live).fork
//       _     <- Console.readLine("Press Enter to exit!")
//       _     <- fiber.interrupt
//     } yield ExitCode.success
//   override val run = program
// }

trait 

object Main extends ZIOAppDefault:
  private val app2 = Routes(
      Method.GET / "" -> handler {
        Handler.html {  
          html(
            div(b("News Web Crawler")),
            form(methodAttr := "GET",actionAttr := "/store",input(placeholderAttr:="type mediahouse...",nameAttr:="news"),input(placeholderAttr:="type topic...",nameAttr:="topic"),button(("submit"),typeAttr := "submit")),
          )}},
      Method.GET / "store" -> handler { (req: Request) =>
        val shouldCrawl = (_: URLL) => true
        val processResult = (url: URLL, _: String) => {
          stringArray :+ url.toString
          Response.text("hii")
          Console.printLine(url.toString).orDie
        }
        val program: ZIO[Any, Response, Response] = for
          news <- ZIO.fromOption(
            req.url.queryParams.map.get("news")
              .flatMap(_.headOption)
          ).mapError(_ => Response.text("news not found"))

          topic <- ZIO.fromOption(
            req.url.queryParams.map.get("topic")
              .flatMap(_.headOption)
          ).mapError(_ => Response.text("topic not found"))

          urll <- ZIO.fromOption(URLL.make("https://www."+news+".com/"+topic))
            .mapError(_ => Response.text("url parsing failed"))
          seeds = Set(urll)
          _ <- Console.printLine(news+" "+topic).orDie
          
          program2 = for {
              fiber <- WebCrawler.run(seeds, shouldCrawl, processResult)
                .provide(
                  WebCrawler.live,
                  Web.live).fork
              _     <- Console.readLine("Press Enter to exit!")
              _     <- fiber.interrupt
            } yield()
          _ <- program2.mapError(_ => Response.text("crawl failed"))
        yield {
          //println(stringArray)
          Response.text("wtf")
        }

        program
      }).toHttpApp

  override val run = Server.serve(app2).provide(Server.defaultWithPort(9000))
")
file:///C:/Projects/webcrawler/src/main/scala/Main.scala
file:///C:/Projects/webcrawler/src/main/scala/Main.scala:75: error: expected identifier; obtained object
object Main extends ZIOAppDefault:
^
#### Short summary: 

expected identifier; obtained object