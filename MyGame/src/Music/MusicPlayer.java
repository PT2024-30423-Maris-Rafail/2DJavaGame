package Music;

import GameState.MusicName;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.io.File;


public class MusicPlayer {

    File folder = new File("Tracks");
    public File[] tracks = folder.listFiles((_, name) -> name.endsWith(".wav"));

    String[] trackNames = new String[tracks.length];
    //State state;
    public Clip[] musicTracks;

    //public Clip clip1;
    //public GameState nigger = new GameState;
    public int nameToIndex(MusicName name) {
        switch (name) {
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
            case ELEVATOR_PERMIT -> {
                return 7;
            }
            case JOYRIDE -> {
                return 8;
            }
        }
        return -1;
    }

    public MusicPlayer() {
        musicTracks = new Clip[tracks.length];
        for (int i = 0; i < tracks.length; i++) {
            trackNames[i] = "";
            trackNames[i] = tracks[i].getName().replace(".wav", "");
            musicTracks[i] = getsClip(i);
        }

        //preloadSongs();
    }

    public void playSound(MusicName name) {
        int index = nameToIndex(name);
        musicTracks[index].setFramePosition(0);
        musicTracks[index].start();
        if (name == MusicName.Aria_Math || name == MusicName.Empty || name == MusicName.Geom_Dash || name == MusicName.ELEVATOR_PERMIT || name == MusicName.JOYRIDE)
            musicTracks[index].loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stopSound(MusicName name) {
        int index = nameToIndex(name);
        //musicTracks[index].setFramePosition(0);
        musicTracks[index].stop();
    }


    public Clip getsClip(int name) {
        Clip clip;
        try {
            File file = new File("Tracks/" + trackNames[name] + ".wav");
            //System.out.println("Tracks/"+trackNames[index]+".wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            return clip;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
