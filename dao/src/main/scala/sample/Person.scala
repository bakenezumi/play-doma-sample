package sample

import java.util.OptionalInt

import scala.annotation.meta.field
import org.seasar.doma._

@Entity(immutable = true)
case class Person(
  @(Id@field)
  @(GeneratedValue@field)(strategy = GenerationType.IDENTITY)
  @ParameterName("id") id: OptionalInt = OptionalInt.empty,
  @(Column@field)(updatable = false)
  @ParameterName("name") name: Name,
  @ParameterName("age") age: OptionalInt,
  @ParameterName("address") address: Address,
  @(Version@field)
  @ParameterName("version") version: OptionalInt = OptionalInt.of(-1)
)
