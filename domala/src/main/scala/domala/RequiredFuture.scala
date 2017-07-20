package domala

import java.util.function.Supplier

import scala.concurrent.{ExecutionContext, Future}
import scala.compat.java8.FunctionConverters._  

object RequiredFuture {

  def apply[T](body: => T)(implicit ec: ExecutionContext): Future[T] = Future {
      ScalaDomaConfig.singleton.getTransactionManager.required((() => body).asJava)
  }

}

