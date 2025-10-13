package com.eazybytes.accounts.service.imp;

import com.eazybytes.accounts.constants.AccountsConstants;
import com.eazybytes.accounts.dto.AccountsDto;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.entity.Accounts;
import com.eazybytes.accounts.entity.Customer;
import com.eazybytes.accounts.exception.CustomerAlreadyExistsException;
import com.eazybytes.accounts.exception.ResourceNotFoundException;
import com.eazybytes.accounts.mapper.AccountsMapper;
import com.eazybytes.accounts.mapper.CustomerMapper;
import com.eazybytes.accounts.repository.AccountsRepository;
import com.eazybytes.accounts.repository.CustomerRepository;
import com.eazybytes.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private final CustomerRepository customerRepository;
    private final AccountsRepository accountsRepository;
    @Override
    public void createAccount(CustomerDto customerDto) {
        // TODO: Implement account creation logic here

        Customer customer= CustomerMapper.mapToCustomer(customerDto,new Customer());
        Optional<Customer> customerOptional = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(customerOptional.isPresent()){
            throw new CustomerAlreadyExistsException("Customer already exists with mobile number "+customerDto.getMobileNumber());
        }

        customerRepository.save(customer);
        accountsRepository.save(createNewAccount(customer));

    }
    /**
     * @param customer - Customer Object
     * @return the new account details
     */
    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);

        return newAccount;
    }
    // function details account using the account number
    /**
     * @param accountNumber - account number
     * @return the account details
     */
    /**
     * @param mobileNumber - Input Mobile Number
     * @return Accounts Details based on a given mobileNumber
     */
    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));
        return customerDto;
    }

    /**
     * @param customerDto - CustomerDto Object
     * @return boolean indicating if the update of Account details is successful or not
     */
    @Override
    public boolean updateAccount(CustomerDto customerDto) {

        // TODO: Implement account updation logic here

        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if (accountsDto != null) {
             Accounts accounts=accountsRepository
                     .findById(accountsDto.getAccountNumber())
                     .orElseThrow(()-> new ResourceNotFoundException("Account", "accountNumber", accountsDto.getAccountNumber().toString()));

             AccountsMapper.mapToAccounts(accountsDto, accounts);
             accounts=accountsRepository.save(accounts);
             Long CustomerId=accounts.getCustomerId();
             Customer customer=customerRepository.findById(CustomerId).orElseThrow(
                     () -> new ResourceNotFoundException("Customer", "customerId", CustomerId.toString())
             );
             CustomerMapper.mapToCustomer(customerDto, customer);
             customerRepository.save(customer);
             isUpdated = true;
        }
        return isUpdated;
    }

    /**
     * @param mobileNumber - mobile number
     * @return boolean indicating if the deletion of Account is successful or not
     */
    @Override
    public boolean deleteAccount(String mobileNumber) {
        boolean isDeleted = false;
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        isDeleted = true;
        return isDeleted;
    }

}
