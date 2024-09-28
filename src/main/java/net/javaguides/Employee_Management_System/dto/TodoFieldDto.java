package net.javaguides.Employee_Management_System.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public class TodoFieldDto {
    private String title;
    private String description;
    private Integer priority;
}

