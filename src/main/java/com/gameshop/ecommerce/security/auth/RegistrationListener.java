package com.gameshop.ecommerce.security.auth;

import com.gameshop.ecommerce.security.auth.model.OnRegistrationCompleteEvent;
import com.gameshop.ecommerce.web.user.model.User;
import com.gameshop.ecommerce.web.user.service.EmailService;
import com.gameshop.ecommerce.web.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegistrationListener implements
        ApplicationListener<OnRegistrationCompleteEvent> {
    private final UserService userService;
    private final EmailService emailService;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        user.setConfirmationCode(RandomString.make(64));
        userService.update(user.getId(), user);
        emailService.sendConfirmationEmail(user.getEmail(), user.getConfirmationCode());
    }
}
