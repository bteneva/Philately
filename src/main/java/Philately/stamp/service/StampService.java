package Philately.stamp.service;

import Philately.stamp.model.Stamp;
import Philately.stamp.model.WishedStamp;
import Philately.stamp.repository.StampRepository;
import Philately.stamp.repository.WishedStampRepository;
import Philately.user.model.User;
import Philately.user.service.UserService;
import Philately.web.dto.NewStampRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StampService {
    private final StampRepository stampRepository;
    private final WishedStampRepository wishedStampRepository;

    @Autowired
    public StampService(StampRepository stampRepository, WishedStampRepository wishedStampRepository) {
        this.stampRepository = stampRepository;
        this.wishedStampRepository = wishedStampRepository;
    }

    public void createStamp(NewStampRequest newStampRequest, User user) {
        Stamp stamp = Stamp.builder()
                .name(newStampRequest.getName())
                .description(newStampRequest.getDescription())
                .imgURL(newStampRequest.getImageURL())
                .paper(newStampRequest.getPaper())
                .owner(user)
                .build();

       stampRepository.save(stamp);
    }

    public List<Stamp> getAllStamps() {
        return stampRepository.findAll();
    }

//    public void createWished(UUID id, User user) {
//        Stamp stamp = stampRepository.findById(id).get();
//
//        WishedStamp wishedStamp = WishedStamp.builder()
//                .name(stamp.getName())
//                .description(stamp.getDescription())
//                .imgURL(stamp.getImgURL())
//                .owner(user)
//                .build();
//
//        wishedStampRepository.save(wishedStamp);
//    }

    public void createWished(UUID stampId, User user) {

        Stamp stamp = getById(stampId);

        WishedStamp wishedStamp = WishedStamp.builder()
                .imgURL(stamp.getImgURL())
                .name(stamp.getName())
                .description(stamp.getDescription())
                .owner(user)
                .build();

        wishedStampRepository.save(wishedStamp);
    }

    public Stamp getById(UUID id) {

        return stampRepository.findById(id).orElseThrow(() -> new RuntimeException("Stamp with id [%s] does not exist.".formatted(id)));
    }

    public void deleteWishedStampById(UUID id) {
        wishedStampRepository.deleteById(id);
    }
}
