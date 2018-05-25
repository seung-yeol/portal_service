package kr.ac.jejunu.spring;


import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@org.springframework.stereotype.Controller
@RequestMapping("/helloworld")
public class SimpleController {

    //요거는 SimpleUrlHandler
//    @RequestMapping("/hi")
    @GetMapping("/hi")          //요롷게 축약가능
    public ModelAndView hello() {
//        ModelAndView modelAndView = new ModelAndView("hello");
        ModelAndView modelAndView = null;       //일부로 NullPointerException 일으킴
        modelAndView.addObject("hello", "Hello World!!!");
        return modelAndView;
    }

    @ExceptionHandler(NullPointerException.class)
    public String error(NullPointerException e) {
        return "error";     //error이름을 가진 뷰가 리턴이 됨. 스트링이지만.
    }

    //get형식으로 받아옴
//    @RequestMapping(path = "/upload", method = RequestMethod.GET)
    @GetMapping("/upload")
    public String upload() {
        return "upload";
    }

//    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    @PostMapping("/upload")
    public ModelAndView upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        File path = new File(request.getServletContext().getRealPath("/")
                + "/WEB-INF/static/" + file.getOriginalFilename());
        //build폴더의 WEB-INF/static폴더에 생김

        FileOutputStream fileOutputStream = new FileOutputStream(path);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        bufferedOutputStream.write(file.getBytes());
        bufferedOutputStream.close();   //반드시 사용하고 종료!

        ModelAndView modelAndView = new ModelAndView("upload");
        modelAndView.addObject("url", "/image/" + file.getOriginalFilename());
        //이 뷰를 받게된 web은 다시 요 바로 위의 url로 가서 이미지를 가져오려고 하고 이때 SimpleUrlHandler가 실행되어
        //자동으로 가져옴.

        return modelAndView;
    }

}
