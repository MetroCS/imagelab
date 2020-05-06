package sound;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * A multiple-voice event.
 * @author Jody Paul
 * @version 1.1
 */
public class Chord {
    /** Store notes of a chord. */
    private List<Note> notes;

    /**
     * Construct a chord with no notes.
     */
    public Chord() {
        this.notes = new ArrayList<Note>();
    }

    /**
     * Construct a chord with one note.
     * @param oneNote the note of the chord
     */
    public Chord(final Note oneNote) {
        this.notes = new ArrayList<Note>();
        addNote(oneNote);
    }

    /**
     * Construct a chord with a specified
     * number of voices, comprised of no notes.
     * @param numVoices the number of voices
     */
    public Chord(final int numVoices) {
        this.notes = new ArrayList<Note>(numVoices);
    }

    /**
     * Shallow copy constructor.
     * @param orig the chord to be replicated
     */
    public Chord(final Chord orig) {
        this.notes = new ArrayList<Note>(orig.notes);
    }

    /**
     * Add a note to this chord.
     * @param newNote the note to add
     */
    public void addNote(final Note newNote) {
        this.notes.add(newNote);
    }

    /**
     * Access the number of voices of this chord.
     * @return the number of voices
     */
    public int numVoices() {
        return this.notes.size();
    }

    /**
     * Access the note of the specified voice.
     * @param voiceNum the intended voice number
     * @return the indicated note
     */
    public Note getVoiceNote(final int voiceNum) {
        return this.notes.get(voiceNum);
    }

    /**
     * Generate an iterator over the notes in this chord.
     * @return iterator over notes in this chord
     */
    public Iterator<Note> iterator() {
        return notes.iterator();
    }
}
