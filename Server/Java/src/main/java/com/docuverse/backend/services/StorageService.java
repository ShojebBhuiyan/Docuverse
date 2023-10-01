package com.docuverse.backend.services;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import io.github.cdimascio.dotenv.Dotenv;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

@Service
public class StorageService {
    private final AmazonS3 space;

    Dotenv dotenv = Dotenv.load();

    public StorageService() {
        AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(
                new BasicAWSCredentials(dotenv.get("DO_SPACES_ACCESS_KEY"), dotenv.get("DO_SPACES_SECRET_KEY"))
        );
        space = AmazonS3ClientBuilder
                .standard()
                .withCredentials(awsCredentialsProvider)
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(dotenv.get("DO_SPACES_ENDPOINT"), dotenv.get("DO_SPACES_REGION"))
                )
                .build();
    }

    public void uploadFile(MultipartFile file, String threadId) throws IOException {
        System.out.println("Upload Service");
        System.out.println("Bucket: " + dotenv.get("DO_SPACES_NAME"));


        try (InputStream fs =  file.getInputStream();) {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(file.getContentType());
            String fileName = DateTime.now().toString() + file.getOriginalFilename();
            System.out.println("Input Stream Got");

            space.putObject(new PutObjectRequest(dotenv.get("DO_SPACES_ENDPOINT"), threadId + "/" + fileName, fs, objectMetadata));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
