package sound;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import java.util.Iterator;

/**
 * Render sound using the javax.sound.midi package.
 * @author Dr. Jody Paul
 * @version 1.2
 */
public class Music {
    /** Standard Velocity. */
    public static final int STD_VELOCITY = 64;
    /** Standard Duration. */
    public static final int STD_DURATION = Note.DE / 2;
    /** Standard Instrument. */
    public static final int STD_INSTRUMENT = Note.VIBES;
    /** Synthesizer reference. */
    private Synthesizer synth;
    /** Synthesizer channels. */
    private MidiChannel[] channels;
    /** code for default instrument. */
    private int instrument;
    /** Default note velocity. */
    private int velocity = STD_VELOCITY;
    /** Default number of Channels. */
    private int numChannels = 1;

    /**
     * Construct Music using Note.Vibes instrument.
     */
    public Music() {
        this(STD_INSTRUMENT);
    }

    /**
     * Construct Music using specified instrument
     * and a single channel.
     * @param instr the integer code for the midi instrument
     */
    public Music(final int instr) {
        instrument = instr;
        velocity = STD_VELOCITY;
        numChannels = 1;
        establishSynthesizer(numChannels, instrument);
    }

    /**
     * Construct Music with specified channels and instrument.
     * @param numCh the number of channels
     * @param instr the integer code for the midi
     *        instrument to use for all channels
     */
    public Music(final int numCh, final int instr) {
        instrument = instr;
        velocity = STD_VELOCITY;
        numChannels = numCh;
        establishSynthesizer(numChannels, instrument);
    }

    /**
     * Construct music with specified number of channels
     * and specified instrument for each channel.
     * @param numCh the number of channels
     * @param instr the integer codes for the midi instruments
     */
    public Music(final int numCh, final int[] instr) {
        instrument = instr[0];
        velocity = STD_VELOCITY;
        numChannels = numCh;
        establishSynthesizer(numChannels, instr);
    }

    /**
     * Create and initialize the default synthesizer.
     * Uses a default number of channels and instrument.
     */
    private void establishSynthesizer() {
        establishSynthesizer(numChannels, instrument);
    }

    /**
     * Create and initialize the default synthesizer.
     * Uses default instrument.
     * @param numCh the number of channels to initialize
     */
    private void establishSynthesizer(final int numCh) {
        numChannels = numCh;
        establishSynthesizer(numChannels, instrument);
    }

    /**
     * Create and initialize the default synthesizer.
     * @param numCh the number of channels to initialize
     * @param instr the integer code for the midi
     *        instrument used for every channel
     */
    private void establishSynthesizer(final int numCh, final int instr) {
        int[] instruments = new int[numCh];
        for (int i = 0; i < numCh; i++) {
             instruments[i] = instr;
         }
        establishSynthesizer(numCh, instruments);
    }

    /**
     * Create and initialize the default synthesizer.
     * @param numCh the number of channels to initialize
     * @param instr the integer code for the midi
     *        instrument used for every channel
     */
    private void establishSynthesizer(final int numCh, final int[] instr) {
        try {
            synth = MidiSystem.getSynthesizer();
            synth.open();
            channels = synth.getChannels();
            for (int i = 0; i < numCh; i++) {
                channels[i].programChange(instr[i]);
            }
        } catch (Exception e) {
            System.err.println("No Available Synthesizer");
        }
    }

    /**
     * Create a list of the available instruments.
     * @return list of instruments available in this object's synth
     */
    public java.util.List<Instrument> availableInstruments() {
        java.util.List<Instrument> instruments
                 = new java.util.ArrayList<Instrument>();
        Instrument[] availInst = synth.getAvailableInstruments();
        int numInst = availInst.length;
        for (int i = 0; i < numInst; i++) {
            if (availInst[i] != null) {
                instruments.add(availInst[i]);
            }
        }
        return instruments;
    }

    /** Previous note memory for playChord() method. */
    private java.util.ArrayList<Note> noteHistory = null;
    /**
     * Play a chord.
     * If the pitch for a given channel's note is identical to
     * that in the previous chord played, the previous note is
     * not turned off and the new note is not turned on.
     * @param chord the chord to play
     * @param defaultDuration the default duration for notes
     */
    public void playChord(final Chord chord, final int defaultDuration) {
        /** Current note */
        Note note;
        /** Previous note */
        Note prev;
        /** Current duration */
        int duration = defaultDuration < 0 ? STD_DURATION : defaultDuration;
        /** Note durations */
        int[] noteDurations = new int[chord.numVoices()];
        /** Chord iterator */
        Iterator<Note> chordIt = chord.iterator();
        /** Establish history if this is the first chord played. */
        if (null == noteHistory) {
            noteHistory = new java.util.ArrayList<Note>();
            for (int i = 0; i < chord.numVoices(); i++) {
                noteHistory.add(Note.NULL_NOTE);
            }
        }
        while (chordIt.hasNext()) {
            note = chordIt.next();
            // System.out.println("  Note: " + note);
            prev = noteHistory.get(note.getChannel());
            if (note.equals(Note.NULL_NOTE)) {
                //System.out.print("[NULL_NOTE] ");
                channels[note.getChannel()].noteOff(prev.getPitch(),
                        prev.getVelocityOn());
            } else if (note.getPitch() != prev.getPitch()) {
                // System.out.println("  Note: " + note);
                channels[note.getChannel()].noteOff(prev.getPitch(),
                        prev.getVelocityOn());
                channels[note.getChannel()].noteOn(note.getPitch(),
                        note.getVelocityOn());
                duration = (duration > note.getDuration())
                        ? note.getDuration() : duration;
                noteHistory.set(note.getChannel(), note);
            }
        }
        try {
            Thread.sleep(duration); // Duration of shortest note
            duration = 0;
        } catch (Exception e) {
            System.out.println("Problem sleeping?");
        }
    }

    /**
     * Play a tune.
     * @param tune the tune to play
     */
    public void playTune(final Tune tune) {
        /** Current chord */
        Chord chord;
        /** Current duration */
        int duration = STD_DURATION;
        /** Tune iterator */
        Iterator<Chord> tuneIt = tune.iterator();
        int line = 0;
        while (tuneIt.hasNext()) {
            System.out.println("[Line " + (line++) + "] ");
            chord = tuneIt.next();
            playChord(chord, duration);
        }
        silence();
    }

    /**
     * Stop all tones coming from synthesizer.
     */
    public void silence() {
        Note note;
        int numNotes = noteHistory.size();
        for (int i = 0; i < numNotes; i++) {
            note = noteHistory.get(i);
            channels[note.getChannel()].noteOff(note.getPitch(),
                    note.getVelocityOn());
        }
    }

}
