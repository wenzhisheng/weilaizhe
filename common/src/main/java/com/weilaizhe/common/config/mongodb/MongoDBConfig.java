//package com.weilaizhe.common.config.mongodb;
//
//import com.mongodb.Mongo;
//import com.mongodb.MongoClient;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.convert.CustomConversions;
//import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
//
//import java.util.Arrays;
//
//@Configuration
//public class MongoDBConfig {
//
//
//    //MongoDB properties read from the application.yaml configuration file (to handle different profiles)
//    @Value("${spring.data.mongodb.host}")
//    private String mongoHost;
//
//    @Value("${spring.data.mongodb.port}")
//    private int mongoPort;
//
//    @Value("${spring.data.mongodb.database}")
//    private String mongoDatabase;
//
//
//    @Bean
//    public MongoTemplate mongoTemplate() throws Exception {
//
//        MongoTemplate mongoTemplate = new MongoTemplate(mongo(), mongoDatabase);
//        MappingMongoConverter mongoMapping = (MappingMongoConverter) mongoTemplate.getConverter();
//        mongoMapping.setCustomConversions(customConversions()); // tell mongodb to use the custom converters
//        mongoMapping.afterPropertiesSet();
//        return mongoTemplate;
//
//    }
//
//    /**
//     * Configure the MongoDB client
//     *
//     **/
//    @Bean
//    public Mongo mongo() throws Exception {
//        return new MongoClient(mongoHost, mongoPort);
//    }
//
//
//    /**
//     * Returns the list of custom converters that will be used by the MongoDB template
//     *
//     **/
//    public CustomConversions customConversions() {
//        return new CustomConversions(Arrays.asList(new DoubleToBigDecimalConverter(), new BigDecimalToDoubleConverter()));
//    }
//}