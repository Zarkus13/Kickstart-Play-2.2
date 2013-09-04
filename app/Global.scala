import play.api.{Application, GlobalSettings}
import org.squeryl._
import org.squeryl.adapters.MySQLAdapter
import play.api.db.DB

/**
 * Created with IntelliJ IDEA.
 * User: Alexis
 * Date: 01/07/13
 * Time: 20:56
 * To change this template use File | Settings | File Templates.
 */

object Global extends GlobalSettings {

    override def onStart(app: Application) {
        Class.forName("com.mysql.jdbc.Driver")

        SessionFactory.concreteFactory = Some(() => Session.create(
            DB.getConnection()(app),
            new MySQLAdapter)
        )
    }
}