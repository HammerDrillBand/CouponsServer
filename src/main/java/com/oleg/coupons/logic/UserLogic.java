package com.oleg.coupons.logic;

import com.oleg.coupons.dal.IUsersDal;
import com.oleg.coupons.dto.SuccessfulLoginDetails;
import com.oleg.coupons.dto.User;
import com.oleg.coupons.dto.UserLoginData;
import com.oleg.coupons.dto.UsersPageResult;
import com.oleg.coupons.entities.UserEntity;
import com.oleg.coupons.enums.ErrorType;
import com.oleg.coupons.enums.UserType;
import com.oleg.coupons.exceptions.ApplicationException;
import com.oleg.coupons.utils.CommonValidations;
import com.oleg.coupons.utils.JWTUtils;
import com.oleg.coupons.utils.PasswordEncryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class UserLogic {

    private IUsersDal usersDal;
    private CompanyLogic companyLogic;

    @Autowired
    public UserLogic(IUsersDal usersDal, CompanyLogic companyLogic) {
        this.usersDal = usersDal;
        this.companyLogic = companyLogic;
    }

    public int addUser(User user) throws ApplicationException {
        validateUser(user);
        String password = user.getPassword();
        String encodedPassword = PasswordEncryption.encryptPassword(password);
        user.setPassword(encodedPassword);

        UserEntity userEntity = new UserEntity(user);
        try {
            userEntity = this.usersDal.save(userEntity);
        } catch (Exception e) {
            throw new ApplicationException(ErrorType.GENERAL_ERROR, "Failed to add user", e);
        }
        return userEntity.getId();
    }

    public void updateUser(User user) throws ApplicationException {
        if (user.getPassword() == "") {
            User currentUser = usersDal.getById(user.getId());
            user.setPassword(currentUser.getPassword());
        } else {
            validateUser(user);
            String password = user.getPassword();
            String encodedPassword = PasswordEncryption.encryptPassword(password);
            user.setPassword(encodedPassword);
        }

        UserEntity userEntity = new UserEntity(user);
        try {
            this.usersDal.save(userEntity);
        } catch (Exception e) {
            throw new ApplicationException(ErrorType.GENERAL_ERROR, "Failed to update user", e);
        }
    }

    public void deleteUser(int id) throws ApplicationException {
        if (!isUserExist(id)) {
            throw new ApplicationException(ErrorType.COULD_NOT_FIND, "Could not find the user to be deleted");
        }
        try {
            this.usersDal.deleteById(id);
        } catch (Exception e) {
            throw new ApplicationException(ErrorType.GENERAL_ERROR, "Failed to delete user with id " + id, e);
        }
    }

    public User getById(int id) throws ApplicationException {
        User user = this.usersDal.getById(id);
        if (user == null) {
            throw new ApplicationException(ErrorType.COULD_NOT_FIND, "Could not find the user you were looking for");
        }
        return user;
    }

    public List<User> getAll() throws ApplicationException {
        List<User> users = this.usersDal.getAll();
        if (users == null) {
            throw new ApplicationException(ErrorType.COULD_NOT_FIND, "Could not find the users you were looking for");
        }
        return users;
    }

    public UsersPageResult getByFilters(int page, int[] companyIds) throws ApplicationException {
        int usersPerPage = 20;
        int adjustedPage = page - 1;
        Pageable pageable = PageRequest.of(adjustedPage, usersPerPage);
        Page<User> usersPage = this.usersDal.getByFilters(companyIds, pageable);
        if (usersPage == null) {
            throw new ApplicationException(ErrorType.COULD_NOT_FIND, "Could not find the users you were looking for");
        }
        int totalPages = usersPage.getTotalPages();
        List<User> users = usersPage.getContent();

        UsersPageResult usersPageResult = new UsersPageResult(users, totalPages);

        return usersPageResult;
    }

    public User getByUsername(String username) throws ApplicationException {
        User user = this.usersDal.getByUsername(username);
        if (user == null) {
            throw new ApplicationException(ErrorType.COULD_NOT_FIND, "Could not find the users you were looking for");
        }
        return user;
    }

    public List<User> getByCompanyId(int companyId) throws ApplicationException {
        List<User> users = this.usersDal.getByCompanyId(companyId);
        if (users == null) {
            throw new ApplicationException(ErrorType.COULD_NOT_FIND, "Could not find the users you were looking for");
        }
        return users;
    }

    public String login(UserLoginData loginDetails) throws ApplicationException {
        validateUsername(loginDetails.getUsername());
        validatePasswordFormat(loginDetails.getPassword());

        authenticatePassword(loginDetails.getUsername(), loginDetails.getPassword());

        String encodedPassword = PasswordEncryption.encryptPassword(loginDetails.getPassword());
        loginDetails.setPassword(encodedPassword);

        SuccessfulLoginDetails successfulLoginDetails = this.usersDal.login(loginDetails.getUsername(), loginDetails.getPassword());
        if (successfulLoginDetails == null) {
            throw new ApplicationException(ErrorType.LOGIN_FAILED);
        }
        try {
            String token = JWTUtils.createJWT(successfulLoginDetails);
            return token;
        } catch (Exception e) {
            throw new ApplicationException(ErrorType.GENERAL_ERROR, "Failed to login", e);
        }
    }

    private void validateUser(User user) throws ApplicationException {
        validateUsername(user.getUsername());
        validatePasswordFormat(user.getPassword());
        validateUserType(user.getUserType());
        validateCompanyId(user.getCompanyId(), user.getUserType());
    }

    private void validateCompanyId(Integer companyId, UserType userType) throws ApplicationException {
        if (!userType.equals(UserType.COMPANY)) {
            if (companyId != null) {
                throw new ApplicationException(ErrorType.USER_TYPE_NOT_COMPANY_BUT_COMPANY_ID_EXIST);
            }
        } else {
            if (companyId == null) {
                throw new ApplicationException(ErrorType.USER_TYPE_COMPANY_BUT_COMPANY_ID_NOT_EXIST);
            }
            if (!companyLogic.isCompanyExist(companyId)) {
                throw new ApplicationException(ErrorType.COMPANY_NOT_EXIST);
            }
        }
    }

    private void validateUserType(UserType type) throws ApplicationException {
        if (type == null) {
            throw new ApplicationException(ErrorType.USER_TYPE_NULL);
        }
    }

    private void validateUsername(String username) throws ApplicationException {
        if (!CommonValidations.validateEmailAddressStructure(username)) {
            throw new ApplicationException(ErrorType.USERNAME_INVALID);
        }
    }

    private void validatePasswordFormat(String password) throws ApplicationException {
        String passwordFormat = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,20}$";
        Pattern pat = Pattern.compile(passwordFormat);
        if (!pat.matcher(password).matches()) {
            throw new ApplicationException(ErrorType.USER_PASSWORD_INVALID);
        }
    }

    boolean isUserExist(int id) {
        return this.usersDal.existsById(id);
    }

    private void authenticatePassword(String username, String password) throws ApplicationException {
        User user = getByUsername(username);
        String storedPassword = user.getPassword();
        String encryptedReceivedPassword = PasswordEncryption.encryptPassword(password);

        if (user != null) {
            if (encryptedReceivedPassword.equals(storedPassword)) {
                return;
            }
        }
        throw new ApplicationException(ErrorType.LOGIN_FAILED);
    }
}


