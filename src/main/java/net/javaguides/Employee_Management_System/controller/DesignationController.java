package net.javaguides.Employee_Management_System.controller;

import lombok.AllArgsConstructor;
import net.javaguides.Employee_Management_System.dto.DesignationDto;
import net.javaguides.Employee_Management_System.service.DesignationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // this class is capable to handle http request
@RequestMapping("/api/designations") // to define the base url of all the rest APIs
@AllArgsConstructor
public class DesignationController {
    private DesignationService designationService;

    @PostMapping
    public ResponseEntity<DesignationDto> createDesignation(@RequestBody DesignationDto designationDto){
        DesignationDto savedDesignation = designationService.createDesignation(designationDto);
        return new ResponseEntity<>(savedDesignation, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    ResponseEntity<?> getDesignationById(@PathVariable long id){
        DesignationDto designationDto = designationService.getDesignationById(id);
        return ResponseEntity.ok(designationDto);
    }

    @GetMapping
    ResponseEntity<?> getAllDesignation(){
        List<DesignationDto> projects = designationService.getAllDesignations();
        return ResponseEntity.ok(projects);
    }

    @PutMapping("{id}")
    ResponseEntity<?> updateDesignation(@PathVariable long id, @RequestBody DesignationDto updatedDesignationDto){
        DesignationDto designationDto = designationService.updateDesignation(id, updatedDesignationDto);
        return ResponseEntity.ok(designationDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteDesignation(@PathVariable long id){
        designationService.deleteDesignation(id);
        return ResponseEntity.ok("Designation deleted successfully");
    }
}
