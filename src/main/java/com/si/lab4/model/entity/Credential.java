package com.si.lab4.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Credentials")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Credential {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long credentialId;

    private String password;

    @OneToOne(mappedBy = "credential")
    @JsonManagedReference
    private User user;

    public Credential(String password) {
        this.password = password;
    }
}
