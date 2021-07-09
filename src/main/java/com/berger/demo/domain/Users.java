package com.berger.demo.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    String name;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Department> departments = new ArrayList<>();

    public Users() {
    }

    public Long getId() {
        return id;
    }

    public Users(String name) {
        this.name = name;
    }

    public Users(String name, List<Department> departments) {
        this.name = name;
        this.departments = departments;
    }

    public String getName() {
        return name;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Users)) return false;
        Users users = (Users) o;
        return name.equals(users.name) && departments.equals(users.departments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, departments);
    }
}
