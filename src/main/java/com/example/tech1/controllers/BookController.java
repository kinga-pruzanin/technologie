package com.example.tech1.controllers;

import com.example.tech1.Book;
import com.example.tech1.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * Controller for managing books.
 */
@RestController
@RequestMapping("/book")
public class BookController {

    private final BookRepository bookRepo;

    /**
     * Constructs a new BookController.
     * @param bookRepo The book repository.
     */
    @Autowired
    public BookController(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    /**
     * Adds a new book.
     * @param book The book to add.
     * @return The added book.
     * @throws ResponseStatusException If the book with the same ISBN already exists in the database.
     */
    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public @ResponseBody Book addBook(@RequestBody Book book) {
        String isbnRegex = "\\d{13}";
        if (!book.getIsbn().matches(isbnRegex)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ISBN must contain exactly 13 digits");
        }
        if (bookRepo.findBookByIsbn(book.getIsbn()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This book was already added to the database");
        }
        return bookRepo.save(book);
    }

    /**
     * Retrieves all books.
     * @return All books in the database.
     */
    @GetMapping("/getAll")
    public @ResponseBody Iterable<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    /**
     * Deletes a book by its ISBN.
     *
     * If the book has never been borrowed, it is deleted from the repository.
     * If the book has been borrowed, it checks whether the book can be deleted.
     * If it cannot be deleted due to active rentals, a conflict status is returned.
     * Otherwise, the book is marked as deleted and a created status is returned.
     *
     * @param isbn the ISBN of the book to be deleted.
     */
    @DeleteMapping("/delete/{isbn}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteBookByIsbn(@PathVariable String isbn) {
        Book book = bookRepo.findByIsbn(isbn);
        if (book == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with ISBN " + isbn + " not found");
        }
        boolean wasBorrowed = bookRepo.wasBookEverBorrowed(isbn);
        if (wasBorrowed) {
            boolean result = bookRepo.canDeleteBook(isbn);
            if (!result) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot delete book with active rentals");
            } else {
                book.setDeleted(true);
                bookRepo.save(book);
                throw new ResponseStatusException(HttpStatus.CREATED, "Book marked as deleted");
            }
        } else {
            bookRepo.delete(book);
        }
    }

    /**
     * Updates the information of a book.
     *
     * If the book with the given ISBN is not found, a not found status is returned.
     * Otherwise, the book information is updated and saved in the repository.
     *
     * @param isbn the ISBN of the book to be updated.
     * @param updatedBook the updated book information.
     */
    @PutMapping("/update/{isbn}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBook(@PathVariable("isbn") String isbn, @RequestBody Book updatedBook) {
        try {
            Book existingBook = bookRepo.findByIsbn(isbn);
            if (existingBook == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
            }

            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setAuthor(updatedBook.getAuthor());
            existingBook.setPublisher(updatedBook.getPublisher());
            existingBook.setPublishYear(updatedBook.getPublishYear());
            existingBook.setAvailableCopies(updatedBook.getAvailableCopies());

            bookRepo.save(existingBook);

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating book", e);
        }
    }

}
