package api;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojo.User;

import static io.restassured.RestAssured.given;

public class RestResource {

    @Step
    public static Response post(String path, String token, Object requestUser){
        return given(SpecBuilder.getRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestUser)
                .header("Authorization", "Bearer " + token)
                .when().post(path)
                .then().spec(SpecBuilder.getResponseSpec())
                .extract().response();
    }

    @Step
    public static Response get(String path, String token){
        return given(SpecBuilder.getRequestSpec())
                .header("Authorization", "Bearer " + token)
                .when()
                .get(path)
                .then()
                .spec(SpecBuilder.getResponseSpec())
                .extract().response();
    }

    @Step
    public static Response put(String path, String token, Object requestUser){
        return given(SpecBuilder.getRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestUser)
                .header("Authorization", "Bearer " + token)
                .when().put(path)
                .then()
                .spec(SpecBuilder.getResponseSpec())
                .extract().response();
    }

    @Step
    public static Response patch(String path, String token, Object requestUser) {
        return given(SpecBuilder.getRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestUser)
                .header("Authorization", "Bearer " + token)
                .when()
                .patch(path)
                .then().
                spec(SpecBuilder.getResponseSpec())
                .extract().response();
    }

    @Step
    public static Response delete(String path, String token){
        return given(SpecBuilder.getRequestSpec())
                .header("Authorization", "Bearer " + token)
                .when()
                .delete(path)
                .then()
                .spec(SpecBuilder.getResponseSpec())
                .extract().response();
    }
}
