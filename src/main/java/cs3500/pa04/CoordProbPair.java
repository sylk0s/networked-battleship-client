package cs3500.pa04;

import cs3500.pa03.model.Coord;

/**
 * A very stupid data structure that's only meant for internal use in probability board
 */
public class CoordProbPair {

  /**
   * The coordinate in the pair
   */
  Coord coord;

  /**
   * The probability associated with the probability
   */
  double prob;

  /**
   * constructor
   *
   * @param coord the coordinate
   * @param prob the probability
   */
  CoordProbPair(Coord coord, double prob) {
    this.coord = coord;
    this.prob = prob;
  }

  /**
   * Compares two pairs using probability
   *
   * @param that the other object
   * @return an int representing the comparison
   */
  public int compareTo(CoordProbPair that) {
    return Double.compare(this.prob, that.prob);
  }

  /**
   * Get this pair's coordinate
   *
   * @return The coordinate to get
   */
  public Coord getCoord() {
    return this.coord;
  }
}
