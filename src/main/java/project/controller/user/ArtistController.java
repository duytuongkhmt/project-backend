package project.controller.user;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.business.ArtistBusiness;
import project.payload.response.ResponseObject;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/top-artist")
public class ArtistController {
    private final ArtistBusiness artistBusiness;

    @GetMapping
    public ResponseEntity<ResponseObject> getTopArtists(){
        return ResponseEntity.ok(new ResponseObject(artistBusiness.getTopArtists()));
    }

}
