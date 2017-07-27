import org.scalatestplus.play._
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.libs.json._
import play.api.test._
import play.api.test.Helpers._

class ApplicationSpec extends PlaySpec with GuiceOneAppPerTest {

  "SampleController" should {

    "get persons" in {
      val persons = route(app, FakeRequest(GET, "/persons")).get

      status(persons) mustBe OK
      contentType(persons) mustBe Some("application/json")
      contentAsJson(persons) mustBe (Json.parse(
        """
        [
          {
            "id":1,
            "name":"SMITH",
            "age":10,
            "address":{"city":"Tokyo","street":"Yaesu"},
            "departmentId":1,
            "version":0
          },
          {
            "id":2,
            "name":"ALLEN",
            "age":20,
            "address":{"city":"Kyoto","street":"Karasuma"},
            "departmentId":2,
            "version":0
          }
        ]
        """))
    }

    "get person" in {
      val person = route(app, FakeRequest(GET, "/persons/1")).get

      status(person) mustBe OK
      contentType(person) mustBe Some("application/json")
      contentAsJson(person) mustBe (Json.parse(
        """
        {
          "id":1,
          "name":"SMITH",
          "age":10,
          "address":{"city":"Tokyo","street":"Yaesu"},
          "departmentId":1,
          "version":0
        }
        """))
    }

    "post person" in {
      val json = Json.parse(
        """
        {
          "name":"WARD",
          "age":30,
          "address":{"city":"Fukuoka","street":"Gion"},
          "departmentId":1
        }
        """)
      val person = route(app, FakeRequest(POST, "/persons").withJsonBody(json)).get

      status(person) mustBe OK
      contentType(person) mustBe Some("application/json")
      contentAsJson(person) mustBe (Json.parse(
        """
        {
          "entity":{
            "id":3,
            "name":"WARD",
            "age":30,
            "address":{"city":"Fukuoka","street":"Gion"},
            "departmentId":1,
            "version":1
          },
          "count": 1
        }
        """))

      val persons = route(app, FakeRequest(GET, "/persons")).get
      contentAsJson(persons) mustBe (Json.parse(
        """
        [
          {
            "id":1,
            "name":"SMITH",
            "age":10,
            "address":{"city":"Tokyo","street":"Yaesu"},
            "departmentId":1,
            "version":0
          },
          {
            "id":2,
            "name":"ALLEN",
            "age":20,
            "address":{"city":"Kyoto","street":"Karasuma"},
            "departmentId":2,
            "version":0
          },
          {
            "id":3,
            "name":"WARD",
            "age":30,
            "address":{"city":"Fukuoka","street":"Gion"},
            "departmentId":1,
            "version":1
          }
        ]
        """))
    }

    "put person" in {
      val json = Json.parse(
        """
        {
          "id":1,
          "name":"SMITH",
          "age":40,
          "address":{"city":"Tokyo","street":"Marunouchi"},
          "departmentId":2,
          "version":0
        }
        """)
      val person = route(app, FakeRequest(PUT, "/persons").withJsonBody(json)).get

      status(person) mustBe OK
      contentType(person) mustBe Some("application/json")
      contentAsJson(person) mustBe (Json.parse(
        """
        {
          "entity":{
            "id":1,
            "name":"SMITH",
            "age":40,
            "address":{"city":"Tokyo","street":"Marunouchi"},
            "departmentId":2,
            "version":1
          },
          "count": 1
        }
        """))

      val persons = route(app, FakeRequest(GET, "/persons")).get
      contentAsJson(persons) mustBe (Json.parse(
        """
        [
          {
            "id":1,
            "name":"SMITH",
            "age":40,
            "address":{"city":"Tokyo","street":"Marunouchi"},
            "departmentId":2,
            "version":1
          },
          {
            "id":2,
            "name":"ALLEN",
            "age":20,
            "address":{"city":"Kyoto","street":"Karasuma"},
            "departmentId":2,
            "version":0
          }
        ]
        """))
    }

    "delete person" in {
      val person = route(app, FakeRequest(DELETE, "/persons/2")).get

      status(person) mustBe NO_CONTENT

      val persons = route(app, FakeRequest(GET, "/persons")).get
      contentAsJson(persons) mustBe (Json.parse(
        """
        [
          {
            "id":1,
            "name":"SMITH",
            "age":10,
            "address":{"city":"Tokyo","street":"Yaesu"},
            "departmentId":1,
            "version":0
          }
        ]
        """))
    }

  }

}
