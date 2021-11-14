package discord.commands;

import java.awt.Color;
import java.time.OffsetDateTime;
import java.util.Random;

import com.afrunt.randomjoke.Jokes;

import discord.commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class JokeCommand implements ServerCommand{
	
	Jokes jokes = new Jokes().withDefaultSuppliers();
	Random rand = new Random();
	@Override
	public void preformCommand(Member member, TextChannel channel, Message message) {
		
		Color color = new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255));
		
		
		EmbedBuilder builder = new EmbedBuilder();
		
		
		//builder.setDescription(jokes.randomJoke() + "\n" +  member.getUser().getAsMention());
		jokes.randomJoke().ifPresent(joke -> builder.setDescription(joke.getText().toString()+ "\n"));
		builder.setColor(color);
		builder.setFooter("Powered by Odai.");
		builder.setTimestamp(OffsetDateTime.now());
		message.delete().queue();
		channel.sendMessage(builder.build()).queue();
	}

}
