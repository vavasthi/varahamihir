package com.avasthi.varahamihir.identityserver.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users",
        uniqueConstraints =
          {
                  @UniqueConstraint(name = "uq_email", columnNames = {"email"}),
                  @UniqueConstraint(name = "uq_username", columnNames = {"username"})
          })
@EntityListeners(AuditingEntityListener.class)
public class User implements Serializable {

    public User() {

    }
    public User(Tenant tenant, String fullname, String username, String email, long mask, String authToken, Date expiry) {
        this.tenant = tenant;
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.mask = mask;
        this.authToken = authToken;
        this.expiry = expiry;

    }
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="tenant_id", nullable = false)
    private Tenant tenant;
    @Column(name = "fullname")
    private String fullname;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "mask")
    private long mask;
    @Column(name = "auth_token")
    private String authToken;
    @Column(name = "expiry")
    private Date expiry;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_granted_authorities", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "granted_authorities")
    private Set<String> grantedAuthorities;
}