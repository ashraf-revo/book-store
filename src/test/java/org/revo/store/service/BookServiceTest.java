package org.revo.store.service;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.revo.store.domain.Book;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class BookServiceTest {
    private final BookService bookService = new BookService();


    @Test
    public void findOne() {
        Book save = bookService.save(new Book("first title ", "first author", "desc"));
        assertThat(bookService.findOne(save.getId()).isPresent(), equalTo(true));
    }

    @Test
    public void search() {
        Book save = bookService.save(new Book("first title ", "first author", "desc"));
        List<Book> first = bookService.search("first");
        assertThat(first.size(), CoreMatchers.not(0));
    }

    @Test
    public void count() {
        int count = bookService.count();
        bookService.save(new Book("test", "test", "test"));
        assertThat(bookService.count(), equalTo(count + 1));
    }
}
