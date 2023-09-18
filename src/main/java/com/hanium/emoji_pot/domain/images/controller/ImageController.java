package com.hanium.emoji_pot.domain.images.controller;

import com.hanium.emoji_pot.domain.images.dto.ImageUploadDto;
import com.hanium.emoji_pot.domain.images.service.ImageService;
import com.hanium.emoji_pot.global.exception.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
@Slf4j
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/posts/{postId}/images")
    public Response uploadImage(@PathVariable("postId") Long postId, @RequestPart List<MultipartFile> files, Authentication authentication) throws IOException, SQLException {
        String requestEmail = authentication.getName();
        log.info("이미지를 추가할 게시글 id : {} 이미지 추가 요청자 Email : {}", postId, requestEmail);
        imageService.imageUpload(postId, requestEmail, files);
        return Response.success("이미지를 추가했습니다.");
    }

    @GetMapping("/posts/{postId}/images")
    public Response getImages(@PathVariable("postId") Long postId) {
        return Response.success(imageService.getImages(postId));
    }

    @GetMapping("/images")
    public Response getAllImages() throws SQLException {
        return Response.success(imageService.getAllImages());
    }

    @DeleteMapping("/images/{imageId}")
    public Response delete(@PathVariable("imageId") Long imageId) throws SQLException {
        log.info("삭제할 이미지 id : {}", imageId);
        imageService.deleteImage(imageId);
        return Response.success("이미지를 삭제했습니다.");
    }

}
