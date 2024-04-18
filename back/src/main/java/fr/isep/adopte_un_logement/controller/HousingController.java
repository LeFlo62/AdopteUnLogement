package fr.isep.adopte_un_logement.controller;

import fr.isep.adopte_un_logement.dto.HousingCreationDTO;
import fr.isep.adopte_un_logement.dto.HousingDTO;
import fr.isep.adopte_un_logement.dto.HousingListItemDTO;
import fr.isep.adopte_un_logement.dto.StringDTO;
import fr.isep.adopte_un_logement.entities.Housing;
import fr.isep.adopte_un_logement.mapper.HousingMapper;
import fr.isep.adopte_un_logement.model.UserDetailsImpl;
import fr.isep.adopte_un_logement.service.HousingService;
import fr.isep.adopte_un_logement.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestMapping("/housing")
@RestController
@AllArgsConstructor
public class HousingController {

    private HousingService housingService;
    private HousingMapper housingMapper;
    private ImageService imageService;

    @GetMapping("/list")
    public ResponseEntity<List<HousingListItemDTO>> getHousingListPaginated(Pageable pageable) {
        return ResponseEntity.ok(housingMapper.toDTO(housingService.getHousingListPaginated(pageable).getContent()));
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<List<HousingListItemDTO>> getHousingListByAuthorId(@PathVariable("id") String id) {
        return ResponseEntity.ok(housingMapper.toDTO(housingService.getHousingListByAuthorId(UUID.fromString(id))));
    }

    @PostMapping("/create")
    public ResponseEntity<StringDTO> createHousing(HousingCreationDTO housingCreationDTO) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = userDetails.getId().toString();

        housingCreationDTO.setAuthorId(userId);

        Housing housing = housingMapper.toEntity(housingCreationDTO);

        List<UUID> images = imageService.uploadImages(housingCreationDTO.getImages());

        housing.setImages(images);
        String id = housingService.createHousing(housing).getId().toString();
        return ResponseEntity.ok(StringDTO.from(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HousingDTO> getHousingById(@PathVariable("id") String id) {
        Optional<Housing> housingOpt = housingService.getHousingById(UUID.fromString(id));
        if(housingOpt.isPresent()){
            return ResponseEntity.ok(housingMapper.toHousingDTO(housingOpt.get()));
        }
        return ResponseEntity.notFound().build();
    }
}
