package com.example.grades;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    private final List<Student> students = new ArrayList<>(List.of(
        new Student(1L, "Ahmed", "SE4111", 95.0),
        new Student(2L, "Sara", "SE4111", 88.5)
    ));

    @GetMapping("/students")
    public List<Student> getAll() {
        return students;
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getById(@PathVariable Long id) {
        return students.stream()
            .filter(s -> s.getId() != null && s.getId().equals(id))
            .findFirst()
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/students")
    public ResponseEntity<Student> add(@RequestBody Student s) {
        if (s == null) {
            return ResponseEntity.badRequest().build();
        }

        long nextId = students.stream()
            .mapToLong(student -> student.getId() != null ? student.getId() : 0L)
            .max()
            .orElse(0L) + 1L;

        s.setId(nextId);
        students.add(s);
        logger.info("Added student: id={}, name={}", s.getId(), s.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(s);
    }
}