package domala

import java.util.function.Supplier
import scala.concurrent.{ExecutionContext, Future}

object RequiredFuture {

  def apply[T](body: => T)(implicit ec: ExecutionContext): Future[T] = Future {
    ScalaDomaConfig.singleton.getTransactionManager.required(() => body)
  }

}

