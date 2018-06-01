package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    @GetMapping("/{id}")
    public User get(@PathVariable Integer id){
        //Optional 은 null을 넣을수도 있는 리스트라고 생각해.
        return userRepository.findById(id).get();
    }

    @GetMapping("/list")
    public List<User> list(){
        return userRepository.findAll();
    }

    @GetMapping("/findExample")
    public Page<User> findExample(@RequestParam String name, @RequestParam String password,
                                  @RequestParam Integer page, @RequestParam Integer size) {

                                                                  //정렬방법         정렬 타겟
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "id");
        return userRepository.findAllByNameAndPassword(name, password, pageRequest);
    }

    @PostMapping
    public User create(@RequestBody User user){
        return userRepository.save(user);
    }

    @PutMapping
    public void modify(@RequestBody User user){
        userRepository.save(user);
    }

    @DeleteMapping
    public void delete(@PathVariable Integer id) {
        userRepository.delete(userRepository.findById(id).get());
    }
}
