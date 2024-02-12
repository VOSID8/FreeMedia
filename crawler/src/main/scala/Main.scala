
import zio._
import zio.http._
import zio.http.template._
import zio.{Random, Schedule, Scope, ZIO, ZIOAppArgs, ZIOAppDefault}


trait tryy {
  var stringArray: Array[String] = new Array[String](30)
  var c: Int = 0
  def canRetry = scala.util.Random.nextBoolean()
}
object Main extends ZIOAppDefault with tryy:
  private val app2 = Routes(
      Method.GET / "" -> handler {
        Handler.html {  
          html(
            div(b("News Web Crawler [Set to 30 results in 15sec Crawling]")),
            form(methodAttr := "GET",actionAttr := "/store",input(placeholderAttr:="type mediahouse...",nameAttr:="news"),input(placeholderAttr:="type topic...",nameAttr:="topic"),button(("submit"),typeAttr := "submit")),
          )}},
      Method.GET / "store" -> handler { (req: Request) =>
        val shouldCrawl = (_: URLL) => true
        val processResult = (url: URLL, _: String) => {
          if(c < 30) stringArray.update(c,url.toString)
          //println(stringArray(9))
          c = c+1
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
              _ <- ZIO.sleep(15.seconds)
              _ <- fiber.interrupt    
              //_    <- ZIO.when(c>10)(ZIO.fail(new Exception(s"amen"))).retry(Schedule.exponential(zio.Duration.fromSeconds(3)))
            } yield ()
          _ <- program2
          //.mapError(_ => Response.text("amen"))
        yield {
          println(stringArray.toList)
          val result = "Top 30 Results:"+ "\n" + stringArray.mkString("\n")
          c = 0
          Response.text(result)
        }
        program
      }).toHttpApp

  override val run = Server.serve(app2).provide(Server.defaultWithPort(8090))

