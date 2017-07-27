package controllers

import javax.inject._

import play.api.mvc._
import play.api.libs.json._

import scala.collection.JavaConverters._
import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}
import scala.compat.java8.OptionConverters._

import domala.RequiredFuture

import sample._

import PersonConverter._

@Singleton
class SampleController @Inject() (val controllerComponents: ControllerComponents)(implicit exec: ExecutionContext) extends BaseController {

  lazy val dao = new PersonDaoImpl

  def selectById(id: Int) = Action.async {
    RequiredFuture {
      dao.selectById(id)
    } map { result => 
      result.asScala match {
        case Some(person) => Ok(Json.toJson(person))
        case None => NotFound("not found.")
      }
    }
  }

  def selectWithDeparmentById(id: Int) = Action.async {
    RequiredFuture {
      dao.selectWithDeparmentById(id)
    } map { result => 
      result.asScala match {
        case Some(person) => Ok(Json.toJson(person))
        case None => NotFound("not found.")
      }
    }
  }

  def selectAll = Action.async {
    RequiredFuture {
      dao.selectAll()
    } map { persons =>
      Ok(Json.toJson(persons.asScala))
    }
  }

  implicit def as(request: Request[AnyContent]) = new {
    def asPerson = request.body.asJson.map(_.as[Person])
      .getOrElse(throw new RuntimeException("Request body colud not parse"))
  }

  def insert = Action.async { request => {
    RequiredFuture {
      dao.insert(request.asPerson)
    } map { result =>
      Ok(Json.toJson(result))
    }
  }}

  def update = Action.async { request => {
    RequiredFuture {
      dao.update(request.asPerson)
    } map { result =>
      Ok(Json.toJson(result))
    }
  }}

  def delete(id: Int) = Action.async {
    RequiredFuture {
      val result = dao.selectById(id)
      result.asScala match {
        case Some(person) => dao.delete(person).getCount
        case _ => 0
      }
    } map { result =>
      NoContent
    }
  }

}
