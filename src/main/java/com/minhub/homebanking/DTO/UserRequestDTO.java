package com.minhub.homebanking.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Getter
@Setter
public class UserRequestDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
