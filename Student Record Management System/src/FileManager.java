import java.io.*;
import java.util.*;

public class FileManager {

    static String TEXT_FILE   = "students.txt";
    static String BINARY_FILE = "students.dat";
    static String SERIAL_FILE = "students.ser";
    static String BACKUP_FILE = "backup.txt";

    public static void setupFiles() {
        try {
            File f = new File(TEXT_FILE);
            if (!f.exists()) {
                f.createNewFile();
                System.out.println("Created students.txt");
            }
        } catch (Exception e) {
            System.out.println("Setup error: " + e.getMessage());
        }
    }

    public static void showFileInfo() {
        String[] files = {TEXT_FILE, BINARY_FILE, SERIAL_FILE};
        for (String name : files) {
            File f = new File(name);
            System.out.println("--- " + name + " ---");
            if (f.exists()) {
                System.out.println("Path:          " + f.getAbsolutePath());
                System.out.println("Size:          " + f.length() + " bytes");
                System.out.println("Last Modified: " + new Date(f.lastModified()));
            } else {
                System.out.println("File does not exist yet.");
            }
        }
    }

    public static void saveToText(ArrayList<Student> students) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(TEXT_FILE));
            for (Student s : students) {
                pw.println(s.toString());
            }
            pw.close();
            System.out.println("Saved to " + TEXT_FILE);
        } catch (Exception e) {
            System.out.println("Error saving text file: " + e.getMessage());
        }
    }

    public static ArrayList<Student> loadFromText() {
        ArrayList<Student> list = new ArrayList<>();
        try {
            File f = new File(TEXT_FILE);
            if (!f.exists()) return list;

            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                int    id   = Integer.parseInt(parts[0]);
                String name = parts[1];
                String dept = parts[2];
                double gpa  = Double.parseDouble(parts[3]);
                list.add(new Student(id, name, dept, gpa));
            }
            sc.close();
        } catch (Exception e) {
            System.out.println("Error loading text file: " + e.getMessage());
        }
        return list;
    }

    public static void saveToBinary(ArrayList<Student> students) {
        try {
            DataOutputStream out = new DataOutputStream(
                    new FileOutputStream(BINARY_FILE)
            );
            out.writeInt(students.size());
            for (Student s : students) {
                out.writeInt(s.getStudentId());
                out.writeUTF(s.getName());
                out.writeUTF(s.getDepartment());
                out.writeDouble(s.getGpa());
            }
            out.close();
            System.out.println("Saved to " + BINARY_FILE);
        } catch (Exception e) {
            System.out.println("Error saving binary file: " + e.getMessage());
        }
    }

    public static ArrayList<Student> loadFromBinary() {
        ArrayList<Student> list = new ArrayList<>();
        try {
            File f = new File(BINARY_FILE);
            if (!f.exists()) return list;

            DataInputStream in = new DataInputStream(
                    new FileInputStream(BINARY_FILE)
            );
            int count = in.readInt();
            for (int i = 0; i < count; i++) {
                int    id   = in.readInt();
                String name = in.readUTF();
                String dept = in.readUTF();
                double gpa  = in.readDouble();
                list.add(new Student(id, name, dept, gpa));
            }
            in.close();
        } catch (Exception e) {
            System.out.println("Error loading binary file: " + e.getMessage());
        }
        return list;
    }

    public static void saveToSerial(ArrayList<Student> students) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream(SERIAL_FILE)
            );
            out.writeObject(students);
            out.close();
            System.out.println("Saved to " + SERIAL_FILE);
        } catch (Exception e) {
            System.out.println("Error saving serialized file: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<Student> loadFromSerial() {
        ArrayList<Student> list = new ArrayList<>();
        try {
            File f = new File(SERIAL_FILE);
            if (!f.exists()) return list;

            ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream(SERIAL_FILE)
            );
            list = (ArrayList<Student>) in.readObject();
            in.close();
        } catch (Exception e) {
            System.out.println("Error loading serialized file: " + e.getMessage());
        }
        return list;
    }

    public static void createBackup() {
        try {
            File source = new File(TEXT_FILE);
            if (!source.exists()) {
                System.out.println("No file to backup!");
                return;
            }
            BufferedReader reader = new BufferedReader(new FileReader(TEXT_FILE));
            BufferedWriter writer = new BufferedWriter(new FileWriter(BACKUP_FILE));
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
            reader.close();
            writer.close();
            System.out.println("Backup created: " + BACKUP_FILE);
        } catch (Exception e) {
            System.out.println("Backup error: " + e.getMessage());
        }
    }

    public static void generateReport(ArrayList<Student> students) {
        if (students.size() == 0) {
            System.out.println("No students to report!");
            return;
        }
        double highest = students.get(0).getGpa();
        double lowest  = students.get(0).getGpa();
        double total   = 0;

        for (Student s : students) {
            double gpa = s.getGpa();
            total += gpa;
            if (gpa > highest) highest = gpa;
            if (gpa < lowest)  lowest  = gpa;
        }

        System.out.println("======= REPORT =======");
        System.out.println("Total Students: " + students.size());
        System.out.println("Highest GPA:    " + highest);
        System.out.println("Lowest GPA:     " + lowest);
        System.out.println("Average GPA:    " + (total / students.size()));
        System.out.println("======================");
    }
}