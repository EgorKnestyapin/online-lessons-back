package de.aittr.online_lessons.controllers;

import de.aittr.online_lessons.controllers.api.FileApi;
import de.aittr.online_lessons.exception_handling.Response;
import de.aittr.online_lessons.services.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class FilesController implements FileApi {

    private final FileService fileService;

    public Response upload(MultipartFile file) {
        return fileService.upload(file);
    }
}
