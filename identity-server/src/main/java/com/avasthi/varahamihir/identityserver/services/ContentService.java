package com.avasthi.varahamihir.identityserver.services;

import com.avasthi.varahamihir.common.errors.ExceptionMessage;
import com.avasthi.varahamihir.common.exceptions.ContentWritingFailedException;
import com.avasthi.varahamihir.common.exceptions.EntityNotFoundException;
import com.avasthi.varahamihir.identityserver.entities.Content;
import com.avasthi.varahamihir.identityserver.repositories.ContentRepository;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ContentService {

    @Value("${varahamihir.content.basepath}")
    private String basePath;
    @Value("${varahamihir.content.google-service-account}")
    private String googleServiceAccount;

    private final String contentBucket = "avasthi-home.appspot.com";
    private final String topLevelFolder = "content";

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
/*            Files.copy(is, destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            is.close();
            Content content = Content.builder()
                    .mimetype(mimeType)
                    .originalFilename(uploadedFilename)
                    .size(size)
                    .storagePathname(destinationFile.toString())
                    .build();
            content.setUrl(String.format("%s/%s/%s",
                    Paths.V1.Content.fullPath,
                    content.getId().toString(),
                    URLEncoder.encode(uploadedFilename, Charset.forName("UTF-8"))));
            contentRepository.save(content);*/
            Content content = Content.builder()
                    .mimetype(mimeType)
                    .originalFilename(uploadedFilename)
                    .size(size)
                    .storagePathname(destinationFile.toString())
                    .build();
            Blob blob = uploadToCloud(contentBucket, String.format("%s/%s", topLevelFolder, content.getOriginalFilename()), mimeType, is);
            content.setUrl(blob.getMediaLink());
            return Optional.of(content);
        }
        catch(java.io.IOException ex) {
            throw new ContentWritingFailedException(String.format(ExceptionMessage.CONTENT_WRITING_FAILED_ERROR.getReason(), destinationFile.toString()),
                    String.format(ExceptionMessage.CONTENT_WRITING_FAILED_ERROR.getError(), destinationFile.toString()));
        }
    }
    public List<Content> save(MultipartFile[] files) {

        List<Content> contentList = new ArrayList<>();
        for (MultipartFile file : files) {
            contentList.add(save(file).get());
        }
        return contentList;
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
    public Blob uploadToCloud(String bucketName,
                              String filename,
                              String contentType,
                              InputStream content) throws IOException {

        ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64.decode(googleServiceAccount));
        Credentials credentials = GoogleCredentials.fromStream(inputStream);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        //storage.create(BucketInfo.newBuilder(bucketName).setStorageClass(StorageClass.COLDLINE).build());
        BlobId blobId = BlobId.of(bucketName, filename);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(contentType).build();
        Blob blob = storage.createFrom(blobInfo, content, Storage.BlobWriteOption.detectContentType());
        return blob;
    }

}
