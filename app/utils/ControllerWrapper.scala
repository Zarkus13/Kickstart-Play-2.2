package utils

import play.api.mvc._
import play.api.data.FormError
import play.api.libs.json.{JsObject, Json}
import scala.concurrent.Future

/**
 * Created with IntelliJ IDEA.
 * User: Alexis
 * Date: 24/06/13
 * Time: 22:51
 * To change this template use File | Settings | File Templates.
 */

class ControllerWrapper extends Controller {
    implicit val msgs: Msgs = new Msgs

    implicit def flashingMsgs(msgs: Msgs): List[(String, String)] = {
        val msgsInfos: String = msgs.infos.mkString(":::")
        val msgsErrors: String = msgs.errors.mkString(":::")

        var list: List[(String, String)] = Nil

        msgs.infos match {
            case Nil => {}
            case i: List[String] => { list = list :+ ("msgs-infos" -> i.mkString(":::")) }
        }

        msgs.errors match {
            case Nil => {}
            case e: List[String] => { list = list :+ ("msgs-errors" -> e.mkString(":::")) }
        }

        list
    }

    def parseMsgsFromFlash[A](key: String, f: String => Unit)(implicit req: Request[A]): Unit = {
        flash.get(key) match {
            case Some(m) => m.split(":::") foreach f
            case None => {}
        }
    }

    def ActionWrapper[A](action: Action[A]) = Action.async(action.parser) { implicit req =>
        msgs.cleanAll()
        parseMsgsFromFlash("msgs-infos", msgs.addInfo)
        parseMsgsFromFlash("msgs-errors", msgs.addError)

        action(req)
    }

    def errors(errors: Seq[FormError]): JsObject = {
        Json.obj(
            "errors" -> errors.map(e =>
                Json.obj(
                    "key" -> e.key,
                    "message" -> e.message
                )
            )
        )
    }


    // Permet de sécuriser une action en vérifiant
    object SecuredAction extends ActionBuilder[Request] {
        protected def invokeBlock[A](req: Request[A], action: (Request[A]) => Future[SimpleResult]): Future[SimpleResult] = {
            req.session.get("username").map({ u =>
                action(req)
            }).getOrElse(
                Future.successful(Forbidden)
            )
        }
    }
}
