package com.test2.demo;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ServerListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                Server server=new Server();
                server.start();
            }
        });
        thread.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}