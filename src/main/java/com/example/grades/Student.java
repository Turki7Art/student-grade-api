package com.example.grades;

public class Student {
    private Long id;
    private String name;
    private String course;
    private double grade;

    public Student() {}
    public Student (Long id, String name, String course, double grade) {
        this.id = id;
        this.name = name;
        this.course = course;
        this.grade = grade;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName (String name) { this.name = name; }
    public String getCourse() { return course; }
    public void setCourse (String course) { this.course = course; }
    public double getGrade() { return grade; }
    public void setGrade (double grade) { this.grade = grade; }
}