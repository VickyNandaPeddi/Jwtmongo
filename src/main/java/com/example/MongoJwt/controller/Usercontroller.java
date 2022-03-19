package com.example.MongoJwt.controller;

import com.example.MongoJwt.dao.Userdao;
import com.example.MongoJwt.model.Jwtrequest;
import com.example.MongoJwt.model.User;
import com.example.MongoJwt.service.CustomUserdetailsservice;
import com.example.MongoJwt.util.Jwtutil;
import com.example.MongoJwt.util.Techieutils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class Usercontroller {
    @Autowired
    private Userdao userdao;
    @Autowired

    private PasswordEncoder encoder;
    @Autowired
    private Jwtutil jwtutil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserdetailsservice customUserdetailsservice;
    @Autowired
    Techieutils techieutils;

    @PostMapping("/save")
//    @PreAuthorize("hasRole('MANAGER')")
    public User saveuser(@RequestBody User user) {
//        user.stream().forEach(name -> name.setPassword(encoder.encode(name.getPassword())));
        user.setPassword(encoder.encode(user.getPassword()));
        return userdao.save(user);
    }


    @PostMapping("/generatetoken")
//    @PreAuthorize("hasRole('MANAGER')")
    public String erateToken(@RequestBody Jwtrequest jwtrequest) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(jwtrequest.getUsername(), jwtrequest.getPassword()));
        } catch (Exception ex) {
            throw new Exception("inavalid username/password");
        }
        UserDetails loadUserByUsername = customUserdetailsservice.loadUserByUsername(jwtrequest.getUsername());
        String generateToken = jwtutil.generateToken(jwtrequest.getUsername());
        System.out.println(generateToken);
        return generateToken;
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasRole('USER')")
    public User getalluser(@PathVariable String id) {
        return userdao.findById(id).get();
    }

    @GetMapping("/admin")
//    @PreAuthorize("hasRole('ADMIN')")
    public String getalluser() {
        return "this is only for admin";
    }

    @GetMapping("/")
//    @PreAuthorize("hasRole('ADMIN')")
    public User findbyusername() {
        return userdao.findByUsername("nanda");
    }

    @PostMapping("/principal")
    public Object getprincipal(GrantedAuthority principal) {
        Object details = principal.getAuthority();
        System.out.println(details);

        return details;
    }
}
