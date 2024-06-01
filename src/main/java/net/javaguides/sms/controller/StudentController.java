package net.javaguides.sms.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.javaguides.sms.dto.StudentDto;
import net.javaguides.sms.entity.Student;
import net.javaguides.sms.service.StudentService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@AllArgsConstructor
public class StudentController {

    private StudentService studentService;

    // Handler method to handle list students request
    @GetMapping("/students")
    public ModelAndView getAllStudents(){
        List<StudentDto> students = studentService.getAllStudents();
        ModelAndView modelAndView = new ModelAndView("students");
        modelAndView.addObject("students", students);
        return modelAndView;
    }

    // Handler method to handle new student request
    @GetMapping("/students/new")
    public ModelAndView newStudent(){
        // Student model object to store student form data
        ModelAndView modelAndView = new ModelAndView("create_student");
        StudentDto studentDto = new StudentDto();
        modelAndView.addObject("student", studentDto);
        return modelAndView;
    }

    // Handler method to handle save student form submit request
    @PostMapping("/students")
    public ModelAndView saveStudent(@Valid @ModelAttribute("student") StudentDto studentDto,
                                    BindingResult result,
                                    ModelAndView modelAndView){
        if (result.hasErrors()){
            modelAndView.addObject("student", studentDto);
            modelAndView.setViewName("create_student");
            return modelAndView;
        }
        studentService.createStudent(studentDto);
        modelAndView.setViewName("redirect:/students");
        return modelAndView;
    }

    // Handler method to handle edit student request
    @GetMapping("/students/{studentId}/edit")
    public ModelAndView editStudent(@PathVariable("studentId") Long studentId,
                                    ModelAndView modelAndView){
        StudentDto studentDto = studentService.getStudentById(studentId);
        modelAndView.addObject("student", studentDto).setViewName("edit_student");
        return modelAndView;
    }

    // Handler method to handle edit student form submit request
    @PostMapping("/students/{studentId}")
    public ModelAndView updateStudent(@Valid @ModelAttribute("student") StudentDto studentDto,
                                      BindingResult result,
                                      ModelAndView modelAndView,
                                      @PathVariable("studentId") Long studentId){
        if (result.hasErrors()){
            modelAndView.addObject("student", studentDto);
            modelAndView.setViewName("edit_student");
            return modelAndView;
        }
        studentDto.setId(studentId);
        studentService.updateStudent(studentDto);
        modelAndView.setViewName("redirect:/students");
        return modelAndView;
    }

    // Handler method to handle delete student request
    @GetMapping("/students/{studentId}/delete")
    public ModelAndView deleteStudent(@PathVariable("studentId") Long studentId,
                                    ModelAndView modelAndView){
        studentService.deleteStudent(studentId);
        modelAndView.setViewName("redirect:/students");
        return modelAndView;
    }

    // Handler method to handle view student request
    @GetMapping("/students/{studentId}/view")
    public ModelAndView viewStudent(@PathVariable("studentId") Long studentId,
                                      ModelAndView modelAndView){
        StudentDto studentDto = studentService.getStudentById(studentId);
        modelAndView.addObject("student", studentDto).setViewName("view_student");
        return modelAndView;
    }
}
