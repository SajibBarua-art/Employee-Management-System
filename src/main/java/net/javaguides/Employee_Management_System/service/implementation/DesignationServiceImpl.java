package net.javaguides.Employee_Management_System.service.implementation;

import lombok.AllArgsConstructor;
import net.javaguides.Employee_Management_System.dto.DesignationDto;
import net.javaguides.Employee_Management_System.entity.Designation;
import net.javaguides.Employee_Management_System.exception.DesignationNotFoundException;
import net.javaguides.Employee_Management_System.mapper.DesignationMapper;
import net.javaguides.Employee_Management_System.repository.DesignationRepository;
import net.javaguides.Employee_Management_System.service.MessageService;
import net.javaguides.Employee_Management_System.service.DesignationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DesignationServiceImpl implements DesignationService {
    @Autowired
    private DesignationRepository designationRepository;

    @Autowired
    private MessageService messageService;

    @Override
    public DesignationDto getDesignationById(long designationId) {
        Designation designation = designationRepository.findById(designationId)
                .orElseThrow(() -> new DesignationNotFoundException(
                   messageService.getMessage("designation.notfound", designationId)
                ));

        return DesignationMapper.mapToDesignationDto(designation);
    }

    @Override
    public List<DesignationDto> getAllDesignations() {
        List<Designation> designations = designationRepository.findAll();
        return designations.stream().map(designation -> DesignationMapper.mapToDesignationDto(designation))
                .collect(Collectors.toList());
    }

    @Override
    public DesignationDto createDesignation(DesignationDto designationDto) {
        Designation designation = DesignationMapper.mapToDesignation(designationDto);

        Designation savedDesignation = designationRepository.save(designation);

        return DesignationMapper.mapToDesignationDto(savedDesignation);
    }

    @Override
    public DesignationDto updateDesignation(Long pid, DesignationDto designationDto) {
        Designation designation = designationRepository.findById(pid)
                .orElseThrow(() -> new DesignationNotFoundException(
                        messageService.getMessage("designation.notfound", pid)
                ));

        designation.setDesignationName(designationDto.getDesignationName());
        Designation updatedDesignation = designationRepository.save(designation);

        return DesignationMapper.mapToDesignationDto(designationRepository.save(updatedDesignation));
    }

    @Override
    public void deleteDesignation(long designationId) {
        Designation designation = designationRepository.findById(designationId)
                .orElseThrow(() -> new DesignationNotFoundException(
                        messageService.getMessage("designation.notfound", designationId)
                ));

        designationRepository.deleteById(designationId);
    }
}
