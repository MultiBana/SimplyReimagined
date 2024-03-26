package com.multibana.simplyreimagined.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    // remove the attack speed tooltip text
    // if attack speed is "9" that means it's a trident and add hardcoded "8 Ranged Damage" row (non-translatable rn)
    @ModifyReturnValue(method = "getTooltip", at=@At("RETURN"))
    private List<Text> showTridentRangedDamageTooltip(List<Text> original){
        int to_remove = -1;
        for(int i = 0; i < original.size(); i++){
            if(original.get(i).toString().contains("attack_speed")){
                to_remove = i;
                // TODO - dont manipulate/add text if the client is not in english (add to if "&& language.isEnglish")
                if(original.get(i).toString().contains("args=[9")){
                    String prevString = original.get(i-1).getString();
                    float meleeDamage = Float.parseFloat(prevString.split(" ")[1]);
                    float rangedDamage = 8.0F + (meleeDamage - 6.0F);
                    String rangedDamageStr = Float.toString(rangedDamage);
                    if((float)(int)rangedDamage == rangedDamage){
                        rangedDamageStr = Integer.toString((int)rangedDamage);
                    }
                    original.add(ScreenTexts.space().append(rangedDamageStr + " Ranged Damage").formatted(Formatting.DARK_GREEN));
                }
            }
        }
        if(to_remove > -1){
            original.remove(to_remove);
        }
        return original;
    }
}
