package com.indix.bootcamp.parser

//import com.indix.bootcamp.models.{Product, Price, Result}
import com.indix.bootcamp.models.{Product, Price}
import org.jsoup.nodes.Document

class JabongParser extends Parser {
  override def parseProduct(document: Document, pageUrl: String): Product = {
    val title = document.select("title").text().split("-")(0)
    val description = document.select("div[id=productInfo] > p").text()
    Product(title, description, pageUrl)
  }

  override def parsePrice(document: Document): Price = {
    val salePrice = document.select("span[itemprop=price]").text().toDouble
    val listPrice = document.select("span[class=striked-price fs14 c222 d-inline mt5]").text()
    if(listPrice == "")
      Price(salePrice,salePrice)
    else
      Price(listPrice.toDouble,salePrice)
  }
}
/*
class FlipkartParser extends Parser {
  override def parseProduct(document: Document, pageUrl: String): Product = {
    val title = document.select("h1[itemprop=name]").text()
    val description = document.select("#specifications").text()
    Product(title, description, pageUrl)
  }

  override def parsePrice(document: Document): Price = {
    val salePrice = document.select("meta[itemprop=price]").attr("content").toDouble
    val listPrice = document.select(".old-price").text()
    if(listPrice == "")
      Price(salePrice,salePrice)
    else
      Price(listPrice.toDouble,salePrice)
  }
}
*/