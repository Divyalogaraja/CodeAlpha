package task1;
import java.util.ArrayList;
import java.util.Scanner;

class Student {
    String name;
    double grade;

    Student(String name, double grade) {
        this.name = name;
        this.grade = grade;
    }
}

public class Task1 {
    public static void main(String[] args) {
        ArrayList<Student> students = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of students: ");
        int n = scanner.nextInt();
        scanner.nextLine(); // consume newline

        for (int i = 0; i < n; i++) {
            System.out.print("Enter name of student " + (i + 1) + ": ");
            String name = scanner.nextLine();

            System.out.print("Enter grade for " + name + ": ");
            double grade = scanner.nextDouble();
            scanner.nextLine(); // consume newline

            students.add(new Student(name, grade));
        }

        double total = 0, highest = Double.MIN_VALUE, lowest = Double.MAX_VALUE;
        for (Student s : students) {
            total += s.grade;
            if (s.grade > highest) highest = s.grade;
            if (s.grade < lowest) lowest = s.grade;
        }

        double average = total / students.size();

        System.out.println("\n=== Student Grade Summary ===");
        for (Student s : students) {
            System.out.println("Name: " + s.name + " | Grade: " + s.grade);
        }

        System.out.printf("\nAverage Grade: %.2f\n", average);
        System.out.printf("Highest Grade: %.2f\n", highest);
        System.out.printf("Lowest Grade: %.2f\n", lowest);

        scanner.close();
    }
}
