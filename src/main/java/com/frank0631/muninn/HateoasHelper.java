package com.frank0631.muninn;

import com.frank0631.nidhogg.book.Book;
import com.frank0631.nidhogg.book.BookFormat;
import com.frank0631.nidhogg.customer.Customer;

import uk.co.blackpepper.bowman.Client;
import uk.co.blackpepper.bowman.ClientFactory;
import uk.co.blackpepper.bowman.Configuration;
import uk.co.blackpepper.bowman.annotation.RemoteResource;
import uk.co.blackpepper.bowman.annotation.ResourceId;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.*;
import com.github.javafaker.*;


public class HateoasHelper {

    private String                 APIAddress;
    private ClientFactory          clientFactory;
    private Client<CustomerEntity> customerClient;
    private Client<BookEntity>     bookClient;

    public HateoasHelper(String APIAddress) {
        this.APIAddress = APIAddress;
        this.clientFactory = Configuration.builder().setBaseUri(APIAddress).build().buildClientFactory();
        this.customerClient = clientFactory.create(CustomerEntity.class);
        this.bookClient = clientFactory.create(BookEntity.class);

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
    
//These helper classes might be able to be made dynimically with decorators

    @RemoteResource("/data/customers")
    public static class CustomerEntity extends Customer {
        private URI id;

        public CustomerEntity() {
        }

        public CustomerEntity(Customer c) {
            super(c);
        }

        @ResourceId
        public URI getId() {
            return id;
        }

        @Override
        public String toString() {
            return String.format("Customer[id=%s, firstName='%s', lastName='%s']", getId(), getFirstName(), getLastName());
        }

    }

    @RemoteResource("/data/books")
    public static class BookEntity extends Book {
        private URI id;

        public BookEntity() {
        }

        public BookEntity(Book b) {
            super(b);
        }

        @ResourceId
        public URI getId() {
            return id;
        }

        @Override
        public String toString() {
            return String.format("Book[title=%s, author='%s', publishDate='%s']", getTitle(), getAuthor(), getPublishDate());
        }

    }


}
