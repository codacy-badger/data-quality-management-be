package com.bbahaida.dataqualitymanagement.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data @NoArgsConstructor @ToString @AllArgsConstructor
@Table(name="db_configurations")
//@EntityListeners(AuditingEntityListener.class)
public class DBConfiguration implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @Column
    private int type;
    @Column
    private String host;
    @Column
    private int port;
    @Column
    private String databaseName;
    @Column
    private String username;
    @Column
    private String password;

    @ManyToOne
    @JoinColumn(name="MANAGER_ID")
    @JsonIgnore
    private AppUser appUser;
}
