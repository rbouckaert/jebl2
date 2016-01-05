package jebl.evolution.align.scores;

public class Pam100 extends AminoAcidScores {

  private final float[][] residueScores = {

            /*  A   R   N   D   C   Q   E   G   H   I   L   K   M   F   P   S   T   W   Y   V */
            {   4},
            {  -3,  7},
            {  -1, -2,  5},
            {  -1, -4,  3,  5},
            {  -3, -5, -5, -7,  9},
            {  -2,  1, -1,  0, -8,  6},
            {   0, -3,  1,  4, -8,  2,  5},
            {   1, -5, -1, -1, -5, -3, -1,  5},
            {  -3,  1,  2, -1, -4,  3, -1, -4,  7},
            {  -2, -3, -3, -4, -3, -4, -3, -5, -4,  6},
            {  -3, -5, -4, -6, -8, -2, -5, -6, -3,  1,  6},
            {  -3,  2,  1, -1, -8,  0, -1, -3, -2, -3, -4,  5},
            {  -2, -1, -4, -5, -7, -2, -4, -4, -4,  1,  3,  0,  9},
            {  -5, -6, -5, -8, -7, -7, -8, -6, -3,  0,  0, -7, -1,  8},
            {   1, -1, -2, -3, -4, -1, -2, -2, -1, -4, -4, -3, -4, -6,  7},
            {   1, -1,  1, -1, -1, -2, -1,  0, -2, -3, -4, -1, -3, -4,  0,  4},
            {   1, -3,  0, -2, -4, -2, -2, -2, -3,  0, -3, -1, -1, -5, -1,  2,  5},
            {  -7,  1, -5, -9, -9, -7, -9, -9, -4, -7, -3, -6, -6, -1, -7, -3, -7, 12},
            {  -4, -6, -2, -6, -1, -6, -5, -7, -1, -3, -3, -6, -5,  4, -7, -4, -4, -2,  9},
            {   0, -4, -3, -4, -3, -3, -3, -3, -3,  3,  0, -4,  1, -3, -3, -2,  0, -9, -4,  5}};

  public Pam100() { buildScores(residueScores); }

}