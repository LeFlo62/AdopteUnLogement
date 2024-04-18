package fr.isep.adopte_un_logement.controller;

import fr.isep.adopte_un_logement.service.ImageService;
import org.apache.catalina.connector.ClientAbortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@RequestMapping("/image")
@RestController
public class ImageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageController.class);
    private ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping(value="/{id}", produces = "image/jpg")
    public StreamingResponseBody getImage(@PathVariable("id") String id) {
        final UUID uuid = UUID.fromString(id);
        return outputStream -> {
            try {
                InputStream input = imageService.getImage(uuid);

                input.transferTo(outputStream);
            } catch (ClientAbortException e){
                LOGGER.debug("Client aborted");
            } catch (Exception e) {
                throw new IOException(e);
            }

        };
    }

}
