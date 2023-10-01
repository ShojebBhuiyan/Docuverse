package com.docuverse.backend.services;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.docuverse.backend.models.Document;
import com.docuverse.backend.repositories.DocumentRepository;
import com.docuverse.backend.repositories.ThreadRepository;
import io.github.cdimascio.dotenv.Dotenv;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;

@Service
public class StorageService {
    private final AmazonS3 space;

    private final DocumentRepository documentRepository;
    private final ThreadRepository threadRepository;

    Dotenv dotenv = Dotenv.load();

    public StorageService(DocumentRepository documentRepository, ThreadRepository threadRepository) {
        this.documentRepository = documentRepository;
        this.threadRepository = threadRepository;
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
            objectMetadata.setContentLength(file.getSize());
            String fileName = DateTime.now().toString() + file.getOriginalFilename();
            System.out.println("Input Stream Got");
            space.putObject(new PutObjectRequest(dotenv.get("DO_SPACES_ENDPOINT"), threadId + "/" + fileName, fs, objectMetadata));

            Optional<Document> existingDocument = documentRepository.findDocumentByTitle(file.getOriginalFilename());

            if (existingDocument.isPresent()) {
                existingDocument.get().setTitle(file.getOriginalFilename());
                existingDocument.get().setUrl(new URL(dotenv.get("DO_SPACES_ENDPOINT") + dotenv.get("DO_SPACES_ENDPOINT") + "/" + threadId + fileName));
                documentRepository.save(existingDocument.get());
            } else {
                com.docuverse.backend.models.Document newDocument = new com.docuverse.backend.models.Document();
                newDocument.setTitle(file.getOriginalFilename());
                newDocument.setUrl(new URL(dotenv.get("DO_SPACES_ENDPOINT") + dotenv.get("DO_SPACES_ENDPOINT") + "/" + threadId + fileName));
                documentRepository.save(newDocument);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
