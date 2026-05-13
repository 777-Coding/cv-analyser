package com.cvanalyser.cvanalyser.controller;

import com.cvanalyser.cvanalyser.model.CV;
import com.cvanalyser.cvanalyser.service.CVService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/cv")
@RequiredArgsConstructor
public class CVController {

    private final CVService cvService;

    @PostMapping("/upload")
    public ResponseEntity<CV> uploadCV(@RequestParam("file") MultipartFile file)
            throws IOException {
        CV cv = cvService.uploadCV(file);
        return ResponseEntity.ok(cv);
    }

    @GetMapping("/list")
    public ResponseEntity<List<CV>> listCVs() {
        return ResponseEntity.ok(cvService.getUserCVs());
    }
}