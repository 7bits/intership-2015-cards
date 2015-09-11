package it.sevenbits.cards.service;


import it.sevenbits.cards.validation.Sender;
import it.sevenbits.cards.web.domain.forms.FeedbackForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import it.sevenbits.cards.core.domain.User;
import it.sevenbits.cards.core.repository.UserRepository;
import it.sevenbits.cards.web.domain.forms.RegistrationForm;

import org.apache.log4j.Logger;

@Service
public class UserService {
    @Autowired

    @Qualifier(value = "userRepository")
    private UserRepository userRepository;
    Logger LOG = Logger.getLogger(UserService.class);

    private static Sender sender = new Sender();

    public void createUser(RegistrationForm form) throws ServiceException {
        final User user = new User();
        user.setEmail(form.getEmail());
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(form.getPassword()));
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while saving discount: " + e.getMessage(), e);
        }
    }
    public void changeUserRoleByEmail(String userRole, String email) throws ServiceException {
        try {
            userRepository.changeUserRoleByEmail(userRole, email);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while finding UserId by User Name discount: " + e.getMessage(), e);
        }
    }

    public User findByEmail(String email) throws ServiceException{
        try{
            return  userRepository.findByEmail(email);
        } catch (Exception e){
            throw new ServiceException("An error occurred while finding user by email: " + e.getMessage(), e);
        }
    }

    @Async
    public void sendMailToFeedback(FeedbackForm form) {
        String email = form.getEmail();
        String title = form.getTitle();
        String describe = form.getDescribe();
        sender.send("Уведомление о получении письма обратной связи", "Спасибо, ваше мнение очень важно для нас. Ожидайте ответа в ближайшее время.\n", email);
        sender.send("Обращение в службу поддержки от пользователя " + email, "Заголовок: " + title +"\n"+"Источник отправки: " + email + "\n" + "Сообщение: \n" + describe, "discountfeedback@gmail.com");

    }
}