Student Record Management System

A console-based Java application that manages student records using File I/O and Streams. Built as a home test project to demonstrate Java file handling concepts.


System Design

The project is structured around 3 Java classes:

Student.java is the data model. It holds the four fields of a student — studentId, name, department, and gpa — all private with public getters and setters. It implements Serializable so it can be saved directly as a Java object to a file.

FileManager.java handles all file operations. It contains static methods for saving and loading students using three different storage formats (text, binary, serialization), creating backups using buffered streams, generating reports, and displaying file properties using the File class.

Main.java is the entry point. It runs a while loop that shows a menu, reads the user's choice with Scanner, and calls the right FileManager method. All input is wrapped in try-catch for safe error handling.


How to Run

Requirements: Java JDK 8 or higher, IntelliJ IDEA or any Java IDE

Steps in IntelliJ IDEA:


Open IntelliJ IDEA and create a new Java project
Create three Java class files: Student, FileManager, Main
Paste the code into each file
Open Main.java and click the green play button next to the main method
The program starts in the terminal and shows the menu



How to Use

When the program runs you will see this menu:

===== Student Record System =====
1. Add student
2. Search student by ID
3. Update student GPA
4. Delete student
5. Display all students
6. Generate report
7. Show file properties
8. Create backup
9. Exit
Enter choice:

Option 1 — Add student: Enter the student ID, name, department, and GPA. The student is added to memory and immediately saved to all three file formats.

Option 2 — Search by ID: Enter a student ID. The program loops through the list and prints that student's full details if found.

Option 3 — Update GPA: Enter the student ID then the new GPA. The setter method updates the student object and all three files are saved again.

Option 4 — Delete student: Enter the student ID. The student is removed from the list and all three files are updated.

Option 5 — Display all students: Prints every student currently in the system with ID, name, department, and GPA.

Option 6 — Generate report: Calculates and prints total number of students, highest GPA, lowest GPA, and average GPA.

Option 7 — Show file properties: Uses the File class to display the name, full path, size in bytes, and last modified date for all three data files.

Option 8 — Create backup: Uses BufferedReader and BufferedWriter to copy students.txt line by line into backup.txt.

Option 9 — Exit: Stops the loop and closes the program.


Data Files

The program automatically creates and manages these files:

FileFormatPurposestudents.txtPlain text, comma-separatedPrimary readable storagestudents.datBinary (DataOutputStream)Binary format storagestudents.serSerialized Java objectObject serialization storagebackup.txtPlain text copyBackup of text file

A sample entry in students.txt looks like: 1,Yusuf,Computer Science,3.8


File I/O Concepts Used

Text File I/O (Scanner + PrintWriter): The program uses PrintWriter to write each student as a comma-separated line to students.txt. On startup it uses Scanner pointed at the file to read each line back, splits it by comma, and reconstructs Student objects.

Binary File I/O (DataOutputStream + DataInputStream): The program writes each student's fields as raw bytes to students.dat using DataOutputStream. It writes the count of students first, then for each student writes an int, two UTF strings, and a double. DataInputStream reads them back in the exact same order.

Object Serialization (ObjectOutputStream + ObjectInputStream): Because Student implements Serializable, the entire ArrayList of students can be saved to students.ser in one line using ObjectOutputStream.writeObject(). It is loaded back with ObjectInputStream.readObject() and cast back to ArrayList.

Buffered Streams (BufferedReader + BufferedWriter): The backup feature uses BufferedReader to read students.txt line by line efficiently, and BufferedWriter to write each line to backup.txt. Buffering loads data in chunks instead of one byte at a time, making it faster.

File Class: Used in setupFiles() to check if students.txt exists and create it automatically if not. Used in showFileInfo() to display file name, absolute path, size in bytes, and last modified date for all three data files.

Exception Handling: All file operations are wrapped in try-catch blocks. The main menu loop also uses try-catch so if the user types wrong input the program prints an error message and continues instead of crashing.


Class Details

Student.java

Implements Serializable so it can be saved as an object. Has four private fields. Constructor sets all fields. Getters allow reading fields from outside. Setters allow updating fields (used by Update option). The toString() method returns a comma-separated line like "1,Yusuf,CS,3.8" used when writing to the text file.

FileManager.java

All methods are static so Main.java can call them without creating a FileManager object. Contains ten methods: setupFiles, showFileInfo, saveToText, loadFromText, saveToBinary, loadFromBinary, saveToSerial, loadFromSerial, createBackup, generateReport.

Main.java

Creates one Scanner object for all input. Calls FileManager.setupFiles() once on startup. Calls FileManager.loadFromText() to fill the ArrayList with any previously saved students. Runs a while loop until the user picks Exit. All menu logic is inside a single try-catch block.

