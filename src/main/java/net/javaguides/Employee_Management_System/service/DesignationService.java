package net.javaguides.Employee_Management_System.service;

import net.javaguides.Employee_Management_System.dto.DesignationDto;
import net.javaguides.Employee_Management_System.entity.Designation;

import java.util.List;

public interface DesignationService {
    DesignationDto getDesignationById(Long id);

    List<DesignationDto> getAllDesignations();

    DesignationDto createDesignation(Designation designation);

    DesignationDto updateDesignation(Long pid, DesignationDto designationDto);

    void deleteDesignation(Long designationId);
}
