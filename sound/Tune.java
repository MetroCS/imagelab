package sound;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * A multi-voice sound construction.
 *
 * @author Dr. Jody Paul
 * @version 1.2
 */
public class Tune {
    /** The notes for this tune, represented as Chords. */
    private List<Chord> tuneNotes;

    /** The default tempo for this tune. */
    private int tempo = Note.DQ;

    /** The number of voices for this tune (default 1). */
    private int numVoices = 1;

    /** The number of semitones in a major third interval. */
    private static final int MAJOR_THIRD = 4;

    /**The number of semitones in a perfect fifth interval. */
    private static final int PERFECT_FIFTH = 7;

    /**
     * Construct a Tune with no chords or notes.
     */
    public Tune() {
        this.tuneNotes = new ArrayList<Chord>();
    }

    /**
     *  Add a Chord to the end of this tune.
     *  @param newChord the Chord to add
     */
    public void addChord(final Chord newChord) {
        this.tuneNotes.add(newChord);
    }

    /**
     * Create an Iterator over the multi-notes in this tune.
     * @return this tune's iterator
     */
    public Iterator<Chord> iterator() {
        return tuneNotes.iterator();
    }

    /**
     * Access the tempo of this tune.
     * @return the tempo
     */
    public int getTempo() {
        return this.tempo;
    }

    /**
     * A sample tune for testing.
     * @return a tune with chords
     */
    public static Tune sampleTune() {
        Tune t = new Tune();
        Chord c = new Chord();
        c.addNote(new Note(0,
                           Note.C,
                           Note.DH,
                           Note.VPP + Note.VRANGE / 2));
        c.addNote(new Note(1,
                           Note.C + MAJOR_THIRD,
                           Note.DH,
                           Note.VPP + Note.VRANGE / 2));
        c.addNote(new Note(2,
                           Note.C + PERFECT_FIFTH,
                           Note.DH,
                           Note.VPP + Note.VRANGE / 2));
        t.addChord(c);
        t.addChord(c);
        return t;
    }
}
