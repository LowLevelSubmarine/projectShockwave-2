package commands.music_handling;

import statics.Statics;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.managers.AudioManager;

public class IdleChecker implements Runnable {

    private Guild guild;

    public IdleChecker(Guild guild) {
        this.guild = guild;
    }

    @Override
    public void run() {
        //Check if there is a need for setting a timeout
        if (hasListeners()) {
            return;
        }
        try {
            //Wait for defined idle timout
            Thread.sleep(Statics.PLAYINGIDLETIMEOUTSEC * 1000);
            //Check if there is still no one listening to the bot
            //and afterwards if a guildplayer exists for that guild
            if (!hasListeners() && GuildPlayerManager.has(this.guild)) {
                //Get existing player
                GuildPlayer player = GuildPlayerManager.get(this.guild);
                //Let GuildPlayer stop if it is playing
                if (player.isPlaying()) {
                    player.stop();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean hasListeners() {
        AudioManager audioManager = this.guild.getAudioManager();
        return audioManager.isConnected() && audioManager.getConnectedChannel().getMembers().size() > 1;
    }
}
