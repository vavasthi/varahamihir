package com.avasthi.varahamihir.common.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPojo implements Serializable {

    private UUID id;
    private String createdBy;
    private String updatedBy;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createdAt;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date updatedAt;
    private UUID tenantId;
    private String fullname;
    private String username;
    private String password;
    private String email;
    private long mask;
    private String authToken;
    private Date expiry;
    private Set<String> grantedAuthorities;
}