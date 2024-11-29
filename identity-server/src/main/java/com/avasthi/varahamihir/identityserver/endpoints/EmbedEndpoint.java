package com.avasthi.varahamihir.identityserver.endpoints;

import com.avasthi.varahamihir.common.errors.ExceptionMessage;
import com.avasthi.varahamihir.common.exceptions.EntityNotFoundException;
import com.avasthi.varahamihir.identityserver.entities.Content;
import com.avasthi.varahamihir.identityserver.entities.Embed;
import com.avasthi.varahamihir.identityserver.services.ContentService;
import com.avasthi.varahamihir.identityserver.services.EmbedService;
import com.avasthi.varahamihir.identityserver.utils.Paths;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(Paths.V1.Embed.fullPath)
@RequiredArgsConstructor
public class EmbedEndpoint {
    private final EmbedService embedService;

    @RequestMapping(value = Paths.pageAndSizePath, method = RequestMethod.GET)
    public Page<Embed> getAll(@PathVariable(value = Paths.pageVariable, required = false) Integer page,
                              @PathVariable(value = Paths.sizeVariable, required = false) Integer size) {
        if (page == null || page == 0) {
            page = Paths.defaultPageNumber;
        }
        if (size == null || size == 0) {
            size = Paths.defaultPageSize;
        }
        return embedService.findAll(page, size);
    }
    @RequestMapping(method = RequestMethod.POST)
    public Optional<Embed> save(@RequestBody Embed embed) {
        return embedService.save(embed);
    }
    @RequestMapping(value = Paths.idPath, method = RequestMethod.DELETE)
    public Optional<Embed> delete(@PathVariable(Paths.idPathVariable) UUID id) {
        return embedService.delete(id);
    }
}
