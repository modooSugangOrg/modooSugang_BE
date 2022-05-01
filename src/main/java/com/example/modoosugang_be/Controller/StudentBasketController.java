package com.example.modoosugang_be.Controller;

import com.example.modoosugang_be.Domain.Lecture;
import com.example.modoosugang_be.Domain.RegisterBasket;
import com.example.modoosugang_be.Domain.RegisterLecture;
import com.example.modoosugang_be.Service.LectureService;
import com.example.modoosugang_be.Service.RegisterBasketService;
import com.example.modoosugang_be.Service.RegisterLectureService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class StudentBasketController {

    private final RegisterBasketService registerBasketService;
    private final LectureService lectureService;

    @PostMapping("/student/enroll/apply/basket")
    public boolean Applybasket(@RequestBody Map<String, Object> param) {

        System.out.println((param));
        String code = param.get("code").toString();
        String univ = param.get("univ").toString();
//        String semester = param.get("semester").toString();
        String id = param.get("id").toString();

        int schedule = 5; // 스케쥴 부분 타입 확인 필요
        String student_id = univ + "@" + param.get("id").toString(); // 일단 임의설정

        RegisterBasket regist = new RegisterBasket();

//        regist.setId(Integer.valueOf(code)); // register_lecture_id : ??
        regist.setIdx(Integer.valueOf(code)); // lecture_index : 강의code ?
        regist.setStudent(student_id);
        regist.setSchedule(schedule);
        regist.setUniv(univ);

        System.out.println((regist));
        registerBasketService.save(regist);
        return true;
    }

    // 장바구니 2번테이블 data
    @PostMapping("/student/enrolled/basket_list/")
    public List LectureTable(@RequestBody Map<String, Object> param) {
        String semester = param.get("semester").toString();
        String univ = param.get("univ").toString();
        String studentID = univ + "@" + param.get("id").toString();


        // 해당 리스트에 담긴 code를 각각 추출해 Lecture 테이블의 부가정보들을 가져옴
        List<RegisterBasket> logs = registerBasketService.findLectureList(univ, studentID); // 학기와 학번으로 List 가져오기

        List list = new ArrayList();
        for (RegisterBasket log : logs) {
            int code = log.getIdx();
            Optional<Lecture> OptionalBasket = lectureService.findRemain(code);

            JSONObject data = new JSONObject();

            // Create Json Array
            data.put("index", log.getIdx());
            data.put("name", OptionalBasket.get().getName());
            data.put("major", OptionalBasket.get().getMajor());
            data.put("classify", OptionalBasket.get().getClassify());
            data.put("time", OptionalBasket.get().getTime());
            data.put("classes",OptionalBasket.get().getClasses());
            data.put("credit", OptionalBasket.get().getCredit());

            list.add(data);
        }

        return list;
    }

    @PostMapping("/student/enroll/drop/basket")
    public boolean DropLecture(@RequestBody Map<String, Object> param) {
        System.out.println((param));

        int id = Integer.parseInt(param.get("id").toString()); // g
        int code = Integer.valueOf(param.get("code").toString());
        String univ = param.get("univ").toString();
        String semester = param.get("semester").toString();
        String student = univ + "@" + param.get("student").toString();

        registerBasketService.DeleteBasket(code, univ, student);

        return true;
    }

}