class Main {
  public static void main(String[] args) {
    //Print introduction
    System.out.println("Welcome to chess, prepare to play.\nAll pieces are labeled by the first letter in their name, the exception to this is the king, which is labeled as a \"W\".\nPlayers will be refered to as the letter that represents their king.");
    //Create board
    Board game = new Board();
    //Play the game until one wins
    while(game.win() == '-')
    {
      game.displayBoard();
      game.takeTurn();
    }
    game.displayBoard();
    //Display which player won.
    System.out.println("Player " + game.win() + " wins");
  }
}