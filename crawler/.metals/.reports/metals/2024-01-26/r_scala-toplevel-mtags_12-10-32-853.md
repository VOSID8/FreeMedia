error id: file:///C:/Projects/webcrawler/src/main/scala/WebCrawler.scala:[275..278) in Input.VirtualFile("file:///C:/Projects/webcrawler/src/main/scala/WebCrawler.scala", "import zio._

trait WebCrawler {
  def run(
    seeds: Set[URL],
    shouldCrawl: URL => Boolean,
    processResult: (URL, String) => UIO[Any]
  ): ZIO[Any, Nothing, Unit]
}

object WebCrawler {

  val live: ZLayer[Any,Nothing,WebCrawler] = 
    ???
  
  final case class

  def extractURLs(root: URL, html: String): Set[URL] = {
    val pattern = "href=[\"\']([^\"\']+)[\"\']".r

    scala.util.Try {
      val matches = (for (m <- pattern.findAllMatchIn(html)) yield m.group(1)).toSet

      for {
        m   <- matches
        url <- URL.make(m) ++ root.relative(m)
      } yield url
    }
      .getOrElse(Set.empty)
  }

}
")
file:///C:/Projects/webcrawler/src/main/scala/WebCrawler.scala
file:///C:/Projects/webcrawler/src/main/scala/WebCrawler.scala:18: error: expected identifier; obtained def
  def extractURLs(root: URL, html: String): Set[URL] = {
  ^
#### Short summary: 

expected identifier; obtained def