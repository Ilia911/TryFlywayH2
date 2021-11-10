package com.itrex.java.lab.service;

import com.itrex.java.lab.entity.User;
import com.itrex.java.lab.entity.dto.CertificateDTO;
import com.itrex.java.lab.entity.dto.UserDTO;
import com.itrex.java.lab.exeption.ServiceException;
import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDTO> findAll() throws ServiceException;

    boolean delete(int id) throws ServiceException;

    UserDTO update(User user) throws ServiceException;

    Optional<UserDTO> register(User user) throws ServiceException;

    List<CertificateDTO> assignCertificate(int userId, int certificateId) throws ServiceException;

    List<CertificateDTO> removeCertificate(int userId, int certificateId) throws ServiceException;
}