package com.frank0631.muninn;

import com.frank0631.nidhogg.customer.Customer;

import uk.co.blackpepper.bowman.*;
import uk.co.blackpepper.bowman.annotation.*;
import java.net.URI;
import java.util.*;


    @RemoteResource("/customers")
    public class CustomerEntity extends Customer {
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