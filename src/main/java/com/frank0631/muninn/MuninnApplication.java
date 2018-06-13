package com.frank0631.muninn;

import com.frank0631.nidhogg.book.Book;
import com.frank0631.nidhogg.book.BookFormat;
import com.frank0631.nidhogg.customer.Customer;
import com.frank0631.nidhogg.echo.TEchoService;
import com.frank0631.nidhogg.calculator.TCalculatorService;
import com.frank0631.nidhogg.calculator.TOperation;

import org.apache.thrift.*;
import org.apache.thrift.protocol.*;
import org.apache.thrift.transport.*;

import retrofit2.*;
import retrofit2.http.*;
import retrofit2.converter.gson.GsonConverterFactory;
import com.google.gson.*;
import com.google.gson.annotations.SerializedName;
import okhttp3.ResponseBody;

import java.util.*;
import com.github.javafaker.*;

public class MuninnApplication {
	private static final String ADDRESS = "http://localhost";
	private static final int PORT = 9001;

	public static void main(String[] args) {
		MuninnApplication muninn = new MuninnApplication(ADDRESS, PORT);
	}

	public MuninnApplication(String address, int port) {
		//setup helpers
		String api_url = address + ":" + port;
		ThriftHelper thriftHelper = new ThriftHelper(api_url);
		RestHelper restHelper = new RestHelper(api_url);
		HateoasHelper hateoasHelper = new HateoasHelper(api_url);

		//inputs
		String echoMessage = "Hello";
		int num1 = 2, num2 = 2;
		TOperation op = TOperation.ADD;

		//fake data
		Faker faker = new Faker(new Locale("en"));
		String firstName = faker.name().firstName();
		String lastName = faker.name().lastName();
		Customer customer = new Customer(firstName, lastName);

		String name = faker.book().author();
		String title = faker.book().title();
		Book book = new Book(name, title, "null", "null",
				BookFormat.ELECTRONIC, 0);

		triftTest(thriftHelper, echoMessage, num1, num2, op);
		restTest(restHelper, echoMessage, num1, num2, op);
		hateoasTest(hateoasHelper, customer, book);

	}

	public void triftTest(ThriftHelper thriftHelper, String echoMessage,
			int num1, int num2, TOperation op) {
		String echoed;
		int calced;

		//thrift calls
		echoed = thriftHelper.echo(echoMessage);
		calced = thriftHelper.calc(num1, num2, op);

		System.out.printf("Sent to thrift echo \"%s\"; got back \"%s\"\n",
				echoMessage, echoed);
		System.out.printf("Sent to thrift calc %d %s %d; got back %d\n", num1,
				op, num2, calced);
	}

	public void restTest(RestHelper restHelper, String echoMessage, int num1,
			int num2, TOperation op) {
		String echoed;
		int calced;

		//rest calls
		echoed = restHelper.echo(echoMessage);
		calced = restHelper.calc(num1, num2, op.toString());

		System.out.printf("Sent to rest echo \"%s\"; got back \"%s\"\n",
				echoMessage, echoed);
		System.out.printf("Sent to rest calc %d %s %d; got back %d\n", num1,
				op, num2, calced);
	}
	
	public void hateoasTest(HateoasHelper hateoasHelper, Customer customer, Book book){
		hateoasHelper.addCustomer(customer);
		hateoasHelper.getCustomers();
		
		hateoasHelper.addBook(book);
		hateoasHelper.getBooks();
	}

	


	//		restEcho(echoMessage);

	//		restCalc(2, 2, "ADD");

	//        Gson gson = new GsonBuilder()
	//            .setLenient()
	//            .create();
	//        Retrofit retrofit = new Retrofit.Builder()
	//            .baseUrl(API_URL)
	//            .addConverterFactory(GsonConverterFactory.create(gson))
	//            .build();
	//
	//        HuginnService huginnService = retrofit.create(HuginnService.class);
	//        try {
	//            
	//            Faker faker = new Faker(new Locale("en"));
	//            String firstName = faker.name().firstName();
	//            String lastName = faker.name().lastName();
	//            Customer customer = new Customer(firstName, lastName);
	//                        
	//            Call<Customer> call1 = huginnService.createCustomer(customer);
	//            Response<Customer> result = call1.execute();
	//            System.out.println(result.toString());
	////            System.out.println(result.errorBody().string());
	//                        
	//            Call<ResponseBody> call2 = huginnService.getCustomers();
	//            Response<ResponseBody> result2 = call2.execute();
	//            System.out.println(result2.body().string());
	//            
	//            
	//        } catch (Exception e) {
	//            e.printStackTrace();
	//        }

	//        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(API_URL).build();
	//        //restAdapter.setLogLevel(RestAdapter.LogLevel.FULL);
	//        HuginnService service = restAdapter.create(HuginnService.class);
	//        
	//        Response resp = service.getCustomers();
	//        System.out.println(resp.body());

	//		
	//
	//		Faker faker = new Faker(new Locale("en"));
	//		String firstName = faker.name().firstName();
	//		String lastName = faker.name().lastName();
	//		Customer customer = new Customer(firstName, lastName);
	//
	//		String name = faker.book().author();
	//		String title = faker.book().title();
	//		Book book = new Book(name, title, "null", "null", BookFormat.ELECTRONIC, 0);

	//    public static void thriftEcho(String msg) {
	//        try {
	//            TTransport transport = new THttpClient(ADDRESS + ":" + PORT + thriftEndpointEcho);
	//            TProtocol protocol = new TJSONProtocol(transport);
	//            TEchoService.Client client = new TEchoService.Client(protocol);
	//
	//            transport.open();
	//            String echo = client.echo(msg);
	//            transport.close();
	//
	//            System.out.println(echo);
	//        } catch (TTransportException e) {
	//            e.printStackTrace();
	//        } catch (TException e) {
	//            e.printStackTrace();
	//        }
	//    }
	//
	//    public static void thriftCalc(int num1, int num2, TOperation op) {
	//        try {
	//            TTransport transport = new THttpClient(ADDRESS + ":" + PORT + thriftEndpointCalculator);
	//            TProtocol protocol = new TJSONProtocol(transport);
	//            TCalculatorService.Client client = new TCalculatorService.Client(protocol);
	//            transport.open();
	//
	//            int result = client.calculate(num1, num2, op);
	//            transport.close();
	//
	//            System.out.println(result);
	//        } catch (TTransportException e) {
	//            e.printStackTrace();
	//        } catch (TException e) {
	//            e.printStackTrace();
	//        }
	//    }

	//	public static void restEcho(String msg) {
	//		try {
	//			Client client = ClientBuilder.newClient();
	//			WebTarget target = client.target(ADDRESS + ":" + PORT
	//					+ restEndpointEcho);
	//			Invocation.Builder invocationBuilder;
	//			invocationBuilder = target.request(MediaType.APPLICATION_JSON);
	//
	//			Form form = new Form();
	//			form.param("input", msg);
	//			Response response = invocationBuilder.post(Entity.form(form));
	//
	//			System.out.println(response.getStatus());
	//			System.out.println(response.readEntity(String.class));
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//		}
	//	}
	//
	//	public static void restCalc(int num1, int num2, String op) {
	//		try {
	//			Client client = ClientBuilder.newClient();
	//			WebTarget target = client.target(ADDRESS + ":" + PORT
	//					+ restEndpointCalculator);
	//			Invocation.Builder invocationBuilder;
	//			invocationBuilder = target.request(MediaType.APPLICATION_JSON);
	//
	//			Form form = new Form();
	//			form.param("num1", Integer.toString(num1));
	//			form.param("num2", Integer.toString(num2));
	//			form.param("op", op);
	//			Response response = invocationBuilder.post(Entity.form(form));
	//
	//			System.out.println(response.getStatus());
	//			System.out.println(response.readEntity(Map.class));
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//		}
	//	}

}
