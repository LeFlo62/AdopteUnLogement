package fr.isep.adopte_un_logement.service;

import fr.isep.adopte_un_logement.config.MinioConfig;
import io.minio.*;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ImageService {

    private static final Logger LOGGER = Logger.getLogger(ImageService.class.getName());
    private static final String BUCKET_NAME = "adopteunlogement";

    private final MinioClient minioClient;

    @Autowired
    public ImageService(MinioConfig minioConfig){
        this.minioClient = MinioClient.builder()
                .endpoint(minioConfig.getUrl())
                .credentials(minioConfig.getUser(), minioConfig.getPassword())
                .build();

        try {
            if(!this.minioClient.bucketExists(BucketExistsArgs.builder().bucket(BUCKET_NAME).build())){
                this.minioClient.makeBucket(MakeBucketArgs.builder().bucket(BUCKET_NAME).build());
            }
        } catch (ServerException | InsufficientDataException | ErrorResponseException | IOException |
                 NoSuchAlgorithmException | InvalidKeyException | InvalidResponseException | XmlParserException |
                 InternalException e) {
            throw new RuntimeException(e);
        }
    }

    public InputStream getImage(UUID uuid) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        GetObjectResponse response = minioClient.getObject(GetObjectArgs.builder()
                .bucket(BUCKET_NAME)
                .object(uuid.toString())
                .build());

        return response;
    }

    public List<UUID> uploadImages(MultipartFile[] images){
        List<UUID> uuids = new ArrayList<>();

        List<SnowballObject> snowballs = new ArrayList<>();

        for(int i = 0; i < images.length; ++i){
            UUID uuid = UUID.randomUUID();
            MultipartFile file = images[i];

            try {
                snowballs.add(new SnowballObject(uuid.toString(), file.getInputStream(), file.getSize(), null));
                uuids.add(uuid);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, String.format("Error while uploading image %s", file.getName()), e);
            }

        }

        try {
            minioClient.uploadSnowballObjects(UploadSnowballObjectsArgs.builder()
                    .bucket(BUCKET_NAME)
                    .objects(snowballs)
                    .build());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, String.format("Error while uploading snowball images"), e);
        }

        return uuids;
    }


}
