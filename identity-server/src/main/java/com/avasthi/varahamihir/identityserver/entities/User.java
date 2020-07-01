package com.avasthi.varahamihir.identityserver.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users",
        uniqueConstraints =
          {
                  @UniqueConstraint(name = "uq_email", columnNames = {"tenant_id","email"}),
                  @UniqueConstraint(name = "uq_username", columnNames = {"tenant_id", "username"})
          })
@EntityListeners(AuditingEntityListener.class)
public class User implements Serializable {

    public String getPassword() {
        return password;
    }
    @Id
    @org.hibernate.annotations.Type(type="uuid-char")
    @Column(name = "id")
    private UUID id;
    @Column(name = "tenant_id")
    @org.hibernate.annotations.Type(type="uuid-char")
    private UUID tenantId;
    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;
    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;
    @CreatedDate
    @Column(name = "created_at")
    private Date createdAt;
    @LastModifiedDate
    @Column(name = "updated_at")
    private Date updatedAt;
    @Column(name = "fullname")
    private String fullname;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_granted_authorities", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "granted_authorities")
    private Set<String> grantedAuthorities;
    private boolean expired = false;
    private boolean locked = false;
    private boolean credentialsLocked = false;
}