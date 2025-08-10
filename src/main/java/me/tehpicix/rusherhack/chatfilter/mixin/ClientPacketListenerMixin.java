package me.tehpicix.rusherhack.chatfilter.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.tehpicix.rusherhack.chatfilter.Filter;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundDisguisedChatPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerChatPacket;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {

    @Inject(method = "handleChat", at = @At("HEAD"), cancellable = true)
    private void onHandleChat(ClientboundPlayerChatPacket packet, CallbackInfo ci) {
        if(Filter.match(packet.body().content())) {
            ci.cancel();
            return;
        }
    }

    @Inject(method = "handleSystemChat", at = @At("HEAD"), cancellable = true)
    private void onSystemChat(ClientboundSystemChatPacket packet, CallbackInfo ci) {
        if(Filter.match(packet.content().getString())) {
            ci.cancel();
            return;
        }
    }

    @Inject(method = "handleDisguisedChat", at = @At("HEAD"), cancellable = true)
    private void onDisguisedChat(ClientboundDisguisedChatPacket packet, CallbackInfo ci) {
        if(Filter.match(packet.message().getString())) {
            ci.cancel();
            return;
        }
    }

}
