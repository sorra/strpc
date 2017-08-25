package com.successfactors.strpc.server;

import java.io.File;

import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

public class ServerMain {

  public static void main(String[] args) throws Exception {
    final String gradleClassesDir = "build/classes/java/main";
    final String ideaClassesDir = "out/production/classes";

    Tomcat tomcat = new Tomcat();

    //The port that we should run on can be set into an environment variable
    //Look for that variable and default to 8080 if it isn't there.
    String webPort = System.getenv("PORT");
    if (webPort == null || webPort.isEmpty()) {
      webPort = "8080";
    }

    tomcat.setPort(Integer.valueOf(webPort));

    final String webappDir = "src/main/webapp/";
    StandardContext ctx = (StandardContext) tomcat.addWebapp("/", new File(webappDir).getAbsolutePath());
    System.out.println("configuring app with basedir: " + new File("./" + webappDir).getAbsolutePath());

    // Declare an alternative location for your "WEB-INF/classes" dir
    // Servlet 3.0 annotation will work
    File additionWebInfClasses = new File(ideaClassesDir);
    WebResourceRoot resources = new StandardRoot(ctx);
    resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes",
        additionWebInfClasses.getAbsolutePath(), "/"));
    ctx.setResources(resources);

    tomcat.start();
    tomcat.getServer().await();
  }
}