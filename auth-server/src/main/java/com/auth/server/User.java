package com.auth.server;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @ManyToMany(fetch= FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinTable(name="user_role",
            joinColumns=@JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="role_id"))
    private Set<Role> roles;
}

//{
//        "firstName": "priya",
//        "lastName": "priya",
//        "email": "priya@gmail.com",
//        "p": "some p",
//        "roles": [
//        {
//        "name": "ADMIN",
//        "id": 1,
//        "authority": "ADMIN"
//        }
//        ],
//        "id": 3
//  }