package Music;

import GameState.MusicName;
import GameState.State;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.io.File;


public class MusicPlayer {

    File folder = new File("Tracks");
    public File[] tracks = folder.listFiles((dir, name) -> name.endsWith(".wav"));
    public MusicTrack[] trackList = new MusicTrack[tracks.length];
    String trackNames[] = new String[tracks.length];
    //State state;
    public Clip musicTracks[];
    //public Clip clip1;
    //public GameState nigger = new GameState;
    public int nameToIndex(MusicName name) {
        switch(name){
            case Aria_Math -> {
                return 0;
            }
            case Hobbit -> {
                return 1;
            }
            case Empty -> {
                return 2;
            }
            case Whoosh -> {
                return 3;
            }
            case PortalSFX -> {
                return 4;
            }
            case Spuder_Man_Fade -> {
                return 5;
            }
            case Geom_Dash -> {
                return 6;
            }
        }
        return -1;
    }
    public MusicPlayer() {
        musicTracks = new Clip[tracks.length];
        for (int i = 0; i < tracks.length; i++) {
            trackNames[i] = "";
            trackNames[i] = tracks[i].getName().replace(".wav", "");
            musicTracks[i]=getsClip(i);
        }

        //preloadSongs();
    }
    public void playSound(MusicName name) {
        int index = nameToIndex(name);
        musicTracks[index].setFramePosition(0);
        musicTracks[index].start();
    }
    public void stopSound(MusicName name) {
        int index = nameToIndex(name);
        //musicTracks[index].setFramePosition(0);
        musicTracks[index].stop();
    }

//    public void preloadSongs() {
//        try {
//            for (int i = 0; i < tracks.length; i++) {
//                //System.out.println(i+"\n");
//                trackList[i].track = AudioSystem.getAudioInputStream(tracks[i]);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    public Clip getsClip(int name) {
        int index = name;
        Clip clip;
        try{
            File file = new File("Tracks/"+trackNames[index]+".wav");
            //System.out.println("Tracks/"+trackNames[index]+".wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            return clip;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
//    public void play(State state,int track) {
//
//        try {
//            clip = AudioSystem.getClip();
//            switch(state){
//                case MAP1 -> clip.open(trackList[track].track);
//            }
//            //clip.open(trackList[0].track);
//            clip.start();
//            System.out.println("here");
//            if(track == 2 || track == 0)clip.loop(Clip.LOOP_CONTINUOUSLY);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    public Clip playSad(){
        System.out.println(trackNames[0]);
        try{
            File file = new File("Tracks/empty thoughts.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            return clip;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public Clip playPause(){
        try{
            File file = new File("Tracks/Stay Inside Me.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            return clip;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


    //private static Clip clip1;
    public void initializeClip(Clip clip1,int tr) {
        try {
            clip1 = AudioSystem.getClip();
            clip1.open(trackList[tr].track);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Play the Clip
    public void playPortal() {
        Clip clip1 = null;
        initializeClip(clip1,3);
        try {
            if (clip1 != null) {
                clip1.setFramePosition(0); // Reset to the start
                clip1.start();            // Play the sound
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Clip playIntro(){
        try{
            File file = new File("Tracks/SM_FADE.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            return clip;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Clip tpToStart(){
        try{
            File file = new File("Tracks/long-whoosh-194554.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            return clip;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public void Portal(){
        try{
            File file = new File("Tracks/Magic PORTAL Sound FX (NO Copyright).wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        //return null;
    }
    public void playMinecraftMusic(){
        try{
            File file = new File("Tracks/C418 - Aria Math ( Handpan Cover ).wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        //return null;
    }

    public void playMusic(String path) {

        try {
            File musicFile = new File(path);
            if (musicFile.exists()) {
                AudioInputStream audio = AudioSystem.getAudioInputStream(musicFile);
                Clip clip = AudioSystem.getClip();
                clip.open(trackList[0].track);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                System.out.println("Tracks/Concerning Hobbits.wav");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
}
