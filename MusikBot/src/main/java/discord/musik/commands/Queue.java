package discord.musik.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import discord.musik.MusicController;

public class Queue {
	
	
	private List<AudioTrack> queuelist;
	private MusicController controller;
	
	//to end the track and begin another one by using poll Method
	private final BlockingQueue<AudioTrack> list;



	public Queue(MusicController controller) {
		this.setController(controller);
		this.setQueuelist(new ArrayList<AudioTrack>());
		this.list = new LinkedBlockingQueue<>();
	}
	
	public boolean next() {
		
		if(this.queuelist.size() >= 1) {
			AudioTrack track = queuelist.remove(0);
			
			if(track != null) {
				
				this.controller.getPlayer().playTrack(track);
				return true;
			}
		}
		
		
		return false;
	}
	
	
	
	public void addTrackToQueue(AudioTrack track) {
		this.queuelist.add(track);
		
		if(controller.getPlayer().getPlayingTrack() == null) {
			next();
		}
	}
	
	
	public MusicController getController() {
		return controller;
	}
	public void setController(MusicController controller) {
		this.controller = controller;
	}
	public List<AudioTrack> getQueuelist() {
		return queuelist;
	}
	public void setQueuelist(List<AudioTrack> queuelist) {
		this.queuelist = queuelist;
	}
	

	public void shuffel() {
		Collections.shuffle(queuelist);
		
		for(AudioTrack track : queuelist) {
			list.add(track);
		}
		this.controller.getPlayer().startTrack(list.poll(), false);
	}
	public void nextTrack() {
		for(AudioTrack track : queuelist) {
			list.add(track);
		}

		this.controller.getPlayer().startTrack(list.poll(), false);
		//queuelist.indexOf(controller.getPlayer().getPlayingTrack());
	}
	
}
