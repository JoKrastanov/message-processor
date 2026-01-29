package com.example.message_processor.messaging.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageHistoryRepository extends JpaRepository<MessageHistory, Long>
{
    MessageHistory findTopByMessageUuidOrderByVersionDesc(String uuid);
}
