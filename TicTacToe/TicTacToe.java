package org.freqphrs;

import java.util.Scanner;

public class TicTacToe {
  
  private char[][] board;
  private int dim;
  private int validmovescount = 0;
  private enum Symbols{X,O;}
  
  public TicTacToe(int N){
    this.dim = N;
    board = new char[N][N];
    for(int i=0;i<N;i++) for(int j=0;j<N;j++) board[i][j] = '-';
  }
  
  public void start(){
    Scanner sc = new Scanner(System.in);
    System.out.println("Which One to Choose X or O");
    String P1Symbol = sc.next();
    if(!("X").equals(P1Symbol) && !("O").equals(P1Symbol)) throw new IllegalArgumentException("Invalid Symbol by P1, choose X OR Y"); 
    String P2Symbol = Symbols.valueOf(P1Symbol).compareTo(Symbols.O) == 0 ? Symbols.X.name(): Symbols.O.name();
    System.out.println(String.format("Player One Symbol is %s",P1Symbol));
    System.out.println(String.format("Player Two Symbol is %s",P2Symbol));
    Player P1 =  new Player(P1Symbol, "P1");
    Player P2 =  new Player(P2Symbol, "P2");
    Player currentPlayer  = P1;
    Player prevPlayer = null;
    int [] inputs = new int [2];
    showboard();
    System.out.println(String.format(" Next Player turn : %s (Symbol in the form of I  J coordinates) %s", currentPlayer.getName(),currentPlayer.getSymbol()));
    while(sc.hasNext()){
      //coordinates in the form of (i,j)
      inputs[0] = sc.nextInt();
      inputs[1] = sc.nextInt();
      System.out.println(String.format("Inputs are %d, %d",inputs[0],inputs[1]));
      while(!validmove(inputs, currentPlayer, prevPlayer)){
        System.out.println(String.format("Player: %s move is invalid, Pls enter move Again , %s", currentPlayer.getName(),currentPlayer.getSymbol()));
        inputs[0] = sc.nextInt();
        inputs[1] = sc.nextInt();
        System.out.print(String.format("Inputs are %d, %d",inputs[0],inputs[1]));
        showboard();
      }
      if(isGameDone(inputs, currentPlayer)){
        System.out.println(String.format("Player: %s WON", currentPlayer.getName()));
        showboard();
        System.exit(0);
      }
      else if(validmovescount == (board.length * board.length)) {
        System.out.println(String.format("Game Exhausted No WINNER"));
        showboard();
        System.exit(0);
      }
      prevPlayer = currentPlayer;
      currentPlayer = "P1".equals(currentPlayer.getName()) ? P2 :P1;
      System.out.println(String.format(" Next Player turn : %s (Symbol in the form of I  J coordinates), %s", currentPlayer.getName(),currentPlayer.getSymbol()));
     }
  }
  
  
    private boolean validmove(int[] inps, Player currply, Player prevply){
      if(inps[0] < 0 || inps[1] < 0 || inps[1] > board.length -1 || inps[1] > board.length -1) return false;
      else if(board[inps[0]][inps[1]] != '-') return false;
      else if(prevply != null && currply.getName().equals(prevply.getName())) return false;
      board[inps[0]][inps[1]] = currply.getSymbol().charAt(0);
      validmovescount+=1;
      showboard();
      return true;
    }
    
  
    private boolean isGameDone(int[] inputs, Player currply) {
      // TODO Auto-generated method stub
      if(checkrow(inputs, currply)) return true;
      else if(checkColumn(inputs, currply)) return true;
      if(inputs[0] == inputs[1]){
        return (checkdiagnol(inputs, currply) || checkreversediagnol(inputs, currply)); 
      }
      return false;
    }

    private boolean checkrow(int[] inputs, Player currply){
      char ch = currply.getSymbol().charAt(0);
      //Check for Row
      for(int i=0;i<board.length;i++){
        if(board[inputs[0]][i] != ch) return false;
      }
      return true;
    }
    
    private boolean checkColumn(int[] inputs, Player currply){
      //check for Column
      char ch = currply.getSymbol().charAt(0);
      
      for(int i=0;i<board.length;i++){
        if(board[i][inputs[1]] != ch) return false;
      }
      return true;
    }
    
    private boolean checkdiagnol(int[] inputs, Player currply){
      char ch = currply.getSymbol().charAt(0);
      for(int i=0;i<board.length;i++){
        if(board[i][i] != ch) return false;
      }
      return true;
    }
    
    private boolean checkreversediagnol(int[] inputs, Player currply){
      char ch = currply.getSymbol().charAt(0);
      for(int i=0;i<board.length;i++){
        if(board[board.length -(i+1)][i] != ch) return false;
      }
      return true;
    }

    public void showboard(){
      for(int i=0;i<dim;i++) {
        for(int j=0;j<dim;j++){
          System.out.print(board[i][j] + " ");
        }
        System.out.println();
      }
    }
    
    private static class Player{
      private String symbol;
      private String name;

      public Player(String symbol, String name){
        this.symbol = symbol;
        this.name = name;
      }

    public String getSymbol() {
      return symbol;
    }
    public String getName() {
      return name;
    }
  }
    
    public static void main(String args[]){
      TicTacToe ttt = new TicTacToe(4);
      ttt.start();
    }
  
}
