package com.itrex.java.lab.service.impl;

import com.itrex.java.lab.entity.Contract;
import com.itrex.java.lab.entity.User;
import com.itrex.java.lab.entity.dto.ContractDTO;
import com.itrex.java.lab.entity.dto.UserDTO;
import com.itrex.java.lab.exeption.RepositoryException;
import com.itrex.java.lab.exeption.ServiceException;
import com.itrex.java.lab.repository.ContractRepository;
import com.itrex.java.lab.service.ContractService;
import com.itrex.java.lab.service.TestServiceConfiguration;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestServiceConfiguration.class)
class ContractServiceImplTest {

    @Autowired
    private ContractRepository repository;
    @Autowired
    private ContractService service;

    @Test
    void find_validData_shouldReturnContract() throws RepositoryException, ServiceException {
        //given
        int expectedContractId = 1;
        int expectedOwnerId = 1;
        User owner = new User();
        owner.setId(expectedOwnerId);
        String expectedDescription = "first contract";
        LocalDate expectedStartDate = LocalDate.parse("2022-01-01");
        LocalDate expectedEndDate = LocalDate.parse("2022-12-31");
        Integer expectedPrice = 28000;
        //when
        Mockito.when(repository.find(expectedContractId)).thenReturn(Optional.of(Contract.builder()
                .id(expectedContractId).owner(owner).description(expectedDescription).startDate(expectedStartDate)
                .endDate(expectedEndDate).startPrice(expectedPrice).build()));
        ContractDTO actualContract = service.find(expectedContractId).get();
        //then
        assertEquals(expectedContractId, actualContract.getId());
        assertEquals(expectedOwnerId, actualContract.getOwner().getId());
        assertEquals(expectedDescription, actualContract.getDescription());
        assertEquals(expectedStartDate, actualContract.getStartDate());
        assertEquals(expectedEndDate, actualContract.getEndDate());
        assertEquals(expectedPrice, actualContract.getStartPrice());
    }

    @Test
    void findAll_validData_shouldReturnContractList() throws RepositoryException, ServiceException {
        //given
        int expectedContractListSize = 2;
        //when
        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(new Contract(), new Contract()));
        List<ContractDTO> actualList = service.findAll();
        //then
        assertEquals(expectedContractListSize, actualList.size());
    }

    @Test
    void delete_validData_shouldDeleteContract() throws RepositoryException, ServiceException {
        //given
        int contractId = 1;
        //when
        Mockito.when(repository.delete(contractId)).thenReturn(true);
        //then
        assertTrue(service.delete(contractId));
    }

    @Test
    void update_validData_shouldUpdateExistedContract() throws RepositoryException, ServiceException {
        //given
        User contractOwner = User.builder().id(1).build();
        UserDTO contractOwnerDTO = UserDTO.builder().id(1).build();

        Contract expectedContract = Contract.builder()
                .id(1).owner(contractOwner).description("edited contract").startDate(LocalDate.now().plusDays(2L))
                .endDate(LocalDate.now().plusDays(5L)).startPrice(50000).build();
        ContractDTO expectedContractDTO = ContractDTO.builder()
                .id(1).owner(contractOwnerDTO).description("edited contract").startDate(LocalDate.now().plusDays(2L))
                .endDate(LocalDate.now().plusDays(5L)).startPrice(50000).build();

        //when
        Mockito.when(repository.update(expectedContract)).thenReturn(expectedContract);
        ContractDTO actualContractDTO = service.update(expectedContract);
        //then
        assertContractEquals(expectedContractDTO, actualContractDTO);
    }

    @Test
    void add_validData_shouldReturnNewCreatedContract() throws RepositoryException, ServiceException {
        //given
        User contractOwner = User.builder().id(1).build();
        UserDTO contractOwnerDTO = UserDTO.builder().id(1).build();

        Contract expectedContract = Contract.builder()
                .id(3).owner(contractOwner).description("new contract").startDate(LocalDate.now().plusDays(1L))
                .endDate(LocalDate.now().plusDays(2L)).startPrice(50000).build();
        ContractDTO expectedContractDTO = ContractDTO.builder()
                .id(3).owner(contractOwnerDTO).description("new contract").startDate(LocalDate.now().plusDays(1L))
                .endDate(LocalDate.now().plusDays(2L)).startPrice(50000).build();
        //when
        Mockito.when(repository.add(expectedContract)).thenReturn(Optional.of(expectedContract));
        ContractDTO actualContract = service.add(expectedContract).get();
        //then
        assertContractEquals(expectedContractDTO, actualContract);
    }

    private void assertContractEquals(ContractDTO expectedContract, ContractDTO actualContract) {

        assertAll(
                () -> assertEquals(expectedContract.getId(), actualContract.getId()),
                () -> assertEquals(expectedContract.getOwner().getId(), actualContract.getOwner().getId()),
                () -> assertEquals(expectedContract.getDescription(), actualContract.getDescription()),
                () -> assertEquals(expectedContract.getStartDate(), actualContract.getStartDate()),
                () -> assertEquals(expectedContract.getEndDate(), actualContract.getEndDate()),
                () -> assertEquals(expectedContract.getStartPrice(), actualContract.getStartPrice())
        );
    }
}