package jp.co.jc21ps.activity_management.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import jp.co.jc21ps.activity_management.entity.JoinRequestEntity;
import jp.co.jc21ps.activity_management.entity.JoinRequestSaveEntity;

@Repository
public class JoinRequestRepository {
    private final JdbcTemplate jdbcTemplate;

    public JoinRequestRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 初期画面表示
    public List<JoinRequestEntity> getJoinRequestById(JoinRequestEntity paramEntity) {
        /*
         * TODO ➊ 初期表示情報を取得するSQLを完成させる。
         */
        String sql = """
                SELECT
                    mst_club.*
                FROM
                    mst_club
                WHERE
                    NOT EXISTS (
                        SELECT 1
                        FROM trn_join_request
                        WHERE trn_join_request.club_id = mst_club.club_id
                        AND trn_join_request.user_id = ?
                    )
                    AND NOT EXISTS (
                        SELECT 1
                        FROM trn_club_member
                        WHERE trn_club_member.club_id = mst_club.club_id
                        AND trn_club_member.user_id = ?
                    )
                """;

        List<JoinRequestEntity> responseEntity = new ArrayList<>();
        List<Map<String, Object>> joinRequestList = jdbcTemplate.queryForList(sql, paramEntity.getUserId(),
                paramEntity.getUserId());

        // リストが空だった場合
        if (joinRequestList.isEmpty()) {
            return responseEntity;
        }

        for (Map<String, Object> joinRequest : joinRequestList) {

            // entityに値をセットする
            JoinRequestEntity joinData = new JoinRequestEntity();
            joinData.setClubName((String) joinRequest.get("club_name"));
            joinData.setClubDescription((String) joinRequest.get("club_description"));
            joinData.setClubId((String) joinRequest.get("club_id"));
            responseEntity.add(joinData);

        }

        return responseEntity;
    }

    // 申請処理
    public void insertClub(JoinRequestSaveEntity paramEntity) {
        /*
         * TODO ➋ 申請者の情報をインサートするSQLを完成させる。
         */
        String sql = """
                INSERT INTO
                    trn_join_request
                    (user_id,
                     club_id,
                     leader_flg)
                VALUES (?,?,?)
                """;

        // entityから値をゲットする
        Object[] paramList = {
                paramEntity.getUserId(),
                paramEntity.getClubId(),
                false 
        };

        jdbcTemplate.update(sql, paramList);
    }
}