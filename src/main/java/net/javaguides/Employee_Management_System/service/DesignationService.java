package net.javaguides.Employee_Management_System.service;

import net.javaguides.Employee_Management_System.dto.DesignationDto;

import java.util.List;

public interface DesignationService {
    DesignationDto getDesignationById(long id);

    List<DesignationDto> getAllDesignations();

    DesignationDto createDesignation(DesignationDto designationDto);

    DesignationDto updateDesignation(Long pid, DesignationDto designationDto);

    void deleteDesignation(long designationId);
}
