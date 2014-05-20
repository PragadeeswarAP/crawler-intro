package com.indix.bootcamp.parser

import org.scalatest.{Matchers, FunSuite}
import com.indix.bootcamp.utils.TestUtils

class JabongParserTest extends FunSuite with Matchers with TestUtils {

  test("should parse product page1") {
    val document = readDocument("/jabong/jabong_1.html")
    val parser = new JabongParser
    val parsedProduct = parser.parseProduct(document, "http://www.jabong.com/swayam-Signature-Multi-Bedsheets-503652.html")
    parsedProduct.name should be("Swayam Signature Multi Bedsheets ")
    parsedProduct.description should include("Shop Swayam Signature Multi Bedsheets online at Jabong.com. ✔ Free Shipping ✔ Cash on Delivery (COD) ✔ 30 Days Return")
  }

  test("should parse prices from product page1") {
    val document = readDocument("/jabong/jabong_1.html")
    val parser = new JabongParser
    val parsedPrice = parser.parsePrice(document)
    parsedPrice.listPrice should be(879.0)
    parsedPrice.salePrice should be(747.0)
  }

  test("should parse product page2") {
    val document = readDocument("/jabong/jabong_2.html")
    val parser = new JabongParser
    val parsedProduct = parser.parseProduct(document, "http://www.jabong.com/Adidas-Isolation-Black-Basketball-Shoes-189314.html?pos=2")
    parsedProduct.name should be("Adidas Isolation Black Basketball Shoes ")
    parsedProduct.description should include("Shop Adidas Isolation Black Basketball Shoes online at Jabong.com. ✔ Free Shipping ✔ Cash on Delivery (COD) ✔ 30 Days Return")
  }

  test("should parse prices from product page2") {
    val document = readDocument("/jabong/jabong_2.html")
    val parser = new JabongParser
    val parsedPrice = parser.parsePrice(document)
    parsedPrice.listPrice should be(4599.0)
    parsedPrice.salePrice should be(4599.0)
  }
}