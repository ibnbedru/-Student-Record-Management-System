import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        FileManager.setupFiles();

        ArrayList<Student> students = FileManager.loadFromText();

        boolean running = true;

        while (running) {
            System.out.println("\n===== Student Record System =====");
            System.out.println("1. Add student");
            System.out.println("2. Search student by ID");
            System.out.println("3. Update student GPA");
            System.out.println("4. Delete student");
            System.out.println("5. Display all students");
            System.out.println("6. Generate report");
            System.out.println("7. Show file properties");
            System.out.println("8. Create backup");
            System.out.println("9. Exit");
            System.out.print("Enter choice: ");

            try {
                int choice = sc.nextInt();
                sc.nextLine();

                if (choice == 1) {
                    System.out.print("Student ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Name: ");
                    String name = sc.nextLine();
                    System.out.print("Department: ");
                    String dept = sc.nextLine();
                    System.out.print("GPA (0.0 - 4.0): ");
                    double gpa = sc.nextDouble();
                    sc.nextLine();
                    if (gpa < 0 || gpa > 4.0) {
                        throw new Exception("GPA must be between 0.0 and 4.0!");
                    }
                    students.add(new Student(id, name, dept, gpa));
                    FileManager.saveToText(students);
                    FileManager.saveToBinary(students);
                    FileManager.saveToSerial(students);
                    System.out.println("Student added successfully!");

                } else if (choice == 2) {
                    System.out.print("Enter ID to search: ");
                    int searchId = sc.nextInt();
                    sc.nextLine();
                    boolean found = false;
                    for (Student s : students) {
                        if (s.getStudentId() == searchId) {
                            System.out.println("ID:         " + s.getStudentId());
                            System.out.println("Name:       " + s.getName());
                            System.out.println("Department: " + s.getDepartment());
                            System.out.println("GPA:        " + s.getGpa());
                            found = true;
                            break;
                        }
                    }
                    if (!found) System.out.println("Student not found!");

                } else if (choice == 3) {
                    System.out.print("Enter ID to update: ");
                    int updateId = sc.nextInt();
                    sc.nextLine();
                    boolean found = false;
                    for (Student s : students) {
                        if (s.getStudentId() == updateId) {
                            System.out.print("New GPA: ");
                            double newGpa = sc.nextDouble();
                            sc.nextLine();
                            s.setGpa(newGpa);
                            FileManager.saveToText(students);
                            FileManager.saveToBinary(students);
                            FileManager.saveToSerial(students);
                            System.out.println("Student updated!");
                            found = true;
                            break;
                        }
                    }
                    if (!found) System.out.println("Student not found!");

                } else if (choice == 4) {
                    System.out.print("Enter ID to delete: ");
                    int deleteId = sc.nextInt();
                    sc.nextLine();
                    boolean found = false;
                    for (int i = 0; i < students.size(); i++) {
                        if (students.get(i).getStudentId() == deleteId) {
                            students.remove(i);
                            FileManager.saveToText(students);
                            FileManager.saveToBinary(students);
                            FileManager.saveToSerial(students);
                            System.out.println("Student deleted!");
                            found = true;
                            break;
                        }
                    }
                    if (!found) System.out.println("Student not found!");

                } else if (choice == 5) {
                    if (students.size() == 0) {
                        System.out.println("No students found!");
                    } else {
                        System.out.println("--- All Students ---");
                        for (Student s : students) {
                            System.out.println(
                                    "ID: " + s.getStudentId() +
                                            " | Name: " + s.getName() +
                                            " | Dept: " + s.getDepartment() +
                                            " | GPA: " + s.getGpa()
                            );
                        }
                    }

                } else if (choice == 6) {
                    FileManager.generateReport(students);

                } else if (choice == 7) {
                    FileManager.showFileInfo();

                } else if (choice == 8) {
                    FileManager.createBackup();

                } else if (choice == 9) {
                    running = false;
                    System.out.println("Goodbye!");

                } else {
                    System.out.println("Invalid choice! Enter 1 to 9.");
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                sc.nextLine();
            }
        }

        sc.close();
    }
}
