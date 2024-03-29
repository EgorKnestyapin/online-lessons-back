package de.aittr.online_lessons.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import de.aittr.online_lessons.exception_handling.Response;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final AmazonS3 amazonS3;

    @Transactional
    @SneakyThrows
    public Response upload(MultipartFile file) {

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
                new PutObjectRequest("online-lessons-files", newFileName, inputStream, metadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead);

        amazonS3.putObject(request);

        String link = amazonS3.getUrl("online-lessons-files", newFileName).toString();

        return Response.builder()
                .message(link)
                .build();
    }
}

