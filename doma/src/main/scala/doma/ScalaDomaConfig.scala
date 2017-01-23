package domala

import javax.sql.DataSource

import org.seasar.doma.SingletonConfig
import org.seasar.doma.jdbc.Config
import org.seasar.doma.jdbc.dialect._
import org.seasar.doma.jdbc.tx._
  
object ScalaDomaConfig {
  private var CONFIG: Option[ScalaDomaConfig] = None
  def initialize(dataSource: DataSource, dialect: Dialect = new H2Dialect) = {
    CONFIG = Some(new ScalaDomaConfig(dataSource, dialect))
  }
  def singleton = CONFIG.getOrElse(throw new RuntimeException("ScalaDomaConfig is not initialized."))
}

@SingletonConfig
class ScalaDomaConfig private (ds: DataSource, dialect: Dialect) extends Config {

  getSqlFileRepository.clearCache

  val dataSource = new LocalTransactionDataSource(ds)

  val transactionManager = new LocalTransactionManager(dataSource.getLocalTransaction(getJdbcLogger))

  override def getDataSource = dataSource
  override def getDialect = dialect
  override def getTransactionManager = transactionManager

}
