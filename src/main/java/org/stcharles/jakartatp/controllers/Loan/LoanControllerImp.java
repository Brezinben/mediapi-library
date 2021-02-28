package org.stcharles.jakartatp.controllers.Loan;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.NotFoundException;
import org.stcharles.jakartatp.api.Loan.LoanInput;
import org.stcharles.jakartatp.api.Loan.LoanOutput;
import org.stcharles.jakartatp.dao.Item.ItemDao;
import org.stcharles.jakartatp.dao.Loan.LoanDao;
import org.stcharles.jakartatp.dao.User.UserDao;
import org.stcharles.jakartatp.model.*;
import org.stcharles.jakartatp.qualifier.Prod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Prod
public class LoanControllerImp implements LoanController {

    @Inject
    @Prod
    private UserDao userDao;

    @Inject
    @Prod
    private LoanDao loanDao;

    @Inject
    @Prod
    private ItemDao itemDao;

    // predicate to filter the duplicates by the given key extractor.
    public static <T> Predicate<T> distinctByItemId(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> uniqueMap = new ConcurrentHashMap<>();
        return t -> uniqueMap.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    @Override
    public List<LoanOutput> getAll(Integer userId) {
        User user = getWantedUser(userId);
        return loanDao.getLoans(user)
                .stream()
                .map(LoanOutput::new)
                .collect(Collectors.toList());
    }

    @Override
    public LoanOutput get(Integer userId, Integer loanId) {
        Loan loan = getWantedLoan(userId, loanId);
        return new LoanOutput(loan);
    }

    @Override
    public LoanOutput changeStatus(Integer userId, Integer loanId, LoanState status) {
        Loan loan = getWantedLoan(userId, loanId)
                .setStatus(status);
        return new LoanOutput(loan);
    }

    @Override
    public LoanOutput create(Integer userId, LoanInput loanInput) {
        ArrayList<LoanInput> list = new ArrayList<>();
        list.add(loanInput);
        return createMultiple(userId, list).get(0);
    }

    @Override
    @Transactional
    public List<LoanOutput> createMultiple(Integer userId, List<LoanInput> loans) {
        int maxSize = 5;
        if (loans.size() > maxSize) {
            throw new ValidationException("Vous ne pouvez pas déclarer plus de " + maxSize + " enregistrements ici vous en avez " + loans.size());
        }
        User user = getWantedUser(userId);

        //On les transforment en Loan
        List<Loan> loanList = loans.stream()
                .filter(distinctByItemId(loan -> loan.itemId))
                .map(loanInput -> new Loan(loanInput.dateStart, user, getWantedItem(loanInput.itemId)))
                .collect(Collectors.toList());

        //On les persistent si l'item est valide
        loanList.forEach(loan -> {
                    itemValidation(loan.getItem());
                    loanDao.persist(loan);
                    //On set notre id dans loan pour la relation entre les deux
                    loan.getItem().setLoan(loan);
                }
        );
        return loanList.stream()
                .map(LoanOutput::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<LoanOutput> getByStatus(Integer userId, LoanState status) {
        return loanDao.getLoans(getWantedUser(userId))
                .stream()
                .filter(loan -> loan.getStatus() == status)
                .map(LoanOutput::new)
                .collect(Collectors.toList());
    }

    private Item getWantedItem(Integer itemId) {
        Optional<Item> item = Optional.ofNullable(itemDao.get(itemId));
        return item.orElseThrow(NotFoundException::new);
    }

    private User getWantedUser(Integer id) {
        Optional<User> user = Optional.ofNullable(userDao.get(id));
        return user.orElseThrow(NotFoundException::new);
    }

    private Loan getWantedLoan(Integer userId, Integer loanId) {
        User user = getWantedUser(userId);
        Optional<Loan> loan = Optional.ofNullable(loanDao.getOneLoanFromUser(user, loanId));
        return loan.orElseThrow(NotFoundException::new);
    }

    private void itemValidation(Item item) {
        //Si il est déja a quelqu'un
        Optional<Loan> loan = Optional.ofNullable(item.getLoan());
        if (loan.isPresent()) {
            throw new ValidationException("L'item " + item.getId() + " ne peut vous être attribué");
        }
        //On empreinte pas un item inutilisable
        if (item.getState() == ItemState.INUTILISABLE) {
            //TODO
            throw new ValidationException("Vous ne pouvez pas prendre un item (id :" + item.getId() + ") non utilisable");
        }
    }
}
