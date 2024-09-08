package com.avasthi.varahamihir.identityserver.endpoints;

import com.avasthi.varahamihir.common.errors.ExceptionMessage;
import com.avasthi.varahamihir.common.exceptions.EntityNotFoundException;
import com.avasthi.varahamihir.identityserver.entities.Content;
import com.avasthi.varahamihir.identityserver.services.ContentService;
import com.avasthi.varahamihir.identityserver.utils.Paths;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(Paths.V1.Content.fullPath)
@RequiredArgsConstructor
public class ContentEndpoint {
    private final ContentService contentService;
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Optional<Content> save(@RequestParam("file") MultipartFile file) {
        return contentService.save(file);
    }

    @RequestMapping(value = Paths.V1.Content.multiple, method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<Content> save(@RequestParam("files") MultipartFile[] files) {
        return contentService.save(files);
    }

    @RequestMapping(value = Paths.V1.Content.contentPath, method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> retrieve(@PathVariable(Paths.V1.Content.contentId) UUID contentId,
                                                        @PathVariable(Paths.V1.Content.filename) String filename) {
        Content content = contentService
                .findById(contentId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ExceptionMessage.ENTITY_NOT_FOUND_ERROR.getReason(), "Content", contentId.toString()),
                        String.format(ExceptionMessage.ENTITY_NOT_FOUND_ERROR.getError(), "Content", contentId.toString())));
        try {

            return ResponseEntity.ok()
                    .contentType(MediaType.valueOf(content.getMimetype()))
                    .contentLength(content.getSize())
                    .body(new InputStreamResource(contentService.retrieve(content)));
        }
        catch(Exception e) {

        }
        throw new EntityNotFoundException(String.format(ExceptionMessage.ENTITY_NOT_FOUND_ERROR.getReason(), "Content", contentId.toString()),
                String.format(ExceptionMessage.ENTITY_NOT_FOUND_ERROR.getError(), "Content", contentId.toString()));
    }
    @RequestMapping(value = "{" + Paths.V1.Content.contentId + "}", method = RequestMethod.DELETE)
    public Optional<Content> delete(@PathVariable(Paths.V1.Content.contentId) UUID contentId) {
        return contentService.delete(contentId);
    }
}
