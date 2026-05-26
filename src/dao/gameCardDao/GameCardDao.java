package dao.gameCardDao;

import abst.daoAbst.gameCard.IGameCardReadable;
import abst.daoAbst.gameCard.IGameCardWriteable;
import dao.DaoConnection;
import dto.gameCardDto.GameCardRequestDto;
import dto.gameCardDto.GameCardResponseDto;
import enums.CardLocation;
import enums.CardType;
import enums.DeckType;
import enums.SpecialCardType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GameCardDao implements
        IGameCardReadable,
        IGameCardWriteable {

    private static GameCardDao instance;

    public static GameCardDao getInstance() {
        if (instance == null) {
            instance = new GameCardDao();
        }
        return instance;
    }

    private GameCardDao() {
    }

    @Override
    public List<GameCardResponseDto> findByGameSessionId(int gameSessionId) {

        List<GameCardResponseDto> gameCards = new ArrayList<>();

        String sql = """
                SELECT id, game_session_id, card_id, deck_type, card_type, 
                       special_card_type, card_value, location, position_index, is_used
                FROM game_cards
                WHERE game_session_id = ?
                ORDER BY position_index
                """;

        try (Connection connection = DaoConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, gameSessionId);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    gameCards.add(mapResultSetToDto(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch game cards by session id: " + e.getMessage(), e);
        }

        return gameCards;
    }

    @Override
    public List<GameCardResponseDto> findByGameSessionIdAndLocation(int gameSessionId, CardLocation location) {

        List<GameCardResponseDto> gameCards = new ArrayList<>();

        String sql = """
                SELECT id, game_session_id, card_id, deck_type, card_type,
                       special_card_type, card_value, location, position_index, is_used
                FROM game_cards
                WHERE game_session_id = ? AND location = ?
                ORDER BY position_index
                """;

        try (Connection connection = DaoConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, gameSessionId);
            statement.setString(2, location.name());

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    gameCards.add(mapResultSetToDto(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch game cards by session id and location: " + e.getMessage(), e);
        }

        return gameCards;
    }

    @Override
    public GameCardResponseDto findById(int id) {

        String sql = """
                SELECT id, game_session_id, card_id, deck_type, card_type, 
                       special_card_type, card_value, location, position_index, is_used
                FROM game_cards
                WHERE id = ?
                """;

        try (Connection connection = DaoConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToDto(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch game card by id: " + e.getMessage(), e);
        }

        return null;
    }

    @Override
    public void save(GameCardRequestDto requestDto) {

        String sql = """
                INSERT INTO game_cards (
                    game_session_id, card_id, deck_type, card_type, special_card_type, 
                    card_value, location, position_index, is_used
                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DaoConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            setPreparedStatementForSave(statement, requestDto);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to save game card: " + e.getMessage(), e);
        }
    }

    @Override
    public void saveAll(List<GameCardRequestDto> requestDtos) {

        if (requestDtos == null || requestDtos.isEmpty()) {
            return;
        }

        String sql = """
                INSERT INTO game_cards (
                    game_session_id, card_id, deck_type, card_type, special_card_type, 
                    card_value, location, position_index, is_used
                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DaoConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Döngü içinde her bir kayıt tek tek çalıştırılıyor
            for (GameCardRequestDto requestDto : requestDtos) {
                setPreparedStatementForSave(statement, requestDto);
                statement.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to save all game cards: " + e.getMessage(), e);
        }
    }

    @Override
    public void updateLocation(int gameCardId, CardLocation location, int positionIndex) {

        String sql = """
                UPDATE game_cards
                SET location = ?, position_index = ?
                WHERE id = ?
                """;

        try (Connection connection = DaoConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, location.name());
            statement.setInt(2, positionIndex);
            statement.setInt(3, gameCardId);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("No game card found to update location with id: " + gameCardId);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update game card location: " + e.getMessage(), e);
        }
    }

    @Override
    public void updateUsedStatus(int gameCardId, boolean used) {

        String sql = """
                UPDATE game_cards
                SET is_used = ?
                WHERE id = ?
                """;

        try (Connection connection = DaoConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setBoolean(1, used);
            statement.setInt(2, gameCardId);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("No game card found to update used status with id: " + gameCardId);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update game card used status: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteByGameSessionId(int gameSessionId) {

        String sql = """
                DELETE FROM game_cards
                WHERE game_session_id = ?
                """;

        try (Connection connection = DaoConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, gameSessionId);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete game cards by session id: " + e.getMessage(), e);
        }
    }

    // --- HELPER METHODS ---

    private GameCardResponseDto mapResultSetToDto(ResultSet rs) throws SQLException {
        return new GameCardResponseDto(
                rs.getInt("id"),
                rs.getInt("game_session_id"),
                rs.getInt("card_id"),
                DeckType.valueOf(rs.getString("deck_type")),
                CardType.valueOf(rs.getString("card_type")),
                rs.getString("special_card_type") != null
                        ? SpecialCardType.valueOf(rs.getString("special_card_type"))
                        : null,
                (Integer) rs.getObject("card_value"),
                CardLocation.valueOf(rs.getString("location")),
                rs.getInt("position_index"),
                rs.getBoolean("is_used")
        );
    }

    private void setPreparedStatementForSave(PreparedStatement statement, GameCardRequestDto requestDto) throws SQLException {
        statement.setInt(1, requestDto.getGameSessionId());
        statement.setInt(2, requestDto.getCardId());
        statement.setString(3, requestDto.getDeckType() != null ? requestDto.getDeckType().name() : null);
        statement.setString(4, requestDto.getCardType() != null ? requestDto.getCardType().name() : null);
        statement.setString(5, requestDto.getSpecialCardType() != null ? requestDto.getSpecialCardType().name() : null);

        if (requestDto.getCardValue() != null) {
            statement.setInt(6, requestDto.getCardValue());
        } else {
            statement.setNull(6, java.sql.Types.INTEGER);
        }

        statement.setString(7, requestDto.getLocation() != null ? requestDto.getLocation().name() : null);
        statement.setInt(8, requestDto.getPositionIndex());
        statement.setBoolean(9, requestDto.isUsed());
    }
}