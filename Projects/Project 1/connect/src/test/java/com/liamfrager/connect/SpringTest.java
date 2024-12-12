package com.liamfrager.connect;

import com.liamfrager.connect.controller.APIController;
import com.liamfrager.connect.entity.User;
import com.liamfrager.connect.entity.Post;
import com.liamfrager.connect.repository.UserRepository;
import com.liamfrager.connect.repository.PostRepository;
import com.liamfrager.connect.service.UserService;
import com.liamfrager.connect.service.PostService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class SpringTest {
    ApplicationContext applicationContext;

    /**
     * Retrieve the applicationContext for your app by starting it up.
     */
    @BeforeEach
    public void setUp(){
        String[] args = new String[] {};
        applicationContext = SpringApplication.run(ConnectApplication.class, args);
    }
    /**
     * Reset the applicationContext after each test.
     */
    @AfterEach
    public void tearDown(){
        SpringApplication.exit(applicationContext);
    }

    /**
     * Retrieve the APIController as a bean.
     * The APIController must be a bean in order for this test to pass.
     */
    @Test
    public void getAPIControllerBean(){
        APIController bean = applicationContext.getBean(APIController.class);
        Assertions.assertNotNull(bean);
    }
    /**
     * Retrieve the UserService as a bean.
     * The UserService must be a bean in order for this test to pass.
     */
    @Test
    public void getUserServiceBean(){
        UserService bean = applicationContext.getBean(UserService.class);
        Assertions.assertNotNull(bean);
    }
    /**
     * Retrieve the PostService as a bean.
     * The PostService must be a bean in order for this test to pass.
     */
    @Test
    public void getPostServiceBean(){
        PostService bean = applicationContext.getBean(PostService.class);
        Assertions.assertNotNull(bean);
    }
    /**
     * Retrieve the UserRepository as a bean.
     * The UserRepository must be a bean in order for this test to pass.
     */
    @Test
    public void getUserRepositoryBean(){
        UserRepository bean = applicationContext.getBean(UserRepository.class);
        Assertions.assertNotNull(bean);
    }
    /**
     * Retrieve the PostRepository as a bean.
     * The PostRepository must be a bean in order for this test to pass.
     */
    @Test
    public void getPostRepositoryBean(){
        PostRepository bean = applicationContext.getBean(PostRepository.class);
        Assertions.assertNotNull(bean);
    }
    /**
     * After retrieving the UserRepository bean, it should exhibit the functionality of a JPARepository
     * for an "User" entity.
     */
    @Test
    public void userRepositoryIsRepositoryTest() throws ReflectiveOperationException {
        UserRepository repository = applicationContext.getBean(UserRepository.class);
        Method[] repositoryMethods = repository.getClass().getMethods();
        Method saveMethod = null;
        Method findAllMethod = null;
        String expectedUsername = "ted";
        String expectedEmail = "test@email.com";
        String expectedPassword = "password123";
        User testUser = new User(expectedUsername, expectedEmail, expectedPassword);
        for(Method m : repositoryMethods){
            System.out.println(m.getName());
            if(m.getName().equals("save") && m.getParameterCount() == 1){
                saveMethod = m;
            }else if(m.getName().equals("findAll") && m.getParameterCount() == 0){
                findAllMethod = m;
            }
        }
        if(saveMethod == null || findAllMethod == null){
            Assertions.fail("The save / findAll methods were not found. Ensure that UserRepository properly " +
                    "extends JPARepository.");
        }
        List<User> userList1 = (List<User>) findAllMethod.invoke(repository, new Object[]{});
        System.out.println(userList1);
        Assertions.assertTrue(userList1.size() == 4, "There should be no users in the " +
                "JPARepository on startup.");
        User actualUser = (User) saveMethod.invoke(repository, testUser);
        Assertions.assertEquals(actualUser.getUsername(), expectedUsername);
        List<User> userList2 = (List<User>) findAllMethod.invoke(repository, new Object[]{});
        Assertions.assertTrue(userList2.size() > 4, "The user should be addable to the " +
                "JPARepository.");
    }
    /**
     * After retrieving the PostRepository bean, it should exhibit the functionality of a JPARepository
     * for a "Post" entity.
     */
    @Test
    public void postRepositoryIsRepositoryTest() throws ReflectiveOperationException{
        PostRepository repository = applicationContext.getBean(PostRepository.class);
        Method[] repositoryMethods = repository.getClass().getMethods();
        Method saveMethod = null;
        Method findAllMethod = null;
        int expectedPostedBy = 9999;
        String expectedText = "ted test 1";
        long expectedTimePosted = 999999999999L;
        Post testPost = new Post(expectedPostedBy, expectedText, expectedTimePosted);
        for(Method m : repositoryMethods){
            System.out.println(m.getName());
            if(m.getName().equals("save") && m.getParameterCount() == 1){
                saveMethod = m;
            }else if(m.getName().equals("findAll") && m.getParameterCount() == 0){
                findAllMethod = m;
            }
        }
        if(saveMethod == null || findAllMethod == null){
            Assertions.fail("The save / findAll methods were not found. Ensure that PostRepository properly " +
                    "extends JPARepository.");
        }
        List<User> userList1 = (List<User>) findAllMethod.invoke(repository, new Object[]{});
        System.out.println(userList1);
        Assertions.assertTrue(userList1.size() == 3, "There should be no posts in the " +
                "JPARepository on startup.");
        Post actualPost = (Post) saveMethod.invoke(repository, testPost);
        Assertions.assertEquals(actualPost.getPostText(), expectedText);
        List<User> userList2 = (List<User>) findAllMethod.invoke(repository, new Object[]{});
        Assertions.assertTrue(userList2.size() > 3, "The post should be addable to the " +
                "JPARepository.");
    }
    /**
     * Verify the functionality of Spring MVC, independently of the project requirement endpoints, by sending a request
     * to an arbitrary endpoint.
     */
    @Test
    public void default404Test() throws IOException, InterruptedException {
        HttpClient webClient = HttpClient.newHttpClient();
        int random = (int) (Math.random()*100000);
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/arbitrary"+random))
                .build();
        HttpResponse<String> response = webClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(404, status);
        String body = response.body().toString();
        Assertions.assertTrue(body.contains("timestamp"));
        Assertions.assertTrue(body.contains("status"));
        Assertions.assertTrue(body.contains("error"));
        Assertions.assertTrue(body.contains("path"));
    }
}
