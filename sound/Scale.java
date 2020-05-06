package sound;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * A musical scale.
 * Comprised of an ordered set of pitches (midi note numbers).
 * @author Dr. Jody Paul
 * @version 1.2
 */
 public class Scale {
    /** The pitches for this tune. */
    private List<Integer> scalePitches;
    /** Max offset to low frequency keys. */
    private static final int OCTAVE_START_OFFSET = -3;
    /** Max offset to high frequency keys. */
    private static final int OCTAVE_END_OFFSET = 4;
    /** The factor to move between octaves. */
    private static final int  OCTAVE_FACTOR = 12;
    /** The number of semitones in a minor seventh interval. */
    private static final int MINOR_SEVENTH = 10;
    /** The number of semitones in a major sixth interval. */
    private static final int MAJOR_SIXTH = 9;
    /** The number of semitones in a perfect fifth interval. */
    private static final int PERFECT_FIFTH = 7;
    /** The number of semitones in a perfect fourth interval. */
    private static final int PERFECT_FOURTH = 5;
    /** The number of semitones in a major third interval. */
    private static final int MAJOR_THIRD = 4;
    /** The number of semitones in a minor third interval. */
    private static final int MINOR_THIRD = 3;
    /** The number of semitones in a major second interval. */
    private static final int MAJOR_SECOND = 2;

    /**
     * Construct an empty Scale.
     */
    public Scale() {
        scalePitches = new ArrayList<Integer>();
    }

    /**
     *  Add a pitch to the end of this scale.
     *  @param midiPitch the pitch to add
     */
    public void addPitch(final int midiPitch) {
        addPitch(Integer.valueOf(midiPitch));
    }

    /**
     *  Add a pitch to the end of this scale.
     *  @param midiPitch the pitch to add
     */
    public void addPitch(final Integer midiPitch) {
        scalePitches.add(midiPitch);
    }

    /**
     *  Access a specified pitch in this scale.
     *  The index used is the parameter modulo
     *  the number of pitches in the scale.
     *  @param whichPitch the index of the pitch to retrieve
     *  @return the pitch in the scale as specified by the parameter
     */
    public Integer getPitch(final int whichPitch) {
        return scalePitches.get(whichPitch % numPitches());
    }

    /**
     * Create an Iterator over the pitches in this scale.
     * @return iterator over pitches
     */
    public Iterator<Integer> iterator() {
        return scalePitches.iterator();
    }

    /**
     * Access the number of pitches in this scale.
     * @return the number of pitches
     */
    public int numPitches() {
        return scalePitches.size();
    }

    /**
     * Create a 7-octave Pentatonic scale.
     * @return a pentationic scale
     */
    public static Scale pentatonic1() {
        Scale s = new Scale();
        for (int i = OCTAVE_START_OFFSET; i < OCTAVE_END_OFFSET; i++) {
            s.addPitch(Note.C + (OCTAVE_FACTOR * i));
            s.addPitch((Note.C + MAJOR_SECOND) + (OCTAVE_FACTOR * i));
            s.addPitch((Note.C + PERFECT_FOURTH) + (OCTAVE_FACTOR * i));
            s.addPitch((Note.C + PERFECT_FIFTH) + (OCTAVE_FACTOR * i));
            s.addPitch((Note.C + MAJOR_SIXTH) + (OCTAVE_FACTOR * i));
        }
        s.addPitch((Note.C + (OCTAVE_FACTOR * OCTAVE_END_OFFSET)));
        return s;
    }

    /**
     * Create another 7-octave Pentatonic scale.
     * @return a pentationic scale
     */
    public static Scale pentatonic2() {
        Scale s = new Scale();
        for (int i = OCTAVE_START_OFFSET; i < OCTAVE_END_OFFSET; i++) {
            s.addPitch(Note.C + (OCTAVE_FACTOR * i));
            s.addPitch((Note.C + MINOR_THIRD)  + (OCTAVE_FACTOR * i));
            s.addPitch((Note.C + PERFECT_FOURTH)  + (OCTAVE_FACTOR * i));
            s.addPitch((Note.C + PERFECT_FIFTH)  + (OCTAVE_FACTOR * i));
            s.addPitch((Note.C + MINOR_SEVENTH) + (OCTAVE_FACTOR * i));
        }
        s.addPitch((Note.C + (OCTAVE_FACTOR * OCTAVE_END_OFFSET)));
        return s;
    }

    /**
     * Yet another 7-octave Pentatonic scale.
     * @return a pentatonic scale
     */
    public static Scale pentatonic3() {
        Scale s = new Scale();
        for (int i = OCTAVE_START_OFFSET; i < OCTAVE_END_OFFSET; i++) {
            s.addPitch(Note.C + (OCTAVE_FACTOR * i));
            s.addPitch((Note.C + MAJOR_SECOND) + (OCTAVE_FACTOR * i));
            s.addPitch((Note.C + MAJOR_THIRD) + (OCTAVE_FACTOR * i));
            s.addPitch((Note.C + PERFECT_FIFTH) + (OCTAVE_FACTOR * i));
            s.addPitch((Note.C + MAJOR_SIXTH) + (OCTAVE_FACTOR * i));
        }
        s.addPitch((Note.C + (OCTAVE_FACTOR * OCTAVE_END_OFFSET)));
        return s;
    }

}
