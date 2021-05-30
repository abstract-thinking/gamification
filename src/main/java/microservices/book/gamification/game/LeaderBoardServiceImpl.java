package microservices.book.gamification.game;


import lombok.RequiredArgsConstructor;
import microservices.book.gamification.game.domain.LeaderBoardRow;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeaderBoardServiceImpl implements LeaderBoardService {

    private final ScoreRepository scoreRepository;
    private final BadgeRepository badgeRepository;

    @Override
    public List<LeaderBoardRow> getCurrentLeaderBoard() {
        return scoreRepository.findFirst10().stream()
                .map(row -> row.withBadges(retrieveBadges(row)))
                .collect(Collectors.toList());
    }

    private List<String> retrieveBadges(LeaderBoardRow row) {
        return badgeRepository.findByUserIdOrderByBadgeTimestampDesc(row.getUserId()).stream()
                .map(b -> b.getBadgeType().getDescription())
                .collect(Collectors.toList());
    }
}
