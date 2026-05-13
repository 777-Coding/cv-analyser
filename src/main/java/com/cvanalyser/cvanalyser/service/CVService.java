package com.cvanalyser.cvanalyser.service;

import com.cvanalyser.cvanalyser.model.CV;
import com.cvanalyser.cvanalyser.model.User;
import com.cvanalyser.cvanalyser.repository.CVRepository;
import com.cvanalyser.cvanalyser.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import org.apache.pdfbox.Loader;
@Service
@RequiredArgsConstructor
public class CVService {

    private final CVRepository cvRepository;
    private final UserRepository userRepository;

    public CV uploadCV(MultipartFile file) throws IOException {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String extractedText = extractTextFromPDF(file);

        CV cv = new CV();
        cv.setUser(user);
        cv.setFileName(file.getOriginalFilename());
        cv.setExtractedText(extractedText);

        return cvRepository.save(cv);
    }

    public List<CV> getUserCVs() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return cvRepository.findByUserId(user.getId());
    }

    private String extractTextFromPDF(MultipartFile file) throws IOException {
        try (PDDocument document = Loader.loadPDF(file.getBytes())) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }
}