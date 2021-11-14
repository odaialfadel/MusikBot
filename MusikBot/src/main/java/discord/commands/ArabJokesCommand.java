package discord.commands;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Random;

import discord.commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class ArabJokesCommand implements ServerCommand{
	
	HashMap<Integer,String> jokes = new HashMap<Integer,String>();
	HashMap<Integer,String> toldJokes = new HashMap<Integer,String>();
	
	File file = new File("jokes.txt");
	
	Random rand = new Random();
	Color color = new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255));
	@Override
	public void preformCommand(Member member, TextChannel channel, Message message) {
		listOfJokes();
		EmbedBuilder builder = new EmbedBuilder();

		int id = rand.nextInt(jokes.size());
		String str=jokes.get(id);
		toldJokes.put(id, jokes.get(id));
		jokes.remove(id, str);
		
		
		
		builder.setDescription(str);
		builder.setColor(color);
		builder.setFooter("Powered by Odai.");
		builder.setTimestamp(OffsetDateTime.now());
		message.delete().queue();
		channel.sendMessage(builder.build()).queue();
		
	}
	
	
	public HashMap<Integer,String> listOfJokes() {
		try {
			//um die arabische Buchstaben zu lesen
			Reader reader = new InputStreamReader(new FileInputStream(file), "utf-8");
			BufferedReader br = new BufferedReader(reader);
	        String st;
	        int id = 0;
	        //um alle Witze zu lesen und in einer List zu speichern
	        while((st=br.readLine()) != null){
	            jokes.put(id, st);
	            id++;
	        }
	        br.close();
		} catch (IOException e) {
			System.err.println("Datei existiert nicht");
		}
		return jokes;
	}

}
