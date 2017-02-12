package ch.theza

case class Pageview(
  eventId: String, 
  timestamp: String, 
  url: String)

trait PageviewFormat {
  def toMessageValue(pageview: Pageview): String = 
    s"${pageview.eventId},${pageview.timestamp},${pageview.url}"

  def fromMessageValue(value: String): Pageview = {
    val Array(eventId, timestamp, url) = value split ",\\s*"
    Pageview(eventId, timestamp, url)
  }
}

object PageviewFormat extends PageviewFormat
