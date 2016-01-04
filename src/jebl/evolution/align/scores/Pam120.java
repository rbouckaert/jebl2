package jebl.evolution.align.scores;

public class Pam120 extends AminoAcidScores {

  private final float[][] residueScores = {

            /*  A   R   N   D   C   Q   E   G   H   I   L   K   M   F   P   S   T   W   Y   V */
            {   3},
            {  -3,  6},
            {  -1, -1,  4},
            {   0, -3,  2,  5},
            {  -3, -4, -5, -7,  9},
            {  -1,  1,  0,  1, -7,  6},
            {   0, -3,  1,  3, -7,  2,  5},
            {   1, -4,  0,  0, -4, -3, -1,  5},
            {  -3,  1,  2,  0, -4,  3, -1, -4,  7},
            {  -1, -2, -2, -3, -3, -3, -3, -4, -4,  6},
            {  -3, -4, -4, -5, -7, -2, -4, -5, -3,  1,  5},
            {  -2,  2,  1, -1, -7,  0, -1, -3, -2, -3, -4,  5},
            {  -2, -1, -3, -4, -6, -1, -3, -4, -4,  1,  3,  0,  8},
            {  -4, -5, -4, -7, -6, -6, -7, -5, -3,  0,  0, -7, -1,  8},
            {   1, -1, -2, -3, -4,  0, -2, -2, -1, -3, -3, -2, -3, -5,  6},
            {   1, -1,  1,  0,  0, -2, -1,  1, -2, -2, -4, -1, -2, -3,  1,  3},
            {   1, -2,  0, -1, -3, -2, -2, -1, -3,  0, -3, -1, -1, -4, -1,  2,  4},
            {  -7,  1, -4, -8, -8, -6, -8, -8, -3, -6, -3, -5, -6, -1, -7, -2, -6, 12},
            {  -4, -5, -2, -5, -1, -5, -5, -6, -1, -2, -2, -5, -4,  4, -6, -3, -3, -2,  8},
            {   0, -3, -3, -3, -3, -3, -3, -2, -3,  3,  1, -4,  1, -3, -2, -2,  0, -8, -3,  5}};

  public Pam120() { buildScores(residueScores); }

}