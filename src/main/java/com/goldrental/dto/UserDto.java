package com.goldrental.dto;

import com.goldrental.entity.Rental;
import jakarta.persistence.OneToMany;
import lombok.NoArgsConstructor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
   private Long id;
   private String username;
   private String email;
   private String phoneNumber;
   private String password;
   private String createdAt;



}
