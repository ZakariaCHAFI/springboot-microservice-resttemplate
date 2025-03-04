package zci.trainning.userservice.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import zci.trainning.userservice.dto.DepartmentDto;
import zci.trainning.userservice.dto.ResponseDto;
import zci.trainning.userservice.dto.UserDto;
import zci.trainning.userservice.entity.User;
import zci.trainning.userservice.repository.UserRepository;
import zci.trainning.userservice.service.UserService;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RestTemplate restTemplate;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public ResponseDto getUser(Long userId) {
        ResponseDto responseDto = new ResponseDto();
        User user = userRepository.findById(userId).get();
        UserDto userDto = mapToUser(user);

        ResponseEntity<DepartmentDto> responseEntity = restTemplate.getForEntity("http://localhost:8080/api/departments/" + user.getDepartmentId(),
                DepartmentDto.class);

        DepartmentDto departmentDto = responseEntity.getBody();
        System.out.println(responseEntity.getStatusCode());
        responseDto.setUser(userDto);
        responseDto.setDepartment(departmentDto);

        return responseDto;
    }

    private UserDto mapToUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }
}
