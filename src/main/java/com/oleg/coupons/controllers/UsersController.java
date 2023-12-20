package com.oleg.coupons.controllers;

import com.oleg.coupons.dto.User;
import com.oleg.coupons.dto.UserLoginData;
import com.oleg.coupons.dto.UsersPageResult;
import com.oleg.coupons.exceptions.ApplicationException;
import com.oleg.coupons.logic.UserLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    private UserLogic userLogic;

    @Autowired
    public UsersController(UserLogic userLogic) {
        this.userLogic = userLogic;
    }

    @PostMapping
    public int addUser(@RequestBody User user) throws ApplicationException {
        return this.userLogic.addUser(user);
    }

    @PutMapping
    public void updateUser(@RequestBody User user) throws ApplicationException {
        this.userLogic.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") int id) throws ApplicationException {
        this.userLogic.deleteUser(id);
    }

    @GetMapping
    public List<User> getAllUsers() throws ApplicationException {
        return this.userLogic.getAll();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") int id) throws ApplicationException {
        return this.userLogic.getById(id);
    }

    @GetMapping("/byCompanyId")
    public List<User> getUsersByCompanyId(@RequestParam("companyId") int companyId) throws ApplicationException {
        return this.userLogic.getByCompanyId(companyId);
    }

    @PostMapping("/login")
    public String login(@RequestBody UserLoginData loginDetails) throws ApplicationException {
        return this.userLogic.login(loginDetails);
    }

    @GetMapping("/byFilters")
    public UsersPageResult getByFilters(@RequestParam("page") int page,
                                        @RequestParam(value = "companyIds", required = false) Integer[] companyIds,
                                        @RequestParam(value = "searchText", required = false) String searchText) throws ApplicationException {
        return this.userLogic.getByFilters(page, companyIds, searchText);
    }
}
