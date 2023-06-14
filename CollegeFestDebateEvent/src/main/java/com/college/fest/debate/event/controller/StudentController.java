package com.college.fest.debate.event.controller;

import com.college.fest.debate.event.entity.Student;
import java.security.Principal;
import com.college.fest.debate.event.service.StudentService;

import net.bytebuddy.asm.Advice.OffsetMapping.ForOrigin.Renderer.ForReturnTypeName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    
    @GetMapping
    public String hello() {
     return "redirect:/students/list";
    }


    @GetMapping("/students/list")
    public String listStudents(Model model) {
        try {
            List<Student> students = studentService.getAllStudents();
            model.addAttribute("students", students);
            
            // Fetch the logged-in user's role
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userRole = ""; // Initialize the user role as an empty string

            if (authentication != null && authentication.isAuthenticated()) {
                // Assuming the user role is stored in a GrantedAuthority object
                // You may need to customize this based on your actual role implementation
                userRole = authentication.getAuthorities().iterator().next().getAuthority();
            }

            
         // Add the attributes to the model
            model.addAttribute("isUserLoggedIn", authentication != null && authentication.isAuthenticated());
            model.addAttribute("userRole", userRole);
   
            return "list";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Failed to fetch student details");
            return "error";
        }
    }

    @GetMapping("/students/add")
    public String showAddForm(Model model) {
        model.addAttribute("student", new Student());
        return "add";
    }

    @PostMapping("/students/add")
    public String addStudent(@ModelAttribute("student") Student student, Model model) {
        try {
            studentService.saveStudent(student);
            model.addAttribute("successMessage", "Student added successfully");
            return "redirect:/students/list";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Failed to add student");
            return "error";
        }
    }

    @GetMapping("/students/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        try {
            Optional<Student> studentOptional = studentService.getStudentById(id);
            if (studentOptional.isPresent()) {
                Student student = studentOptional.get();
                model.addAttribute("student", student);
                return "edit";
            } else {
                model.addAttribute("errorMessage", "Student not found");
                return "error";
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Failed to fetch student details for editing");
            return "error";
        }
    }

    @PostMapping("/students/edit/{id}")
    public String updateStudent(@PathVariable("id") Long id, @ModelAttribute("student") Student student, Model model) {
        try {
            student.setId(id);
            studentService.saveStudent(student);
            model.addAttribute("successMessage", "Student updated successfully");
            return "redirect:/students/list";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Failed to update student");
            return "error";
        }
    }

    @GetMapping("/students/view/{id}")
    public String viewStudent(@PathVariable("id") Long id, Model model) {
        try {
            Optional<Student> studentOptional = studentService.getStudentById(id);
            if (studentOptional.isPresent()) {
                Student student = studentOptional.get();
                model.addAttribute("student", student);
                return "view";
            } else {
                model.addAttribute("errorMessage", "Student not found");
                return "error";
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Failed to fetch student details for viewing");
            return "error";
        }
    }

    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable("id") Long id, Model model) {
        try {
            studentService.deleteStudent(id);
            model.addAttribute("successMessage", "Student deleted successfully");
            return "redirect:/students/list";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Failed to delete student");
            return "error";
        }
    }


}
