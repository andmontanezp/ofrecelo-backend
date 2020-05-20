package cl.ofrecelo.api.offer.service;

import org.bson.types.ObjectId;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface CloudStorageService {

    String uploadFile(byte[] file, String fileName, String offerTitle, ObjectId userId) throws Exception;
    byte[] dowloadFile(String blobName) throws Exception;
}
