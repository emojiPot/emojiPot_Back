package com.hanium.emoji_pot.domain.comments.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReCommentRequestDto {

    @NotBlank
    private String replyContent;

}
