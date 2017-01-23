package playdoma

import domala.ScalaDomaConfig
import javax.inject._
import play.api.db.Database

@Singleton
class PlayDomaInitializer @Inject() (db: Database) {
  ScalaDomaConfig.initialize(db.dataSource)
}

