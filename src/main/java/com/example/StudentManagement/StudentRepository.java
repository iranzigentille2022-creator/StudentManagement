package com.example.StudentManagement;

import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentRepository {

    public void save(Student student) {

        String sql = "INSERT INTO students (full_name, age, email) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            String fullName = student.getFirstName() + " " + student.getLastName();

            statement.setString(1, fullName);
            statement.setInt(2, student.getAge());
            statement.setString(3, student.getEmail());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error saving student", e);
        }
    }

    public List<Student> findAll() {

        List<Student> students = new ArrayList<>();

        String sql = "SELECT id, full_name, age, email FROM students";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Student student = new Student();

                student.setId(resultSet.getInt("id"));

                String fullName = resultSet.getString("full_name");
                String[] names = fullName.split(" ", 2);

                student.setFirstName(names[0]);
                student.setLastName(names.length > 1 ? names[1] : "");

                student.setAge(resultSet.getInt("age"));
                student.setEmail(resultSet.getString("email"));

                students.add(student);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving students", e);
        }

        return students;
    }
}