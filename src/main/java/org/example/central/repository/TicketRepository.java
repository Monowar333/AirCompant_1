package org.example.central.repository;

import org.example.central.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, Long> {

    @Query(
            value = "SELECT t.* FROM ticket t " +
                    "WHERE t.user_id = :userId", nativeQuery = true
    )
    List<TicketEntity> allTicketsForUser(@Param("userId") Long name);
    
}
