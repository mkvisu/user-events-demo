package edu.labs.events.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fasterxml.jackson.annotation.JsonFormat;

@Audited
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "events")
@EntityListeners(AuditingEntityListener.class)
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String address;
    private String code;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date eventDate;
    
    @CreatedDate
    @Column(updatable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedDate
    @Column(insertable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
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
