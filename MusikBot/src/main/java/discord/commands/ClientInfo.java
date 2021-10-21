package discord.commands;

import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

import discord.commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;

public class ClientInfo implements ServerCommand {

	@Override
	public void preformCommand(Member member, TextChannel channel, Message message) {
		message.delete().queue();

		channel.sendTyping().queue();
		List<Member> ment = message.getMentionedMembers();

		if (ment.size() > 0) {
			for (Member m : ment) {
				onInfo(member, m, channel);
			}
		}

	}

	private void onInfo(Member requester, Member m, TextChannel channel) {

		EmbedBuilder builder = new EmbedBuilder();
		builder.setFooter("Requested by " + requester.getEffectiveName());
		builder.setColor(0x23cba7);
		builder.setTimestamp(OffsetDateTime.now());
		builder.setThumbnail(m.getUser().getEffectiveAvatarUrl());

		StringBuilder strBuilder = new StringBuilder();

		formatDate(parseDate(m.getTimeCreated().toString()), "yyyy-MM-dd hh:mm:ss");
		parseDate(m.getTimeCreated().toString()).toString();
		strBuilder.append("User " + m.getAsMention() + "\n");
		strBuilder.append("ClientID: " + m.getId() + "\n");
		strBuilder.append(
				"Joined:  " + formatDate(parseDate(m.getTimeJoined().toString()), "yyyy-MM-dd hh:mm:ss") + "\n");
		strBuilder.append(
				"Created: " + formatDate(parseDate(m.getTimeCreated().toString()), "yyyy-MM-dd hh:mm:ss") + "\n");

		strBuilder.append(" \n *Rollen:* \n");

		StringBuilder roleBuilder = new StringBuilder();
		for (Role role : m.getRoles()) {
			roleBuilder.append(role.getAsMention() + " ");
		}
		strBuilder.append(roleBuilder.toString().trim() + "\n");

		builder.setDescription(strBuilder);
		channel.sendMessage(builder.build()).complete().delete().queueAfter(50, TimeUnit.SECONDS);
	}

	public Date parseDate(String inputDate) {

		Date outputDate = null;
		String[] possibleDateFormats = { "yyyy.MM.dd G 'at' HH:mm:ss z", "EEE, MMM d, ''yy", "h:mm a",
				"hh 'o''clock' a, zzzz", "K:mm a, z", "yyyyy.MMMMM.dd GGG hh:mm aaa", "EEE, d MMM yyyy HH:mm:ss Z",
				"yyMMddHHmmssZ", "yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", "YYYY-'W'ww-u",
				"EEE, dd MMM yyyy HH:mm:ss z", "EEE, dd MMM yyyy HH:mm zzzz", "yyyy-MM-dd'T'HH:mm:ssZ",
				"yyyy-MM-dd'T'HH:mm:ss.SSSzzzz", "yyyy-MM-dd'T'HH:mm:sszzzz", "yyyy-MM-dd'T'HH:mm:ss z",
				"yyyy-MM-dd'T'HH:mm:ssz", "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd'T'HHmmss.SSSz", "yyyy-MM-dd", "yyyyMMdd",
				"dd/MM/yy", "dd/MM/yyyy" };
		try {
			outputDate = DateUtils.parseDate(inputDate, possibleDateFormats);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return outputDate;
	}

	public String formatDate(Date date, String requiredDateFormat) {
		SimpleDateFormat df = new SimpleDateFormat(requiredDateFormat);
		String outputDateFormatted = df.format(date);
		return outputDateFormatted;
	}

}
