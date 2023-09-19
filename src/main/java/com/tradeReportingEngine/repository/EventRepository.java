package main.java.com.tradeReportingEngine.repository;

import main.java.com.tradeReportingEngine.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
    // custom query methods can be added here
}

