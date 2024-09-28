package net.javaguides.Employee_Management_System.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequestDto {
    private String email;

    @NonNull
    private String password;
}

