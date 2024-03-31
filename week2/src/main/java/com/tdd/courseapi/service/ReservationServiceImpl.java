package com.tdd.courseapi.service;

import com.tdd.courseapi.common.ReservationManager;
import com.tdd.courseapi.common.ReservationValidation;
import com.tdd.courseapi.controller.request.RequestDTO;
import com.tdd.courseapi.controller.response.ResponseDTO;
import com.tdd.courseapi.domain.ReservationEntity;
import com.tdd.courseapi.constant.ReservationStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@Transactional
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

  private final ReservationManager reservationManager;
  private final ReservationValidation reservationValidation;


  @Override
  public ResponseDTO reserve(RequestDTO requestDTO) {
    // 예약 유효성 검증선행
    long userId = requestDTO.getUserId();
    long courseId = requestDTO.getCourseId();
    boolean result = reservationValidation.validateRequest(userId, courseId);
    ResponseDTO responseDTO = new ResponseDTO();

    if (result) {
      ReservationStatus status = reservationManager.reserve(userId, courseId);
      if (status == ReservationStatus.SUCCESS) {
        responseDTO.setStatus(status);
        responseDTO.setMessage("[특강신청 성공] 사용자아이디 : " + userId + ", 특강아이디 : " + courseId);
        return responseDTO;
      } else {
        responseDTO.setStatus(status);
        responseDTO.setMessage("[특강신청 실패] 사용자아이디 : " + userId + ", 특강아이디 : " + courseId);
        return responseDTO;
      }
    } else {
      responseDTO.setStatus(ReservationStatus.FAIL);
      responseDTO.setMessage("[특강신청 실패] 사용자아이디 : " + userId + ", 특강아이디 : " + courseId);
      return responseDTO;
    }
  }


  @Override
  public int countReservationByCourseId(long courseId) {
    return reservationManager.countByCourseEntityCourseId(courseId);
  }

  @Override
  public ReservationStatus getSuccessFail(long userId, long courseId) {
    ReservationStatus status = reservationManager.getSuccessFail(userId, courseId);
    return status;
  }
}
