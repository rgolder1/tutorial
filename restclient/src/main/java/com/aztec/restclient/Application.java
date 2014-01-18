package com.aztec.restclient;

import org.springframework.web.client.RestTemplate;

/**
 * Because the Jackson JSON processing library is in the classpath, RestTemplate will use it (via a 
 * message converter) to convert the incoming JSON data into a Page object. From there, the contents 
 * of the Page object will be printed to the console.
 * 
 * Uses Spring Boot for the build system to create a single, executable jar containing everything. 
 * This makes it easy to ship, version, and deploy the service as an application throughout the development 
 * lifecycle, across different environments, and so forth.
 * 
 * Suppose that you want to find out what Facebook knows about Pivotal. Knowing that Pivotal has a page on 
 * Facebook and that the ID is "gopivotal", you should be able to query Facebook's Graph API via this URL:
 * 
 * http://graph.facebook.com/gopivotal
 * 
 * If you request that URL through your web browser or curl, you'll receive a JSON document that looks something like this:
 * 
 * {
 *   "id": "161112704050757",
 *   "about": "At Pivotal, our mission is to enable customers to build a new class of applications, leveraging big and fast data, 
 *   "app_id": "0",
 *   "can_post": false,
 *   "category": "Internet/software",
 *   "checkins": 0,
 *   [......]
 *   
 *  Easy enough, but not terribly useful when fetched through a browser or through curl.
 *  
 *  A more useful way to consume a REST web service is programmatically. To help you with that task, Spring provides a 
 *  convenient template class called RestTemplate. RestTemplate makes interacting with most RESTful services a one-line 
 *  incantation. And it can even bind that data to custom domain types.
 *  
 *  Because the Jackson JSON processing library is in the classpath, RestTemplate will use it (via a message converter) 
 *  to convert the incoming JSON data into a Page object. From there, the contents of the Page object will be printed to 
 *  the console.
 */
public class Application {

    public static void main(String args[]) {
        RestTemplate restTemplate = new RestTemplate();
        Page page = restTemplate.getForObject("http://graph.facebook.com/gopivotal", Page.class);
        System.out.println("Name:    " + page.getName());
        System.out.println("About:   " + page.getAbout());
        System.out.println("Phone:   " + page.getPhone());
        System.out.println("Website: " + page.getWebsite());
    }

}
