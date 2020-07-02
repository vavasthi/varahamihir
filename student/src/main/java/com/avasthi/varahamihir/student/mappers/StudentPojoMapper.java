package com.avasthi.varahamihir.student.mappers;

import com.avasthi.varahamihir.common.pojos.StudentPojo;
import com.avasthi.varahamihir.student.entities.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentPojoMapper {

  public StudentPojo convert(Student s) {

      return StudentPojo.builder()
              .userId(s.getUserId())
              .tenantId(s.getTenantId())
              .createdAt(s.getCreatedAt())
              .createdBy(s.getCreatedBy())
              .updatedBy(s.getUpdatedBy())
              .build(); // Not copying password into pojo
  }
  public Student convert(StudentPojo s) {

    return Student.builder()
            .userId(s.getUserId())
            .createdAt(s.getCreatedAt())
            .createdBy(s.getCreatedBy())
            .tenantId(s.getTenantId())
            .updatedAt(s.getUpdatedAt())
            .updatedBy(s.getUpdatedBy())
            .build();
  }
}
