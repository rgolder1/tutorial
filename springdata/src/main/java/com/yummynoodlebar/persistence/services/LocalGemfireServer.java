package com.yummynoodlebar.persistence.services;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Creates a cache server in this process and adds some data to the cache
 * @author David Turanski
 *
 */
public class LocalGemfireServer {
  @SuppressWarnings("unchecked")
  public static void main(String[] args) throws IOException,
      InterruptedException {
    ApplicationContext context = new ClassPathXmlApplicationContext("server/cache-config.xml");

    System.out.println("Press <ENTER> to terminate the cache server");
    System.in.read();
    System.exit(0);
  }
}