package com.team992.pidl.Model.Entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    private String id;

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private int age;

    private Boolean isAdult;

    @DBRef
    private ContactEntity contact;

    private String image; // Now it is a link to image. TODO create image variable
}