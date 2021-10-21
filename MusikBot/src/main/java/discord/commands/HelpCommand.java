package discord.commands;

import java.time.OffsetDateTime;
import java.util.concurrent.TimeUnit;

import discord.commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class HelpCommand implements ServerCommand{

	@Override
	public void preformCommand(Member member, TextChannel channel, Message message) {
		
//		message.delete().complete();
		message.delete().queueAfter(2, TimeUnit.SECONDS);
		
		EmbedBuilder builder = new EmbedBuilder();
		builder.setDescription("**Hilfe zum Bot**\n"+"*was brauchst du denn für hilfe, wir haben doch noch gar nichts xD*\n"
								+"**-clear** löcht die letzten x Nachrichten." + " \nBitteschon " +  member.getUser().getAsMention());
		builder.setColor(0x23cba7);
		builder.setFooter("Powered by Odai.");
		builder.setTimestamp(OffsetDateTime.now());
		
		channel.sendMessage(builder.build()).queue();
		
//		member.getUser().openPrivateChannel().queue((ch) -> {
//			ch.sendMessage(builder.build()).queue();
//		});
		
	}

}
