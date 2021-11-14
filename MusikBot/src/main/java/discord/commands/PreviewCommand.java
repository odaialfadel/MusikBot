package discord.commands;

import java.awt.Color;
import java.util.Random;

import com.afrunt.randomjoke.Jokes;

import discord.commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class PreviewCommand implements ServerCommand {
	
	Jokes jokes = new Jokes().withDefaultSuppliers();
	Random rand = new Random();
	
	@Override
	public void preformCommand(Member member, TextChannel channel, Message message) {
		
		Color color = new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255));
		
		String mess = message.getContentRaw().substring(9);
		EmbedBuilder builder = new EmbedBuilder();
		
		builder.setDescription(mess.toUpperCase());
		builder.setColor(color);
		builder.setFooter("Powered by Odai.");
		message.delete().queue();
		channel.sendMessage(builder.build()).queue();

		
	}
	
	

}
