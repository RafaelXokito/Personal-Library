# Personal Library API Documentation

## Overview
This document provides detailed information on how to interact with the Personal Library API. For any endpoints requiring authorization, ensure you have the proper Bearer token included in the request headers.

## API Endpoints

### User Authentication

#### 1. Register
- **URL**: `http://localhost:8080/api/auth/register`
- **Method**: POST
- **Body**: Contains user details like `firstName`, `lastName`, `email`, `password`, and `isWriter`.

#### 2. Login
- **URL**: `http://localhost:8080/api/auth/login`
- **Method**: POST
- **Body**: Contains user `email` and `password`.

#### 3. Me
- **URL**: `http://localhost:8080/api/auth/me`
- **Method**: GET
- **Headers**:
    - Authorization: Bearer [token_value]

#### 4. Refresh Token
- **URL**: `http://localhost:8080/api/auth/token`
- **Method**: POST
- **Body**: Contains `refreshToken`.

### Book Management

#### 5. Create Book
- **URL**: `http://localhost:8080/api/books`
- **Method**: POST
- **Headers**:
    - Authorization: Bearer [token_value]
- **Body**: Contains book details like `title`, `description`, and `content`.

#### 6. GET Books
- **URL**: `http://localhost:8080/api/books`
- **Method**: GET

#### 7. GET Books Search
- **URL**: `http://localhost:8080/api/books/search?title=${title}&keyword=${description}&writerName=${content}`
- **Method**: GET

#### 8. GET Book
- **URL**: `http://localhost:8080/api/books/${bookId}`
- **Method**: GET

#### 9. Add Book
- **Description**: Adds a book to the system.
- **URL**: `http://localhost:8080/api/books/${bookId}/add`
- **Method**: PATCH
- **Headers**:
    - Authorization: Bearer [token_value]

#### 10. Remove Book
- **Description**: Removes a book from the system.
- **URL**: `http://localhost:8080/api/books/${bookId}/remove`
- **Method**: DELETE
- **Headers**:
    - Authorization: Bearer [token_value]

### Book Details

#### 11. GET Book Writer
- **Description**: Retrieves the writer of the specified book.
- **URL**: `http://localhost:8080/api/books/${bookId}/writer`
- **Method**: GET

#### 12. GET Book Current Readers
- **Description**: Retrieves a list of current readers for a given book.
- **URL**: `http://localhost:8080/api/books/${bookId}/currentreaders`
- **Method**: GET
- **Headers**:
    - Authorization: Bearer [token_value]

#### 13. GET Book Readers
- **Description**: Retrieves a list of all readers of a specified book.
- **URL**: `http://localhost:8080/api/books/${bookId}/readers`
- **Method**: GET
- **Headers**:
    - Authorization: Bearer [token_value]

### Reading Flow and Pagination

#### 14. Read Book
- **Description**: Marks a book as read for the authenticated user.
- **URL**: `http://localhost:8080/api/books/${bookId}/read`
- **Method**: PATCH
- **Headers**:
    - Authorization: Bearer [token_value]

#### 15. Next Book Page
- **Description**: Navigates to the next page of book listings.
- **URL**: `http://localhost:8080/api/books/nextpage`
- **Method**: PATCH
- **Headers**:
    - Authorization: Bearer [token_value]

#### 16. Prev Book Page
- **Description**: Navigates to the previous page of book listings.
- **URL**: `http://localhost:8080/api/books/prevpage`
- **Method**: PATCH
- **Headers**:
    - Authorization: Bearer [token_value]

## Conclusion
Interact with the API endpoints as described to manage and retrieve data from the Personal Library system. Always ensure to use the correct headers and body parameters as necessary.