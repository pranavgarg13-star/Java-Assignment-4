# Java-Assignment 4
By : Pranav Garg , Roll No. : 2401010034

City Library Digital Management System

This is a simple menu-driven Java application for managing a digital library.
It supports adding books/members, issuing & returning books, searching, sorting, and storing data in text files.

## Features
- Book Management
- Add new books
- Store book details (ID, title, author, category)
- Issue a book
- Return a book
- Search books (by title, author, or category)
- Sort books by:
a. Title
b. Author
- Auto-generate Book IDs
- Save & load books from file (books.txt)
- Member Management
- Add new members
- Store member info (ID, name, email)
- Track issued books
- Save & load members from file (members.txt)
- File Handling
- Persistent data storage using text files
- Save records after every update
- Load data on startup

## Contains These Classes

Book → Stores book details, handles issuing/returning, sorting

Member → Stores member details & issued books

LibraryManager → Core logic, menu system, file handling

CityLibrarySystem (Main Class) → Starts the program

## How to Run

1. Compile the program : javac CityLibrarySystem.java


2. Run the program : java CityLibrarySystem


3. Make sure the program has permission to create/write the files:
- books.txt
- members.txt

## Sample Interaction
===== City Library Digital Management System =====
1. Add Book
2. Add Member
3. Issue Book
4. Return Book
5. Search Books
6. Sort Books
7. Exit
Enter choice: 1

Enter Book Title: Atomic Habits

Enter Author: James Clear

Enter Category: Self-help

Book added successfully with ID: 101


===== City Library Digital Management System =====

Enter choice: 5

Search by (title/author/category): atomic

Book ID: 101

Title: Atomic Habits

Author: James Clear

Category: Self-help

Issued: No


===== City Library Digital Management System =====

Enter choice: 3

Enter Book ID: 101

Enter Member ID: 201

Book issued successfully!
