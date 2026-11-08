package com.example.grades;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

@GetMapping("/health")
public Map<String, String> health() {
    logger.info("Health check called");
    return Map.of("status", "UP");
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