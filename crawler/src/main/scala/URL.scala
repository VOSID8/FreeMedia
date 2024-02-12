final class URLL private (private val parsed: java.net.URL) { self =>

  override def equals(that: Any): Boolean =
    that match {
      case that: URLL => this.parsed == that.parsed
      case _         => false
    }

  override def hashCode: Int =
    parsed.hashCode

  def relative(page: String): Option[URLL] =
    try Some(new URLL(new java.net.URL(parsed, page)))
    catch {
      case t: VirtualMachineError => throw t
      case _: Throwable           => None
    }

  override def toString: String =
    url

  def url: String =
    parsed.toString
}

object URLL {

  def make(url: String): Option[URLL] =
    try Some(new URLL(new java.net.URL(url)))
    catch {
      case t: VirtualMachineError => throw t
      case _: Throwable           => None
    }

}
