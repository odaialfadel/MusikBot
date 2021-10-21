package discord.commands;

import java.awt.Color;
import java.util.Random;

import discord.commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class RoleCreation implements ServerCommand{

	@Override
	public void preformCommand(Member member, TextChannel channel, Message message) {
		
		Guild guild = channel.getGuild();
		
		String[] args = message.getContentDisplay().split("\\s+");
		int length = args.length;
		
		if(length < 1) {
			StringBuilder builder = new StringBuilder();
			
			if(args[length-1].startsWith("#") && length > 2) {
				for(int i = 0; i < length - 1; i++) {
					builder.append(args[i]+" ");
					String hexCode = args[length-1];
					
					String roleName = builder.toString().trim();
					channel.sendTyping().queue();
					Color color = Color.decode(hexCode);
					guild.createRole().queue(role -> {
						role.getManager().setName(roleName).setColor(color).setPermissions(Permission.MESSAGE_READ, Permission.MESSAGE_HISTORY, Permission.VOICE_SPEAK).queue();
						EmbedBuilder embed = new EmbedBuilder();
						embed.setDescription("Role "+ roleName + " erstellt Horraaay!");
						embed.setColor(color);
						channel.sendMessage(embed.build());
					});
					
				}
			}else {
				for(int i = 0; i < length; i++) {
					builder.append(args[i]+" ");
					
					String roleName = builder.toString().trim();
					channel.sendTyping().queue();
					Random rand  = new Random();
					
				//	Color color = new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255));
					Color color = new Color(255,255,255);
					guild.createRole().queue(role -> {
						role.getManager().setName(roleName).setColor(color).setPermissions(Permission.MESSAGE_READ, Permission.MESSAGE_HISTORY, Permission.VOICE_SPEAK).queue();
						EmbedBuilder embed = new EmbedBuilder();
						embed.setDescription("Role "+ roleName + " erstellt Horraaay!");
						embed.setColor(color);
						channel.sendMessage(embed.build());
					});
				}
			}
		}else {
			EmbedBuilder builder = new EmbedBuilder();
			builder.setDescription("Bist du doof? -createrole <Name>Farbe>");
			channel.sendMessage(builder.build());
		}
		
		
	}

}
