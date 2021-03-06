package kr.bydelta.koala.kkma

import kr.bydelta.koala.POS.POSTag
import kr.bydelta.koala.Processor
import kr.bydelta.koala.helper.UserDicReader
import kr.bydelta.koala.traits.CanUserDict
import org.snu.ids.ha.dic.{RawDicFileReader, SimpleDicFileReader, Dictionary => Dict}

import scala.collection.JavaConverters._

/**
  * Created by bydelta on 16. 7. 24.
  */
object Dictionary extends CanUserDict {
  val userdic = new UserDicReader
  var isDicChanged = false

  def addUserDictionary(dict: (String, POSTag)*) {
    if (dict.nonEmpty) {
      try {
        userdic ++=
          dict.map {
            case (word, integratedTag) => (word, Processor.KKMA originalPOSOf integratedTag)
          }
        isDicChanged = true
      } catch {
        case e: Exception =>
          e.printStackTrace()
      }
    }
  }

  def addUserDictionary(morph: String, tag: POSTag) {
    if (morph.length > 0) {
      try {
        userdic +=(morph, Processor.KKMA originalPOSOf tag)
        isDicChanged = true
      } catch {
        case e: Exception =>
          e.printStackTrace()
      }
    }
  }

  def reloadDic() {
    if (isDicChanged) {
      userdic.reset()
      Dict.reload(
        Seq(
          new SimpleDicFileReader("/dic/kcc.dic"),
          new SimpleDicFileReader("/dic/noun.dic"),
          new SimpleDicFileReader("/dic/person.dic"),
          new RawDicFileReader("/dic/raw.dic"),
          new SimpleDicFileReader("/dic/simple.dic"),
          new SimpleDicFileReader("/dic/verb.dic"),
          userdic
        ).asJava
      )
      isDicChanged = false
    }
  }
}
