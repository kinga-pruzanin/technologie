package com.example.tech1;

import jakarta.persistence.*;

@Entity
@Table(name = "details")
public class BookDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "bookId")
    private Book book;

    private String genre;

    private String summary;

    private String coverImageURL;
}
