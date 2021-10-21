package discord.listener;

import discord.Launch;
import discord.games.CounterGame;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandListner extends ListenerAdapter {

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		String message = event.getMessage().getContentDisplay();

		if (event.isFromType(ChannelType.TEXT)) {
			TextChannel channel = event.getTextChannel();

			// Prefix arg0 arg1 arg2
			if (message.startsWith("-")) {
				String[] args = message.substring(1).split("\\s+");
				if (args.length > 0) {
					if (!Launch.INSTANCE.getCmdMan().preform(args[0], event.getMember(), channel, event.getMessage())) {
						channel.sendMessage("```diff\r\n- Command is not valid!!!\r\n```").queue();
					}
				}
			}
			if(channel.getIdLong()==899709642601549874l) {
				CounterGame.countUpdate(channel, event.getMessage());
			}
		}
	}

}
