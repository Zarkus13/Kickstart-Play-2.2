package models

import org.squeryl._
import org.squeryl.PrimitiveTypeMode._
import play.api.libs.json.{JsValue, Writes}

/**
 * Created with IntelliJ IDEA.
 * User: Alexis
 * Date: 01/07/13
 * Time: 20:16
 * To change this template use File | Settings | File Templates.
 */
abstract class Model[PKType, ObjectType](implicit evidence: ObjectType <:< KeyedEntity[PKType]) extends KeyedEntity[PKType] with Writes[ObjectType] {
    def table: Table[ObjectType]

    def save(): Unit = {
        inTransaction {
            table.insertOrUpdate(this.asInstanceOf[ObjectType])
        }
    }

    def delete(): Unit = {
        inTransaction {
            table.delete(id)
        }
    }

    def toJson(): JsValue
}

trait StaticModel[PKType, ObjectType <: KeyedEntity[PKType]] {
    def table: Table[ObjectType]

    def all: List[ObjectType] = {
        inTransaction {
            from(table)(i => select(i)).toList
        }
    }

    def findById(id: PKType): Option[ObjectType] = {
        inTransaction {
            table.lookup(id)
        }
    }

    def deleteById(id: PKType): Boolean = {
        inTransaction {
            table.delete(id)
        }
    }
}

object DB extends Schema {
//    val posts = table[Post]("posts")
//    val newsPosts = table[NewsPost](assets"posts")
//    val testPosts = table[TestPost]("posts")
//    val articlePosts = table[ArticlePost]("posts")
//    val pages = table[Page]("pages")
//
//    val newsToPages = oneToManyRelation(newsPosts, pages).via((news, page) => news.id === page.postId)
//    val testsToPages = oneToManyRelation(testPosts, pages).via((test, page) => test.id === page.postId)
//    val articlesToPages = oneToManyRelation(articlePosts, pages).via((article, page) => article.id === page.postId)
}
