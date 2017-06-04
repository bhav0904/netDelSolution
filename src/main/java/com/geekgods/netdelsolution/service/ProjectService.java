package com.geekgods.netdelsolution.service;

import com.geekgods.netdelsolution.domain.Project;
import com.geekgods.netdelsolution.domain.ProjectIssue;
import com.geekgods.netdelsolution.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private MailService mailService;

    @Autowired
    private UserRepository userRepository;

    public void sendNotificationsToVolunteers(Project project) {
        List<String> issues =
                project.getIssues().stream().map(ProjectIssue::getIssue).collect(Collectors.toList());

        //TODO optimize to filter at database, format email
        userRepository.findAll().stream()
                .filter(user -> user.getIssues().stream().anyMatch(userIssue -> issues.contains(userIssue.getIssue())))
                .forEach(user -> mailService.sendEmail(user.getEmail(), "New Community Project of your interest was added",
                        " Project: " + project.getProjectName() + "\n Description: " + project.getProjectDescription() +
                        "\n Where: " + project.getProjectAddress() + "\n Issues: " + issues, false, false));
    }
}
