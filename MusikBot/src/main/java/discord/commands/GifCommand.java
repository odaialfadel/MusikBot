package discord.commands;

import java.awt.Color;
import java.time.OffsetDateTime;
import java.util.Random;

import at.mukprojects.giphy4j.Giphy;
import at.mukprojects.giphy4j.entity.search.SearchFeed;
import discord.commands.types.ServerCommand;
import discord.manage.CONFIG;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class GifCommand implements ServerCommand {
	CONFIG TOKEN = new CONFIG();
	
	Random rand = new Random();
	Color color = new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255));
	
	@Override
	public void preformCommand(Member member, TextChannel channel, Message message) {
			TOKEN.readProperty();
			String[] args = message.getContentDisplay().split("\\s+");
			if(args.length > 0) {
				try {
					
					Giphy giphy = new Giphy(TOKEN.getAPI());
					SearchFeed feed = giphy.search(args[1], rand.nextInt(99), 0);
					message.delete().queue();
					channel.sendMessage(feed.getDataList().get(rand.nextInt(feed.getDataList().size())).getImages().getOriginal().getUrl()).queue();
				}catch(Exception e) {
					EmbedBuilder builder = new EmbedBuilder();
					builder.setDescription("Es gibt keine Gif oder Sticker mit dieser Name: ("+args[1]+")!!");
					builder.setColor(color);
					builder.setFooter("Powered by Odai.");
					builder.setTimestamp(OffsetDateTime.now());
					channel.sendMessage(builder.build()).queue();
				}
			}
			
			
			
	}

}
