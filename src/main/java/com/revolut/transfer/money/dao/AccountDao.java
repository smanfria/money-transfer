package com.revolut.transfer.money.dao;

import com.revolut.transfer.money.exception.AccountNotFoundException;
import com.revolut.transfer.money.model.Account;
import com.revolut.transfer.money.model.Money;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;

public class AccountDao {

    private EntityManager em = JPAFactory.getEntityManagerFactory().createEntityManager();

    public Account save(Account newAccount) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(newAccount);
            transaction.commit();
            return newAccount;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    public boolean close(String accountId) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Account account = findAccountChecked(accountId, LockModeType.PESSIMISTIC_WRITE);
            em.remove(account);
            transaction.commit();
            return true;
        } catch (AccountNotFoundException e) {
            transaction.rollback();
            throw e;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        }

    }

    public Account find(String accountId) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Account account = findAccountChecked(accountId, LockModeType.PESSIMISTIC_READ);
            transaction.commit();
            return account;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    public boolean transfer(String accountFrom, String accountTo, Money money) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Account accountToWithdraw = findAccountChecked(accountFrom, LockModeType.PESSIMISTIC_WRITE);
            accountToWithdraw.withdraw(money);

            Account accountToDeposit = findAccountChecked(accountTo, LockModeType.PESSIMISTIC_WRITE);
            accountToDeposit.deposit(money);

            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    private Account findAccountChecked(String accountId, LockModeType pessimisticWrite) {
        Account account = em.find(Account.class, accountId, pessimisticWrite);
        if (account == null) {
            throw new AccountNotFoundException("Account not found. Id: " + accountId);
        }
        return account;
    }
}
