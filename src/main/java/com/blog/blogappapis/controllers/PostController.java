package com.blog.blogappapis.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.blogappapis.config.AppConstants;
import com.blog.blogappapis.payloads.ApiResponse;
import com.blog.blogappapis.payloads.PostDto;
import com.blog.blogappapis.payloads.PostResponse;
import com.blog.blogappapis.services.FileService;
import com.blog.blogappapis.services.PostService;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;
    
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId){
        PostDto createdPostDto = this.postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<>(createdPostDto, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId){
        List<PostDto> posts = this.postService.getPostsByUser(userId);
        return new ResponseEntity<>(posts,HttpStatus.OK);   
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId){
        List<PostDto> posts = this.postService.getPostsByCategory(categoryId);
        return new ResponseEntity<>(posts,HttpStatus.OK);   
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
     @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE , required = false) Integer pageSize,
     @RequestParam(value="sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
     @RequestParam(value="sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir){
        PostResponse postResponse = this.postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(postResponse,HttpStatus.OK);    
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
        PostDto post  = this.postService.getPostById(postId);
        return new ResponseEntity<>(post,HttpStatus.OK);
    }
    
    @DeleteMapping("/posts/{postId}")
    public ApiResponse deletePost(@PathVariable Integer postId) {
        this.postService.deletePost(postId);
        return new ApiResponse("Post deleted successfully", true);
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId) {
        PostDto updatedPostDto = this.postService.updatePost(postDto, postId);
        return new ResponseEntity<>(updatedPostDto, HttpStatus.OK);
    }
    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<List<PostDto>> searchPosts(@PathVariable String keyword) {
        List<PostDto> posts = this.postService.searchPosts(keyword);
        return new ResponseEntity<>(posts,HttpStatus.OK);   
    }

    @PostMapping("posts/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadImage(@RequestParam("image") MultipartFile image, @PathVariable Integer postId) throws IOException {
        String path = AppConstants.UPLOAD_DIR;
        PostDto postDto = this.postService.getPostById(postId);
        String fileName = this.fileService.uploadImage(path, image);
        
        postDto.setImageName(fileName);
        PostDto updatedPost = this.postService.updatePost(postDto, postId);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);

    }

    @GetMapping(value = "/posts/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable(value = "imageName") String imageName, HttpServletResponse response) throws IOException {
        String path = AppConstants.UPLOAD_DIR;
        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());

    }

}
