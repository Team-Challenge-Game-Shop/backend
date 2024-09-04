package com.gameshop.ecommerce.web.generator.service;

import com.gameshop.ecommerce.web.user.model.User;
import com.gameshop.ecommerce.web.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserGeneratorService {
    private static final String USER_PHOTO = "https://girlsonlytravel.com/img/works/user.jfif";
    private final UserRepository userRepository;

    public void generateUsers() {
        List<User> users = new ArrayList<>();
        Faker faker = new Faker();
        for (int i = 0; i < 10; i++) {
            User user = createUser(faker);
            users.add(user);
        }
        userRepository.saveAll(users);
    }

    private User createUser(Faker faker) {
        User user = new User();
        user.setFirstName(faker.name().firstName());
        user.setLastName(faker.name().lastName());
        user.setEmail(faker.internet().emailAddress());
        user.setIsEmailVerified(true);
        user.setUserPhoto(USER_PHOTO);
        return user;
    }
}
