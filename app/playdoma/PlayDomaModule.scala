package playdoma

import org.seasar.doma.jdbc.tx.TransactionManager

import play.api.inject.Module
import play.api.{Configuration, Environment}

class PlayDomaModule extends Module {

 override def bindings(env: Environment, conf: Configuration) = Seq(
    bind[PlayDomaInitializer].toSelf.eagerly
  )

}
