package kr.bydelta.koala.single

import kr.bydelta.koala.POS
import kr.bydelta.koala.twt.{Dictionary, Tagger}

import scala.io.StdIn

/**
  * Created by bydelta on 16. 7. 22.
  */
object Twitter {
  def main(args: Array[String]) {
    Dictionary.addUserDictionary("은전한닢" -> POS.NNG, "꼬꼬마" -> POS.NNG, "구글하" -> POS.VV)
    val tagger = new Tagger

    var line = ""
    do {
      print("Input a sentence >> ")
      line = StdIn.readLine()
      if (line.nonEmpty) {
        println("품사 부착...")
        tagger.tagParagraph(line).foreach {
          sent =>
            println(sent.singleLineString)
            println()
        }
      }
    } while (line.nonEmpty)
  }
}
