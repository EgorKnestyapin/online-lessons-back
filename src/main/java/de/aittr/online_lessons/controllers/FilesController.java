package de.aittr.online_lessons.controllers;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import de.aittr.online_lessons.exception_handling.Response;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class FilesController {

    private final AmazonS3 amazonS3;

    @SneakyThrows
    @PostMapping("/api/files")
    public Response upload(@RequestParam("file") MultipartFile file) {
        String originalFileName = file.getOriginalFilename();

        String extension;
        if (originalFileName != null) {
            extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        } else {
            throw new IllegalArgumentException("Null original file name.");
        }

        String uuid = UUID.randomUUID().toString();
        String newFileName = uuid + "." + extension;

        InputStream inputStream = file.getInputStream();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());

        PutObjectRequest request =
                new PutObjectRequest("online-lessons-files", newFileName, inputStream, metadata);

        amazonS3.putObject(request);

        return Response.builder()
                .message(newFileName)
                .build();
    }
}
