final case class CrawlState(visited: Set[URLL]) {
  def visit(url: URLL): CrawlState   = copy(visited = visited + url)
  def visitAll(urls: Iterable[URLL]) = copy(visited = visited ++ urls)
  def hasVisited(url: URLL): Boolean = visited.contains(url)
}

object CrawlState {
  val empty: CrawlState =
    CrawlState(Set.empty)
}
