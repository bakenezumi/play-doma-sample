package domala

import java.util.function.Supplier

import scala.concurrent.{ExecutionContext, Future}
import scala.compat.java8.FunctionConverters._  

object ScalaDomaHelper  {

  def requiredFuture[T](block: () => T)(implicit ec: ExecutionContext) = Future {
      ScalaDomaConfig.singleton.getTransactionManager.required(block.asJava)
  }

}

