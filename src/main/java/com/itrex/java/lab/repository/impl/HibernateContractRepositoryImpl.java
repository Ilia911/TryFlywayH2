package com.itrex.java.lab.repository.impl;

import com.itrex.java.lab.entity.Contract;
import com.itrex.java.lab.exeption.RepositoryException;
import com.itrex.java.lab.repository.ContractRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RepositoryException.class)
public class HibernateContractRepositoryImpl implements ContractRepository {

    private static final String FIND_CONTRACTS_QUERY = "select c from Contract c ";
    @Autowired
    private SessionFactory sessionFactory;

    public HibernateContractRepositoryImpl() {
    }

    @Override
    public Optional<Contract> find(int id) throws RepositoryException {
        Contract contract;
        try {
            Session session = sessionFactory.getCurrentSession();
            contract = session.find(Contract.class, id);
        } catch (Exception ex) {
            throw new RepositoryException("Something was wrong in the repository", ex);
        }
        return Optional.of(contract);
    }

    @Override
    public List<Contract> findAll() throws RepositoryException {
        List<Contract> contracts;
        try {
            Session session = sessionFactory.getCurrentSession();
            contracts = session.createQuery(FIND_CONTRACTS_QUERY, Contract.class).list();
        } catch (Exception ex) {
            throw new RepositoryException("Something was wrong in the repository", ex);
        }
        return contracts;
    }

    @Override
    public boolean delete(int id) throws RepositoryException {
        boolean result;
        try {
            Session session = sessionFactory.getCurrentSession();
            Contract contract = session.find(Contract.class, id);
            if (contract != null) {
                session.delete(contract);
                result = (null == session.find(Contract.class, id));
            } else {
                result = false;
            }
        } catch (Exception ex) {
            throw new RepositoryException("Something was wrong in the repository", ex);
        }
        return result;
    }

    @Override
    public Contract update(Contract contract) throws RepositoryException {
        validateContractData(contract);

        Contract updatedContract;
        try {
            Session session = sessionFactory.getCurrentSession();
            session.update("Contract", contract);
            updatedContract = session.find(Contract.class, contract.getId());
        } catch (Exception ex) {
            throw new RepositoryException("Something was wrong in the repository", ex);
        }
        return updatedContract;
    }

    @Override
    public Optional<Contract> add(Contract contract) throws RepositoryException {
        validateContractData(contract);

        Contract createdContract;
        try {
            Session session = sessionFactory.getCurrentSession();
            int newContractId = (Integer) session.save("Contract", contract);
            createdContract = session.find(Contract.class, newContractId);
        } catch (Exception ex) {
            throw new RepositoryException("Something was wrong in the repository", ex);
        }
        return Optional.ofNullable(createdContract);
    }

    private void validateContractData(Contract contract) throws RepositoryException {
        if (contract == null) {
            throw new RepositoryException("Contract can not be 'null'!");
        }
        if (contract.getStartDate() == null || contract.getStartDate().isBefore(LocalDate.now())) {
            throw new RepositoryException("Contract field 'startDate' must not be null or before today " +
                    "(" + LocalDate.now() + ")!");
        }
        if (contract.getEndDate() == null || contract.getEndDate().isBefore(contract.getStartDate())) {
            throw new RepositoryException("Contract field 'endDate' must not be null or before today " +
                    "(" + LocalDate.now() + ")!");
        }
    }
}
