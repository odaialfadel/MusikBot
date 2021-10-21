package discord.commands;

import discord.commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class PreviewCommand implements ServerCommand {

	@Override
	public void preformCommand(Member member, TextChannel channel, Message message) {
		String mess = message.getContentRaw().substring(9);
		EmbedBuilder builder = new EmbedBuilder();
		
		builder.setDescription(mess);
		builder.setColor(0xeb974e);
		builder.setFooter("Powered by Dodo!");
		message.delete().queue();
		channel.sendMessage(builder.build()).queue();
		
	}
	
	

}
