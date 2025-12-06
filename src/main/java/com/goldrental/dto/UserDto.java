package com.goldrental.dto;


import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
   private Long id;
   private String name;
   private String email;
   private String phone;
   private String password;
   private String createdAt;

}
