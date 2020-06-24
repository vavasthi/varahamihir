package com.avasthi.varahamihir.identityserver.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractBaseEntity {

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
}
