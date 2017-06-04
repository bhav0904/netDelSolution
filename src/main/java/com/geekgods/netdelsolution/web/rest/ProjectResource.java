package com.geekgods.netdelsolution.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.geekgods.netdelsolution.domain.Project;
import com.geekgods.netdelsolution.domain.ProjectIssue;
import com.geekgods.netdelsolution.domain.User;
import com.geekgods.netdelsolution.domain.UserIssue;
import com.geekgods.netdelsolution.repository.ProjectRepository;
import com.geekgods.netdelsolution.repository.UserRepository;
import com.geekgods.netdelsolution.service.BotService;
import com.geekgods.netdelsolution.service.ProjectService;
import com.geekgods.netdelsolution.service.dto.ProjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@Transactional
public class ProjectResource {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectService projectService;

    @PostMapping("/projects")
    @Timed
    public ResponseEntity<String> saveProject(@RequestBody ProjectDTO projectDTO) {
        //TODO move this business logic to the service layer
        Project project = new Project();
        ProjectIssue projectIssue = new ProjectIssue();
        projectIssue.setIssue(projectDTO.getIssue());
        projectIssue.setProject(project);
        project.getIssues().add(projectIssue);
        //TODO add security to createdBy
        project.setCreatedBy(projectDTO.getCreatedBy());

        project.setProjectName(projectDTO.getName());
        project.setProjectAddress(projectDTO.getAddress());
        project.setProjectDescription(projectDTO.getDescription());
        this.projectRepository.save(project);

        this.projectService.sendNotificationsToVolunteers(project);


        return ResponseEntity.ok("Success");
    }

    @GetMapping("/projects")
    @Timed
    public ResponseEntity<List<ProjectDTO>> getProjects(@RequestParam(required = false) String login, @RequestParam(required = false) String createdBy) {

        if (login != null) {
            User user = this.userRepository.findOneByLogin(login).orElseThrow(() -> new RuntimeException("User not found"));

            List<String> issuesUserIsInterestedIn = user.getIssues().stream().map(UserIssue::getIssue).collect(Collectors.toList());

            List<ProjectDTO> projects = this.projectRepository.findAll().stream().map(project -> new ProjectDTO(project.getProjectName(),
                    project.getProjectAddress(), ((ProjectIssue) project.getIssues().toArray()[0]).getIssue(),
                    project.getProjectDescription(), project.getCreatedBy()))
                    .filter(projectDTO -> issuesUserIsInterestedIn.contains(projectDTO.getIssue())).collect(Collectors.toList());

            return ResponseEntity.ok(projects);
        }

        if (createdBy != null) {

            return ResponseEntity.ok(this.projectRepository.findAllByCreatedBy(createdBy).stream().map(project -> new ProjectDTO(project.getProjectName(), project.getProjectAddress(),
                    ((ProjectIssue) project.getIssues().toArray()[0]).getIssue(),
                    project.getProjectDescription(), project.getCreatedBy())).collect(Collectors.toList()));
        }

        return ResponseEntity.ok(null);

    }

    @GetMapping("/location")
    @Timed
    public ResponseEntity<String> getLocation() {
        return ResponseEntity.ok("{\"location\":\"" + BotService.getLocation() + "\"}");
    }
}
