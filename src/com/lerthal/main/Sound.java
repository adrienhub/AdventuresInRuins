package com.lerthal.main;

import java.io.*;
import javax.sound.sampled.*;
public class Sound {

	public static class Clips{
		public Clip[] clips;
		private int p;
		private int count;
		
		public Clips(byte[] buffer,  int count) throws LineUnavailableException , IOException , UnsupportedAudioFileException {
			if(buffer == null){
				return;
			}
			
			clips = new Clip[count];
			this.count = count;
			
			for(int i = 0 ; i < count; i++){
				
			}
			
		}
	}

}