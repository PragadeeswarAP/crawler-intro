package com.indix.bootcamp.parser

import com.indix.bootcamp.models.{Product, Price, Result}
import org.jsoup.nodes.Document

class JabongParser extends Parser {
  override def parseProduct(document: Document, pageUrl: String): Product = {
    val title = document.select("title").text().split("-")(0)
    val description = document.select("meta[name=description]").attr("content")
    Product(title, description, pageUrl)
  }

  override def parsePrice(document: Document): Price = {
    val salePrice = document.select("span[itemprop=price]").text().toDouble
    val listPriceTry = document.select("span[class=striked-price fs14 c222 d-inline mt5]").text()
    var listPrice = 0.0
    if(listPriceTry == ""){
      listPrice = salePrice
    }
    else{
      listPrice = listPriceTry.split(" ")(1).toDouble
    }
    Price(listPrice,salePrice)
  }
}

class FlipkartParser extends Parser {
  override def parseProduct(document: Document, pageUrl: String): Product = {
    val title = document.select("h1[itemprop=name]").text()
    val description = document.select("#specifications").text()
    Product(title, description, pageUrl)
  }

  // TODO: Fix the price Extraction
  override def parsePrice(document: Document): Price = {
    val salePrice = document.select("meta[itemprop=price]").attr("content").toDouble
    val listPricetry = document.select(".old-price").text()
    var listPrice = 0.0
    if(listPricetry == ""){
      listPrice = salePrice
    }
    else{
      listPrice = listPricetry.split(" ")(1).toDouble
    }
    Price(listPrice,salePrice)
  }
}
