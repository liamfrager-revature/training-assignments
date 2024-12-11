package com.liamfrager.connect;

import com.liamfrager.connect.controller.APIController;
import com.liamfrager.connect.entity.Account;
import com.liamfrager.connect.entity.Post;
import com.liamfrager.connect.repository.AccountRepository;
import com.liamfrager.connect.repository.PostRepository;
import com.liamfrager.connect.service.AccountService;
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
     * Retrieve the AccountService as a bean.
     * The AccountService must be a bean in order for this test to pass.
     */
    @Test
    public void getAccountServiceBean(){
        AccountService bean = applicationContext.getBean(AccountService.class);
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
     * Retrieve the AccountRepository as a bean.
     * The AccountRepository must be a bean in order for this test to pass.
     */
    @Test
    public void getAccountRepositoryBean(){
        AccountRepository bean = applicationContext.getBean(AccountRepository.class);
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
     * After retrieving the AccountRepository bean, it should exhibit the functionality of a JPARepository
     * for an "Account" entity.
     */
    @Test
    public void accountRepositoryIsRepositoryTest() throws ReflectiveOperationException {
        AccountRepository repository = applicationContext.getBean(AccountRepository.class);
        Method[] repositoryMethods = repository.getClass().getMethods();
        Method saveMethod = null;
        Method findAllMethod = null;
        String expectedUsername = "ted";
        String expectedEmail = "test@email.com";
        String expectedPassword = "password123";
        Account testAccount = new Account(expectedUsername, expectedEmail, expectedPassword);
        for(Method m : repositoryMethods){
            System.out.println(m.getName());
            if(m.getName().equals("save") && m.getParameterCount() == 1){
                saveMethod = m;
            }else if(m.getName().equals("findAll") && m.getParameterCount() == 0){
                findAllMethod = m;
            }
        }
        if(saveMethod == null || findAllMethod == null){
            Assertions.fail("The save / findAll methods were not found. Ensure that AccountRepository properly " +
                    "extends JPARepository.");
        }
        List<Account> accountList1 = (List<Account>) findAllMethod.invoke(repository, new Object[]{});
        System.out.println(accountList1);
        Assertions.assertTrue(accountList1.size() == 4, "There should be no accounts in the " +
                "JPARepository on startup.");
        Account actualAccount = (Account) saveMethod.invoke(repository, testAccount);
        Assertions.assertEquals(actualAccount.getUsername(), expectedUsername);
        List<Account> accountList2 = (List<Account>) findAllMethod.invoke(repository, new Object[]{});
        Assertions.assertTrue(accountList2.size() > 4, "The account should be addable to the " +
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
        List<Account> accountList1 = (List<Account>) findAllMethod.invoke(repository, new Object[]{});
        System.out.println(accountList1);
        Assertions.assertTrue(accountList1.size() == 3, "There should be no posts in the " +
                "JPARepository on startup.");
        Post actualPost = (Post) saveMethod.invoke(repository, testPost);
        Assertions.assertEquals(actualPost.getPostText(), expectedText);
        List<Account> accountList2 = (List<Account>) findAllMethod.invoke(repository, new Object[]{});
        Assertions.assertTrue(accountList2.size() > 3, "The post should be addable to the " +
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
