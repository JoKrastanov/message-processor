package com.example.message_processor.messaging.repository;

import java.time.LocalDateTime;
import java.util.Map;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(nullable = false)
    private Map<String, Object> originalMessage;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(nullable = false)
    private Map<String, Object> currentMessage;
    
    @Column(nullable = false)
    private String status; // PROCESSING, COMPLETED, FAILED
    
    @Column
    private String errorMessage;
    
    @Column(nullable = false)
    private LocalDateTime time_received;
    
    @Column
    private LocalDateTime time_processed;
    
    @Column(nullable = false)
    private LocalDateTime last_update;
    
    @PrePersist
    protected void onCreate() {
        this.time_received = LocalDateTime.now();
        this.last_update = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        this.last_update = LocalDateTime.now();
    }
}
