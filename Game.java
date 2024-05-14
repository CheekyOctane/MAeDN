import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    private Board board;
    private int currentPlayer;
    private int diceValue;
    private boolean canRollDice;
    private int currentRolls;

    public Game(Board newBoard) {
        board = newBoard;
        currentPlayer = 1;
        diceValue = 0;
        canRollDice = true;
        currentRolls = 0;
    }


    public void handlePieceInput(Piece piece) {
        if (!isCorrectPiece(piece.getNumber(), currentPlayer)) {
            return;
        }
        if (diceValue == 0) {
            return;
        }
        System.out.println("Test");
    }

    public void handleDiceInput() {
        if (!canRollDice) {
            return;
        }
        int number = board.dice.roll();
        canRollDice = false;
        if (!hasPiecesOnBoard(currentPlayer)) { //executes when a player has no pieces on the board, gives them 3 rolls to get a 6
            if (number != 6) {
                currentRolls++;
                board.animateDice(number, () -> {
                    board.setInfoFieldText("Available moves: 0", 2);
                    canRollDice = true;
                    if (currentRolls == 3) {
                        nextPlayer();
                        currentRolls = 0;
                    }
                });
                return;
            } else {
                board.animateDice(number, () -> {
                    board.setInfoFieldText("Available moves: 0", 2);
                    canRollDice = true;
                    //TODO: Add piece to board and other checks
                });
            }
        }

        diceValue = number;
        board.animateDice(number, () -> { //The code for the Runnable aka the callback which can be executed at a later time
            board.setInfoFieldText("Available moves: " + diceValue, 2);
        });
    }

    private boolean isCorrectPiece(int pieceNumber, int selectedPlayer) { //compares if a given piece belongs to a given player
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

    private boolean isAtHome(int pieceNumber) {
        return board.piecePositions.get(pieceNumber) >= 16;
    }

    private boolean hasPiecesOnBoard(int player) { //checks if a given player has pieces on the board (not including home or finish) by getting their path and then checking if any of their pieces are on that path
        List<Integer> positions = board.positionManager.getColorPath(player);
        int index = 0;
        for (int position : positions) {
            index++;
            if (index <= 4 || index >= 45) {
                continue;
            }
            for (Map.Entry<Integer, Integer> entry : board.piecePositions.entrySet()) {
                if (entry.getValue() == position && isCorrectPiece(entry.getKey(), player)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void nextPlayer() { //Switches to the next player, also updates the info field
        if (currentPlayer == 4) {
            currentPlayer = 1;
            board.setInfoFieldText("Red to move", 1);
        } else {
            currentPlayer++;
            if (currentPlayer == 2) {
                board.setInfoFieldText("Blue to move", 1);
            } else if (currentPlayer == 3) {
                board.setInfoFieldText("Yellow to move", 1);
            } else {
                board.setInfoFieldText("Green to move", 1);
            }
        }
    }
}