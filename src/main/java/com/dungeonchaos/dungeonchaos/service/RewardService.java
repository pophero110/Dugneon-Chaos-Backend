package com.dungeonchaos.dungeonchaos.service;


import com.dungeonchaos.dungeonchaos.exception.InformationNotFoundException;
import com.dungeonchaos.dungeonchaos.model.Item.Item;
import com.dungeonchaos.dungeonchaos.model.Item.ItemType;
import com.dungeonchaos.dungeonchaos.model.Opponent;
import com.dungeonchaos.dungeonchaos.model.Player;
import com.dungeonchaos.dungeonchaos.model.Reward.Reward;
import com.dungeonchaos.dungeonchaos.model.Reward.RewardType;
import com.dungeonchaos.dungeonchaos.repository.ItemRepository;
import com.dungeonchaos.dungeonchaos.repository.PlayerRepository;
import com.dungeonchaos.dungeonchaos.repository.RewardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class RewardService {

    private final RewardRepository rewardRepository;
    private final ItemRepository itemRepository;
    private final PlayerRepository playerRepository;

    RewardService(RewardRepository rewardRepository, ItemRepository itemRepository, PlayerRepository playerRepository) {
        this.rewardRepository = rewardRepository;
        this.itemRepository = itemRepository;
        this.playerRepository = playerRepository;
    }


    /**
     * Creates a reward of the specified reward type for the given player and opponent.
     * @param rewardType The type of the reward.
     * @param player The player who will receive the reward.
     * @param opponent The opponent defeated to earn the reward.
     * @return The created reward.
     */
    public Reward createReward(RewardType rewardType, Player player, Opponent opponent) {
        Reward reward = new Reward();
        reward.setPlayer(player);
        if (opponent.getName().equals("Goblin")) {
            reward.setRewardType(rewardType);
            reward.setDescription("Defeat a Goblin and Earn 10 gold coins");
            player.addGoldCoin(10);
        }
        return rewardRepository.save(reward);
    }

    /**
     * Creates a item reward for the player with the given ID.
     * @param playerId The ID of the player.
     * @return The created reward.
     * @throws InformationNotFoundException if the player is not found.
     */
    public Reward createItemReward(Long playerId) {
        Player player = playerRepository.findById(playerId).orElseThrow(() -> new InformationNotFoundException("Player is not found with id " + playerId));
        Reward reward = new Reward();
        List<Item> items = itemRepository.findAll();
        Random random = new Random();
        Item randomItem = items.get(random.nextInt(items.size()));
        reward.setDescription("Open a treasure chest and yield " + randomItem.getName());
        reward.setRewardItemId(randomItem.getId());
        reward.setRewardType(RewardType.ITEM);
        reward.setPlayer(player);

        return rewardRepository.save(reward);
    }
}
