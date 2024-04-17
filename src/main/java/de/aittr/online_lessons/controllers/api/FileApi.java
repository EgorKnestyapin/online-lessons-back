package de.aittr.online_lessons.controllers.api;

import de.aittr.online_lessons.controllers.FilesController;
import de.aittr.online_lessons.exception_handling.Response;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * API for file controller {@link FilesController}
 *
 * @author EgorKnestyapin
 * @version 1.0.0
 */
@Tags(value = @Tag(name = "files"))
public interface FileApi {

    @PostMapping(value = "/api/files", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    Response upload(@Parameter(content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE))
                    @RequestParam("file") MultipartFile file);
}
