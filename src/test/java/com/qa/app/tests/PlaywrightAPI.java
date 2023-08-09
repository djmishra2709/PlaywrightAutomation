package com.qa.app.tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import com.qa.app.utilities.Userdetails;
import com.qa.app.utilities.UserswithLombok;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PlaywrightAPI {
    APIResponse responce;
    APIRequestContext apireqContext;
    Playwright playwright;
    Random random;
    @BeforeTest
    public void setup()
    {
        playwright = Playwright.create();
        APIRequest req = playwright.request();
        apireqContext = req.newContext();
        random = new Random();
    }

    //get request
    @Test
    public void APItest1() {
        responce  = apireqContext.get("https://gorest.co.in/public/v2/users");
        System.out.println(responce.status());
        Assert.assertEquals(responce.status(),200);
        System.out.println(responce.text());

    }

    //setup query parameter
    @Test
    public void APItest2() {
        responce  = apireqContext.get("https://gorest.co.in/public/v2/users", RequestOptions.create()
                .setQueryParam("id",3944119)
                .setQueryParam("status","inactive"));
        System.out.println(responce.status());
        Assert.assertEquals(responce.status(),200);
        System.out.println(responce.text());

    }

    //get response in json format
    @Test
    public void APItest3() throws IOException {
        responce  = apireqContext.get("https://gorest.co.in/public/v2/users", RequestOptions.create()
                .setQueryParam("id",3944119)
                .setQueryParam("status","inactive"));
        System.out.println(responce.status());

        ObjectMapper objtmap = new ObjectMapper();
        JsonNode jsonparser =objtmap.readTree(responce.body());
        System.out.println(jsonparser.toPrettyString());

    }
    // using dispose method
    @Test
    public void APItest4() throws IOException {
        responce  = apireqContext.get("https://gorest.co.in/public/v2/users", RequestOptions.create()
                .setQueryParam("id",3944119)
                .setQueryParam("status","inactive"));
        responce.dispose(); //dispose only the response body but status code, url , status text will be same
        System.out.println(responce.status());

    }

    //capture the headers from the response
    @Test
    public void APItest5() throws IOException {
        responce  = apireqContext.get("https://gorest.co.in/public/v2/users");
        responce.headers().forEach((e,v)->System.out.println(e+":"+v));
        System.out.println("=====================================");
        responce.headersArray().forEach((e)->System.out.println(e.name+": "+e.value));

    }

    //use post call with hashmap
    @Test
    public void APItest6() throws IOException {
        Map<String,Object> data =new HashMap<>();
        data.put("name","testngPW");
        data.put("email","test"+random.nextInt(1000)+"@gmail.com");
        data.put("gender","male");
        data.put("status","active");

       responce  = apireqContext.post("https://gorest.co.in/public/v2/users", RequestOptions.create()
                .setHeader("Content-Type","application/json")
                .setHeader("Authorization","Bearer e2c642609a587b4ef1f496ecf10b26fe8e3073e96977417f564cd4a1347da8c4")
                .setData(data));

        Assert.assertEquals(responce.status(),201);
        System.out.println(responce.text());
        Assert.assertEquals(responce.statusText(),"Created");

        ObjectMapper objtmap = new ObjectMapper();
        JsonNode jsonparser =objtmap.readTree(responce.body());
        System.out.println(jsonparser.get("id").asText());

        responce  = apireqContext.get("https://gorest.co.in/public/v2/users/"+jsonparser.get("id").asText(), RequestOptions.create()
                       .setHeader("Authorization","Bearer e2c642609a587b4ef1f496ecf10b26fe8e3073e96977417f564cd4a1347da8c4"));

        Assert.assertEquals(responce.status(),200);
        Assert.assertEquals(responce.statusText(),"OK");
        
        ObjectMapper objtmap1 = new ObjectMapper();
        JsonNode jsonparser1 =objtmap.readTree(responce.body());
        System.out.println(jsonparser1.get("email").asText());

           }


    //use post call with json File -> method 1
    @Test
    public void APItest7() throws IOException {

     byte[] filebyte=null;
     File file = new File("./src/test/Data/user.json");
        filebyte = Files.readAllBytes(file.toPath());

        responce  = apireqContext.post("https://gorest.co.in/public/v2/users", RequestOptions.create()
                .setHeader("Content-Type","application/json")
                .setHeader("Authorization","Bearer e2c642609a587b4ef1f496ecf10b26fe8e3073e96977417f564cd4a1347da8c4")
                .setData(filebyte));
        Assert.assertEquals(responce.status(),201);
        System.out.println(responce.text());
        Assert.assertEquals(responce.statusText(),"Created");

        ObjectMapper objtmap = new ObjectMapper();
        JsonNode jsonparser =objtmap.readTree(responce.body());
        System.out.println(jsonparser.get("id").asText());

    }

    //use post call with POJO class -> method 1
    //Deserialization - > JSON to POJO class
    @Test
    public void APItest8() throws IOException {

        Userdetails user = new Userdetails("TestingAPIPOJo","test"+random.nextInt(1000)+"@gmail.com","active","male");

        APIResponse responce1  = apireqContext.post("https://gorest.co.in/public/v2/users", RequestOptions.create()
                .setHeader("Content-Type","application/json")
                .setHeader("Authorization","Bearer e2c642609a587b4ef1f496ecf10b26fe8e3073e96977417f564cd4a1347da8c4")
                .setData(user));
        Assert.assertEquals(responce1.status(),201);
        System.out.println(responce1.text());
        Assert.assertEquals(responce1.statusText(),"Created");

        //convert response json to POJO
        ObjectMapper objtmap1 = new ObjectMapper();
        Userdetails actuser =objtmap1.readValue(responce1.text(),Userdetails.class);
        System.out.println(actuser);

        Assert.assertEquals(user.getEmail(),actuser.getEmail());
        Assert.assertEquals(user.getGender(),actuser.getGender());
        Assert.assertEquals(user.getStatus(),actuser.getStatus());
        Assert.assertEquals(user.getStatus(),actuser.getStatus());

    }

    //use post call with POJO class -> method 1
    //Deserialization - > JSON to POJO class using Lombok
    @Test
    public void APItest9() throws IOException {

        UserswithLombok user = UserswithLombok.builder().name("TestingAPIPOJo")
                .email("test"+random.nextInt(1000)+"@gmail.com")
                .status("active")
                .gender("male").build();

        APIResponse responce1  = apireqContext.post("https://gorest.co.in/public/v2/users", RequestOptions.create()
                .setHeader("Content-Type","application/json")
                .setHeader("Authorization","Bearer e2c642609a587b4ef1f496ecf10b26fe8e3073e96977417f564cd4a1347da8c4")
                .setData(user));
        Assert.assertEquals(responce1.status(),201);
        System.out.println(responce1.text());
        Assert.assertEquals(responce1.statusText(),"Created");

        //convert response json to POJO
        ObjectMapper objtmap1 = new ObjectMapper();
        Userdetails actuser =objtmap1.readValue(responce1.text(),Userdetails.class);
        System.out.println(actuser);

        Assert.assertEquals(user.getEmail(),actuser.getEmail());
        Assert.assertEquals(user.getGender(),actuser.getGender());
        Assert.assertEquals(user.getStatus(),actuser.getStatus());
        Assert.assertEquals(user.getStatus(),actuser.getStatus());

    }
    @AfterTest
    public void Finally() {
        playwright.close();

    }
}
