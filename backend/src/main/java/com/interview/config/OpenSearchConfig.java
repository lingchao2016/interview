package com.interview.config;

import org.opensearch.client.RestHighLevelClient;
import org.opensearch.data.client.orhlc.AbstractOpenSearchConfiguration;
import org.opensearch.data.client.orhlc.ClientConfiguration;
import org.opensearch.data.client.orhlc.RestClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.interview.repository")
public class OpenSearchConfig extends AbstractOpenSearchConfiguration {

    @Value("${opensearch.uris:http://localhost:9200}")
    private String opensearchUri;

    @Value("${opensearch.username:}")
    private String username;

    @Value("${opensearch.password:}")
    private String password;

    @Override
    @Bean
    public RestHighLevelClient opensearchClient() {
        ClientConfiguration clientConfiguration;

        if (username != null && !username.isEmpty()) {
            clientConfiguration = ClientConfiguration.builder()
                    .connectedTo(opensearchUri.replace("http://", "").replace("https://", ""))
                    .withBasicAuth(username, password)
                    .build();
        } else {
            clientConfiguration = ClientConfiguration.builder()
                    .connectedTo(opensearchUri.replace("http://", "").replace("https://", ""))
                    .build();
        }

        return RestClients.create(clientConfiguration).rest();
    }
}
