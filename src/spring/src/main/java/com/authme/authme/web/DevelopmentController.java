package com.authme.authme.web;

import com.authme.authme.data.dto.ProfileDTO;
import com.authme.authme.utils.Profile;
import org.apache.commons.io.IOUtils;
import org.aspectj.apache.bcel.util.ClassPath;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@RestController
public class DevelopmentController {
    private Map<Long, Profile> data = new LinkedHashMap<>();
    Random random = new Random();

//    @PostMapping("/dev/test")
//    public void test(@RequestPart("images") List<MultipartFile> images) {
//        pictures = new LinkedList<>();
//        for (MultipartFile image : images) {
//            try {
//                System.out.println(ClassPath.getClassPath());
//                File filePath = new File("/images/");
//                filePath.mkdirs();
//                File dest = new File(filePath.getAbsolutePath(), fileCounter + ".tmp");
//                dest.createNewFile();
//                fileCounter++;
//                image.transferTo(dest);
//                pictures.add(dest);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @GetMapping("/dev/test")
//    public void getImageTest(HttpServletResponse response) {
//        try {
//            StreamUtils.copy(new FileInputStream(pictures.get(0)), response.getOutputStream());
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @GetMapping(path = "/dev/picture")
//    public void getPictureTest(Long userId, Long pictureId, HttpServletResponse response) throws IOException {
//        StreamUtils.copy(new FileInputStream(pictures.get(Integer.parseInt(pictureId.toString()))), response.getOutputStream());
//    }

    @GetMapping("/dev/profile")
    public ProfileDTO getProfileData(@RequestHeader(name = "dataId") Long dataId) {
        return data.get(dataId).getProfileDTO();
    }

    @GetMapping("/dev/profile/image/{userId}/{pictureId}")
    public void getPicture(@PathVariable("userId") Long userId,
                           @PathVariable("pictureId") Integer pictureId,
                           HttpServletResponse response) throws IOException {
        StreamUtils.copy(new FileInputStream(data.get(userId).getPictures().get(pictureId)), response.getOutputStream());
    }

    @RequestMapping(method = RequestMethod.POST, path = "/dev/profile", consumes = {"multipart/form-data"})
    public ProfileDTO patchProfileData(@RequestHeader(name = "dataId") Long dataId,
                                       @RequestPart("body") ProfileDTO profileDTO,
                                       @RequestPart(value = "pictures", required = false) List<MultipartFile> pictures) {
        Profile profile = data.get(dataId);
        profile.setProfileDTO(profileDTO);
        for (MultipartFile picture : pictures) {
            try {
                File filePath = new File("/images/" + dataId + "/");
                if (!filePath.exists()) {
                    filePath.mkdirs();
                }
                File dest = new File(filePath.getAbsolutePath(), profile.getImageCounter() + ".tmp");
                profile.incrementImageCounter();
                if(!dest.exists())
                    dest.createNewFile();
                picture.transferTo(dest);
                profile.getPictures().add(dest);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data.get(dataId).getProfileDTO();
    }

    @GetMapping("/dev/entry")
    public String createEntry() {
        Long dataId = random.nextLong();
        data.put(dataId, new Profile());
        return dataId.toString();
    }
}
