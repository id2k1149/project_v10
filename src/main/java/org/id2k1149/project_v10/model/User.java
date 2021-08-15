package org.id2k1149.project_v10.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    private String username;
    private String password;
    @Transient
    private String passwordConfirm;
    @Enumerated(EnumType.STRING)
    private Role role;
}