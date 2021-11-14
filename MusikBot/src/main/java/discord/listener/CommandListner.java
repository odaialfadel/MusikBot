package discord.listener;

import discord.Launch;
import discord.games.CounterGame;
import discord.manage.CONFIG;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandListner extends ListenerAdapter {

	CONFIG TOKEN = new CONFIG();
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		String message = event.getMessage().getContentDisplay();

		if (event.isFromType(ChannelType.TEXT)) {
			TextChannel channel = event.getTextChannel();
			
			String prefix = "";
			TOKEN.readProperty();
			if(TOKEN.getPrefix()!=null) {
				prefix = TOKEN.getPrefix();
			}else {
				prefix = "-";
			}
			
			/*
			 * Unsere Prefix 
			 * Prefix arg0 arg1 arg2
			 */
			if (message.startsWith(prefix)) {
				String[] args = message.substring(1).split("\\s+");
				if (args.length > 0) {
					if (!Launch.INSTANCE.getCmdMan().preform(args[0], event.getMember(), channel, event.getMessage())) {
						channel.sendMessage("```diff\r\n- Kommando ist nicht valid!!!\r\n```").queue();
					}
				}
			}
			if(channel.getIdLong()==899709642601549874l) {
				CounterGame.countUpdate(channel, event.getMessage());
			}
		}
	}

}
