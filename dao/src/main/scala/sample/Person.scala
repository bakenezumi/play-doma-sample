package sample

import java.util.OptionalInt

import scala.annotation.meta.field
import org.seasar.doma._

@Entity(immutable = true)
case class Person(
  @(Id@field)
  @(GeneratedValue@field)(strategy = GenerationType.IDENTITY)
  id: OptionalInt = OptionalInt.empty,
  @(Column@field)(updatable = false)
  name: Name,
  age: OptionalInt,
  address: Address,
  departmentId: OptionalInt,
  @(Version@field)
  version: OptionalInt = OptionalInt.of(-1)
)
