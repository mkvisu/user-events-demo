package edu.labs.user.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.context.SecurityContextHolder;

@Audited
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User {

	@Id
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
    private String salt;
    
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime updatedAt;

    @LastModifiedBy
    @Column(insertable = false)
    private String updatedBy;
    
//    @PrePersist
//    public void onPrePersist() {
//        String userName = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        setCreatedBy(userName);
//        setCreatedAt(LocalDateTime.now());
//    }
//     
//    @PreUpdate
//    public void onPreUpdate() {
//    	String userName = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        setUpdatedBy(userName);
//        setUpdatedAt(LocalDateTime.now());
//    }

}
