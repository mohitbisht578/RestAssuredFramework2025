package api.applicationApi;

import api.RestResource;
import api.SpecBuilder;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojo.User;

import static io.restassured.RestAssured.given;

public class UserAPI {

    static String accessToken = "0e652eb1e7c4e397edf8431cc58727be5cf8eb139f7b6f0a2162d0bbd365f5d5";

    @Step
    public static Response post(User requestUser){
        return RestResource.post("/public/v2/users", accessToken, requestUser);
    }

    @Step
    public static Response post(User requestUser, String token){
        return RestResource.post("/public/v2/users", token, requestUser);
    }

    @Step
    public static Response get(int userId){
        return RestResource.get("/public/v2/users/"+userId, accessToken);
    }

    @Step
    public static Response put(User requestUser, int userId){
        return RestResource.put("/public/v2/users/"+userId, accessToken, requestUser);
    }

    @Step
    public static Response patch(User requestUser, int userId){
        return RestResource.patch("/public/v2/users/" +userId, accessToken, requestUser);
    }

    @Step
    public static Response delete(int userId){
        return RestResource.delete("/public/v2/users/"+userId, accessToken);
    }

}
