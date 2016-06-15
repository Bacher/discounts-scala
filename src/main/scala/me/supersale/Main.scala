package me.supersale

//import scala.concurrent.ExecutionContext.Implicits.global

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.ToResponseMarshallable
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.unmarshalling.FromRequestUnmarshaller
import akka.stream.ActorMaterializer
import spray.json._
import spray.json.{DefaultJsonProtocol, JsonFormat}

import scala.language.implicitConversions
import spray.json._

import scala.io.StdIn

case class Order(email: String, amount: Long)

object Main {
  def main(args: Array[String]) {
    implicit val system = ActorSystem("my-system")
    implicit val materializer = ActorMaterializer()
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext = system.dispatcher

    val dis = new Discounts

    val route =
      pathPrefix("api" / Remaining) { apiName =>
        println("Api Name: " + apiName)

        get {
          try {
            val client = Client.loadByToken("123")

            complete(HttpEntity(ContentTypes.`application/json`, JsObject("r" -> client.toJson).compactPrint))
          } catch {
            case error: RequestError =>
              complete(HttpEntity(ContentTypes.`application/json`, JsObject("e" -> error.toJson).compactPrint))
            case _: Throwable => ???
          }
        } ~
          post {
            // decompress gzipped or deflated requests if required
            decodeRequest {
              // unmarshal with in-scope unmarshaller
              entity(as[String]) { body =>
                println(body.parseJson)

                val response = try {
                  dis.handleApiRequest(apiName, body.parseJson.asJsObject)
                } catch {
                  case err: RequestError => err.toJson
                  case err: Throwable =>
                    err.printStackTrace()
                    new RequestError().toJson
                }

                complete {
                  HttpEntity(
                    ContentTypes.`application/json`,
                    response.compactPrint
                  )
                }
              }
            }
          }
      }

    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}