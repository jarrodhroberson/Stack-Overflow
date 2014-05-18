package com.stackoverflow;

import javax.annotation.Nonnull;

public class Q23726363B
{
    public static void main(final String[] args)
    {
        final Game game = new Game(args[0], args[1]);
    }

    public static class Game
    {
        private final Player p1;
        private final Player p2;

        public Game(@Nonnull final String p1, @Nonnull final String p2)
        {
            this.p1 = new Player(p1);
            this.p2 = new Player(p2);
        }

        public class Player
        {
            private final String name;

            private Player(@Nonnull final String name) {this.name = name;}

            public Game getGame() { return Game.this; }
        }
    }
}
