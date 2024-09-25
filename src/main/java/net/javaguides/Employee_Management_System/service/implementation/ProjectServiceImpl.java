package net.javaguides.Employee_Management_System.service.implementation;

import lombok.AllArgsConstructor;
import net.javaguides.Employee_Management_System.dto.ProjectDto;
import net.javaguides.Employee_Management_System.entity.Project;
import net.javaguides.Employee_Management_System.exception.ProjectNotFoundException;
import net.javaguides.Employee_Management_System.mapper.ProjectMapper;
import net.javaguides.Employee_Management_System.repository.ProjectRepository;
import net.javaguides.Employee_Management_System.service.MessageService;
import net.javaguides.Employee_Management_System.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private MessageService messageService;

    @Override
    public ProjectDto getProjectById(long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(
                   messageService.getMessage("project.notfound", projectId)
                ));

        return ProjectMapper.mapToProjectDto(project);
    }

    @Override
    public List<ProjectDto> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        return projects.stream().map(project -> ProjectMapper.mapToProjectDto(project))
                .collect(Collectors.toList());
    }

    @Override
    public ProjectDto createProject(ProjectDto projectDto) {
        Project project = ProjectMapper.mapToProject(projectDto);

        Project savedProject = projectRepository.save(project);

        return ProjectMapper.mapToProjectDto(savedProject);
    }

    @Override
    public ProjectDto updateProject(Long pid, ProjectDto projectDto) {
        Project project = projectRepository.findById(pid)
                .orElseThrow(() -> new ProjectNotFoundException(
                        messageService.getMessage("project.notfound", pid)
                ));

        project.setProjectName(projectDto.getProjectName());
        Project updatedProject = projectRepository.save(project);

        return ProjectMapper.mapToProjectDto(projectRepository.save(updatedProject));
    }

    @Override
    public void deleteProject(long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(
                        messageService.getMessage("project.notfound", projectId)
                ));

        projectRepository.deleteById(projectId);
    }
}
