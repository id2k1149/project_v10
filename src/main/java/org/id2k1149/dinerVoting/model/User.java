package org.id2k1149.dinerVoting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User extends AbstractBaseEntity {
    @Id
    private Long id;
    @Size(min = 5, max = 32)
    @NotBlank
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 6, max = 64)
    @NotBlank
    private String password;
    @Transient
    @JsonIgnore
    private String passwordConfirm;
    @Enumerated(EnumType.STRING)
    private Role role;
}