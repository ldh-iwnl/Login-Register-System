package com.mayikt.test;

import com.mayikt.entity.UserEntity;
import com.mayikt.service.UserService;

import java.util.Scanner;

public class UserTest {
    private UserService userService = new UserService();
    public void registerUser(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("please enter phone number: ");
        String phone = scanner.nextLine();
        System.out.println("please enter pwd: ");
        String pwd = scanner.nextLine();
        UserEntity user = new UserEntity(phone,pwd);
        int result = userService.Register(user);
        if(result>0){
            System.out.println("successfully registered the user");
        }else{
            System.out.println("fail to register");
        }
    }

    public void index(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. register");
        System.out.println("2. Login");
        int inp = scanner.nextInt();
        switch (inp){
            case 1:
                registerUser();
            case 2:
                login();
        }
    }

    public void login(){
        // login attempt is 3
        for(int i=0; i<3;i++){
            Scanner scanner = new Scanner(System.in);
            System.out.println("please enter phone number: ");
            String phone = scanner.nextLine();
            System.out.println("please enter pwd: ");
            String pwd = scanner.nextLine();
            UserEntity dbUser = userService.login(new UserEntity(phone, pwd));
            if(dbUser==null){
                System.out.println("user entered phone number or pwd is incorrect. wrong attempt: "+(i+1));
            }else{
                System.out.println("login successfully");
                return;
            }
        }
    }

    public static void main(String[] args) {
        UserTest userTest = new UserTest();
        userTest.index();
    }
}
