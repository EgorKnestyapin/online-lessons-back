package de.aittr.online_lessons.controllers;

import de.aittr.online_lessons.controllers.api.FileApi;
import de.aittr.online_lessons.exception_handling.Response;
import de.aittr.online_lessons.services.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controller that accepts requests related to files {@link FileService}
 *
 * @author aliyasagut
 * @version 1.0.0
 */
@RequiredArgsConstructor
@RestController
public class FilesController implements FileApi {

    /**
     * {@link FileService}
     */
    private final FileService fileService;

    /**
     * Upload new file
     *
     * @param file File
     * @return Response message
     */
    public Response upload(MultipartFile file) {
        return fileService.upload(file);
    }
}
