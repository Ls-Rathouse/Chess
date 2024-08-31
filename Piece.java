public class Piece
  {
    private char type;
    private boolean firstMove;
    private int capitalTeam;

    public Piece(char pieceType)
    {
      this.type = pieceType;
      firstMove = true;
      String stringType = pieceType + "";
      //Determine what team a piece is on via its value
      if(stringType.equals(stringType.toUpperCase()))
      {
        capitalTeam = 1;
      }
      else
      {
        capitalTeam = 0;
      }
      if(pieceType == '-')
      {
        capitalTeam = 2;
      }
      
    }

    //Returns what type of piece the piece is
    public char getValue()
    {
      return type;
    }

    //Returns the team a piece is on
    public int getTeam()
    {
      return capitalTeam;
    }

    //Returns whether or not the piece has move once yet
    public boolean getMove()
    {
      return firstMove;
    }

    //Change one type of piece into another
    public void newPiece(char type, int team)
    {
      this.type = type;
      firstMove = false;
      capitalTeam = team;
    }

    //Changes a value on the board to be empty
    public void reset()
    {
      type = '-';
      capitalTeam = 2;
    }
  }