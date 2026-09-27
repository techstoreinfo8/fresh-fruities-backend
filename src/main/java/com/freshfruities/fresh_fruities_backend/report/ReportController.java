package com.freshfruities.fresh_fruities_backend.report;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService service;

    public ReportController(ReportService service) {
        this.service = service;
    }

    @GetMapping
    public Map<String, Object> getSummary() {
        return service.getSummary();
    }
}