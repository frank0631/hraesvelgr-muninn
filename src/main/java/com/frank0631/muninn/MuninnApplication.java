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
		HateoasHelper hateoasHelper = new HateoasHelper(api_url+"/data");

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
		
		hateoasHelper.getRandomBook("An Acceptable Time");
		hateoasHelper.findBooks("An");

	}
	
}
