package com.recoder.capstone.service.user;

import com.recoder.capstone.domain.entity.User;
import com.recoder.capstone.dto.security.PrincipalDetails;
import com.recoder.capstone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * email로 User Entity를 찾는 부분
     * Entity가 존재 한다면 그 Entity를 반환 존재 안한다면 null을 반환
     */
    @Override
    public User getUserByEmail(String email) {
        Optional<User> findUser = userRepository.findByEmail(email);

        return findUser.stream().findFirst().orElse(null);
    }

    @Override
    public User createUser(User user) {
        User saveUser = userRepository.save(user);

        return saveUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> findUser = userRepository.findByEmail(username);

        return findUser.stream().findFirst().map(user -> new PrincipalDetails(user)).orElseThrow(
                () -> {
                    throw new UsernameNotFoundException("존재하지 않는 사용자 입니다.");
                }
        );
    }
}
