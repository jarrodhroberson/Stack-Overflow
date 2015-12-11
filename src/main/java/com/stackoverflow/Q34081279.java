package com.stackoverflow;

public class Q34081279
{
    final static int A_GRADE = 18;
    final static int B_GRADE = 16;
    final static int C_GRADE = 14;
    final static int D_GRADE = 12;

    private static boolean isA(final int count) { return count >= A_GRADE; }
    private static boolean isB(final int count) { return B_GRADE <= count && count < A_GRADE; }
    private static boolean isC(final int count) { return C_GRADE <= count && count < B_GRADE; }
    private static boolean isD(final int count) { return D_GRADE <= count && count < C_GRADE; }
    private static boolean isF(final int count) { return count < D_GRADE; }

    public static void main(final String[] args)
    {
        final String[] words = new String[]{}; /* this is just a placeholer, not provided in question */
        final String[][] table = new String[][]{}; /* this is just a placehoder, not provided in question */

        int markA = 0;
        int markB = 0;
        int markC = 0;
        int markD = 0;
        int markF = 0;

        for (int row = 0; row < 100; row++)
        {
            int count = 0;
            for (int column = 0; column < words.length; column++)
            {
                if (words[column].equals(table[row][column])) { count++; }
            }
            if (count >= A_GRADE) { System.out.format("%d = A", count); }
            else if (B_GRADE <= count && count < A_GRADE) { System.out.format("%d = B", count); }
            else if (C_GRADE <= count && count < B_GRADE) { System.out.format("%d = C", count); }
            else if (D_GRADE <= count && count < C_GRADE) { System.out.format("%d = D", count); }
            else { System.out.format("%d = F", count); }
            System.out.println();
        }

        System.out.println(String.format("Question A %d", markA));
        System.out.println(String.format("Question B %d", markB));
        System.out.println(String.format("Question C %d", markC));
        System.out.println(String.format("Question D %d", markD));
        System.out.println(String.format("Question F %d", markF));
    }
}
