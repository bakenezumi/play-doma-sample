package sample

import org.seasar.doma.Embeddable

@Embeddable
case class Address(city: String, street: String)
