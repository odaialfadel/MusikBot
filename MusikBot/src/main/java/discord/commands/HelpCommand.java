package discord.commands;

import java.time.OffsetDateTime;

import discord.commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class HelpCommand implements ServerCommand{

	@Override
	public void preformCommand(Member member, TextChannel channel, Message message) {
		

		
		EmbedBuilder builder = new EmbedBuilder();
		builder.setDescription("**Hilfe zum Bot**\n"+"*was brauchst du denn f?r hilfe, wir haben doch noch gar nichts xD*\n"
								+"**-clear** l?cht die letzten x Nachrichten." + " \nBitteschon " +  member.getUser().getAsMention());
		builder.setColor(0x23cba7);
		builder.setFooter("Powered by Odai.");
		builder.setTimestamp(OffsetDateTime.now());
		message.delete().complete();
		channel.sendMessage(builder.build()).queue();
		

	}

}
