package com.tp.lingoRingo.rest;

import com.tp.lingoRingo.services.ServiceMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageRestController {
    @Autowired
    ServiceMessage serviceMessage;
    @PostMapping("/message/likesMessage")
    public String likeMessage(@Param("likes") Integer likes, @Param("id") Integer id){
        likes++;
        return serviceMessage.updateLikes(id,likes)?"OK":"Doublon";
    }

    @PostMapping("/message/dislikesMessage")
    public String dislikeMessage(@RequestParam("dislikes") Integer dislikes, @RequestParam("id") Integer id){
        dislikes ++;
        return serviceMessage.updateDislikes(id,dislikes)?"OK":"Doublon";
    }
}

