package com.dungeonchaos.dungeonchaos.controller;


import com.dungeonchaos.dungeonchaos.model.Reward.Reward;
import com.dungeonchaos.dungeonchaos.model.Reward.RewardType;
import com.dungeonchaos.dungeonchaos.request.RewardRequest;
import com.dungeonchaos.dungeonchaos.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path= "/api/rewards")
public class RewardController {

    private RewardService rewardService;

    @Autowired
    RewardController(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    @PostMapping(path = "")
    public ResponseEntity<Reward> createReward(@RequestBody RewardRequest rewardRequest) {
        Reward reward = rewardService.createItemReward(rewardRequest.getPlayerId());
        return new ResponseEntity<>(reward, HttpStatus.CREATED);
    }
}
