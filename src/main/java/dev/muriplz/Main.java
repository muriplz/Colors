package dev.muriplz;

import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import dev.muriplz.listener.PlayerReadyListener;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;


public class Main extends JavaPlugin {

    public static final String MOD_ID = "examplemod";

    public Main(JavaPluginInit init) {
        super(init);

        MixinBootstrap.init();
        Mixins.addConfiguration("mixin.examplemod.json");
    }

    @Override
    protected void setup() {

        // ######## Events ######## //
        this.getEventRegistry().registerGlobal(PlayerReadyEvent.class, PlayerReadyListener::onPlayerReady);

    }
}
