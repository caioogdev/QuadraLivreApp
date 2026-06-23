package br.com.infnet.kafkaservice.courts.controllers;

import br.com.infnet.kafkaservice.courts.documents.CourtDocument;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.slf4j.MDC;

@RestController
@RequestMapping("/courts/search")
@RequiredArgsConstructor
public class CourtController {

    private static final Logger logger = LoggerFactory.getLogger(CourtController.class);
    private final ElasticsearchOperations elasticsearchOperations;

    @GetMapping
    public ResponseEntity<List<CourtDocument>> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String sportType,
            @RequestParam(required = false) String surfaceType,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String street,
            @RequestParam(required = false) String zipCode,
            @RequestParam(required = false) Boolean active) {

        logger.info("Recebendo requisição de busca");
        logger.info("MDC traceId={}, spanId={}", MDC.get("traceId"), MDC.get("spanId"));
        Criteria criteria = new Criteria();

        if (name != null)        criteria = criteria.and("name").is(name);
        if (sportType != null)   criteria = criteria.and("sportType").is(sportType);
        if (surfaceType != null) criteria = criteria.and("surfaceType").is(surfaceType);
        if (city != null)        criteria = criteria.and("city").is(city);
        if (state != null)       criteria = criteria.and("state").is(state);
        if (street != null)      criteria = criteria.and("street").is(street);
        if (zipCode != null)     criteria = criteria.and("zipCode").is(zipCode);
        if (active != null)      criteria = criteria.and("active").is(active);

        CriteriaQuery query = new CriteriaQuery(criteria);

        List<CourtDocument> result = elasticsearchOperations
                .search(query, CourtDocument.class)
                .stream()
                .map(SearchHit::getContent)
                .toList();

        logger.info("Busca concluida: {} resultados", result.size());

        return ResponseEntity.ok(result);
    }
}
