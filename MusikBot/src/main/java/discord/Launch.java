package discord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.security.auth.login.LoginException;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;

import discord.commands.StatschannelCommand;
import discord.listener.CommandListner;
import discord.listener.JoinListener;
import discord.listener.ReactionListener;
import discord.listener.VoiceListener;
import discord.manage.CommandManager;
import discord.manage.LiteSQL;
import discord.manage.SQLManager;
import discord.musik.PlayerManager;
//import discord.listener.VoiceListener;
//import net.dv8tion.jda.api.JDA;
//import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

public class Launch {

	public static Launch INSTANCE;
	
	private CommandManager cmdMan;	
	public ShardManager shardMan;
	private Thread loop;
	public AudioPlayerManager audioPlayerManager;
	public PlayerManager playerManager;
	

	public static void main(String[] args) {
		try {
			new Launch();
		} catch (LoginException e) {
			e.printStackTrace();
		}

	}

	/*
	 * Using ShardManger
	 */
	public Launch() throws LoginException {
		INSTANCE = this;

		LiteSQL.connect();
		SQLManager.onCreate();

		final String token = "ODk3MTc3MDQ3MDkxMjU3Mzk1.YWR3PA.3-gZrsW1FUjOtN90YdVNrDM5mOE";

		DefaultShardManagerBuilder jdaBuilder = DefaultShardManagerBuilder.createDefault(token);

		jdaBuilder.setActivity(Activity.playing("-" + " is the Current Prefix"));
		jdaBuilder.setStatus(OnlineStatus.DO_NOT_DISTURB);
		
		this.audioPlayerManager = new DefaultAudioPlayerManager();
		this.playerManager = new PlayerManager();
		
		this.cmdMan = new CommandManager();

		// listen to the changes
		jdaBuilder.addEventListeners(new CommandListner());
		
		//Todo move to private VoiceChannel
		//jdaBuilder.addEventListeners(new VoiceListener());
		jdaBuilder.addEventListeners(new ReactionListener());
		jdaBuilder.addEventListeners(new JoinListener());
		shardMan = jdaBuilder.build();

		System.out.println("```diff\r\n+ Bot is Online!\r\n```");
		
		AudioSourceManagers.registerRemoteSources(audioPlayerManager);
		audioPlayerManager.getConfiguration().setFilterHotSwapEnabled(true);
		
		shutdown();
		runLoop();

	}

	public void shutdown() {
		new Thread(() -> {
			String line = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			try {
				while ((line = reader.readLine()) != null) {
					if (line.equalsIgnoreCase("exit")) {
						shutdown = true;
						if (shardMan != null) {
							shardMan.setStatus(OnlineStatus.OFFLINE);
							
							
							
							shardMan.shutdown();
							LiteSQL.disconnect();
							
							System.out.println("```diff\r\n+ Bot is shutting down...\r\n```");

							if (loop != null) {
								loop.interrupt();
							}

							reader.close();
							break;
						}else if(line.equalsIgnoreCase("info")) {
							for(Guild guild : shardMan.getGuilds()) {
								System.out.println(guild.getName() + " " + guild.getIdLong());
							}
							}else {
							System.out.println("```diff\r\n- Use *exit* to shutdown!\r\n```");
						}
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}).start();
	}

	public boolean shutdown = false;
	public boolean hasStarted = false;

	public void runLoop() {
		this.loop = new Thread(() -> {

			Long time = System.currentTimeMillis();
			while (!shutdown) {
				if (System.currentTimeMillis() >= time + 1000) {
					time = System.currentTimeMillis();
					onSecond();
				}
			}

		});
		this.loop.setName("Loop");
		this.loop.start();
	}

	String[] status = new String[] { "Programmieren.", "Discord", "Love", "With Odai.", "Rocket League.",
			"%members online." };
	int[] colors = new int[] {0xff9478, 0xd2527f, 0x00b5cc, 0x19b5fe, 0x2ecc71, 0x23cba7, 0x00e640, 0x8c14fc, 0x9f5afd, 0x663399};
	int next = 30;

	public void onSecond() {
		if (next <= 0) {
			Random rand = new Random();
			int i = rand.nextInt(status.length);
			shardMan.getShards().forEach(jda -> {
				String text = status[i].replaceAll("%members", "" + jda.getUsers().size());
				jda.getPresence().setActivity(Activity.playing(text));
			});
			next = 15;
		} else {
			next--;
		}
	}
//	public void onSecond() {
//		//System.out.println("Next: " + next);
//		
//		if(next%5 == 0) {
//			if(!hasStarted) {
//				hasStarted = true;
//				//StatschannelCommand.onStartUP();
//			}
//			
//			Random rand = new Random();
//			
//			int color = rand.nextInt(colors.length);
//			for(Guild guild : shardMan.getGuilds()) {
//				//Role role = guild.getRoleById(545232594938101760l);
//				Role role = guild.getBotRole();
//				role.getManager().setColor(colors[color]).queue();
//			}
//			
//			
//			int i = rand.nextInt(status.length);
//			
//			shardMan.getShards().forEach(jda -> {
//				String text = status[i].replaceAll("%members", "" + jda.getUsers().size());
//				
//				jda.getPresence().setActivity(Activity.playing(text));
//			});
//			
//			//StatschannelCommand.checkStats();
//			
//			if(next == 0) {
//				next = 60;
//				
//				onCheckTimeRanks();
//			}
//			else {
//				next--;
//			}
//		}
//		else {
//			next--;
//		}
//	}
	

	public void onCheckTimeRanks() {
		try {
			ResultSet set = LiteSQL.onQueryRAW(
					"SELECT userid, guildid FROM timeranks WHERE ((julianday(CURRENT_TIMESTAMP) - julianday(time)) * 1000) >= 1");
			List<Long> users = new ArrayList<>();
			// int count = 0;
			while (set.next()) {
				long userid = set.getLong("userid");
				long guildid = set.getLong("guildid");

				Guild guild = this.shardMan.getGuildById(guildid);
				guild.removeRoleFromMember(guild.getMemberById(userid), guild.getRoleById(545232594938101760l))
						.complete();
				System.out.println("Role entfernt.");

				users.add(userid);

				// count++;
			}

			for (long userid : users) {
				LiteSQL.onUpdate("DELETE FROM timeranks WHERE userid = " + userid);
			}

			// System.out.println(count + " Rolen entfernt.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public CommandManager getCmdMan() {
		return cmdMan;
	}
	
	/*
	 * Using JDA
	 */

	/*
	 * public Launch() throws LoginException { INSTANCE = this;
	 * 
	 * 
	 * 
	 * final String token =
	 * "ODk3MTc3MDQ3MDkxMjU3Mzk1.YWR3PA.3-gZrsW1FUjOtN90YdVNrDM5mOE";
	 * 
	 * JDABuilder jdaBuilder = JDABuilder.createDefault(token);
	 * jdaBuilder.setActivity(Activity.playing("-"+" is the Current Prefix"));
	 * jdaBuilder.setStatus(OnlineStatus.DO_NOT_DISTURB);
	 * 
	 * this.cmdMan = new CommandManager();
	 * 
	 * 
	 * //listen to the changes jdaBuilder.addEventListeners(new CommandListner());
	 * jda = jdaBuilder.build();
	 * 
	 * System.out.println("```diff\r\n+ Bot is Online!\r\n```");
	 * 
	 * 
	 * shutdown(); runLoop();
	 * 
	 * }
	 */

}
