package discord.musik.commands;

import java.awt.Color;
import java.util.Random;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import discord.Launch;
import discord.commands.types.ServerCommand;
import discord.musik.AudioLoadResult;
import discord.musik.MusicController;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class PlayCommand implements ServerCommand{
	

	@Override
	public void preformCommand(Member member, TextChannel channel, Message message) {
		
		Random rand  = new Random();
		Color color = new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255));
		
		String[] args = message.getContentDisplay().split("\\s+");
		
		if(args.length > 1) {
			GuildVoiceState state;
			if((state = member.getVoiceState()) !=null) {
				VoiceChannel vc;
				if((vc = state.getChannel()) !=null) {
					MusicController controller = Launch.INSTANCE.playerManager.getController(vc.getGuild().getIdLong());
					AudioPlayerManager apm = Launch.INSTANCE.audioPlayerManager;
					AudioManager manager = vc.getGuild().getAudioManager();
					manager.openAudioConnection(vc);
					
					
					StringBuilder strBuilder = new StringBuilder();
					for(int i = 1; i < args.length; i++) {
						strBuilder.append(args[i]+" ");
					}
					
					String url = strBuilder.toString().trim();
					if(!url.startsWith("http")) {
						url = "ytsearch: " + url;
					}
					
					apm.loadItem(url, new AudioLoadResult(controller,url));
					
				}else {
					
					EmbedBuilder builder = new EmbedBuilder();
					builder.setDescription("Huch? Du bist wohl in keinem VoiceChannel!");
					builder.setColor(color);
					channel.sendMessage(builder.build()).queue();
				}
			}else {
				
				EmbedBuilder builder = new EmbedBuilder();
				builder.setDescription("Huch? Du bist wohl in keinem VoiceChannel!");
				builder.setColor(color);
				channel.sendMessage(builder.build()).queue();
			}
			
			
		}else {
			
			EmbedBuilder builder = new EmbedBuilder();
			builder.setDescription("Bitte benutze -play <url/search query>");
			builder.setColor(color);
			channel.sendMessage(builder.build()).queue();
		}
		
	}

}
