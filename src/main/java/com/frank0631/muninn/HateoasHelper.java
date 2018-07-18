package com.frank0631.muninn;

import com.frank0631.nidhogg.book.Book;
import com.frank0631.nidhogg.book.BookFormat;
import com.frank0631.nidhogg.customer.Customer;

import uk.co.blackpepper.bowman.*;
import uk.co.blackpepper.bowman.annotation.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.*;
import com.fasterxml.jackson.annotation.*;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.net.URI;
import java.util.*;
import com.github.javafaker.*;


public class HateoasHelper {

    private String                 APIAddress;
    private ClientFactory          clientFactory;
    private Client<CustomerEntity> customerClient;
    private Client<BookEntity>     bookClient;
    private Client<BookSearch>       bookSearchClient;

    public HateoasHelper(String APIAddress) {
        this.APIAddress = APIAddress;
        this.clientFactory = Configuration.builder()
        	.setBaseUri(APIAddress)
        	.setClientHttpRequestFactory(new BufferingClientHttpRequestFactory(
        		new HttpComponentsClientHttpRequestFactory()))
        	.build()
        	.buildClientFactory();

        this.customerClient = clientFactory.create(CustomerEntity.class);
        this.bookClient = clientFactory.create(BookEntity.class);
        this.bookSearchClient = clientFactory.create(BookSearch.class);
    }

    //These helper methods look ripe for generics 

    public void getCustomers() {
        Iterable<CustomerEntity> customers = this.customerClient.getAll();
        for (Customer c : customers) {
            System.out.println(c);
        }
    }

    public void getBooks() {
        Iterable<BookEntity> books = this.bookClient.getAll();
        for (Book b : books) {
            System.out.println(b);
        }
    }

    public void addCustomer(Customer customer) {
        try {
            CustomerEntity ce = new CustomerEntity(customer);
            System.out.println(ce);
            this.customerClient.post(ce);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public void addBook(Book book) {
        try {
            BookEntity be = new BookEntity(book);
            System.out.println(be);
            this.bookClient.post(be);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public void getRandomBook(String title) {
        try {
			BookEntity rand = this.bookSearchClient.get().getRandomBook(title);
            System.out.println(rand);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void findBooks(String title) {
        try { 
			List<BookEntity> find = this.bookSearchClient.get().findByTitleContaining(title);
			for (BookEntity b : find) {
            	System.out.println(b);
        	}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
