package com.zjgsu.wzy.campuscourseselectionsystem.model;


import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="courses")
public class Course {
    @Id
    private String id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String title;

    @Embedded
    private Instructor instructor;

    @Embedded
    private ScheduleSlot scheduleSlot;

    @Column(nullable = false)
    private Long capacity;


    @PrePersist
    public void generateId() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public Instructor getInstructor() {
        return instructor;
    }
    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public ScheduleSlot getScheduleSlot() {
        return scheduleSlot;
    }
    public void setScheduleSlot(ScheduleSlot scheduleSlot) {
        this.scheduleSlot = scheduleSlot;
    }

    public Long getCapacity() {
        return capacity;
    }
    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }
}
