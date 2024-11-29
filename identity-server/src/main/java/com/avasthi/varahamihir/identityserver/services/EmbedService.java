package com.avasthi.varahamihir.identityserver.services;

import com.avasthi.varahamihir.common.errors.ExceptionMessage;
import com.avasthi.varahamihir.common.exceptions.ContentWritingFailedException;
import com.avasthi.varahamihir.common.exceptions.EntityNotFoundException;
import com.avasthi.varahamihir.identityserver.entities.Content;
import com.avasthi.varahamihir.identityserver.entities.Embed;
import com.avasthi.varahamihir.identityserver.entities.Recipe;
import com.avasthi.varahamihir.identityserver.repositories.ContentRepository;
import com.avasthi.varahamihir.identityserver.repositories.EmbedRepository;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class EmbedService {

    private final EmbedRepository embedRepository;

    public Page<Embed> findAll(int page, int size) {
        return embedRepository.findAll(PageRequest.of(page, size, Sort.by("updatedAt").descending()));
    }

    public Optional<Embed> save(Embed embed) {
        if (embed.getId() == null) {
            embed.setId(UUID.randomUUID());
        }
        embed = embedRepository.save(embed);
        return Optional.of(embed);
    }
    public Optional<Embed> findById(UUID id) {
        return embedRepository.findById(id);
    }

    public Optional<Embed> delete(UUID embedId) {

        Embed embed = embedRepository.findById(embedId).orElseThrow(() -> new EntityNotFoundException(String.format(ExceptionMessage.ENTITY_NOT_FOUND_ERROR.getReason(), "Embed", embedId.toString()),
                String.format(ExceptionMessage.ENTITY_NOT_FOUND_ERROR.getError(), "Embed", embedId.toString()) ));
        embedRepository.deleteById(embed.getId());
        return Optional.of(embed);
    }
}
