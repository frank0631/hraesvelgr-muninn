package com.frank0631.muninn;

import com.frank0631.nidhogg.book.Book;
import uk.co.blackpepper.bowman.*;
import uk.co.blackpepper.bowman.annotation.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.*;
import com.fasterxml.jackson.annotation.*;
import java.util.*;

    @RemoteResource("/books/search")
    public interface BookSearch {

        @LinkedResource(rel = "getrandombook")
        public BookEntity getRandomBook(String title);

        @LinkedResource(rel = "findByTitle")
        public List<BookEntity> findByTitle(String title);
        
        @LinkedResource(rel = "findByTitleContaining")
        public List<BookEntity> findByTitleContaining(String title);
    }
