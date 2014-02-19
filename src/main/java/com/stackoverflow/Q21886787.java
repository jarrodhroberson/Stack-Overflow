package com.stackoverflow;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Q21886787
{
    public static void main(final String[] args)
    {
        final Matrix<Integer> matrix = new Matrix<Integer>();
        matrix.addRow(1, 2, 3);
        matrix.addRow(4, 5, 6);
        matrix.addRow(7, 8, 9);
        matrix.addRow(10, 11, 12);
        final Matrix.ColumnVisitor<Integer> visitor = new Matrix.ColumnVisitor<Integer>()
        {

            private int numRows = 0;
            private int total = 0;

            @Override
            public void visit(@Nonnull final Integer value)
            {
                numRows++;
                total += value;
            }

            @Override
            public String toString()
            {
                return String.format("Average of Column %d is %f", 1, (double) total / (double) numRows);
            }
        };
        matrix.accept(1, visitor);
        System.out.println(visitor);
    }

    public static class Matrix<T>
    {
        final List<List<T>> matrix = new ArrayList<List<T>>();

        @SafeVarargs
        public final void addRow(@Nonnull final T... values)
        {
            this.matrix.add(Arrays.asList(values));
        }

        public Iterator<List<T>> rowIterator() { return matrix.iterator(); }

        public T cell(@Nonnull final Integer row, @Nonnull final Integer column)
        {
            try
            {
                return matrix.get(row).get(column);
            }
            catch (IndexOutOfBoundsException e)
            {
                return null;
            }
        }

        public void accept(@Nonnull final Integer column, @Nonnull final ColumnVisitor<T> visitor)
        {
            for (final List<T> row : this.matrix) { visitor.visit(row.get(column)); }
        }

        public interface ColumnVisitor<T>
        {
            public void visit(@Nonnull final T value);
        }
    }
}
