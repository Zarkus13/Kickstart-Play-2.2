package utils

/**
 * Created with IntelliJ IDEA.
 * User: Alexis
 * Date: 24/06/13
 * Time: 22:52
 * To change this template use File | Settings | File Templates.
 */

class Msgs {
    var infos: List[String] = Nil
    var errors: List[String] = Nil

    def addInfo(info: String): Unit = {
        infos = infos :+ info
    }

    def addError(error: String): Unit = {
        errors = errors :+ error
    }

    def cleanInfos(): Unit = {
        infos = Nil
    }

    def cleanErrors(): Unit = {
        errors = Nil
    }

    def cleanAll(): Unit = {
        cleanInfos()
        cleanErrors()
    }
}
