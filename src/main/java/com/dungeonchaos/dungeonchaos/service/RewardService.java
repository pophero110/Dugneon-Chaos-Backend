package com.dungeonchaos.dungeonchaos.service;


import com.dungeonchaos.dungeonchaos.model.Opponent;
import com.dungeonchaos.dungeonchaos.model.Player;
import com.dungeonchaos.dungeonchaos.model.Reward.Reward;
import com.dungeonchaos.dungeonchaos.model.Reward.RewardType;
import com.dungeonchaos.dungeonchaos.repository.RewardRepository;
import org.springframework.stereotype.Service;

@Service
public class RewardService {

    private final RewardRepository rewardRepository;

    RewardService(RewardRepository rewardRepository) {
        this.rewardRepository = rewardRepository;
    }


    public Reward createReward(Player player, Opponent opponent) {
        Reward reward = new Reward();
        reward.setPlayer(player);
        if (opponent.getName().equals("Goblin")) {
            reward.setRewardType(RewardType.GOLD_COIN);
            reward.setDescription("Defeat a Goblin and Earn 10 gold coins");
            player.addGoldCoin(10);
        }
        return rewardRepository.save(reward);
    }
}
