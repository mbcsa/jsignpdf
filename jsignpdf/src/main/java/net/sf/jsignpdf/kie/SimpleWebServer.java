/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.sf.jsignpdf.kie;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.HttpString;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/**
 * Hello world!
 * Returns a simple web page on port 8080.
 */
public class SimpleWebServer {

    private static Undertow server;

    public enum Status {
        STARTED, 
        WORKING,
        SUCCESS,
        INCONSISTENT,
        INTERNAL_ERROR
    }
    
    public static Status currentStatus = Status.STARTED;
    
    public static void setSuccess(){
        currentStatus = Status.SUCCESS;
    }
    
    public static void setError(){
        currentStatus = Status.INTERNAL_ERROR;
    }
        
    public static void stop(){
        System.out.println("Deteniendo...");
        server.stop();
        server = null;
    }
    
    public static void start(KieOptions kieOptions) {
        server = Undertow.builder()
                .addHttpListener(8999, "localhost")
                .setHandler( exchange -> {
                    exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
                    exchange.getResponseHeaders().put(new HttpString("Access-Control-Allow-Origin"), "https://ft.doctest.corsisa.com.ar");
                    exchange.getResponseHeaders().put(new HttpString("Access-Control-Allow-Credentials"), "true"); 
                    exchange.getResponseHeaders().put(new HttpString("Access-Control-Allow-Methods"), "GET, POST, PUT, DELETE, OPTIONS"); 
                    exchange.getResponseHeaders().put(new HttpString("Access-Control-Allow-Headers"), "Authorization, Content-Type"); 
                                        
                    if (currentStatus == Status.STARTED) {
                        if (exchange.getRequestMethod().equals(HttpString.tryFromString("POST"))) {
                            // Map<String, Deque<String>> queryParams = exchange.getQueryParameters();
                            StringBuilder requestBody = new StringBuilder();
                            // collect body of the request
                            exchange.getRequestReceiver().receiveFullString((ex, data) -> {
                              requestBody.append(data);
                            });
                            final String body = requestBody.toString();
                            String[] values = body.split(",");
                            System.out.println("Parámetros recibidos.");
                            System.out.println(body);
                            kieOptions.loadOptionsFromWebServer(values);
                            currentStatus = Status.WORKING;
                            exchange.getResponseSender().send("{'status': 'OK'}");
                        } else {
                            if (exchange.getRequestMethod().equals(HttpString.tryFromString("OPTIONS"))) {
                                exchange.getResponseSender().send("");
                            } else {
                                currentStatus = Status.INCONSISTENT;
                                exchange.getResponseSender().send("{'status': '" + currentStatus + "'}");
                                System.exit(0);
                            }
                        }
                    } else {
                        exchange.getResponseSender().send("{'status': '"+ currentStatus +"'}");
                    }
                }).build();

        // Boot the web server
        System.out.println("Escuchando en puerto.");
        server.start();
        
        //        server = Undertow.builder()
//                // Set up the listener - you can change the port/host here
//                // 0.0.0.0 means "listen on ALL available addresses"
//                .addHttpListener(8999, "0.0.0.0")
//
//                .setHandler(exchange -> {
//                    // Sets the return Content-Type to text/html
//                    exchange.getResponseHeaders()
//                            .put(Headers.CONTENT_TYPE, "text/html");
//                    
//                    // Returns a hard-coded HTML document
//                    exchange.getResponseSender()
//                            .send("<html>" +
//                                    "<body>" +
//                                    "<h1>Hello, world!</h1>" +
//                                    "</body>" +
//                                    "</html>");
//                }).build();
    }
}