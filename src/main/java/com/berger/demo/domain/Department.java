package com.berger.demo.domain;

import javax.persistence.*;
import java.util.Optional;

@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    String information;

    @ManyToOne(cascade = CascadeType.ALL)
    Users owner;

    @Column
    Long parentId;

    public Department() {
    }

    public Department(String information, Users owner, Long parentId) {
        this.information = information;
        this.owner = owner;
        this.parentId = parentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public Users getOwner() {
        return owner;
    }

    public void setOwner(Users owner) {
        this.owner = owner;
    }

    public Optional<Long> getParentId() {
        return Optional.ofNullable(parentId);
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
