package discord.commands;

import java.util.ArrayList;
import java.util.List;

import discord.commands.types.ServerCommand;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class ReactCommand implements ServerCommand{

	@Override
	public void preformCommand(Member member, TextChannel channel, Message message) {
		
		//args[0] args[1] args[2] ..... emojis
		
		String[] args = message.getContentDisplay().split("\\s+");
		List<TextChannel> channels = message.getMentionedChannels();
		List<Emote> emotes = message.getEmotes();
		
		
		
		if(!channels.isEmpty()) {
			TextChannel tc = message.getMentionedChannels().get(0);
			String messageIDString = args[2];
			
			try {
				long messageID = Long.parseLong(messageIDString);
				List<String> customemotes = new ArrayList<>();
				
				for(Emote emote : emotes) {
					tc.addReactionById(messageID, emote).queue();
					customemotes.add(":" + emote.getName() + ":");
				}
				
				for(int i = 3; i < args.length; i++) {
					String emote = args[i];
					if(!customemotes.contains(emote)) {
						tc.addReactionById(messageID, args[i]).queue();
					}
				}
				
			} catch (NumberFormatException e) { }
		}
		message.delete().queue();
	}

}
