package org.stcharles.jakartatp.controllers.User;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.NotFoundException;
import org.stcharles.jakartatp.dao.User.UserDao;
import org.stcharles.jakartatp.model.LoanState;
import org.stcharles.jakartatp.model.User;
import org.stcharles.jakartatp.qualifier.Prod;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * The class User controller imp implements user controller
 */
@Prod
@ApplicationScoped
public class UserControllerImp implements UserController {
    @Inject
    @Prod
    private UserDao userDao;


    /**
     * Gets the all
     *
     * @return the all
     * @Override
     */
    public List<User> getAll() {
        return userDao.getAll();
    }


    /**
     * Gets the
     *
     * @param id the id
     * @return the
     * @Override
     */
    public User get(Integer id) {
        return Optional.ofNullable(userDao.get(id)).orElseThrow(NotFoundException::new);
    }


    /**
     * Gets the by email
     *
     * @param email the email
     * @return the by email
     * @Override
     */
    public User getByEmail(String email) {

        return Optional.ofNullable(userDao.getByEmail(email)).orElseThrow(NotFoundException::new);
    }

    /**
     * Create
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @param email     the email
     * @return UserOutput
     * @throws ValidationException
     * @Override
     */
    @Transactional
    public User create(String firstName, String lastName, String email) throws ValidationException {

        Optional<User> validEmail = Optional.ofNullable(userDao.getByEmail(email));
        if (validEmail.isPresent()) {
            throw new ValidationException("L'email sélectionner ne peut pas être insérer dans notre base");
        }
        User user = new User(firstName, lastName, email);
        userDao.persist(user);
        return user;
    }

    /**
     * Update
     *
     * @param id        the id
     * @param firstName the first name
     * @param lastName  the last name
     * @param email     the email
     * @return UserOutput
     * @Override
     */

    @Transactional
    public User update(Integer id, String firstName, String lastName, String email) {

        User user = Optional.ofNullable(userDao.get(id)).orElseThrow(NotFoundException::new);
        user.setFirstName(firstName);
        user.setLastName(lastName);

        Optional<User> emailExist = Optional.ofNullable(userDao.getByEmail(email));
        if (emailExist.isPresent() && !emailExist.get().getEmail().equals(user.getEmail())) {
            throw new ValidationException("L'email ne peut pas être enregistrer car il existe déjà dans notre base");
        }

        user.setEmail(email);
        return user;
    }


    /**
     * Remove
     *
     * @param id the id
     * @return Boolean
     * @Override
     */

    @Transactional
    public Boolean remove(Integer id) {

        User user = Optional.ofNullable(userDao.get(id)).orElseThrow(NotFoundException::new);
        if (user.getLoans().stream().anyMatch(loan -> loan.getStatus().equals(LoanState.EN_COURS))) {
            throw new ValidationException("Il y a des emprunts en cours vous ne pouvez pas supprimer cette utilisateur !");
        }
        try {
            userDao.remove(user);
            return true;
        } catch (Exception exception) {
            Logger.getAnonymousLogger(exception.getMessage());
            return false;
        }
    }
}
