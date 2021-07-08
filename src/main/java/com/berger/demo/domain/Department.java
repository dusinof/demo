package com.berger.demo.domain;

import javax.persistence.*;
import java.util.Objects;
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


    public String getInformation() {
        return information;
    }


    public Users getOwner() {
        return owner;
    }


    public Optional<Long> getParentId() {
        return Optional.ofNullable(parentId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department)) return false;
        Department that = (Department) o;
        return information.equals(that.information) && owner.equals(that.owner) && parentId.equals(that.parentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(information, owner, parentId);
    }
}
