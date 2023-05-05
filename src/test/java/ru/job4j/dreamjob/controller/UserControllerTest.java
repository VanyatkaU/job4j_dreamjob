package ru.job4j.dreamjob.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.UserService;

import javax.servlet.http.HttpServletRequest;

import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserControllerTest {

    private UserService userService;

    private UserController userController;

    private HttpServletRequest request;

    @BeforeEach
    public void initServices() {
        userService = mock(UserService.class);
        userController = new UserController(userService);
        request = new MockHttpServletRequest();
    }

    @Test
    public void whenRequestRegistrationPageThenGetRegistrationPage() {

        assertThat(userController.getRegistrationPage()).isEqualTo("users/register");

    }

    @Test
    public void whenPostRegisterUserThenGetPageWithRegister() {
        var user = new User(1, "email", "name", "password");
        var userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        when(userService.save(userArgumentCaptor.capture())).thenReturn(Optional.of(user));

        var model = new ConcurrentModel();
        var view = userController.register(model, user);
        var actualUser = userArgumentCaptor.getValue();

        assertThat(view).isEqualTo("redirect:/index");
        assertThat(actualUser).isEqualTo(user);
    }

    @Test
    public void whenPostRegisterUserThenGetPageWithError() {
        var user = new User(1, "email", "name", "password");
        var userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        when(userService.save(userArgumentCaptor.capture())).thenReturn(Optional.empty());

        var model = new ConcurrentModel();
        var view = userController.register(model, user);
        var actualExceptionMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("errors/404");
        assertThat(actualExceptionMessage).isEqualTo("Пользователь с такой почтой уже существует");
    }

    @Test
    public void whenRequestLoginPageThenGetLoginPage() {

        assertThat(userController.getLoginPage()).isEqualTo("users/login");

    }

    @Test
    public void whenPostLoginUserThenRedirectSessionIndexPage() {
        var user = new User(1, "email", "name", "password");
        when(userService.findByEmailAndPassword(user.getEmail(), user.getPassword()))
                .thenReturn(Optional.of(user));

        var model = new ConcurrentModel();
        var view = userController.loginUser(user, model, request);

        assertThat(view).isEqualTo("redirect:/index");
    }

    @Test
    public void whenPostLoginUserThenRedirectErrorPage() {
        var user = new User(1, "email", "name", "password");
        when(userService.findByEmailAndPassword(user.getEmail(), user.getPassword()))
                .thenReturn(Optional.empty());

        var model = new ConcurrentModel();
        var view = userController.loginUser(user, model, request);
        var actualExceptionMessage = model.getAttribute("error");

        assertThat(view).isEqualTo("users/login");
        assertThat(actualExceptionMessage).isEqualTo("Почта или пароль введены неверно");
    }

    @Test
    public void whenRequestLogoutThenGetUsersLoginPage() {
        assertThat(userController.logout(request.getSession())).isEqualTo("redirect:/users/login");
    }
}