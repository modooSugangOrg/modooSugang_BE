package com.example.modoosugang_be.Controller;

import com.example.modoosugang_be.Domain.University;
import com.example.modoosugang_be.Service.UniversityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class SelectUnivController {

    private final UniversityService universityService;


    @RestController
    public class univ_list {

        @GetMapping("/home/{univ}")
        public ArrayList<University> test1(@PathVariable(name = "univ") String univ) {
            System.out.println(univ);
            return universityService.findUniversity();
        }
    }

}
