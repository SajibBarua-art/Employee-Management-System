package net.javaguides.Employee_Management_System.service;

import net.javaguides.Employee_Management_System.dto.DesignationDto;
import net.javaguides.Employee_Management_System.entity.Designation;

import java.util.Set;

public interface DesignationService {
    DesignationDto getDesignationById(Long id);

    Set<DesignationDto> getAllDesignations();

    DesignationDto createDesignation(Designation designation);

    DesignationDto updateDesignation(Long pid, DesignationDto designationDto);

    void deleteDesignation(Long designationId);
}
