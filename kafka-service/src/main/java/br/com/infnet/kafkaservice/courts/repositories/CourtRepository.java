package br.com.infnet.kafkaservice.courts.repositories;

import br.com.infnet.kafkaservice.courts.documents.CourtDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CourtRepository extends ElasticsearchRepository<CourtDocument, String> {

}
