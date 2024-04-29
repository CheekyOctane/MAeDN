import java.util.HashMap;

public class Game {
    private Board board;
    private int currentPlayer;
    private int diceValue;
    private boolean canRollDice;

    public Game(Board newBoard) {
        board = newBoard;
        currentPlayer = 1;
        diceValue = 0;
        canRollDice = true;
    }


    public void handlePieceInput(Piece piece) {
        if (!isCorrectPiece(piece.getNumber(), currentPlayer)) {
            return;
        }
        System.out.println("Test");
    }

    public void handleDiceInput() {
        if (!canRollDice) {
            return;
        }
        int number = board.dice.roll();
        if (number != 6) {
            canRollDice = false;
        }
        diceValue += number;
        board.animateDice(number, () -> { //The code for the Runnable aka the callback which can be executed at a later time
            board.setInfoFieldText("Available moves: " + diceValue, 2);
        });
    }

    private boolean isCorrectPiece(int pieceNumber, int selectedPlayer) {
        HashMap<Integer, Integer> playerPieces;
        playerPieces = new HashMap<>();
        playerPieces.put(1, 1);
        playerPieces.put(2, 1);
        playerPieces.put(3, 1);
        playerPieces.put(4, 1);
        playerPieces.put(5, 2);
        playerPieces.put(6, 2);
        playerPieces.put(7, 2);
        playerPieces.put(8, 2);
        playerPieces.put(9, 3);
        playerPieces.put(10, 3);
        playerPieces.put(11, 3);
        playerPieces.put(12, 3);
        playerPieces.put(13, 4);
        playerPieces.put(14, 4);
        playerPieces.put(15, 4);
        playerPieces.put(16, 4);

        return playerPieces.get(pieceNumber) == selectedPlayer;
    }
}
