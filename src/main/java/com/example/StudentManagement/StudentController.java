

package com.example.StudentManagement;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Display registration form
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("student", new Student());
        return "register";
    }

    // Save registered student
    @PostMapping("/register")
    public String registerStudent(@ModelAttribute("student") Student student, Model model) {

        studentService.saveStudent(student);

        model.addAttribute("successMessage",
                "Student registered successfully!");

        model.addAttribute("student", new Student());

        return "register";
    }

    // Display all students
    @GetMapping("/all")
    public String showAllStudents(Model model) {

        model.addAttribute("students",
                studentService.getAllStudents());

        return "Students";
    }
}
