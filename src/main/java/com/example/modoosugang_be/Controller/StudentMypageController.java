package com.example.modoosugang_be.Controller;

import com.example.modoosugang_be.Domain.Lecture;
import com.example.modoosugang_be.Domain.RegisterLecture;
import com.example.modoosugang_be.Service.LectureService;
import com.example.modoosugang_be.Service.RegisterLectureService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StudentMypageController {

    private final RegisterLectureService registerLectureService;
    private final LectureService lectureService;

    @PostMapping("/student/Mypage")
    public List searchRegisterdLecture(@RequestBody Map<String, Object> param) {
//    @GetMapping("/student/Mypage/{univ}/{id}")
//    public List searchRegisterdLecture(@PathVariable(name = "univ") String univ,
//                                       @PathVariable(name = "id") String id) {
        String univ = param.get("univ").toString();
        String id = univ + "@" + param.get("id").toString();
        List<RegisterLecture> registerLectures = registerLectureService.findRegisterLecture(univ, id);
        List list = new ArrayList();

        for (RegisterLecture registerLecture : registerLectures) {
            System.out.println(registerLecture.getLecture());
            Lecture lecture = lectureService.callLecutureByUnivAndId(univ, registerLecture.getLecture());

            JSONObject data = new JSONObject();

            data.put("lecture_id", lecture.getId());
            data.put("lecture_name", lecture.getName());
            data.put("lecture_professor", lecture.getProname());
            data.put("lecture_class", lecture.getClasses());
            data.put("lecture_major", lecture.getMajor());
            data.put("lecture_classify", lecture.getClassify());
            data.put("lecture_time", lecture.getTime());
            data.put("lecture_room", lecture.getRoom());
            data.put("lecture_classroom", lecture.getClasses());
            data.put("lecture_credit", lecture.getCredit());
            data.put("lecture_first_sch", lecture.getFirstsch());
            data.put("lecture_second_sch", lecture.getSecondsch());
            data.put("lecture_idx", lecture.getIdx());

            list.add(data);
        }
        return list;
    }

}
