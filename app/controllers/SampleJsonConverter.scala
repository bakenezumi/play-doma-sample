package controllers

import java.util.OptionalInt

import org.seasar.doma.jdbc.Result

import play.api.libs.json._
import play.api.libs.functional.syntax._

import sample._

object OptionalIntConverter {
  implicit val writesInteger = Writes[OptionalInt] { (d: OptionalInt) =>
    if (d.isPresent) JsNumber(d.getAsInt) else JsNull
  }
  implicit val readsInteger = Reads[OptionalInt] { (json: JsValue) =>
    json match {
      case JsNull => JsSuccess(OptionalInt.empty)
      case _ => json.validate[Int] map(d => OptionalInt.of(d))
    }
  }
}

object PersonConverter {

  implicit val writesName = Writes[Name] { name => JsString(name.value) }
  implicit val readsName = Reads[Name] { json => json.validate[String] map (name => Name(name)) }

  implicit def writesAddress = Json.writes[Address]
  implicit def readsAddrress = Json.reads[Address]

  import OptionalIntConverter._
  implicit def writesPerson =  (
    (__ \ "id").write[OptionalInt] ~
    (__ \ "name").write[Name] ~
    (__ \ "age").write[OptionalInt] ~
    (__ \ "address").write[Address] ~
    (__ \ "departmentId").write[OptionalInt] ~
    (__ \ "version").write[OptionalInt]
  )(unlift(Person.unapply))
  implicit def readsPerson = (
    ((__ \ "id").read[OptionalInt] or Reads.pure(OptionalInt.empty())) ~
    (__ \ "name").read[Name] ~
    (__ \ "age").read[OptionalInt] ~
    (__ \ "address").read[Address] ~
    (__ \ "departmentId").read[OptionalInt] ~
    ((__ \ "version").read[OptionalInt] or Reads.pure(OptionalInt.of(-1)))
  )(Person)

  implicit def writesPersonDepartment =  (
    (__ \ "id").write[OptionalInt] ~
    (__ \ "name").write[Name] ~
    (__ \ "departmentId").write[OptionalInt] ~
    (__ \ "departmentName").write[Name]
  )(unlift(PersonDepartment.unapply))
  implicit def readsPersonDepartment = (
    ((__ \ "id").read[OptionalInt] or Reads.pure(OptionalInt.empty())) ~
    (__ \ "name").read[Name] ~
    (__ \ "departmentId").read[OptionalInt] ~
    (__ \ "departmentName").read[Name]
  )(PersonDepartment)

  implicit val writesDomaResult = new Writes[Result[Person]] {
    def writes(r: Result[Person]): JsValue = {
      Json.obj(
        "entity" -> r.getEntity,
        "count" -> r.getCount
      )
    }
  }

}

