package jp.co.jc21ps.activity_management.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import jp.co.jc21ps.activity_management.entity.JoinRequestEntity;
import jp.co.jc21ps.activity_management.entity.JoinRequestSaveEntity;
import jp.co.jc21ps.activity_management.repository.JoinRequestRepository;
import jp.co.jc21ps.activity_management.dto.JoinRequestDto;
import jp.co.jc21ps.activity_management.dto.JoinRequestSaveDto;

@Service
public class JoinRequestService {
    private final JoinRequestRepository joinRequestRepository;

    public JoinRequestService(JoinRequestRepository joinRequestRepository) {
        this.joinRequestRepository = joinRequestRepository;
    }

    // 初期表示画面
    public List<JoinRequestDto> findRequest(JoinRequestDto paramDto) {

        // entityに値をセット
        JoinRequestEntity joinRequestEntity = new JoinRequestEntity();
        joinRequestEntity.setUserId(paramDto.getUserId());
        joinRequestEntity.setClubId(paramDto.getClubId());

        List<JoinRequestEntity> joinRequestList = joinRequestRepository.getJoinRequestById(joinRequestEntity);
        List<JoinRequestDto> responseDto = new ArrayList<>();

        for (JoinRequestEntity entity : joinRequestList) {

            // dtoに値をセット
            JoinRequestDto joinRequestData = new JoinRequestDto();
            joinRequestData.setClubName(entity.getClubName());
            joinRequestData.setClubDescription(entity.getClubDescription());
            joinRequestData.setClubId(entity.getClubId());
            responseDto.add(joinRequestData);

        }

        return responseDto;
    }

    // インサートメソッド
    public boolean insertJoinRequest(JoinRequestSaveDto paramDto) {
        try {
            // boolean = true の場合
            // entityに値をセット
            JoinRequestSaveEntity joinRequestSaveEntity = new JoinRequestSaveEntity();
            joinRequestSaveEntity.setUserId(paramDto.getUserId());
            joinRequestSaveEntity.setClubId(paramDto.getClubId());

            joinRequestRepository.insertClub(joinRequestSaveEntity);
               
            // 成功のメッセージを返す
            return true;
            

        } catch (Exception e) {
             System.out.println("エラーーーーーーーーーーーーーーーー");
            // boolean = false の場合
            // 失敗のメッセージを返す
            e.printStackTrace();
            return false;
        }
    }
}
