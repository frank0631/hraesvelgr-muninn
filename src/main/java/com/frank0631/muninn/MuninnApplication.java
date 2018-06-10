package com.frank0631.muninn;

import com.frank0631.nidhogg.book.Book;
import com.frank0631.nidhogg.echo.TEchoService;

import org.apache.thrift.*;
import org.apache.thrift.protocol.*;
import org.apache.thrift.transport.*;

import javax.ws.rs.client.*;
import javax.ws.rs.core.*;


public class MuninnApplication {
    private static final String ADDRESS = "http://localhost";
    private static final int PORT = 9001;
    private static String echoMessage = "Hello";
    private static String echoThriftEndpoint = "/thrift/echo/echo/";
    private static String echoRestEndpoint = "/api/echo/";
    
    public static void main(String[] args) {
            restEcho();
            thriftEcho();
    }

    public static void thriftEcho(){
        try {
            TTransport transport = new THttpClient(ADDRESS+ ":" + PORT + echoThriftEndpoint);
            TProtocol protocol = new TJSONProtocol(transport);
            TEchoService.Client client = new TEchoService.Client(protocol);
            transport.open();
            String echo = client.echo(echoMessage);
            transport.close();
            System.out.println(echo);

        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        }
    }
    
    public static void restEcho(){
        try{

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(ADDRESS+ ":" + PORT + echoRestEndpoint);
        
        Invocation.Builder invocationBuilder;
        invocationBuilder = target.request(MediaType.APPLICATION_JSON);
		Form form = new Form();
        form.param("input", echoMessage);
		
        Response response = invocationBuilder.post(Entity.form(form));
        System.out.println(response.getStatus());
        System.out.println(response.readEntity(String.class));
        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
