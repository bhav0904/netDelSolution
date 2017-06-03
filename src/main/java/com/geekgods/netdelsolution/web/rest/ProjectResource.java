package com.geekgods.netdelsolution.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.geekgods.netdelsolution.domain.Project;
import com.geekgods.netdelsolution.domain.ProjectIssue;
import com.geekgods.netdelsolution.domain.User;
import com.geekgods.netdelsolution.domain.UserIssue;
import com.geekgods.netdelsolution.repository.ProjectRepository;
import com.geekgods.netdelsolution.repository.UserRepository;
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

    @PostMapping("/projects")
    @Timed
    public ResponseEntity<String> saveProject(@RequestBody ProjectDTO projectDTO) {
        Project project = new Project();
        ProjectIssue projectIssue = new ProjectIssue();
        projectIssue.setIssue(projectDTO.getIssue());
        projectIssue.setProject(project);
        project.getIssues().add(projectIssue);

        project.setProjectName(projectDTO.getName());
        project.setProjectAddress(projectDTO.getAddress());
        project.setProjectDescription(projectDTO.getDescription());
        this.projectRepository.save(project);
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/projects")
    @Timed
    public ResponseEntity<List<ProjectDTO>> getProjects(@RequestParam String login) {

        User user = this.userRepository.findOneByLogin(login).orElseThrow(() -> new RuntimeException("User not found"));

        List<String> issuesUserIsInterestedIn = user.getIssues().stream().map(UserIssue::getIssue).collect(Collectors.toList());

        List<ProjectDTO> projects = this.projectRepository.findAll().stream().map(project -> new ProjectDTO(project.getProjectName(),
                project.getProjectAddress(), ((ProjectIssue) project.getIssues().toArray()[0]).getIssue(),
                project.getProjectDescription())).filter(projectDTO -> issuesUserIsInterestedIn.contains(projectDTO.getIssue())).collect(Collectors.toList());

        return ResponseEntity.ok(projects);
    }
}
