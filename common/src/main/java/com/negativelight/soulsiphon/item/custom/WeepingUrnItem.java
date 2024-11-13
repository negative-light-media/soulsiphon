package com.negativelight.soulsiphon.item.custom;

import com.negativelight.soulsiphon.Constants;
import com.negativelight.soulsiphon.item.ModItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class WeepingUrnItem extends Item {


    public WeepingUrnItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(ItemStack pStack, Player pPlayer, LivingEntity pInteractionTarget, InteractionHand pUsedHand) {
        //Constants.LOG.info("Interacting with Entity");
        if (!pStack.is(ModItems.WEEPING_URN_FULL.get())) {
            return super.interactLivingEntity(pStack, pPlayer, pInteractionTarget, pUsedHand);
        }

        //We have a full Weeping Urn Now
        if (pInteractionTarget instanceof WitherBoss) {
            pInteractionTarget.addEffect(
                    new MobEffectInstance(
                            MobEffects.HEAL,
                            1,
                            5,
                            true,
                            false)
            );
            pInteractionTarget.heal(10);
            //Constants.LOG.info("Effected Wither");
            pPlayer.addItem(new ItemStack(ModItems.WEEPING_URN.get(), 1));
            pStack.consume(1, pPlayer);
            return InteractionResult.SUCCESS;
        }

        if (pInteractionTarget instanceof Zombie z) {
            Constants.LOG.info("This is a zombie");
            Villager villager = z.convertTo(EntityType.VILLAGER, false);
           villager.setVillagerData(new VillagerData(VillagerType.PLAINS, VillagerProfession.NITWIT,1));
           villager.setBaby(z.isBaby());
           pPlayer.addItem(new ItemStack(ModItems.WEEPING_URN.get(), 1));
           pStack.consume(1, pPlayer);
           return InteractionResult.SUCCESS;
        }


        return super.interactLivingEntity(pStack, pPlayer, pInteractionTarget, pUsedHand);
    }
}
