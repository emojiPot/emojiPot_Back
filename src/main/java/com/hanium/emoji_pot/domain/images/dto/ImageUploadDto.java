package com.hanium.emoji_pot.domain.images.dto;

import com.hanium.emoji_pot.domain.images.Image;
import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageUploadDto {

    private Long postId;
    private String imageUrl;

    public ImageUploadDto(Image image) {
        this.postId = image.getPost().getPostId();
        this.imageUrl = image.getImageUrl();
    }
}
