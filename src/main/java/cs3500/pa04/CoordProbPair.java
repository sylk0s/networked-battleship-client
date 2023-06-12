package cs3500.pa04;

import cs3500.pa03.model.Coord;

public class CoordProbPair {
  Coord coord;
  double prob;

  CoordProbPair(Coord coord, double prob) {
    this.coord = coord;
    this.prob = prob;
  }

  public int compareTo(CoordProbPair that) {
    return Double.compare(this.prob, that.prob);
  }

  public Coord getCoord() {
    return this.coord;
  }
}
