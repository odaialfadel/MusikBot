package discord.commands;

import java.awt.Color;
import java.time.OffsetDateTime;
import java.util.Random;


import discord.commands.types.ServerCommand;
import discord.manage.CONFIG;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class ChangePrefixCommand implements ServerCommand{

	Random rand = new Random();
	Color color = new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255));	
	CONFIG TOKEN = new CONFIG();
	
	@Override
	public void preformCommand(Member member, TextChannel channel, Message message) {
		
		//-prefix !
		
		
		String[] args = message.getContentDisplay().split("\\s+");
		if(args.length > 0) {
			try {
				
				TOKEN.changePrefix(args[1]);
				
				EmbedBuilder builder = new EmbedBuilder();
				builder.setDescription("```diff\r\n Prefix wurde von("+args[0].charAt(0)+") auf ("+args[1]+") geandert!\r\n```" +  member.getUser().getAsMention());
				builder.setColor(color);
				builder.setFooter("Powered by Odai.");
				builder.setTimestamp(OffsetDateTime.now());
				message.delete().queue();
				channel.sendMessage(builder.build()).queue();
				message.delete().queue();
				return;
			}catch(NumberFormatException e){
				e.printStackTrace();
			}
		}
		
		
	}

}
