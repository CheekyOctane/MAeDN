import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    private Board board;
    private int currentPlayer;
    private int diceValue;
    private boolean canRollDice;
    private int currentRolls;
    private boolean canMovePieceOut;
    private boolean mustClearStartField;
    private boolean canRollAgain;

    public Game(Board newBoard) {
        board = newBoard;
        currentPlayer = 1;
        diceValue = 0;
        canRollDice = true;
        currentRolls = 0;
        canMovePieceOut = false;
        mustClearStartField = false;
        canRollAgain = false;
    }


    public void handlePieceInput(Piece piece) {
        if (!isCorrectPiece(piece.getNumber(), currentPlayer)) {
            return;
        }
        if (!canMovePieceOut && wouldLandOnSameColorPiece(piece, diceValue)) {
            return;
        }
        if (!canMovePieceOut && getPiecePositionInColorPath(piece) <= 3) {
            return;
        }
        List<Integer> colorList = board.positionManager.getColorPath(currentPlayer);
        if (mustClearStartField) {
            int startField = board.positionManager.getColorPath(currentPlayer).get(4);
            int nextField = board.positionManager.getColorPath(currentPlayer).get(10);
            int nextField2 = board.positionManager.getColorPath(currentPlayer).get(16);
            if (hasPieceOnField(currentPlayer, nextField2) && hasPieceOnField(currentPlayer, nextField) && nextField2 == board.piecePositions.get(piece.getNumber()) || hasPieceOnField(currentPlayer, nextField) && nextField == board.piecePositions.get(piece.getNumber()) || hasPieceOnField(currentPlayer, startField) && startField == board.piecePositions.get(piece.getNumber())) {
                for (int i : colorList) {
                    if (isCorrectPiece(piece.getNumber(), currentPlayer) && board.piecePositions.get(piece.getNumber()) == i) {
                        movePiece(piece, colorList.get(colorList.indexOf(i) + diceValue));
                        throwPlayerOut(piece);
                        canRollDice = true;
                        mustClearStartField = false;
                        board.setInfoFieldText("Available moves: 0", 2);
                        diceValue = 0;
                        return;
                    }
                }
            } else {
                return;
            }
        }
        if (canMovePieceOut) {
            int piecePosition = board.piecePositions.get(piece.getNumber());
            // Get the first 4 positions of the ColorPath for the current player
            List<Integer> startingPositions = board.positionManager.getColorPath(currentPlayer).subList(0, 4);
            // Check if the clicked piece is on the first 4 fields of the ColorPath
            if (startingPositions.contains(piecePosition)) {
                movePiece(piece, board.positionManager.getColorPath(currentPlayer).get(4));
                throwPlayerOut(piece);
                canMovePieceOut = false;
                board.setInfoFieldText("Available moves: " + diceValue, 2);
                canRollDice = true;
            }
            return;
        }
        if (diceValue == 0) {
            return;
        }
        movePiece(piece, colorList.get(colorList.indexOf(board.piecePositions.get(piece.getNumber())) + diceValue));
        throwPlayerOut(piece);
        diceValue = 0;
        board.setInfoFieldText("Available moves: 0", 2);
        checkForNextPlayer();
    }

    public void handleDiceInput() {
        if (!canRollDice) {
            return;
        }
        int number = board.dice.roll(getBoostedChanceLvl());
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
            } else {
                board.animateDice(number, () -> {
                    canMovePieceOut = true;
                    board.setInfoFieldText("You can move a piece out!", 2);
                    currentRolls = 0;
                });
            }
            return;
        }

        if (number == 6) {
            if (hasPieceInHome()) {
                board.animateDice(number, () -> {
                    if (hasPieceOnField(currentPlayer, board.positionManager.getColorPath(currentPlayer).get(4))) {
                        board.setInfoFieldText("You need to clear your starting field! (6 moves)", 2);
                        mustClearStartField = true;
                        diceValue = 6;
                    } else {
                        canMovePieceOut = true;
                        board.setInfoFieldText("You can move a piece out!", 2);
                    }
                });
                return;
            } else {
                canRollAgain = true;
            }
        }

        diceValue = number;
        board.animateDice(number, () -> { //The code for the Runnable aka the callback which can be executed at a later time
            if (!canPiecesMove()) {
                nextPlayer();
                return;
            }
            board.setInfoFieldText("Available moves: " + diceValue, 2);
        });
    }



    public int getBoostedChanceLvl(){
        board.dice.boostedChanceLvl = 1;    //default value for boostedChance --> no boost
        if(!hasPiecesOnBoard(currentPlayer)){
            board.dice.boostedChanceLvl = 2;
        }
        return board.dice.boostedChanceLvl;
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

    private void movePiece(Piece piece, int field) {
        board.piecePositions.replace(piece.getNumber(), field);
        board.movePiece(piece, board.positionManager.getPosition(field).x, board.positionManager.getPosition(field).y);
    }

    private void checkForNextPlayer() {
        if (!canRollAgain) {
            nextPlayer();
        } else {
            canRollAgain = false;
        }
        canRollDice = true;
    }

    private void throwPlayerOut(Piece piece) {
        int piecePosition = board.piecePositions.get(piece.getNumber());

        for (Map.Entry<Integer, Integer> entry : board.piecePositions.entrySet()) {
            if (!isCorrectPiece(entry.getKey(), currentPlayer) && entry.getValue() == piecePosition) {
                // Get the color path for the piece to be moved back
                List<Integer> colorPath = board.positionManager.getColorPath(getPlayerFromPiece(entry.getKey()));

                // Find a free home field
                for (int i = 0; i < 4; i++) {
                    if (!hasPieceOnField(getPlayerFromPiece(entry.getKey()), colorPath.get(i))) {
                        // Move the piece back to home
                        movePiece(getPieceFromNumber(entry.getKey()), colorPath.get(i));
                        break;
                    }
                }
            }
        }
    }

    private int getPlayerFromPiece(int pieceNumber) {
            return (pieceNumber - 1) / 4 + 1;
    }

    private Piece getPieceFromNumber(int pieceNumber) {
        for (Piece piece : board.circles) {
            if (piece.getNumber() == pieceNumber) {
                return piece;
            }
        }
        return null;
    }

    private boolean hasPieceOnField(int player, int field) {
        for (Map.Entry<Integer, Integer> entry : board.piecePositions.entrySet()) {
            if (entry.getValue() == field && isCorrectPiece(entry.getKey(), player)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasPieceInHome() {
        for (Map.Entry<Integer, Integer> entry : board.piecePositions.entrySet()) {
            if (isAtHome(entry.getKey()) && isCorrectPiece(entry.getKey(), currentPlayer)) {
                return true;
            }
        }
        return false;
    }

    private boolean canPiecesMove() {
        // Get the finish positions for the current player
        List<Integer> finishPositions = board.positionManager.getColorPath(currentPlayer).subList(44, 48);

        // Iterate over all the pieces of the current player
        for (Map.Entry<Integer, Integer> entry : board.piecePositions.entrySet()) {
            if (isCorrectPiece(entry.getKey(), currentPlayer)) {
                // Check if the piece is in front of the finish
                if (finishPositions.contains(entry.getValue() + diceValue)) {
                    // Check if the field it would move to is already occupied
                    if (hasPieceOnField(currentPlayer, entry.getValue() + diceValue)) {
                        continue;
                    } else {
                        // If at least one piece can move, return true
                        return true;
                    }
                } else {
                    // If the piece is not in front of the finish, check if it can move to the next field
                    if (!hasPieceOnField(currentPlayer, entry.getValue() + diceValue)) {
                        // If the next field is not occupied, return true
                        return true;
                    }
                }
            }
        }

        // If all pieces are checked and none can move, return false
        return false;
    }

    private boolean wouldLandOnSameColorPiece(Piece piece, int diceRoll) {
        List<Integer> colorPath = board.positionManager.getColorPath(currentPlayer);
        int currentPositionIndex = colorPath.indexOf(board.piecePositions.get(piece.getNumber()));
        int newPositionIndex = currentPositionIndex + diceRoll;

        if (newPositionIndex < colorPath.size()) {
            int newField = colorPath.get(newPositionIndex);

            for (Map.Entry<Integer, Integer> entry : board.piecePositions.entrySet()) {
                if (isCorrectPiece(entry.getKey(), currentPlayer) && entry.getValue() == newField) {
                    return true;
                }
            }
        }

        return false;
    }

    private int getPiecePositionInColorPath(Piece piece) {
        List<Integer> colorPath = board.positionManager.getColorPath(currentPlayer);
        return colorPath.indexOf(board.piecePositions.get(piece.getNumber()));
    }
}