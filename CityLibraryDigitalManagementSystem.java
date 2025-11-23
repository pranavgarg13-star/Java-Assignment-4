import java.io.*;
import java.util.*;


class Book implements Comparable<Book> {
    private int bookId;
    private String title;
    private String author;
    private String category;
    private boolean isIssued;

    public Book(int bookId, String title, String author, String category, boolean isIssued) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.isIssued = isIssued;
    }

    public int getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getCategory() { return category; }
    public boolean isIssued() { return isIssued; }

    public void markAsIssued() { isIssued = true; }
    public void markAsReturned() { isIssued = false; }

    public void displayBookDetails() {
        System.out.println("Book ID: " + bookId);
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Category: " + category);
        System.out.println("Issued: " + (isIssued ? "Yes" : "No"));
        System.out.println("-----------------------------------");
    }

    @Override
    public int compareTo(Book other) {
        return this.title.compareToIgnoreCase(other.title);
    }

    public String toFileString() {
        return bookId + ";" + title + ";" + author + ";" + category + ";" + isIssued;
    }

    public static Book fromFileString(String line) {
        String[] data = line.split(";");
        return new Book(
            Integer.parseInt(data[0]),
            data[1],
            data[2],
            data[3],
            Boolean.parseBoolean(data[4])
        );
    }
}


class Member {
    private int memberId;
    private String name;
    private String email;
    private List<Integer> issuedBooks = new ArrayList<>();

    public Member(int memberId, String name, String email, List<Integer> issuedBooks) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.issuedBooks = issuedBooks;
    }

    public int getMemberId() { return memberId; }
    public List<Integer> getIssuedBooks() { return issuedBooks; }

    public void addIssuedBook(int bookId) {
        issuedBooks.add(bookId);
    }

    public void returnIssuedBook(int bookId) {
        issuedBooks.remove(Integer.valueOf(bookId));
    }

    public void displayMemberDetails() {
        System.out.println("Member ID: " + memberId);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Issued Books: " + issuedBooks);
        System.out.println("-----------------------------------");
    }

    public String toFileString() {
        return memberId + ";" + name + ";" + email + ";" + issuedBooks.toString();
    }

    public static Member fromFileString(String line) {
        String[] data = line.split(";");
        int id = Integer.parseInt(data[0]);
        String name = data[1];
        String email = data[2];

        List<Integer> list = new ArrayList<>();
        String nums = data[3].replace("[", "").replace("]", "");
        if (!nums.isEmpty()) {
            for (String part : nums.split(",")) {
                list.add(Integer.parseInt(part.trim()));
            }
        }

        return new Member(id, name, email, list);
    }
}


class LibraryManager {

    private Map<Integer, Book> books = new HashMap<>();
    private Map<Integer, Member> members = new HashMap<>();

    private final String BOOKS_FILE = "books.txt";
    private final String MEMBERS_FILE = "members.txt";

    private Scanner sc = new Scanner(System.in);

    public LibraryManager() {
        loadFromFile();
    }

    public void loadFromFile() {
        try {
            File bookFile = new File(BOOKS_FILE);
            if (bookFile.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(bookFile));
                String line;
                while ((line = br.readLine()) != null) {
                    Book b = Book.fromFileString(line);
                    books.put(b.getBookId(), b);
                }
                br.close();
            }

            File memberFile = new File(MEMBERS_FILE);
            if (memberFile.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(memberFile));
                String line;
                while ((line = br.readLine()) != null) {
                    Member m = Member.fromFileString(line);
                    members.put(m.getMemberId(), m);
                }
                br.close();
            }

        } catch (IOException e) {
            System.out.println("Error loading from file.");
        }
    }

    public void saveToFile() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(BOOKS_FILE));
            for (Book b : books.values()) {
                bw.write(b.toFileString());
                bw.newLine();
            }
            bw.close();

            bw = new BufferedWriter(new FileWriter(MEMBERS_FILE));
            for (Member m : members.values()) {
                bw.write(m.toFileString());
                bw.newLine();
            }
            bw.close();

        } catch (IOException e) {
            System.out.println("Error saving to file.");
        }
    }

    public void addBook() {
        System.out.print("Enter Book Title: ");
        String title = sc.nextLine();
        System.out.print("Enter Author: ");
        String author = sc.nextLine();
        System.out.print("Enter Category: ");
        String category = sc.nextLine();

        int id = 100 + books.size() + 1;

        Book b = new Book(id, title, author, category, false);
        books.put(id, b);
        saveToFile();

        System.out.println("Book added successfully with ID: " + id);
    }

    public void addMember() {
        System.out.print("Enter Member Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        int id = 200 + members.size() + 1;

        Member m = new Member(id, name, email, new ArrayList<>());
        members.put(id, m);
        saveToFile();

        System.out.println("Member added with ID: " + id);
    }

    public void issueBook() {
        System.out.print("Enter Book ID: ");
        int bookId = sc.nextInt();
        System.out.print("Enter Member ID: ");
        int memberId = sc.nextInt();
        sc.nextLine();

        if (!books.containsKey(bookId)) {
            System.out.println("Book not found!");
            return;
        }
        if (!members.containsKey(memberId)) {
            System.out.println("Member not found!");
            return;
        }

        Book b = books.get(bookId);
        if (b.isIssued()) {
            System.out.println("Book already issued!");
            return;
        }

        b.markAsIssued();
        members.get(memberId).addIssuedBook(bookId);
        saveToFile();

        System.out.println("Book issued successfully!");
    }

    public void returnBook() {
        System.out.print("Enter Book ID: ");
        int bookId = sc.nextInt();
        sc.nextLine();

        if (!books.containsKey(bookId)) {
            System.out.println("Book not found!");
            return;
        }

        Book b = books.get(bookId);
        b.markAsReturned();

        for (Member m : members.values()) {
            if (m.getIssuedBooks().contains(bookId)) {
                m.returnIssuedBook(bookId);
                break;
            }
        }

        saveToFile();
        System.out.println("Book returned successfully!");
    }

    public void searchBooks() {
        System.out.print("Search by (title/author/category): ");
        String key = sc.nextLine().toLowerCase();

        for (Book b : books.values()) {
            if (b.getTitle().toLowerCase().contains(key) ||
                b.getAuthor().toLowerCase().contains(key) ||
                b.getCategory().toLowerCase().contains(key)) {
                b.displayBookDetails();
            }
        }
    }

    public void sortBooks() {
        List<Book> list = new ArrayList<>(books.values());

        System.out.println("Sort by: 1.Title  2.Author");
        int choice = sc.nextInt();
        sc.nextLine();

        if (choice == 1) {
            Collections.sort(list);
        } else {
            list.sort(Comparator.comparing(Book::getAuthor));
        }

        for (Book b : list) {
            b.displayBookDetails();
        }
    }

    public void menu() {
        int choice;
        do {
            System.out.println("\n===== City Library Digital Management System =====");
            System.out.println("1. Add Book");
            System.out.println("2. Add Member");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Search Books");
            System.out.println("6. Sort Books");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addBook();
                case 2 -> addMember();
                case 3 -> issueBook();
                case 4 -> returnBook();
                case 5 -> searchBooks();
                case 6 -> sortBooks();
                case 7 -> {
                    saveToFile();
                    System.out.println("Exiting...");
                }
                default -> System.out.println("Invalid choice!");
            }

        } while (choice != 7);
    }
}


public class CityLibrarySystem {
    public static void main(String[] args) {
        LibraryManager lm = new LibraryManager();
        lm.menu();
    }
}
