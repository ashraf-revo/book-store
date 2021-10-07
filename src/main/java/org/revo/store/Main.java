package org.revo.store;

import org.apache.commons.lang3.math.NumberUtils;
import org.revo.store.domain.Book;
import org.revo.store.service.BookService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Consumer;

public class Main {

    public static void main(String[] args) {
        BookService bookService = new BookService();
        initData(bookService);

        int input;
        Scanner scanner = new Scanner(System.in);
        System.out.println("==== Book Manager ====");
        while (true) {
            System.out.println("      1) View all books");
            System.out.println("      2) Add a book");
            System.out.println("      3) Edit a book");
            System.out.println("      4) Search for a book");
            System.out.println("      5) Save and exit");
            System.out.print("Choose [1-5]: ");
            input = scanner.nextInt();

            if (input == 1) {
                System.out.println("==== View Books ====");
                List<Book> all = bookService.findAll();
                all.forEach(Main::printBook);
                scanner.nextLine();
                viewBookDetailed("To view details enter the book ID, to return press <Enter>.", scanner, bookService, Main::printDetailedBook);
            } else if (input == 2) {
                System.out.println("2");
                System.out.println("=== Add a Book ====");
                System.out.println("Please enter the following information: ");
                scanner.nextLine();
                System.out.print("Title: ");
                String title = scanner.nextLine();
                System.out.print("Author: ");
                String author = scanner.nextLine();
                System.out.print("Description: ");
                String description = scanner.nextLine();
                Book book = bookService.save(new Book(title, author, description));
                System.out.printf("Book [%s] saved\n ", book.getId());
            } else if (input == 3) {
                System.out.println("==== Edit a Book ====");

                List<Book> all = bookService.findAll();
                all.forEach(Main::printBook);
                scanner.nextLine();
                viewBookDetailed("Enter the book ID of the book you want to edit; to return press <Enter>.",
                        scanner, bookService, it -> editBook(bookService, scanner, it));
            } else if (input == 4) {
                System.out.println("=== Search ====");
                System.out.print("Search: ");
                scanner.nextLine();
                String search = scanner.nextLine();
                List<Book> all = bookService.search(search);
                System.out.println("The following books matched your query. Enter the book ID to see more details, or <Enter> to return.");
                all.forEach(Main::printBook);
                viewBookDetailed("", scanner, bookService, Main::printDetailedBook);
            } else if (input == 5) {
                System.out.println("Library saved.");
                bookService.flush();
                return;
            } else {
                System.out.println("Invalid choice ");
            }

        }
    }

    private static void initData(BookService bookService) {
        if (bookService.count() == 0) {
            bookService.save(new Book("The Hobbit", "me", "The Hobbit"));
            bookService.save(new Book("Lord of the Rings", "me", "Lord of the Rings"));
            bookService.save(new Book("Snow White and the Seven Dwarfs", "me", "Snow White and the Seven Dwarfs"));
            bookService.save(new Book("Moby Dick", "me", "Moby Dick"));
            bookService.save(new Book("Snow Crash", "me", "Snow Crash"));
            bookService.save(new Book("Kite Runner", "me", "Kite Runner"));
            bookService.flush();
        }
    }

    private static void editBook(BookService bookService, Scanner scanner, Book it) {
        System.out.println("Input the following information. To leave a field unchanged, hit <Enter>");
        System.out.printf("Title [%s]: ", it.getTitle());
        String title = scanner.nextLine();
        System.out.printf("Author [%s]: ", it.getAuthor());
        String author = scanner.nextLine();
        System.out.printf("Description [%s]: ", it.getDescription());
        String description = scanner.nextLine();

        if (!title.isEmpty())
            it.setTitle(title);
        if (!author.isEmpty())
            it.setAuthor(author);
        if (!description.isEmpty())
            it.setDescription(description);
        bookService.save(it.getId(), it);
        System.out.println("Book saved.");
    }

    private static void printBook(Book it) {
        System.out.printf("[%s] %s\n", it.getId(), it.getTitle());
    }

    private static void printDetailedBook(Book it) {
        System.out.printf("    ID: %s\n", it.getId());
        System.out.printf("    Title: %s\n", it.getTitle());
        System.out.printf("    Author: %s\n", it.getAuthor());
        System.out.printf("    Description: %s\n", it.getDescription());
    }


    private static void viewBookDetailed(String message, Scanner scanner, BookService bookService, Consumer<Book> printDetailedBook) {
        while (true) {
            System.out.println(message);
            System.out.print("Book ID: ");
            String s = scanner.nextLine();
            if (!s.isEmpty() && NumberUtils.isCreatable(s)) {
                Optional<Book> book = bookService.findOne(Long.parseLong(s));
                book.ifPresent(printDetailedBook);
            } else break;
        }
    }
}

