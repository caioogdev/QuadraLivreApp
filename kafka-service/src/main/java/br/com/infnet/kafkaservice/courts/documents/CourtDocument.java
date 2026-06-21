package br.com.infnet.kafkaservice.courts.documents;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "courts")
@Getter @Setter
public class CourtDocument {

    @Id
    private String id;
    private String name;
    private String sportType;
    private String surfaceType;
    private String city;
    private String state;
    private String street;
    private String zipCode;
    private Boolean active;
    private String openTime;
    private String closeTime;

}
