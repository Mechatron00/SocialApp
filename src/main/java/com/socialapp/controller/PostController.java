package com.socialapp.controller;

import com.socialapp.models.Posts;
import com.socialapp.repository.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:4200/")
public class PostController
{
   @Autowired private PostRepo repo;


    @PostMapping("/post")
    public ResponseEntity<Posts> createPost(@RequestBody Posts post)
    {
        Posts newPost = new Posts();
        newPost.setUserId(post.getUserId());
        newPost.setTitle(post.getTitle());
        newPost.setBody(post.getBody());
         Posts res = repo.save(newPost);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping ("/post/{id}")
    public ResponseEntity<Posts> getPostById(@PathVariable("id") int id)
    {
        Optional<Posts> getPost = repo.findById(id);
        Posts post = new Posts();
        if (getPost.isPresent())
        {
             post = getPost.get();
        }

        return new ResponseEntity<>(post,HttpStatus.OK);
    }

    @GetMapping("/allposts")
    public ResponseEntity<List<Posts>> getAllPosts()
    {
        try {
            List<Posts> postsList = new ArrayList<>();
            postsList = repo.findAll();
            if (postsList != null)
            {
                return new ResponseEntity<>(postsList,HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(new ArrayList<Posts>(),HttpStatus.NO_CONTENT);
            }
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(new ArrayList<Posts>(),HttpStatus.NO_CONTENT);

        }
    }
}
