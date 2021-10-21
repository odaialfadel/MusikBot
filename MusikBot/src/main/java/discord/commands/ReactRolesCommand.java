package discord.commands;

import java.util.List;

import discord.commands.types.ServerCommand;
import discord.manage.LiteSQL;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;

public class ReactRolesCommand implements ServerCommand {

	@Override
	public void preformCommand(Member member, TextChannel channel, Message message) {
		message.delete().queue();

		// args[0] args[1] args[2] args[3] args[4]
		// args[0] args[1] args[2] :ok: @Rolle

		String[] args = message.getContentDisplay().split("\\s+");
		

			List<TextChannel> channels = message.getMentionedChannels();
			List<Role> roles = message.getMentionedRoles();
			List<Emote> emotes = message.getEmotes();
			
			
			if (!channels.isEmpty() && !roles.isEmpty()) {
				TextChannel tc = channels.get(0);
				Role role = roles.get(0);
				String messageIDString = args[2];

				try {
					
					long messageID = Long.parseLong(messageIDString);
					
					if(!emotes.isEmpty()) {
						Emote emote = emotes.get(0);					

					tc.addReactionById(messageID, emote).queue();
					
					LiteSQL.onUpdate(
							"INSERT INTO reactroles(guildid, channelid, messageid, emote, rollenid) VALUES("
							+ channel.getGuild().getIdLong() + "," + tc.getIdLong() + "," + message.getIdLong() + ",'"
							+ emote + "'," + role.getIdLong() + ")");
					}else {
						String emote = args[3];
						tc.addReactionById(messageID, emote).queue();
						LiteSQL.onUpdate("INSERT INTO reactroles(guildid, channelid, messageid, emote, rollenid) VALUES("
								+ channel.getGuild().getIdLong() + "," + tc.getIdLong() + "," + message.getIdLong() + ",'"
								+ emote + "'," + role.getIdLong() + ")");
					}
					
				} catch (NumberFormatException e) {

				}
			
		} else {
			channel.sendMessage("Bitte benutze `-reactrole #channel messageID :emote: @Rolle`").queue();
		}
	}

}
