@Books
Feature: BookTest

  Background:
    * url baseUrl

  @Books @name=perf
  Scenario: Get all books
    * def responseExpected =
    """
    [
    {"id":1,"bookName":"Angels and Demons","authorName":"Dan Brown"},
    {"id":2,"bookName":"Inferno","authorName":"Dan Brown"},
    {"id":3,"bookName":"Harry Potter and the Sorcerers Stone (Book 1)","authorName":"J. K. Rowling"},
    {"id":4,"bookName":"Harry Potter and the Prisoner of Azkaban","authorName":"J. K. Rowling"},
    {"id":5,"bookName":"The Hobbit","authorName":"J. R. R. Tolkien"},
    {"id":6,"bookName":"1984","authorName":"George Orwell"},
    {"id":7,"bookName":"Pride and Prejudice","authorName":"Jane Austen"},
    {"id":8,"bookName":"To Kill a Mockingbird","authorName":"Harper Lee"}
    ]
    """
    * path '/api/books'
    * method GET
    * status 200
    * match $ == responseExpected


  @Books
  Scenario: Get all books expected in table
    * table responseExpected
      | id | bookName                                        | authorName         |
      | 1  | 'Angels and Demons'                             | 'Dan Brown'        |
      | 2  | 'Inferno'                                       | 'Dan Brown'        |
      | 3  | "Harry Potter and the Sorcerers Stone (Book 1)" | 'J. K. Rowling'    |
      | 4  | 'Harry Potter and the Prisoner of Azkaban'      | 'J. K. Rowling'    |
      | 5  | 'The Hobbit'                                    | 'J. R. R. Tolkien' |
      | 6  | '1984'                                          | 'George Orwell'    |
      | 7  | 'Pride and Prejudice'                           | 'Jane Austen'      |
      | 8  | 'To Kill a Mockingbird'                         | 'Harper Lee'       |
    * path '/api/books'
    * method GET
    * status 200
    * match $ == responseExpected


  @Books
  Scenario: Get all books expected in a csv
    * def responseExpected = read("books_authors.csv")
    * path '/api/books'
    * method GET
    * status 200
    * match $ == responseExpected


  @Books
  Scenario: Validating the contains matcher
    * path '/api/books'
    * method GET
    * status 200
    * match $ contains {"id":6,"bookName":"1984","authorName":"George Orwell"}


  @Books
  Scenario: Validating the jsonPath
    * def expectedBookNames =
    """
    ["Angels and Demons",
    "Inferno",
    "Harry Potter and the Sorcerers Stone (Book 1)",
    "Harry Potter and the Prisoner of Azkaban",
    "The Hobbit","1984","Pride and Prejudice",
    "To Kill a Mockingbird"]
    """
    * path '/api/books'
    * method GET
    * status 200
    * match $.[*].bookName == expectedBookNames


  @Books
  Scenario: Validating negative Scenario
    * path '/api/books'
    * method GET
    * status 200
    * match $.[*].bookName != "Istanbul"

  @Books
  Scenario: Validate one book by name
    * path '/api/books/1984'
    * method GET
    * status 200
    * match $ == {"id": '#ignore',"bookName":"1984","authorName":"George Orwell"}


  @Books
  Scenario: Fuzzy match structure using api/books/{bookName}
    * path '/api/books/1984'
    * method GET
    * status 200
    * match $ == {"id":"#number","bookName":"#string","authorName":"#string"}




