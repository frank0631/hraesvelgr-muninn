package com.frank0631.muninn;

import com.frank0631.nidhogg.book.Book;
import uk.co.blackpepper.bowman.*;
import uk.co.blackpepper.bowman.annotation.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.*;
import com.fasterxml.jackson.annotation.*;

import java.util.*;
import java.net.URI;

    @RemoteResource("/books")
    public class BookEntity extends Book {
        private URI id;

        public BookEntity() {
        }

        public BookEntity(Book b) {
            super(b);
        }

        @ResourceId
        @JsonIgnore
        public URI getId() {
            return id;
        }

        @Override
        public String toString() {
            return String.format("Book[title=%s, author='%s', publishDate='%s']", getTitle(), getAuthor(), getPublishDate());
        }
        
    }