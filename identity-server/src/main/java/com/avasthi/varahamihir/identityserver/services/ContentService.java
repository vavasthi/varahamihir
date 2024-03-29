package com.avasthi.varahamihir.identityserver.services;

import com.avasthi.varahamihir.common.errors.ExceptionMessage;
import com.avasthi.varahamihir.common.exceptions.ContentWritingFailedException;
import com.avasthi.varahamihir.common.exceptions.EntityNotFoundException;
import com.avasthi.varahamihir.identityserver.entities.Content;
import com.avasthi.varahamihir.identityserver.repositories.ContentRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContentService {

    @Value("${varahamihir.content.basepath}")
    private String basePath;

    private final ContentRepository contentRepository;
    private final HttpServletRequest servletRequest;
    public Optional<Content> save(MultipartFile file) {
        String uploadedFilename = file.getOriginalFilename();
        String mimeType = file.getContentType();
        long size = file.getSize();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        File destinationDirectory = new File(basePath, sdf.format(new Date()));
        destinationDirectory.mkdirs();
        File destinationFile = new File(destinationDirectory, uploadedFilename);
        try {

            InputStream is = file.getInputStream();
            Files.copy(is, destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            is.close();
            Content content = Content.builder()
                    .mimetype(mimeType)
                    .originalFilename(uploadedFilename)
                    .size(size)
                    .storagePathname(destinationFile.toString())
                    .build();
            content.setUrl(String.format("%s/%s/%s",
                    servletRequest.getRequestURI(),
                    content.getId().toString(),
                    URLEncoder.encode(uploadedFilename, Charset.forName("UTF-8"))));
            contentRepository.save(content);
            return Optional.of(content);
        }
        catch(java.io.IOException ex) {
            throw new ContentWritingFailedException(String.format(ExceptionMessage.CONTENT_WRITING_FAILED_ERROR.getReason(), destinationFile.toString()),
                    String.format(ExceptionMessage.CONTENT_WRITING_FAILED_ERROR.getError(), destinationFile.toString()));
        }
    }
    public Optional<Content> findById(UUID id) {
        return contentRepository.findById(id);
    }

    public InputStream retrieve(Content content) throws FileNotFoundException {
        File storedFile = new File(content.getStoragePathname());
        return new FileInputStream(storedFile);
    }

    public Optional<Content> delete(UUID contentId) {

        Content content = contentRepository
                .findById(contentId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ExceptionMessage.ENTITY_NOT_FOUND_ERROR.getReason(), "Content", contentId.toString()),
                        String.format(ExceptionMessage.ENTITY_NOT_FOUND_ERROR.getError(), "Content", contentId.toString())));
        File localFile = new File(content.getStoragePathname());
        localFile.delete();
        contentRepository.deleteById(content.getId());
        return Optional.of(content);
    }
}
