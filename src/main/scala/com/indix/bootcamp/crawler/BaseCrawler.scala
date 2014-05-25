package com.indix.bootcamp.crawler

import edu.uci.ics.crawler4j.crawler.{Page, WebCrawler}
import edu.uci.ics.crawler4j.parser.HtmlParseData
import com.indix.bootcamp.parser.{JabongParser, Parser}
//import com.indix.bootcamp.parser.{JabongParser, Parser, FlipkartParser}
import java.io.{PrintWriter, File}
import scala.util.Random
import edu.uci.ics.crawler4j.url.WebURL

abstract class BaseCrawler extends WebCrawler {
  val parser: Parser
  val writer = new PrintWriter(new File("/tmp/crawler4j-scala/results-" + Random.nextInt(Int.MaxValue) + ".csv"))

  def excludeFilters = List(
    "(?i)(.*(\\.(pdf|flv))(\\?.*)*)$",
      ".*(\\.(css|js|bmp|gif|jpe?g"
      + "|png|tiff?|mid|mp2|mp3|mp4"
      + "|wav|avi|mov|mpeg|ram|m4v|pdf"
      + "|rm|smil|wmv|swf|wma|zip|rar|gz))$"
  )

  override def shouldVisit(url: WebURL): Boolean = {
    val urlStr = url.getURL
    !excludeFilters.exists(urlStr.matches)
  }

  override def visit(page: Page) {
    println(s"Fetched ${page.getWebURL.getURL} from ${page.getWebURL.getAnchor}")
    page.getParseData match {
      case data: HtmlParseData =>
        val result = parser.parse(data.getHtml, page.getWebURL.getURL)
        val checkProductPage = result.isValidProductPage
        println(s"Parsed successfully as ${result}")
        if(checkProductPage){
          println(s"Writing into csv file successfully as ${result}")
          writer.append(result.toCsv)
          writer.append("\n")
        }
    }
  }

  override def onBeforeExit() {
    writer.close()
  }
}
/*
class FlipkartCrawler extends BaseCrawler {
  override val parser: Parser = new FlipkartParser
}
*/
class JabongCrawler extends BaseCrawler {
  override val parser: Parser = new JabongParser
}