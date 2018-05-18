package kr.ac.jejunu.spring;

import kr.ac.jejunu.hello.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RequestMapping("/user")
@Controller
public class UserController {
    @GetMapping("/servlet")
    public void servlet(HttpServletRequest request, HttpServletResponse response,
                        HttpSession session) throws IOException {
        //session은 브라우저마다 끊기는게 다름.
        //브라우저를 끄지 않았을때 기본으로 30분이 흐르면 세션이 종료됨
        Integer id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String password = request.getParameter("password");

        //세션에 저장하기위한 user객체
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setPassword(password);
        session.setAttribute("user", user);

        response.getWriter().println(String.format("<h1> %s : %s </h1><br/>", "ID ", id));
        response.getWriter().println(String.format("<h1> %s : %s </h1><br/>", "Name ", id));
        response.getWriter().println(String.format("<h1> %s : %s </h1><br/>", "Password ", id));
    }

    @GetMapping("/session")
    public ModelAndView session(HttpSession session){
        ModelAndView modelAndView = new ModelAndView("user");
        modelAndView.addObject("user", session.getAttribute("user"));

        return modelAndView;
    }


    //중갈호 안에 있는 인자를 PathVariable로 얻어올수 있음
    @GetMapping("/path/{id}")
    public ModelAndView user(@PathVariable Integer id, @RequestParam("name") String name,
                       @RequestParam("password") String password, HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView("user");
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setPassword(password);
        modelAndView.addObject("user", user);

        response.addCookie(new Cookie("id", String.valueOf(id)));
        response.addCookie(new Cookie("name", String.valueOf(name)));
        response.addCookie(new Cookie("password", String.valueOf(password)));
        return modelAndView;
    }

    //MatrixVariable은 ;로 구분하여 param을 받아오는방법으로 사용하려면
    //annotation-driven에다가 enable ~~~~ = "true" 해서 추가시켜줘야함 이거는 따로 검색해바.
    /*@GetMapping("/path/{id}")
    public ModelAndView user(@PathVariable Integer id, @MatrixVariable String name,
                             @MatrixVariable String password){
        ModelAndView modelAndView = new ModelAndView("user");
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setPassword(password);
        modelAndView.addObject("user", user);
        return modelAndView;
    }*/

    /*@GetMapping("/path/{id}/{name:[a-z]+}") 요래하면 이름은 숫자 적용 불가능 요거는 RequestParam에서도 적용가능 (위위 예제에서도)
    public ModelAndView user(@PathVariable Integer id, @PathVariable("name") String name,
                             @MatrixVariable String password){
        ModelAndView modelAndView = new ModelAndView("user");
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setPassword(password);
        modelAndView.addObject("user", user);
        return modelAndView;
    }*/

    @GetMapping("/cookie")
    public ModelAndView cookie(@CookieValue Integer id, @CookieValue("name") String name,
                             @CookieValue("password") String password){
        ModelAndView modelAndView = new ModelAndView("user");
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setPassword(password);
        modelAndView.addObject("user", user);

        return modelAndView;
    }


    @GetMapping
    //return값이 void면 해당 클래스 최상위의 viewname을 찾으러감
    //UserController의 어노테이션이 @RequestMapping("/user")요렇게 되있었으니 user.jsp를 찾으러감.
    public void user(Model model){
        User user = new User();
        user.setId(1);
        user.setName("osy");
        user.setPassword("fdfdfdfs");
        model.addAttribute(user);
    }

    //Map , Model, ModelMap을 사용하여 파라미터를 받을수도 있음.

    //ModelAttribute
    @GetMapping
    public void user(/*@ModelAttribute 요거 생략가능.*/ User user){
        //요렇게 아무것도 안넣어도 ModelAttribute가 알아서 다 해줌.
    }

    @GetMapping
    public /*@ModelAttribute 요거 생략가능.*/User user(){
        return new User();  //attributeName은 user로 자동으로 넣어짐.
    }

}
