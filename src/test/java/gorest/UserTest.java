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
import utils.TestDataUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

public class UserTest {

    int userId;
    User expectedUser; // holds created data
    User updatedUser;  // holds updated data

    @Step
    @Test(priority = 1, description = "Should create a user successfully")
    public void createUserTest(){
        expectedUser = new User();
        expectedUser.setName(TestDataUtils.getFullName());
        expectedUser.setEmail(TestDataUtils.getEmail());
        expectedUser.setGender(TestDataUtils.getGender());
        expectedUser.setStatus(TestDataUtils.getStatus());

        Response response = UserAPI.post(expectedUser);
        assertThat(response.statusCode(), equalTo(StatusCode.CODE_201.getCode()));

        User responseUser = response.as(User.class);
        userId = responseUser.getId();
        System.out.println("USER ID ==> " + userId);
        assertUserEqual(responseUser, expectedUser);
        assertThat(responseUser.getName(), equalTo(expectedUser.getName()));
        assertThat(responseUser.getEmail(), equalTo(expectedUser.getEmail()));
        assertThat(responseUser.getGender(), equalTo(expectedUser.getGender()));
        assertThat(responseUser.getStatus(), equalTo(expectedUser.getStatus()));
    }

    @Test(priority = 2, description = "Should retrieve the created user")
    public void getUserTest(){

        Response response = UserAPI.get(userId);
        assertThat(response.statusCode(), equalTo(StatusCode.CODE_200.getCode()));

        User responseUser = response.as(User.class);
        assertUserEqual(responseUser, expectedUser);

        assertThat(responseUser.getId(), equalTo(userId));
        assertThat(responseUser.getName(), equalTo( expectedUser.getName()));
        assertThat(responseUser.getEmail(), equalTo( expectedUser.getEmail()));
        assertThat(responseUser.getGender(), equalTo( expectedUser.getGender()));
        assertThat(responseUser.getStatus(), equalTo( expectedUser.getStatus()));

    }

    @Step
    @Test(priority = 3, description = "Should not create a user with blank name")
    public void shouldNotCreateUserWithBlankName(){

        expectedUser = new User();
        expectedUser.setName(""); // Invalid
        expectedUser.setEmail(TestDataUtils.getEmail());
        expectedUser.setGender(TestDataUtils.getGender());
        expectedUser.setStatus(TestDataUtils.getStatus());

        Response response = UserAPI.post(expectedUser);
        assertThat(response.statusCode(), equalTo(StatusCode.CODE_422.getCode()));

        List<ErrorResponse> errorResponse = response.jsonPath().getList("", ErrorResponse.class);
        assertThat(errorResponse.size(), greaterThan(0));
        assertThat(errorResponse.get(0).getField(), equalTo("name"));
        assertThat(errorResponse.get(0).getMessage(), equalTo("can't be blank"));

    }

    @Step
    @Test(priority = 4, description = "Should not create a user with invalid token")
    public void shouldNotCreateUserWithInvalidToken(){

        String invalid_token = "12345";
        expectedUser = new User();
        expectedUser.setName(TestDataUtils.getFullName());
        expectedUser.setEmail(TestDataUtils.getEmail());
        expectedUser.setGender(TestDataUtils.getGender());
        expectedUser.setStatus(TestDataUtils.getStatus());

        Response response = UserAPI.post(expectedUser, invalid_token);
        assertThat(response.statusCode(), equalTo(StatusCode.CODE_401.getCode()));

        ErrorMessage errorMessage = response.as(ErrorMessage.class);
        assertThat(errorMessage.getMessage(), equalTo("Invalid token"));

    }

    @Step
    @Test(priority = 5, description = "Should update user details")
    public void updateUserTest(){

        updatedUser = new User();
        updatedUser.setName(TestDataUtils.getFullName());
        updatedUser.setEmail(TestDataUtils.getEmail());
        updatedUser.setGender(TestDataUtils.getGender());
        updatedUser.setStatus(TestDataUtils.getStatus());

        Response response = UserAPI.put(updatedUser, userId);
        assertThat(response.statusCode(), equalTo(StatusCode.CODE_200.getCode()));
        User responseUser = response.as(User.class);
        assertUserEqual(responseUser, updatedUser);

    }

    @Step
    @Test(priority = 6, description = "Should retrieve updated user details")
    public void getUpdatedUserTest(){

        Response response = UserAPI.get(userId);
        assertThat(response.statusCode(), equalTo(StatusCode.CODE_200.getCode()));

        User responseUser = response.as(User.class);
        assertThat(responseUser.getId(), equalTo(userId));
        assertUserEqual(responseUser, updatedUser);

    }

    @Step
    @Test(priority = 7, description = "Should delete user")
    public void deleteUser(){
        Response response = UserAPI.delete(userId);
        assertThat(response.statusCode(), equalTo(StatusCode.CODE_204.getCode()));
    }

    @Step
    public void assertUserEqual(User responseUser, User requestUser){
        assertThat(responseUser.getName(), equalTo(requestUser.getName()));
        assertThat(responseUser.getEmail(), equalTo(requestUser.getEmail()));
        assertThat(responseUser.getGender(), equalTo(requestUser.getGender()));
        assertThat(responseUser.getStatus(), equalTo(requestUser.getStatus()));
    }

    @Step
    public void assertStatusCode(int actualStatusCode, int expectedStatusCode){
        assertThat(actualStatusCode, equalTo(expectedStatusCode));
    }

}
