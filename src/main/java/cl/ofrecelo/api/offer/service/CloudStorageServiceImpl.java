package cl.ofrecelo.api.offer.service;

import com.google.api.services.storage.model.Bucket;
import com.google.cloud.storage.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;

@Service
public class CloudStorageServiceImpl implements CloudStorageService {

    @Value("${cloud.storage.segment}")
    private String cloudStorage;

    /**
     * subir archivos cloud
     */
    @Override
    public String uploadFile(MultipartFile file, String fileName, String offerTitle, String userId) throws Exception {
        String mediaLink = "";
        try {
            Date today = new Date();
            if(file != null){
                if(fileName != null && !"".equalsIgnoreCase(fileName)){
                    Storage storage = StorageOptions.getDefaultInstance().getService();
                    if(storage != null){
                        if(cloudStorage != null && !"".equalsIgnoreCase(cloudStorage)){
                            String fileExtension = fileName.substring(fileName.lastIndexOf(".")+1);
                            String contentType = "image/" + fileExtension;
                            String blobName = "offer_" + userId + "_" + offerTitle.replace(" ", "_")
                                    + "_" + today.getTime() + "_" + fileName;
                            BlobId blobId = BlobId.of(cloudStorage, blobName);
                            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(contentType).build();
                            Blob blob = storage.create(blobInfo, new ByteArrayInputStream(file.getBytes()));
                            if(blob != null){
                               mediaLink = blob.getName();
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            throw new Exception("Error uploading the file:" + e);
        }
        return mediaLink;
    }

    @Override
    public byte[] dowloadFile(String blobName) throws Exception {
        byte[] file = null;
        try {
            Storage storage = StorageOptions.getDefaultInstance().getService();
            if(storage != null){
                if(cloudStorage != null && !"".equalsIgnoreCase(cloudStorage)){
                    BlobId blobId = BlobId.of(cloudStorage, blobName);
                    if(blobId != null){
                        file = storage.readAllBytes(blobId);
                    }
                }
            }
        }catch (Exception e){
            throw new Exception("Error downloading the file:" + e);
        }
        return file;
    }
}
