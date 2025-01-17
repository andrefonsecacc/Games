package com.chess.engine.Pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;

public class Bishop extends Piece {

	private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = { -9, -7, 7,
			9 };

	Bishop(int piecePosition, Alliance pieceAlliance) {
		super(piecePosition, pieceAlliance);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Collection<Move> calculateLegalMoves(Board board) {

		final List<Move> legalMoves = new ArrayList<>();

		for (final int candidateCoordinateOffset : CANDIDATE_MOVE_VECTOR_COORDINATES) {

			int candidateDestinationCoordinate = this.piecePosition;

			while (BoardUtils
					.isValidTileCoordinate(candidateDestinationCoordinate)) {
				candidateDestinationCoordinate += candidateCoordinateOffset;

				if (isEightColumnExclusion(candidateDestinationCoordinate,
						candidateCoordinateOffset)
						|| isFirstColumnExclusion(
								candidateDestinationCoordinate,
								candidateCoordinateOffset)) {
					break;
				}

				if (BoardUtils
						.isValidTileCoordinate(candidateDestinationCoordinate)) {

					final Tile candidateDestinationTile = board
							.getTile(candidateDestinationCoordinate);

					if (!candidateDestinationTile.isTileOccupied()) {
						legalMoves.add(new Move.MajorMove(board, this,
								candidateDestinationCoordinate));
					} else {
						final Piece pieceAtDestination = candidateDestinationTile
								.getPiece();
						final Alliance pieceAlliance = pieceAtDestination
								.getPieceAlliance();

						if (this.pieceAlliance != pieceAlliance) {
							legalMoves.add(new Move.AttackMove(board, this,
									candidateDestinationCoordinate,
									pieceAtDestination));
						}

					}
					break;

				}

			}
		}

		return Collections.unmodifiableList(legalMoves);
	}

	private static boolean isFirstColumnExclusion(final int currentPosition,
			final int candidateOffset) {
		return BoardUtils.FIRST_COLUMN[currentPosition]
				&& (candidateOffset == -9 || candidateOffset == 7);
	}

	private static boolean isEightColumnExclusion(final int currentPosition,
			final int candidateOffset) {
		return BoardUtils.EIGHT_COLUMN[currentPosition]
				&& (candidateOffset == -7 || candidateOffset == 9);
	}

}
