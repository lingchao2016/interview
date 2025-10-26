package com.interview.repository;

import com.interview.model.EventDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventSearchRepository extends ElasticsearchRepository<EventDocument, String> {

    List<EventDocument> findByNameContainingIgnoreCase(String name);

    List<EventDocument> findByLocationContainingIgnoreCase(String location);

    List<EventDocument> findByDescriptionContainingIgnoreCase(String description);

    List<EventDocument> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrLocationContainingIgnoreCase(
            String name, String description, String location);
}
