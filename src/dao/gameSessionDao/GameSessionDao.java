package dao.gameSessionDao;

import abst.daoAbst.gameSession.IGameSessionReadable;
import abst.daoAbst.gameSession.IGameSessionWriteable;
import dao.DaoConnection;
import dto.gameSessionDto.GameSessionRequestDto;
import dto.gameSessionDto.GameSessionResponseDto;
import enums.DeckType;
import enums.DifficultyType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameSessionDao implements
        IGameSessionReadable,
        IGameSessionWriteable {

    private static GameSessionDao instance;

    public static GameSessionDao getInstance() {
        if (instance == null) {
            instance = new GameSessionDao();
        }
        return instance;
    }

    private GameSessionDao() {
    }

    @Override
    public GameSessionResponseDto findById(int id) {

        int counter = 1;

        String sql = """
                SELECT id, created_at, updated_at, deck_type,
                       difficulty, current_round_number, total_score,
                       total_target_score, total_discard_count,
                       game_over, player_won
                FROM game_sessions
                WHERE id = ?
                """;

        try (Connection connection = DaoConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(counter, id);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return mapResultSetToDto(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Game session check failed. " + e.getMessage(), e);
        }

        return null;
    }

    @Override
    public List<GameSessionResponseDto> findByUserId(int userId) {

        List<GameSessionResponseDto> sessions = new ArrayList<>();

        String sql = """
                SELECT id, created_at, updated_at, deck_type,
                       difficulty, current_round_number, total_score,
                       total_target_score, total_discard_count,
                       game_over, player_won
                FROM game_sessions
                WHERE user_id = ?
                ORDER BY created_at DESC
                """;

        try (Connection connection = DaoConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {

                GameSessionResponseDto dto =
                        mapResultSetToDto(rs);

                sessions.add(dto);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch game sessions: " + e.getMessage(), e);
        }

        return sessions;

    }

    @Override
    public GameSessionResponseDto findLastActiveSessionByUserId(int userId) {

        String sql = """
                SELECT id, created_at, updated_at, deck_type,
                       difficulty, current_round_number, total_score,
                       total_target_score, total_discard_count,
                       game_over, player_won
                FROM game_sessions
                WHERE user_id = ?
                  AND game_over = false
                ORDER BY updated_at DESC
                LIMIT 1
                """;

        try (Connection connection = DaoConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {

                return mapResultSetToDto(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Failed to fetch last active session: " + e.getMessage(),
                    e
            );
        }

        return null;
    }

    @Override
    public GameSessionResponseDto save(GameSessionRequestDto requestDto) {

        String sql = """
                INSERT INTO game_sessions (
                    user_id,
                    deck_type,
                    difficulty,
                    current_round_number,
                    total_score,
                    total_target_score,
                    total_discard_count,
                    game_over,
                    player_won
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DaoConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sql,
                     Statement.RETURN_GENERATED_KEYS
             )) {

            statement.setInt(1, requestDto.getUserId());
            // Request DTO'nun da Enum kullandığı varsayılarak DB'ye String olarak (.name()) kaydediliyor.
            statement.setString(2, requestDto.getDeckType() != null ? requestDto.getDeckType().name() : null);
            statement.setString(3, requestDto.getDifficulty() != null ? requestDto.getDifficulty().name() : null);
            statement.setInt(4, requestDto.getCurrentRoundNumber());
            statement.setInt(5, requestDto.getTotalScore());
            statement.setInt(6, requestDto.getTotalTargetScore());
            statement.setInt(7, requestDto.getTotalDiscardCount());
            statement.setBoolean(8, requestDto.isGameOver());
            statement.setBoolean(9, requestDto.isPlayerWon());

            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {

                if (generatedKeys.next()) {

                    int generatedId = generatedKeys.getInt(1);

                    return findById(generatedId);
                }
            }

            throw new RuntimeException("Game session could not be saved. No generated id returned.");

        } catch (SQLException e) {
            throw new RuntimeException("Failed to save game session: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(GameSessionResponseDto responseDto) {

        String sql = """
                UPDATE game_sessions
                SET
                    updated_at = CURRENT_TIMESTAMP,
                    current_round_number = ?,
                    total_score = ?,
                    total_target_score = ?,
                    total_discard_count = ?,
                    game_over = ?,
                    player_won = ?
                WHERE id = ?
                """;

        try (Connection connection = DaoConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    responseDto.getCurrentRoundNumber()
            );

            statement.setInt(
                    2,
                    responseDto.getTotalScore()
            );

            statement.setInt(
                    3,
                    responseDto.getTotalTargetScore()
            );

            statement.setInt(
                    4,
                    responseDto.getTotalDiscardCount()
            );

            statement.setBoolean(
                    5,
                    responseDto.isGameOver()
            );

            statement.setBoolean(
                    6,
                    responseDto.isPlayerWon()
            );

            statement.setInt(
                    7,
                    responseDto.getId()
            );

            int affectedRows =
                    statement.executeUpdate();

            if (affectedRows == 0) {

                throw new RuntimeException(
                        "No game session found with id: "
                                + responseDto.getId()
                );
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Failed to update game session: "
                            + e.getMessage(),
                    e
            );
        }
    }

    @Override
    public void markGameAsFinished(int gameSessionId, boolean playerWon) {

        String sql = """
                UPDATE game_sessions
                SET
                    game_over = true,
                    player_won = ?,
                    updated_at = CURRENT_TIMESTAMP
                WHERE id = ?
                """;

        try (Connection connection = DaoConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setBoolean(1, playerWon);

            statement.setInt(2, gameSessionId);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {

                throw new RuntimeException(
                        "No game session found with id: "
                                + gameSessionId
                );
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Failed to mark game session as finished: "
                            + e.getMessage(),
                    e
            );
        }
    }

    //HELPER
    private GameSessionResponseDto mapResultSetToDto(ResultSet rs)
            throws SQLException {

        String deckTypeStr = rs.getString("deck_type");
        DeckType deckType = deckTypeStr != null ? DeckType.valueOf(deckTypeStr) : null;

        String difficultyStr = rs.getString("difficulty");
        DifficultyType difficulty = difficultyStr != null ? DifficultyType.valueOf(difficultyStr) : null;

        return new GameSessionResponseDto(
                rs.getInt("id"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime(),
                deckType,
                difficulty,
                rs.getInt("current_round_number"),
                rs.getInt("total_score"),
                rs.getInt("total_target_score"),
                rs.getInt("total_discard_count"),
                rs.getBoolean("game_over"),
                rs.getBoolean("player_won")
        );
    }

}