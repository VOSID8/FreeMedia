file:///C:/Projects/webcrawler/src/main/scala/Main.scala
### java.lang.IndexOutOfBoundsException: 0

occurred in the presentation compiler.

action parameters:
offset: 309
uri: file:///C:/Projects/webcrawler/src/main/scala/Main.scala
text:
```scala

import zio._
import zio.http._
import zio.http.template._

trait tryy {
  val stringArray: Array[String] = new Array[String](10)
  var c: Int = 0
  // val checking: Unit = {
  //           if(c > 10) ZIO.succeed("Done")
  //           else checking
  //         }
  val checking = ZIO.when(c>10)(@@succeed("Done")
            
}

object Main extends ZIOAppDefault with tryy:
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
          if(c < 10) stringArray.update(c,url.toString)
          else println("Im so done")
          //stringArray.update(c,url.toString)
          c = c+1
          //println(url.toString.getClass)
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
              //_     <- ZIO.loop(1)(c >10,true)(c => if(c>10) ZIO.succeed(fiber.interrupt) else {})

              _     <- checking
              _     <- Console.readLine("The end")
              _     <- fiber.interrupt
              
            } yield ()
          _ <- program2.mapError(_ => Response.text("crawl failed"))
        yield {
          println(stringArray.toList)
          val result = stringArray.mkString("\n")
          Response.text(result)
        }
        program
      }).toHttpApp

  override val run = Server.serve(app2).provide(Server.defaultWithPort(9000))



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


```



#### Error stacktrace:

```
scala.collection.LinearSeqOps.apply(LinearSeq.scala:131)
	scala.collection.LinearSeqOps.apply$(LinearSeq.scala:128)
	scala.collection.immutable.List.apply(List.scala:79)
	dotty.tools.dotc.util.Signatures$.countParams(Signatures.scala:501)
	dotty.tools.dotc.util.Signatures$.applyCallInfo(Signatures.scala:186)
	dotty.tools.dotc.util.Signatures$.computeSignatureHelp(Signatures.scala:94)
	dotty.tools.dotc.util.Signatures$.signatureHelp(Signatures.scala:63)
	scala.meta.internal.pc.MetalsSignatures$.signatures(MetalsSignatures.scala:17)
	scala.meta.internal.pc.SignatureHelpProvider$.signatureHelp(SignatureHelpProvider.scala:51)
	scala.meta.internal.pc.ScalaPresentationCompiler.signatureHelp$$anonfun$1(ScalaPresentationCompiler.scala:388)
```
#### Short summary: 

java.lang.IndexOutOfBoundsException: 0