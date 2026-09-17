package com.nyfaria.combat_oddities.mixin;

import com.nyfaria.combat_oddities.item.AbilitySwordItem;
import com.nyfaria.combat_oddities.item.SwordAbility;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class MixinPlayer {

    @Inject(method = "interactOn", at = @At("HEAD"), cancellable = true)
    private void combatOddities$swordInteract(Entity target, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        Player player = (Player) (Object) this;
        if (player.isSpectator() || !(target instanceof LivingEntity living)) {
            return;
        }
        ItemStack stack = player.getItemInHand(hand);
        if (!(stack.getItem() instanceof AbilitySwordItem sword)) {
            return;
        }
        for (SwordAbility ability : sword.getAbilities()) {
            InteractionResult result = ability.onInteractEntity(stack, player, living, hand);
            if (result != InteractionResult.PASS) {
                cir.setReturnValue(result);
                return;
            }
        }
    }
}
