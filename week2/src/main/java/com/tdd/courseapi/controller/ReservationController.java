package com.tdd.courseapi.controller;

import com.tdd.courseapi.controller.request.RequestDTO;
import com.tdd.courseapi.controller.response.ResponseDTO;
import com.tdd.courseapi.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

  private final ReservationService reservationService;

  /** 특강신청여부 조회 */
  @GetMapping("{userId}/{courseId}")
  public ResponseDTO getReservationSuccessFail(@PathVariable long userId, @PathVariable long courseId) {

    ResponseDTO responseDto = new ResponseDTO();
    responseDto.setStatus(reservationService.getSuccessFail(userId, courseId));

    return responseDto;
  }
  /** 특강신청 */
  @PostMapping("/")
  public ResponseDTO reserve(@RequestBody RequestDTO requestDTO) {
    ResponseDTO responseDto = reservationService.reserve(requestDTO);
    return responseDto;
  }


}
