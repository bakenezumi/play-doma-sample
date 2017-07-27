package sample

import java.util.OptionalInt

import scala.annotation.meta.field
import org.seasar.doma._

@Entity(immutable = true)
case class PersonDepartment(
  id: OptionalInt,
  name: Name,
  departmentId: OptionalInt,
  departmentName: Name
)
