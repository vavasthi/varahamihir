package com.avasthi.varahamihir.guardian.entities;

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
@Table(name = "guardians")
@EntityListeners(AuditingEntityListener.class)
public class Guardian implements Serializable {

    @Id
    @org.hibernate.annotations.Type(type="uuid-char")
    @Column(name = "user_id")
    private UUID userId;
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
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "guardian_children", joinColumns = @JoinColumn(name = "guardian_id"))
    @Column(name = "children")
    @org.hibernate.annotations.Type(type="uuid-char")
    private Set<UUID> children;
}