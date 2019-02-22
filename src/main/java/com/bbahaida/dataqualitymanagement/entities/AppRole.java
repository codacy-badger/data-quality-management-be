package com.bbahaida.dataqualitymanagement.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data @NoArgsConstructor
@Table(name = "roles")
public class AppRole implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;
    @Column
    private String role;


    public AppRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "AppRole{" +
                "id=" + id +
                ", role='" + role + '\'' +
                '}';
    }
}
