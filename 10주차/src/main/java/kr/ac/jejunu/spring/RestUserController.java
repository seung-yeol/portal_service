package kr.ac.jejunu.spring;

import kr.ac.jejunu.hello.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//@Controllerf
@RestController //Restfull한 컨트롤러.
@RequestMapping("/rest")
public class RestUserController {
    @GetMapping("{id}")
    @ResponseBody   //요거는 리턴한것을 xml or json 으로 바꿔줌
                    // 최상위에 @Controller라고 하면 이 어노테이션이 적혀있어야함
                    // 최상위에 @RestController라고 되있으면 이 어노테이션 생략가능
    public User get(@PathVariable Integer id){
        User user = new User();
        user.setId(id);
        user.setName("오승열");
        user.setPassword("1234");

        return user;
    }

    @PostMapping
    @ResponseBody
    public User create(@RequestBody /*요 RequestBody 얻어올 데이터가 xml or json
                                            content-type 이 application/json or xml으로 되어야함.*/ User user){
        return user;
    }

    @PutMapping
    @ResponseBody
    public User update(@RequestBody User user){
        return user;
    }

    @DeleteMapping
    @ResponseBody
    public void delete(@PathVariable Integer id){

    }


}
