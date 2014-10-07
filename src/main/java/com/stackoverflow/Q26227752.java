package com.stackoverflow;

import java.sql.*;

public class Q26227752
{
    public static void main(final String[] args)
    {
        try
        {
            final Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/finalclient", "root", "");
            try
            {
                final Statement ps = cn.createStatement();
                try
                {
                    final ResultSet rs = ps.executeQuery("SELECT MAX(msg_id) as maxid FROM msgs");
                    try
                    {
                        if (rs.next())
                        {
                            final int maxid = rs.getInt("maxid");
                            System.out.println("maxid = " + maxid);
                            System.out.println("nextid = " + maxid + 1);
                        }
                        else
                        {
                            throw new SQLException("no rows returned for \"SELECT MAX(msg_id) FROM msgs\"");
                        }
                    }
                    catch (final SQLException e)
                    {
                        throw new RuntimeException(e);
                    }
                    finally
                    {
                        try { rs.close(); } catch (final SQLException e) { System.err.print(e.getMessage()); }
                    }
                }
                catch (final SQLException e)
                {
                    throw new RuntimeException(e);
                }
                finally
                {
                    try { ps.close(); } catch (final SQLException e) { System.err.print(e.getMessage()); }
                }
            }
            catch (final SQLException e)
            {
                throw new RuntimeException(e);
            }
            finally
            {
                try { cn.close(); } catch (final SQLException e) { System.err.print(e.getMessage()); }
            }
        }
        catch (final SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
}
