import java.util.Scanner;
import java.util.ArrayList;

public class Board
  {
    private int turn;
    private Piece[][] board;
    private static String valid = "12345678ABCDEFGH";

    public Board()
    {
      turn = 0;
      //Set up each row
      board = new Piece[8][8];
      board[0][0] = new Piece('R');
      board[0][1] = new Piece('K');
      board[0][2] = new Piece('B');
      board[0][3] = new Piece('Q');
      board[0][4] = new Piece('W');
      board[0][5] = new Piece('B');
      board[0][6] = new Piece('K');
      board[0][7] = new Piece('R');
      for(int i = 0; i < 8; i++)
        {
          board[1][i] = new Piece('P');
          for(int j = 2; j < 6; j++)
            {
              board[j][i] = new Piece('-');
            }
          board[6][i] = new Piece('p');
        }
      board[7][0] = new Piece('r');
      board[7][1] = new Piece('k');
      board[7][2] = new Piece('b');
      board[7][3] = new Piece('q');
      board[7][4] = new Piece('w');
      board[7][5] = new Piece('b');
      board[7][6] = new Piece('k');
      board[7][7] = new Piece('r');
    }

    public void displayBoard()
    {
      //Print out each line
      System.out.println("\n/ A   B   C   D   E   F   G   H\n  -----------------------------");
      for(int i = 0; i < 8; i++)
        {
          System.out.print((8 - i) + "|");
          for(int j = 0; j < 8; j++)
            {
              System.out.print(board[i][j].getValue() + "   ");
            }
          System.out.print("\n");
          if(i != 7)
          {
            System.out.print(" |");
          }
          System.out.print("\n");
        }
    }

    public void takeTurn()
    {
      Scanner scan = new Scanner(System.in);
      char player = 'W';
      int capitalPlayer = 1;
      //Determine who is playing
      if(turn % 2 == 0)
      {
        player = 'w';
        capitalPlayer = 0;
      }
      //Ask for a get the location of the piece you want to move
      System.out.println("Select a piece a where to move it to player " + player);
      System.out.print("Piece's location: ");
      String oldLoc = scan.next();
      int oldR = 7 - valid.indexOf(oldLoc.substring(1, 2));
      int oldC = valid.indexOf(oldLoc.toUpperCase().charAt(0)) - 8;
      //Check if the piece chosen is valid
      while(oldR == 8 || oldC == -9 || board[oldR][oldC].getValue() == '-' || board[oldR][oldC].getTeam() != capitalPlayer)
        {
          System.out.println("Invalid input, please try again.");
          oldLoc = scan.next();
          oldR = 7 - valid.indexOf(oldLoc.substring(1, 2));
          oldC = valid.indexOf(oldLoc.toUpperCase().charAt(0)) - 8;
        }
      //Ask for a get the location of where you want to move the chosen piece
      System.out.print("New location: ");
      String newLoc = scan.next();
      int newR = 7 - valid.indexOf(newLoc.substring(1, 2));
      int newC = valid.indexOf(newLoc.toUpperCase().charAt(0)) - 8;
      boolean invalid = Board.invalidMove(oldR, oldC, newR, newC, board);
      //Check if the new position is valid
      while(invalid)
        {
          System.out.println("Invalid input, please try again.");
          newLoc = scan.next();
          newR = 7 - valid.indexOf(newLoc.substring(1, 2));
          newC = valid.indexOf(newLoc.toUpperCase().charAt(0)) - 8;
          invalid = Board.invalidMove(oldR, oldC, newR, newC, board);
        }
      //Move the piece to its new spot and remove it from the old one before going to the next turn
      board[newR][newC].newPiece(board[oldR][oldC].getValue(), board[oldR][oldC].getTeam());
      board[oldR][oldC].reset();
      turn++;
    }

    public static boolean invalidMove(int oldR, int oldC, 
int newR, int newC, Piece[][] board)
    {
      if(newR == 8 || newC == -9)
      {
        return true;
      }
      int movement = 0;
      boolean breaker = false;
      ArrayList<Piece> possibleMoves = new ArrayList<Piece>();
      //Move check for lowercase pawns
      if(board[oldR][oldC].getValue() == 'p')
      {
        if(oldR - 1 >= 0 && board[oldR - 1][oldC].getValue() == '-')
        {
          possibleMoves.add(board[oldR - 1][oldC]);
        }
        if(oldR - 2 >= 0 && board[oldR][oldC].getMove() == true && board[oldR - 2][oldC].getValue() == '-' && board[oldR - 1][oldC].getValue() == '-')
        {
          possibleMoves.add(board[oldR - 2][oldC]);
        }
        if(oldR - 1 >= 0 && oldC - 1 >= 0 && board[oldR - 1][oldC - 1].getValue() != '-' && board[oldR - 1][oldC - 1].getTeam() == 1)
        {
          possibleMoves.add(board[oldR - 1][oldC - 1]);
        }
        if(oldR - 1 >= 0 && oldC + 1 <= 7 && board[oldR - 1][oldC + 1].getValue() != '-' && board[oldR - 1][oldC + 1].getTeam() == 1)
        {
          possibleMoves.add(board[oldR - 1][oldC + 1]);
        }
      }
      //Move check for uppercase pawns
      if(board[oldR][oldC].getValue() == 'P')
      {
        if(oldR + 1 <= 7 && board[oldR + 1][oldC].getValue() == '-')
        {
          possibleMoves.add(board[oldR + 1][oldC]);
        }
        if(oldR + 2 <= 7 && board[oldR][oldC].getMove() == true && board[oldR + 2][oldC].getValue() == '-' && board[oldR + 1][oldC].getValue() == '-')
        {
          possibleMoves.add(board[oldR + 2][oldC]);
        }
        if(oldR + 1 <= 7 && oldC - 1 >= 0 && board[oldR + 1][oldC - 1].getValue() != '-' && board[oldR + 1][oldC - 1].getTeam() == 0)
        {
          possibleMoves.add(board[oldR + 1][oldC - 1]);
        }
        if(oldR + 1 <= 7 && oldC + 1 <= 7 && board[oldR + 1][oldC + 1].getValue() != '-' && board[oldR + 1][oldC + 1].getTeam() == 0)
        {
          possibleMoves.add(board[oldR + 1][oldC + 1]);
        }
      }
      //Move check for rooks
      if(board[oldR][oldC].getValue() == 'r' || board[oldR][oldC].getValue() == 'R')
      {
        movement = 1;
        breaker = false;
        while(breaker == false && oldR - movement >= 0)
          {
            if(board[oldR - movement][oldC].getTeam() != board[oldR][oldC].getTeam())
            {
              possibleMoves.add(board[oldR - movement][oldC]);
              if(board[oldR - movement][oldC].getValue() != '-')
              {
                breaker = true;
              }
            }
            else
            {
              breaker = true;
            }
            movement++;
          }
        movement = 1;
        breaker = false;
        while(breaker == false && oldR + movement <= 7)
          {
            if(board[oldR + movement][oldC].getTeam() != board[oldR][oldC].getTeam())
            {
              possibleMoves.add(board[oldR + movement][oldC]);
              if(board[oldR + movement][oldC].getValue() != '-')
              {
                breaker = true;
              }
            }
            else
            {
              breaker = true;
            }
            movement++;
          }
        movement = 1;
        breaker = false;
        while(breaker == false && oldC - movement >= 0)
          {
            if(board[oldR][oldC - movement].getTeam() != board[oldR][oldC].getTeam())
            {
              possibleMoves.add(board[oldR][oldC - movement]);
              if(board[oldR][oldC - movement].getValue() != '-')
              {
                breaker = true;
              }
            }
            else
            {
              breaker = true;
            }
            movement++;
          }
        movement = 1;
        breaker = false;
        while(breaker == false && oldC + movement <= 7)
          {
            if(board[oldR][oldC + movement].getTeam() != board[oldR][oldC].getTeam())
            {
              possibleMoves.add(board[oldR][oldC + movement]);
              if(board[oldR][oldC + movement].getValue() != '-')
              {
                breaker = true;
              }
            }
            else
            {
              breaker = true;
            }
            movement++;
          }
      }
      //Move check for knights
      if(board[oldR][oldC].getValue() == 'k' || board[oldR][oldC].getValue() == 'K')
      {
        if(oldR - 1 >= 0)
        {
          if(oldC - 2 >= 0 && board[oldR - 1][oldC - 2].getTeam() != board[oldR][oldC].getTeam())
          {
            possibleMoves.add(board[oldR - 1][oldC - 2]);
          }
          if(oldC + 2 <= 7 && board[oldR - 1][oldC + 2].getTeam() != board[oldR][oldC].getTeam())
          {
            possibleMoves.add(board[oldR - 1][oldC + 2]);
          }
        }
        if(oldR + 1 <= 7)
        {
          if(oldC - 2 >= 0 && board[oldR + 1][oldC - 2].getTeam() != board[oldR][oldC].getTeam())
          {
            possibleMoves.add(board[oldR + 1][oldC - 2]);
          }
          if(oldC + 2 <= 7 && board[oldR + 1][oldC + 2].getTeam() != board[oldR][oldC].getTeam())
          {
            possibleMoves.add(board[oldR + 1][oldC + 2]);
          }
        }
        if(oldR - 2 >= 0)
        {
          if(oldC - 1 >= 0 && board[oldR - 2][oldC - 1].getTeam() != board[oldR][oldC].getTeam())
          {
            possibleMoves.add(board[oldR - 2][oldC - 1]);
          }
          if(oldC + 1 <= 7 && board[oldR - 2][oldC + 1].getTeam() != board[oldR][oldC].getTeam())
          {
            possibleMoves.add(board[oldR - 2][oldC + 1]);
          }
        }
        if(oldR + 2 <= 7)
        {
          if(oldC - 1 >= 0 && board[oldR + 2][oldC - 1].getTeam() != board[oldR][oldC].getTeam())
          {
            possibleMoves.add(board[oldR + 2][oldC - 1]);
          }
          if(oldC + 1 <= 7 && board[oldR + 2][oldC + 1].getTeam() != board[oldR][oldC].getTeam())
          {
            possibleMoves.add(board[oldR + 2][oldC + 1]);
          }
        }
      }
      //Move check for bishops
      if(board[oldR][oldC].getValue() == 'b' || board[oldR][oldC].getValue() == 'B')
      {
        movement = 1;
        breaker = false;
        while(breaker == false && oldR - movement >= 0 && oldC - movement >= 0)
          {
            if(board[oldR - movement][oldC - movement].getTeam() != board[oldR][oldC].getTeam())
            {
              possibleMoves.add(board[oldR - movement][oldC - movement]);
              if(board[oldR - movement][oldC - movement].getValue() != '-')
              {
                breaker = true;
              }
            }
            else
            {
              breaker = true;
            }
            movement++;
          }
        movement = 1;
        breaker = false;
        while(breaker == false && oldR - movement >= 0 && oldC + movement <= 7)
          {
            if(board[oldR - movement][oldC + movement].getTeam() != board[oldR][oldC].getTeam())
            {
              possibleMoves.add(board[oldR - movement][oldC + movement]);
              if(board[oldR - movement][oldC + movement].getValue() != '-')
              {
                breaker = true;
              }
            }
            else
            {
              breaker = true;
            }
            movement++;
          }
        movement = 1;
        breaker = false;
        while(breaker == false && oldR + movement <= 7 && oldC - movement >= 0)
          {
            if(board[oldR + movement][oldC - movement].getTeam() != board[oldR][oldC].getTeam())
            {
              possibleMoves.add(board[oldR + movement][oldC - movement]);
              if(board[oldR + movement][oldC - movement].getValue() != '-')
              {
                breaker = true;
              }
            }
            else
            {
              breaker = true;
            }
            movement++;
          }
        movement = 1;
        breaker = false;
        while(breaker == false && oldR + movement <= 7 && oldC + movement <= 7)
          {
            if(board[oldR + movement][oldC + movement].getTeam() != board[oldR][oldC].getTeam())
            {
              possibleMoves.add(board[oldR + movement][oldC + movement]);
              if(board[oldR + movement][oldC + movement].getValue() != '-')
              {
                breaker = true;
              }
            }
            else
            {
              breaker = true;
            }
            movement++;
          }
      }
      //Move check for queens
      if(board[oldR][oldC].getValue() == 'q' || board[oldR][oldC].getValue() == 'Q')
      {
        movement = 1;
        breaker = false;
        while(breaker == false && oldR - movement >= 0)
          {
            if(board[oldR - movement][oldC].getTeam() != board[oldR][oldC].getTeam())
            {
              possibleMoves.add(board[oldR - movement][oldC]);
              if(board[oldR - movement][oldC].getValue() != '-')
              {
                breaker = true;
              }
            }
            else
            {
              breaker = true;
            }
            movement++;
          }
        movement = 1;
        breaker = false;
        while(breaker == false && oldR + movement <= 7)
          {
            if(board[oldR + movement][oldC].getTeam() != board[oldR][oldC].getTeam())
            {
              possibleMoves.add(board[oldR + movement][oldC]);
              if(board[oldR + movement][oldC].getValue() != '-')
              {
                breaker = true;
              }
            }
            else
            {
              breaker = true;
            }
            movement++;
          }
        movement = 1;
        breaker = false;
        while(breaker == false && oldC - movement >= 0)
          {
            if(board[oldR][oldC - movement].getTeam() != board[oldR][oldC].getTeam())
            {
              possibleMoves.add(board[oldR][oldC - movement]);
              if(board[oldR][oldC - movement].getValue() != '-')
              {
                breaker = true;
              }
            }
            else
            {
              breaker = true;
            }
            movement++;
          }
        movement = 1;
        breaker = false;
        while(breaker == false && oldC + movement <= 7)
          {
            if(board[oldR][oldC + movement].getTeam() != board[oldR][oldC].getTeam())
            {
              possibleMoves.add(board[oldR][oldC + movement]);
              if(board[oldR][oldC + movement].getValue() != '-')
              {
                breaker = true;
              }
            }
            else
            {
              breaker = true;
            }
            movement++;
          }
        movement = 1;
        breaker = false;
        while(breaker == false && oldR - movement >= 0 && oldC - movement >= 0)
          {
            if(board[oldR - movement][oldC - movement].getTeam() != board[oldR][oldC].getTeam())
            {
              possibleMoves.add(board[oldR - movement][oldC - movement]);
              if(board[oldR - movement][oldC - movement].getValue() != '-')
              {
                breaker = true;
              }
            }
            else
            {
              breaker = true;
            }
            movement++;
          }
        movement = 1;
        breaker = false;
        while(breaker == false && oldR - movement >= 0 && oldC + movement <= 7)
          {
            if(board[oldR - movement][oldC + movement].getTeam() != board[oldR][oldC].getTeam())
            {
              possibleMoves.add(board[oldR - movement][oldC + movement]);
              if(board[oldR - movement][oldC + movement].getValue() != '-')
              {
                breaker = true;
              }
            }
            else
            {
              breaker = true;
            }
            movement++;
          }
        movement = 1;
        breaker = false;
        while(breaker == false && oldR + movement <= 7 && oldC - movement >= 0)
          {
            if(board[oldR + movement][oldC - movement].getTeam() != board[oldR][oldC].getTeam())
            {
              possibleMoves.add(board[oldR + movement][oldC - movement]);
              if(board[oldR + movement][oldC - movement].getValue() != '-')
              {
                breaker = true;
              }
            }
            else
            {
              breaker = true;
            }
            movement++;
          }
        movement = 1;
        breaker = false;
        while(breaker == false && oldR + movement <= 7 && oldC + movement <= 7)
          {
            if(board[oldR + movement][oldC + movement].getTeam() != board[oldR][oldC].getTeam())
            {
              possibleMoves.add(board[oldR + movement][oldC + movement]);
              if(board[oldR + movement][oldC + movement].getValue() != '-')
              {
                breaker = true;
              }
            }
            else
            {
              breaker = true;
            }
            movement++;
          }
      }
      //Move check for kings
      if(board[oldR][oldC].getValue() == 'w' || board[oldR][oldC].getValue() == 'W')
      {
        if(oldR - 1 >= 0)
        {
          if(oldC - 1 >= 0 && board[oldR - 1][oldC - 1].getTeam() != board[oldR][oldC].getTeam())
          {
            possibleMoves.add(board[oldR - 1][oldC - 1]);
          }
          possibleMoves.add(board[oldR - 1][oldC]);
          if(oldC + 1 <= 7 && board[oldR - 1][oldC + 1].getTeam() != board[oldR][oldC].getTeam())
          {
            possibleMoves.add(board[oldR - 1][oldC + 1]);
          }
        }
        if(oldR + 1 <= 7)
        {
          if(oldC - 1 >= 0 && board[oldR + 1][oldC - 1].getTeam() != board[oldR][oldC].getTeam())
          {
            possibleMoves.add(board[oldR + 1][oldC - 1]);
          }
          possibleMoves.add(board[oldR + 1][oldC]);
          if(oldC + 1 <= 7 && board[oldR + 1][oldC + 1].getTeam() != board[oldR][oldC].getTeam())
          {
            possibleMoves.add(board[oldR + 1][oldC + 1]);
          }
        }
        if(oldC - 1 >= 0)
        {
          possibleMoves.add(board[oldR][oldC - 1]);
        }
        if(oldC + 1 <= 7)
        {
          possibleMoves.add(board[oldR][oldC + 1]);
        }
      }
      for(int i = 0; i < possibleMoves.size(); i++)
      {
        if(possibleMoves.get(i) == board[newR][newC])
        {
          return false;
        }
      }
      return true;
    }

    //Check to see if either player has one by seeing if either king has been captured
    public char win()
    {
      boolean checkUpper = false;
      boolean checkLower = false;
      for(int i = 0; i < 8; i++)
      {
        for(int j = 0; j < 8; j++)
          {
            if(board[i][j].getValue() == 'W')
            {
              checkUpper = true;
            }
            if(board[i][j].getValue() == 'w')
            {
              checkLower = true;
            }
          }
      }
      if(!checkUpper)
      {
        return 'w';
      }
      if(!checkLower)
      {
        return 'W';
      }
      return '-';
    }
  }