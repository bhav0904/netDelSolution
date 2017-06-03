package com.geekgods.netdelsolution.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.geekgods.netdelsolution.domain.Issue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class IssueResource {
    @GetMapping("/issues")
    @Timed
    public ResponseEntity<List<String>> getAllIssues() {
        return new ResponseEntity<>(Arrays.asList(Issue.values()).stream().map(issue -> issue.getDisplayName()).collect(Collectors.toList()),
                HttpStatus.OK);
    }
}
