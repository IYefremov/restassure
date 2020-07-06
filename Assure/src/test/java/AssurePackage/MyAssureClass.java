package AssurePackage;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class MyAssureClass {
    RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("https://reqres.in")
            .setContentType(ContentType.JSON)
            .build();
    // .filter(new AllureRestAssured());

    @BeforeTest
    public void setFilter() {
        RestAssured.filters(new AllureRestAssured());
    }

    @Test
    public void postCreateTest() {
//        User user = User.builder()
//                .name("Oleg")
//                .job("Automation")
//                .build();

        given()
                .spec(requestSpec)
                .basePath("/api/users")
                .log().body()

                .when()
                .body("{\"name\": \"Ivan\", \"job\": \"leader\"}")
                .post()

                .then()
                .log().body()
                .statusCode(201)
                .time(lessThan(5000L))
                .body("name", equalTo("Ivan"))
                .body("job", equalTo("leader"));
    }


    @Test
    public void postRegisterSuccessful() {

        given()
                .spec(requestSpec)
                .basePath("/api/register")
                .log().body()

                .when()
                .body("{\"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\"}")
                .post()

                .then()
                .log().body()
                .statusCode(200)
                .time(lessThan(5000L))
                .body("id", equalTo(4))
                .body("token", equalTo("QpwL5tke4Pnpja7X4"));
    }

    @Test
    public void postRegisterUnSuccessful() {

        given()
                .spec(requestSpec)
                .basePath("/api/register")
                .log().body()

                .when()
                .body("{\"email\": \"eve.holt@reqres.in\"}")
                .post()

                .then()
                .log().body()
                .statusCode(400)
                .time(lessThan(5000L));
        // как проверить на наличие сообщения "error": "Missing password" ???????????

    }

    @Test
    public void postLoginSuccessful() {

        given()
                .spec(requestSpec)
                .basePath("/api/login")
                .log().body()

                .when()
                .body("{\"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\"}")
                .post()

                .then()
                .log().body()
                .statusCode(200)
                .time(lessThan(5000L))
                .body("token", equalTo("QpwL5tke4Pnpja7X4"));


    }

    @Test
    public void postLoginUnSuccessful() {

        given()
                .spec(requestSpec)
                .basePath("/api/login")
                .log().body()

                .when()
                .body("{\"email\": \"eve.holt@reqres.in\"}")
                .post()

                .then()
                .log().body()
                .statusCode(400)
                .time(lessThan(5000L));

        // как проверить на наличие сообщения "error": "Missing password" ???????????

    }

    @Test
    public void getListUsers() {

        given()
                .spec(requestSpec)
                .basePath("/api/users?page=2")
                .log().body()

                .when()
                .get()

                .then()
                .log().body()
                .statusCode(200)
                .time(lessThan(5000L))
                .body("total", equalTo(12));
    }

    @Test
    public void getSingleUser() {

        given()
                .spec(requestSpec)
                .basePath("/api/users/2")
                .log().body()
                .when()
                .get()
                .then()
                .log().body()
                .statusCode(200)
                .time(lessThan(5000L))
                .body("data.id", equalTo(2))
                .body("data", hasKey("id"))
                .body("", hasKey("data"));

    }

    @Test
    public void getSingleUserNotFound() {

        given()
                .spec(requestSpec)
                .basePath("/api/users/23")
                .log().body()

                .when()
                .get()

                .then()
                .log().body()
                .log().all()
                .statusCode(404)
                .time(lessThan(5000L))
                .body("isEmpty()", Matchers.is(true));
    }

    @Test
    public void getListResourse() {

        given()
                .spec(requestSpec)
                .basePath("/api/unknown")
                .log().body()

                .when()
                .get()

                .then()
                .log().body()
                .statusCode(200)
                .time(lessThan(5000L))
                .body("data.name[0]", equalTo("cerulean"));
    }

    @Test
    public void getSingleResource() {

        given()
                .spec(requestSpec)
                .basePath("/api/unknown/2")
                .log().body()

                .when()
                .get()

                .then()
                .log().body()
                .statusCode(200)
                .time(lessThan(5000L))
                .body("data.year", equalTo(2001));
    }

    @Test
    public void getSingleResourceNotFound() {

        given()
                .spec(requestSpec)
                .basePath("/api/unknown/23")
                .log().body()

                .when()
                .get()

                .then()
                .log().body()
                .statusCode(404)
                .time(lessThan(5000L));

    }

    @Test
    public void getDeleyedResponce() {

        given()
                .spec(requestSpec)
                .basePath("/api/users?delay=3")
                .log().body()

                .when()
                .get()

                .then()
                .log().body()
                .statusCode(200)
                .time(lessThan(5000L))
                .body("data.first_name[0]", equalTo("George"));
    }

    @Test
    public void putUpdate() {

        given()
                .spec(requestSpec)
                .basePath("/api/users/2")
                .log().body()

                .when()
                .body("{\"name\": \"morpheus\", \"job\": \"zion resident\"}")
                .put()

                .then()
                .log().body()
                .statusCode(200)
                .time(lessThan(5000L))
                .body("name", equalTo("morpheus"));
    }

    @Test
    public void patchUpdate() {

        given()
                .spec(requestSpec)
                .basePath("/api/users/2")
                .log().body()

                .when()
                .body("{\"name\": \"morpheus\", \"job\": \"zion resident\"}")
                .patch()

                .then()
                .log().body()
                .statusCode(200)
                .time(lessThan(5000L))
                .body("name", equalTo("morpheus"));
    }

    @Test
    public void delete() {

        given()
                .spec(requestSpec)
                .basePath("/api/users/2")
                .log().body()

                .when()
                .delete()

                .then()
                .log().body()
                .statusCode(204)
                .time(lessThan(5000L));

    }
}
