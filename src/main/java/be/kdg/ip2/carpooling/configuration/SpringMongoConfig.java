package be.kdg.ip2.carpooling.configuration;

import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "be.kdg.ip2.carpooling.repository")
public class SpringMongoConfig {
    @Value("${spring.data.mongodb.uri}")
    private String uri;

    @Value("${spring.data.mongodb.database}")
    private String mongoDB;

    @Bean
    public MongoDbFactory mongoDbFactory() throws Exception {
        //TODO fix usage of application.properties
        return new SimpleMongoDbFactory(new MongoClientURI("mongodb+srv://Root:Root@cluster0-avqny.gcp.mongodb.net/test"));
        //return new SimpleMongoDbFactory(new MongoClientURI(uri + mongoDB));
        //return new SimpleMongoDbFactory(new MongoClient(),"test");
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoDbFactory());
    }
}
