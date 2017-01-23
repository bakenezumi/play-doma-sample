package sample

import beans.BeanProperty
import org.seasar.doma.Domain

@Domain(valueType = classOf[String])
case class Name(@BeanProperty value: String)
