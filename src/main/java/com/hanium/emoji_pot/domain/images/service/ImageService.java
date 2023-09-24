package com.hanium.emoji_pot.domain.images.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.hanium.emoji_pot.domain.images.Image;
import com.hanium.emoji_pot.domain.images.ImageRepository;
import com.hanium.emoji_pot.domain.images.dto.ImageUploadDto;
import com.hanium.emoji_pot.domain.posts.Post;
import com.hanium.emoji_pot.domain.posts.PostRepository;
import com.hanium.emoji_pot.domain.users.User;
import com.hanium.emoji_pot.domain.users.UserRepository;
import com.hanium.emoji_pot.domain.users.UserRole;
import com.hanium.emoji_pot.global.exception.AppException;
import com.hanium.emoji_pot.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ImageService {

    private final AmazonS3Client amazonS3Client;
    private final ImageRepository imageRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;

    @Transactional
    public void imageUpload(Long postId, String requestEmail, List<MultipartFile> files) throws IOException, SQLException {
        User requestUser = userValid(requestEmail);
        Post post = postValid(postId);

        UserRole requestUserRole = requestUser.getRole();
        String author = post.getUser().getEmail();

        log.info("게시글 이미지 추가 요청자 ROLE = {} 게시글 작성자 author = {}", requestUserRole, author);

        checkAuth(requestEmail, author, requestUserRole);

        for (MultipartFile file : files) {
            File uploadFile = convert(file).orElseThrow(() -> new IllegalArgumentException("파일 업로드에 실패하였습니다."));
            String dir = "images/".concat(postId.toString());
            imageRepository.save(Image.uploadImage(post, upload(uploadFile, dir)));
        }
    }

    private String upload(File uploadFile, String dir) {
        String sourceName = uploadFile.getName();
        String sourceExt = FilenameUtils.getExtension(sourceName).toLowerCase();

        String fileName = dir + "/" + LocalDateTime.now().toString().concat(".").concat(sourceExt);
        String uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);

        return uploadImageUrl;
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    private String putS3(File uploadFile, String fileName) {
        try {
            amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (Exception e) {
            log.error("이미지 s3 업로드 실패");
            log.error(e.getMessage());
            removeNewFile(uploadFile);
            throw new RuntimeException();
        }

        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }

            return Optional.of(convertFile);
        }

        return Optional.empty();
    }

    @Transactional
    public List<String> getImages(Long postId) {
        Post post = postValid(postId);

        return imageRepository.findAllByPost(post).stream().map(Image::getImageUrl).collect(Collectors.toList());
    }

    @Transactional
    public User userValid(String email) {
        return userRepository.findByEmailAndIsDeleted(email, false)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    public Post postValid(Long postId) {
        Post post = postRepository.findByPostIdAndIsDeleted(postId, false)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));

        if (post.getIsDeleted()) {
            throw new AppException(ErrorCode.POST_NOT_FOUND);
        }
        return post;
    }

    public void checkAuth(String requestEmail, String author, UserRole requestUserRole) {
        if (!requestUserRole.equals(UserRole.ROLE_ADMIN) && !author.equals(requestEmail)) {
            throw new AppException(ErrorCode.USER_NOT_MATCH);
        }
    }

    @Transactional
    public List<String> getAllImages() throws SQLException {
        return imageRepository.findAll().stream().map(Image::getImageUrl).collect(Collectors.toList());
    }

    @Transactional
    public List<Image> getImagesByPostId(Long postId) {
        Post post = postValid(postId);

        return imageRepository.findAllByPost(post);
    }

    // 이미지 삭제
    @Transactional
    public void deleteImage(Long postId) throws SQLException{
        String splitStr = ".com/";
        List<Image> imageList = getImagesByPostId(postId);
        for (Image image : imageList) {
            String fileUrl = image.getImageUrl();
            String fileName = fileUrl.substring(fileUrl.lastIndexOf(splitStr) + splitStr.length());

            amazonS3Client.deleteObject(bucket, fileName);
            imageRepository.delete(image);
        }
    }

}
