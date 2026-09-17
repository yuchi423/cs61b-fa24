package gh2;

import deque.*;


//Note: This file will not compile until you complete the Deque61B implementations
public class GuitarString {

    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    private Deque61B<Double> buffer = new LinkedListDeque61B<>();
    private int capacity;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {

        capacity = Math.toIntExact(Math.round(SR / frequency));
        for (int i = 0; i < capacity; i++) {
            buffer.addFirst(0.0);
        }

    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {

        for (int i = 0; i < capacity; i++) {
            double r = Math.random() - 0.5;
            buffer.removeFirst();
            buffer.addLast(r);
        }

    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        double oldFront = buffer.removeFirst();
        double newFront = buffer.get(0);

        double newFrequency = (oldFront + newFront) / 2 * DECAY;
        buffer.addLast(newFrequency);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        return buffer.get(0);
    }
}
