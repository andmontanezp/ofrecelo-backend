package cl.ofrecelo.api.offer.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface CloudStorageService {

    String uploadFile(MultipartFile file, String fileName, String offerTitle, String userId) throws Exception;
    byte[] dowloadFile(String blobName) throws Exception;
}
