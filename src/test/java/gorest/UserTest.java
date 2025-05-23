package gorest;

import api.SpecBuilder;
import api.applicationApi.StatusCode;
import api.applicationApi.UserAPI;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.testng.annotations.Test;
import pojo.ErrorMessage;
import pojo.ErrorResponse;
import pojo.User;
import utils.JsonUtils;
import utils.RestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

public class UserTest {

    public static String generateRandomEmail() {
        return "user_" + UUID.randomUUID().toString().substring(0, 8) + "@example.com";
    }

    int userId;
    User expectedUser; // holds created data
    User updatedUser;  // holds updated data

    @Step
    @Test(description = "should be able to create a user", priority = 1)
    public void createUserTest(){

        expectedUser = new User();
        expectedUser.setName("Tom");
        expectedUser.setEmail(generateRandomEmail());
        expectedUser.setGender("male");
        expectedUser.setStatus("active");

        Response response = UserAPI.post(expectedUser);
        assertThat(response.statusCode(), equalTo(StatusCode.CODE_201.getCode()));

        User responseUser = response.as(User.class);
        userId = responseUser.getId();
        System.out.println("USER ID ==> " + userId);
        assertThat(responseUser.getName(), equalTo(expectedUser.getName()));
        assertThat(responseUser.getEmail(), equalTo(expectedUser.getEmail()));
        assertThat(responseUser.getGender(), equalTo(expectedUser.getGender()));
        assertThat(responseUser.getStatus(), equalTo(expectedUser.getStatus()));
    }

    @Test(priority = 2)
    public void getUserTest(){

        Response response = UserAPI.get(userId);
        assertThat(response.statusCode(), equalTo(StatusCode.CODE_200.getCode()));

        User responseUser = response.as(User.class);
        assertThat(responseUser.getId(), equalTo(userId));
        assertThat(responseUser.getName(), equalTo( expectedUser.getName()));
        assertThat(responseUser.getEmail(), equalTo( expectedUser.getEmail()));
        assertThat(responseUser.getGender(), equalTo( expectedUser.getGender()));
        assertThat(responseUser.getStatus(), equalTo( expectedUser.getStatus()));

    }

    @Step
    @Test(priority = 3)
    public void shouldNotAbleToCreateAUserTest(){

        expectedUser = new User();
        expectedUser.setName(""); // Invalid
        expectedUser.setEmail(generateRandomEmail());
        expectedUser.setGender("male");
        expectedUser.setStatus("active");

        Response response = UserAPI.post(expectedUser);
        assertThat(response.statusCode(), equalTo(StatusCode.CODE_422.getCode()));

        List<ErrorResponse> errorResponse = response.jsonPath().getList("", ErrorResponse.class);
        assertThat(errorResponse.size(), greaterThan(0));
        assertThat(errorResponse.get(0).getField(), equalTo("name"));
        assertThat(errorResponse.get(0).getMessage(), equalTo("can't be blank"));

    }

    @Step
    @Test(priority = 3)
    public void shouldNotAbleToCreateAUserWithInvalidToken(){
        String invalid_token = "12345";
        expectedUser = new User();
        expectedUser.setName("Tom");
        expectedUser.setEmail(generateRandomEmail());
        expectedUser.setGender("male");
        expectedUser.setStatus("active");

        Response response = UserAPI.post(expectedUser, invalid_token);
        assertThat(response.statusCode(), equalTo(StatusCode.CODE_401.getCode()));

        ErrorMessage errorMessage = response.as(ErrorMessage.class);
        assertThat(errorMessage.getMessage(), equalTo("Invalid token"));

    }

    @Step
    @Test(priority = 3)
    public void updateUserTest(){

        updatedUser = new User();
        updatedUser.setName("John Doe");
        updatedUser.setEmail(generateRandomEmail());
        updatedUser.setGender("male");
        updatedUser.setStatus("inactive");
        Response response = UserAPI.put(updatedUser, userId);
        assertThat(response.statusCode(), equalTo(StatusCode.CODE_200.getCode()));
        User responseUser = response.as(User.class);

        assertThat(responseUser.getName(), equalTo(updatedUser.getName()));
        assertThat(responseUser.getEmail(), equalTo(updatedUser.getEmail()));
        assertThat(responseUser.getGender(), equalTo(updatedUser.getGender()));
        assertThat(responseUser.getStatus(), equalTo(updatedUser.getStatus()));
    }

    @Step
    @Test(priority = 4)
    public void getUpdatedUserTest(){

        Response response = UserAPI.get(userId);
        assertThat(response.statusCode(), equalTo(StatusCode.CODE_200.getCode()));

        User responseUser = response.as(User.class);
        assertThat(responseUser.getId(), equalTo(userId));
        assertThat(responseUser.getName(), equalTo(updatedUser.getName()));
        assertThat(responseUser.getEmail(), equalTo(updatedUser.getEmail()));
        assertThat(responseUser.getGender(), equalTo(updatedUser.getGender()));
        assertThat(responseUser.getStatus(), equalTo(updatedUser.getStatus()));
    }

    @Step
    @Test(priority = 5)
    public void deleteUser(){
        Response response = UserAPI.delete(userId);
        assertThat(response.statusCode(), equalTo(StatusCode.CODE_204.getCode()));
    }

    @Step
    public User userBuilder(String name, String email, String gender, String status){
        expectedUser = new User();
        expectedUser.setName(name);
        expectedUser.setEmail(generateRandomEmail());
        expectedUser.setGender("male");
        expectedUser.setStatus("active");
        return expectedUser;
    }

    @Step
    public void assertUserEqual(User responseUser, User requestUser){
        assertThat(responseUser.getName(), equalTo(expectedUser.getName()));
        assertThat(responseUser.getEmail(), equalTo(expectedUser.getEmail()));
        assertThat(responseUser.getGender(), equalTo(expectedUser.getGender()));
        assertThat(responseUser.getStatus(), equalTo(expectedUser.getStatus()));
    }

    @Step
    public void assertStatusCode(int actualStatusCode, int expectedStatusCode){
        assertThat(actualStatusCode, equalTo(expectedStatusCode));
    }

//    @Step
//    public void assertError(int actualStatusCode, int expectedStatusCode){
//        assertThat(actualStatusCode, equalTo(expectedStatusCode));
//    }

}
