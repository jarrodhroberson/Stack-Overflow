package com.stackoverflow;

import javax.annotation.Nonnull;

public class Q23726363
{
    public static void main(final String[] args)
    {
        final Game game = Game.startGame(args[0], args[1]);
    }

    public static class Game
    {
        public static Game startGame(@Nonnull final String playerOneName, @Nonnull final String playerTwoName)
        {
            final Player p1 = new Player(playerOneName);
            final Player p2 = new Player(playerTwoName);
            final Game game = new Game(p1, p2);
            p1.setCurrentGame(game);
            p2.setCurrentGame(game);
            return game;
        }

        private final Player player1;
        private final Player player2;

        private Game(@Nonnull final Player player1, @Nonnull final Player player2)
        {
            this.player1 = player1;
            this.player2 = player2;
        }
    }

    public static class Player
    {
        private final String name;
        private Game currentGame;

        private Player(@Nonnull final String name)
        {
            this.name = name;
        }

        public void setCurrentGame(@Nonnull final Game currentGame)
        {
            this.currentGame = currentGame;
        }
    }
}
