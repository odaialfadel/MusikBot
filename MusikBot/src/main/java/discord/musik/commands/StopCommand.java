package discord.musik.commands;

import java.util.concurrent.TimeUnit;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import discord.Launch;
import discord.commands.types.ServerCommand;
import discord.musik.MusicController;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class StopCommand implements ServerCommand{

	@Override
	public void preformCommand(Member member, TextChannel channel, Message message) {
		
		message.delete().queueAfter(3, TimeUnit.SECONDS);
		
		GuildVoiceState state;
		if((state = member.getVoiceState()) !=null) {
			VoiceChannel vc;
			if((vc = state.getChannel()) !=null) {
				MusicController controller = Launch.INSTANCE.playerManager.getController(vc.getGuild().getIdLong());
				AudioManager manager = vc.getGuild().getAudioManager();
				AudioPlayer player = controller.getPlayer();
				
				player.stopTrack();
				controller.getQueue().getQueuelist().clear();
				manager.closeAudioConnection();
				message.addReaction("U+1F44C").queue();
				
			}
		}
	}
}
