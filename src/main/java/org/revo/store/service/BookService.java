package org.revo.store.service;

import org.revo.store.domain.Book;
import org.revo.store.repository.BookRepository;
import org.revo.store.repository.impl.BookRepositoryImpl;

import java.util.List;
import java.util.Optional;

public class BookService {
    private final BookRepository bookRepository = new BookRepositoryImpl();

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }


    public void flush() {
        bookRepository.flush();
    }

    public Optional<Book> findOne(Long id) {
        return bookRepository.findOne(id);
    }

    public List<Book> search(String s) {
        return bookRepository.search(s, book -> book.getTitle().toLowerCase().contains(s.toLowerCase()) ||
                book.getAuthor().toLowerCase().contains(s.toLowerCase()) ||
                book.getDescription().toLowerCase().contains(s.toLowerCase()));
    }

    public void save(Long id, Book it) {
        bookRepository.save(id, it);
    }

    public int count() {
        return bookRepository.findAll().size();
    }
}
