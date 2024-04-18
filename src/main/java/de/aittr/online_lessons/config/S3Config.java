package de.aittr.online_lessons.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Class for file service configuration.
 *
 * @author EgorKnestyapin
 * @version 1.0.0
 */
@Configuration
public class S3Config {

    @Value("${s3_accessKey}")
    private String s3AccessKey;

    @Value("${s3_secretKey}")
    private String s3SecretKey;

    @Value("${s3_endpoint}")
    private String s3Endpoint;

    @Value("${s3_region}")
    private String s3Region;

    @Bean
    public AmazonS3 amazonS3() {
        AWSCredentials credentials = new BasicAWSCredentials(
                s3AccessKey, s3SecretKey
        );

        AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(
                s3Endpoint, s3Region
        );

        AmazonS3ClientBuilder amazonS3ClientBuilder = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials));

        amazonS3ClientBuilder.setEndpointConfiguration(endpointConfiguration);

        return amazonS3ClientBuilder.build();
    }
}
