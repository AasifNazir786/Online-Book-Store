package com.example.online_book_store.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.online_book_store.dto.LoginUser;
import com.example.online_book_store.dto.RegisterUser;
import com.example.online_book_store.dto.UserDetailDTO;
import com.example.online_book_store.dto.UserWithToken;
import com.example.online_book_store.mapper.RegisterUserMapper;
import com.example.online_book_store.model.User;
import com.example.online_book_store.repository.UserRepository;
import com.example.online_book_store.utils.JwtUtil;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RegisterUserMapper registerUserMapper;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                    RegisterUserMapper registerUserMapper,JwtUtil jwtUtil,
                    AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.registerUserMapper = registerUserMapper;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public UserWithToken registerUser(RegisterUser registerUserDto) {
        if (userRepository.existsByUsername(registerUserDto.getUsername())) {
            throw new IllegalArgumentException("Username is already taken.");
        }
        if(registerUserDto.getEmail() != null){
            if (userRepository.existsByEmail(registerUserDto.getEmail())) {
                throw new IllegalArgumentException("Email is already registered.");
            }
        }

        if(registerUserDto.getPhoneNumber() != null){
            if(userRepository.existsByPhoneNumber(registerUserDto.getPhoneNumber())){
                throw new IllegalArgumentException("Phone Number is already registered.");
            }
        }

        User user = registerUserMapper.toEntity(registerUserDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        validateContactInfo(user.getEmail(), user.getPhoneNumber());
        
        User savedUser = userRepository.save(user);
        String token = jwtUtil.generateJwtToken(savedUser.getUsername(), savedUser.getRole());

        return mapUserToUserWithToken(savedUser, token);
    }

    public UserWithToken verifyUser(LoginUser user){
        User user1 = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if(authentication.isAuthenticated()){
            String token = jwtUtil.generateJwtToken(user.getUsername(), user1.getRole());
            return mapUserToUserWithToken(user1, token);
        }
        return new UserWithToken();
    }

    private void validateContactInfo(String email, String phoneNumber) {
        if (email == null && phoneNumber == null) {
            throw new IllegalArgumentException("Either email or phone number must be provided.");
        }
    }

    public void logoutUser() {

        jwtUtil.removeJwtToken();
    }

    private UserWithToken mapUserToUserWithToken(User user, String token) {
        UserDetailDTO userDetailDTO = new UserDetailDTO();
        userDetailDTO.setId(user.getId());
        userDetailDTO.setFullName(user.getFullName());
        userDetailDTO.setUsername(user.getUsername());
        userDetailDTO.setEmail(user.getEmail());
        userDetailDTO.setAddress(null);
        userDetailDTO.setPhoneNumber(user.getPhoneNumber());
        userDetailDTO.setRole(user.getRole());
        return new UserWithToken(token, userDetailDTO);
    }
}
