package com.example.demo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer>{

    Page<User> findAllByNameAndPassword(@Param("name") String name,@Param("password") String password,
                                        Pageable pageable);

    //요거는 쿼리 직접 코딩
//    @Query("select * from userinfo where name = :name and password = :password", nativeQuery = true)
//    @Query("select u from User u where u.name = :name and u.password = :password")  요거는 qsl   위에거랑 요거 골라서 써
//    Page<User> findAllByNameAndPassword(@Param("name") String name,@Param("password") String password,
//                                        Pageable pageable);
}
