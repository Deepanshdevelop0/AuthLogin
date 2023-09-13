package com.login.authLogin.Controllers;


import com.login.authLogin.Model.Users;
import com.login.authLogin.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Random;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/users")
public class UserController {


    @Autowired
    private UsersRepository usersRepository;


    @RequestMapping("/getOtp")
    public String getOtp(Long mobileNo) {

        if (String.valueOf(mobileNo).length() < 10){
            return "Invalid Mobile Number!";
        }

        Random rand = new Random();
        int otp = rand.nextInt(999999);

        System.out.println("otp" + otp);

        Users users = new Users();
        users.setOtp(otp);
        users.setMobileNumber(mobileNo);

        usersRepository.save(users);

        return String.valueOf(otp);
    }

    @RequestMapping(value = "/validate" , method = RequestMethod.POST)
    public String validate(Users users){

        if (String.valueOf(users.getMobileNumber()).length() < 10 || String.valueOf(users.getOtp()).length() < 6 ){
            return "Invalid Mobile Number or OTP Number";
        }

        try {
            Users user = usersRepository.getById(users.getMobileNumber());

            if (user.getOtp() == users.getOtp()){
                return "Validated Successfully!";
            }
            else if (user.getOtp() != users.getOtp()){
                return "OTP Validation Failed!";
            }
        }
        catch (Exception e){
            return "Mobile Number Not Found!";
        }


        return "Something Went Wrong!";

    }




}
