package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move.*;
import com.chess.engine.board.Move;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Pawn extends Piece {
    private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = {8, 16, 7, 9};

    Pawn(final int piecePosition, final Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentCandidateOffset: CANDIDATE_MOVE_VECTOR_COORDINATES) {
            final int candidateDestinationCoordinate = this.piecePosition + (this.getPieceAlliance().getDirection() * currentCandidateOffset);

            if (!BoardUtils.isValidSquareCoordinate(candidateDestinationCoordinate)) {
                continue;
            }

            if (currentCandidateOffset == 8 && !board.getSquare(candidateDestinationCoordinate).isOccupied()) {
                 // TODO more work to do here
                legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
            } else if (currentCandidateOffset == 16 && this.isFirstMove() &&
                    (BoardUtils.SECOND_ROW[this.piecePosition] && this.getPieceAlliance().isBlack()) ||
                    (BoardUtils.SEVENTH_ROW[this.piecePosition] && this.getPieceAlliance().isWhite())) {
                final int behindCandidateDestinationCoordinate = this.piecePosition + (this.getPieceAlliance().getDirection() * 8);

                if (!board.getSquare(behindCandidateDestinationCoordinate).isOccupied() &&
                        !board.getSquare(candidateDestinationCoordinate).isOccupied()) {
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                }
            } else if (currentCandidateOffset == 7 &&
                    !((BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.getPieceAlliance().isWhite()) ||
                    (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.getPieceAlliance().isBlack()))) {
                 if (board.getSquare(candidateDestinationCoordinate).isOccupied()) {
                     final Piece pieceOnCandidate = board.getSquare(candidateDestinationCoordinate).getPiece();
                     if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
                         // TODO more work to do here
                          legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                     }
                 }
             } else if (currentCandidateOffset == 9 &&
            !((BoardUtils.FIRST_COLUMN[this.piecePosition] && this.getPieceAlliance().isWhite()) || (BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.getPieceAlliance().isBlack()))) {
                if (board.getSquare(candidateDestinationCoordinate).isOccupied()) {
                    final Piece pieceOnCandidate = board.getSquare(candidateDestinationCoordinate).getPiece();
                    if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
                        // TODO more work to do here
                        legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                    }
                }
            }
        }

        return ImmutableList.copyOf(legalMoves);
    }
}
