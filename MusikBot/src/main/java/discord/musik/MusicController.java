package discord.musik;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import discord.Launch;
import net.dv8tion.jda.api.entities.Guild;

public class MusicController {
	
	
	private Guild guild;
	private AudioPlayer player;
	
	public MusicController(Guild guild) {
		this.guild = guild;
		this.player = Launch.INSTANCE.audioPlayerManager.createPlayer();
		
		this.guild.getAudioManager().setSendingHandler(new AudioPlayerSendHandler(player));
		this.player.setVolume(30);
	}
	public Guild getGuild() {
		return guild;
	}
	
	public AudioPlayer getPlayer() {
		return player;
	}
	
	
	
	
	
	
}
