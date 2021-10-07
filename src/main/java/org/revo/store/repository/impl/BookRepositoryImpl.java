package org.revo.store.repository.impl;

import lombok.Getter;
import lombok.Setter;
import org.revo.store.domain.Book;
import org.revo.store.repository.BookRepository;
import org.revo.store.repository.base.RepositoryImpl;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Getter
public class BookRepositoryImpl extends RepositoryImpl<Book, Long> implements BookRepository {
    @Setter
    private List<Book> data = new ArrayList<>();
    private final Path dump = Paths.get(System.getProperty("java.io.tmpdir"), "book.txt");

    public BookRepositoryImpl() {
        super.init();
    }
}
