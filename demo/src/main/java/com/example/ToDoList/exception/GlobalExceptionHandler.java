package com.example.ToDoList.exception;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    public String handleTaskNotFound(TaskNotFoundException ex , Model model) {
        model.addAttribute("errorMessage",ex.getMessage());
        return "error";
    }


    @ExceptionHandler(Exception.class)
    public String handleGenericExceptions(Exception ex , Model model) {
        model.addAttribute("errorMessage" , ex.getMessage());
        return "error";
    }

}
