package discord.commands;

import java.text.DecimalFormat;
import java.util.concurrent.ConcurrentHashMap;

import discord.commands.types.ServerCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class HugCommand implements ServerCommand {

	private ConcurrentHashMap<Long, Long> timeStamps;

	public HugCommand() {
		this.timeStamps = new ConcurrentHashMap<>();
	}

	@Override
	public void preformCommand(Member member, TextChannel channel, Message message) {
		message.delete().queue();
		long id = member.getIdLong();
		if (timeStamps.containsKey(id)) {
			long time = timeStamps.get(id);

			if ((System.currentTimeMillis() - time) >= 3000) {
				this.timeStamps.put(id, System.currentTimeMillis());
				send(member, channel, message);
			} else {
				DecimalFormat df = new DecimalFormat("0.00");
				channel.sendMessage("Du musst noch "
						+ df.format(3000.0d - (System.currentTimeMillis() - time) / 1000.0d) + "Sekunden warten")
						.queue();
			}
		} else {
			this.timeStamps.put(id, System.currentTimeMillis());
			send(member, channel, message);
		}
	}

	public void send(Member member, TextChannel channel, Message message) {		
		channel.sendMessage(member.getAsMention() + " umarmt sich selbst.").queue();
	}

}
