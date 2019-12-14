package sound;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * A musical scale.
 * Comprised of an ordered set of pitches (midi note numbers).
 * 
 * @author Dr. Jody Paul
 * @version 1.2
 */
public class Scale {
    /** The pitches for this tune. */
    private List<Integer> scalePitches;
    
    /**
     * Construct an empty Scale.
     */
    public Scale() {
        scalePitches = new ArrayList<Integer>();
    }

    /**
     *  Add a pitch to the end of this scale.
     *  @param newPitch the pitch to add
     */
    public void addPitch(int midiPitch) {
        addPitch(Integer.valueOf(midiPitch));
    }

    /**
     *  Add a pitch to the end of this scale.
     *  @param newPitch the pitch to add
     */
    public void addPitch(Integer midiPitch) {
        scalePitches.add(midiPitch);
    }

    /**
     *  Access a specified pitch in this scale.
     *  The index used is the parameter modulo the number of pitches in the scale.
     *  @param whichPitch the index of the pitch to retrieve
     *  @return the pitch in the scale as specified by the parameter
     */
    public Integer getPitch(int whichPitch) {
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
        for (int i = -3; i < 4; i++) {
            s.addPitch( Note.C      + (12 * i));
            s.addPitch((Note.C + 2) + (12 * i));
            s.addPitch((Note.C + 5) + (12 * i));
            s.addPitch((Note.C + 7) + (12 * i));
            s.addPitch((Note.C + 9) + (12 * i));
        }
        s.addPitch((Note.C + (12 * 4)));
        return s;
    }

    /**
     * Create another 7-octave Pentatonic scale.
     * @return a pentationic scale
     */
    public static Scale pentatonic2() {
        Scale s = new Scale();
        for (int i = -3; i < 4; i++) {
            s.addPitch( Note.C       + (12 * i));
            s.addPitch((Note.C + 3)  + (12 * i));
            s.addPitch((Note.C + 5)  + (12 * i));
            s.addPitch((Note.C + 7)  + (12 * i));
            s.addPitch((Note.C + 10) + (12 * i));
        }
        s.addPitch((Note.C + (12 * 4)));
        return s;
    }

    /** Yet another 7-octave Pentatonic scale. */
    public static Scale pentatonic3() {
        Scale s = new Scale();
        for (int i = -3; i < 4; i++) {
            s.addPitch( Note.C      + (12 * i));
            s.addPitch((Note.C + 2) + (12 * i));
            s.addPitch((Note.C + 4) + (12 * i));
            s.addPitch((Note.C + 7) + (12 * i));
            s.addPitch((Note.C + 9) + (12 * i));
        }
        s.addPitch((Note.C + (12 * 4)));
        return s;
    }

}
