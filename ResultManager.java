

import java.util.*;


class InvalidMarksException extends Exception {
    public InvalidMarksException(String message) {
        super(message);
    }
}


class Student {
    private int rollNumber;
    private String studentName;
    private int[] marks = new int[3];

    public Student(int rollNumber, String studentName, int[] marks) throws InvalidMarksException {
        this.rollNumber = rollNumber;
        this.studentName = studentName;
        this.marks = marks;
        validateMarks(); 
    }

    
    public void validateMarks() throws InvalidMarksException {
        for (int i = 0; i < marks.length; i++) {
            if (marks[i] < 0 || marks[i] > 100) {
                throw new InvalidMarksException("Invalid marks for subject " + (i + 1) + ": " + marks[i]);
            }
        }
    }

  
    public double calculateAverage() {
        double sum = 0;
        for (int mark : marks) {
            sum += mark;
        }
        return sum / marks.length;
    }

    public void displayResult() {
        System.out.println("Roll Number: " + rollNumber);
        System.out.println("Student Name: " + studentName);
        System.out.print("Marks: ");
        for (int m : marks) {
            System.out.print(m + " ");
        }
        double avg = calculateAverage();
        System.out.println("\nAverage: " + avg);
        String result = (avg >= 40) ? "Pass" : "Fail";
        System.out.println("Result: " + result);
        System.out.println("------------------------------");
    }

  
    public int getRollNumber() {
        return rollNumber;
    }
}


public class ResultManager {

    private static Student[] students = new Student[10]; 
    private static int count = 0; 

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            int choice;
            do {
                System.out.println("===== Student Result Management System =====");
                System.out.println("1. Add Student");
                System.out.println("2. Show Student Details");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");

                
                try {
                    choice = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Error: Please enter a valid numeric choice.\n");
                    continue;
                }

                switch (choice) {
                    case 1:
                        addStudent(sc);
                        break;

                    case 2:
                        showStudentDetails(sc);
                        break;

                    case 3:
                        System.out.println("Exiting program. Thank you!");
                        break;

                    default:
                        System.out.println("Invalid choice! Please try again.");
                }

            } while (true);

        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        } finally {
           
            System.out.println("Program closed successfully.");
            sc.close();
        }
    }

    
    public static void addStudent(Scanner sc) {
        try {
            System.out.print("Enter Roll Number: ");
            int rollNumber = Integer.parseInt(sc.nextLine());

            System.out.print("Enter Student Name: ");
            String studentName = sc.nextLine();

            int[] marks = new int[3];
            for (int i = 0; i < 3; i++) {
                System.out.print("Enter marks for subject " + (i + 1) + ": ");
                marks[i] = Integer.parseInt(sc.nextLine());
            }

           
            Student s = new Student(rollNumber, studentName, marks);
            students[count++] = s;
            System.out.println("Student added successfully. Returning to main menu...\n");

        } catch (InvalidMarksException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Returning to main menu...\n");
        } catch (NumberFormatException e) {
            System.out.println("Error: Please enter valid numeric values for roll number or marks.\n");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error: Student limit reached. Cannot add more.\n");
        }
    }

    
    public static void showStudentDetails(Scanner sc) {
        try {
            System.out.print("Enter Roll Number to search: ");
            int roll = Integer.parseInt(sc.nextLine());

            boolean found = false;
            for (int i = 0; i < count; i++) {
                if (students[i] != null && students[i].getRollNumber() == roll) {
                    students[i].displayResult();
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("No student found with roll number: " + roll);
            } else {
                System.out.println("Search completed.\n");
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: Roll number must be numeric.\n");
        } catch (Exception e) {
            System.out.println("Unexpected error while searching student: " + e.getMessage());
        }
    }
}
