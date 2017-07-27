package playdoma

import domala.ScalaDomaConfig
import javax.inject._
import org.seasar.doma.jdbc.Naming
import play.api.db.Database

@Singleton
class PlayDomaInitializer @Inject() (db: Database) {
  ScalaDomaConfig.initialize(dataSource = db.dataSource, naming = Naming.SNAKE_LOWER_CASE)
}

