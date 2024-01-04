package com.sky.service.impl;

import com.sky.constant.DefaultAddressConstant;
import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.exception.AddressBookBusinessException;
import com.sky.mapper.AddressBookMapper;
import com.sky.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressBookServiceImpl implements AddressBookService {
    @Autowired
    private AddressBookMapper addressBookMapper;

    @Override
    public List<AddressBook> list() {
        return addressBookMapper.list(AddressBook.builder().userId(BaseContext.getCurrentId()).build());
    }

    @Override
    public void insert(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBookMapper.insert(addressBook);
    }

    @Override
    public AddressBook selectById(Long id) {
        return addressBookMapper.selectById(id);
    }

    @Override
    public void update(AddressBook addressBook) {
        addressBookMapper.update(addressBook);
    }

    @Override
    public void deleteById(Long id) {
        addressBookMapper.deleteById(id);
    }

    @Override
    public AddressBook selectDefault() {
        List<AddressBook> addressList = addressBookMapper.list(AddressBook.builder().userId(BaseContext.getCurrentId()).isDefault(DefaultAddressConstant.DEFAULT).build());
        if (addressList != null && !addressList.isEmpty())
            return addressList.get(0);
        else
            throw new AddressBookBusinessException(DefaultAddressConstant.NO_DEFAULT_ADDRESS);
    }

    @Override
    public void setDefault(AddressBook addressBook) {
        // 查出默认地址修改为非默认,效率不如直接将所有地址改为非默认
//        List<AddressBook> addressBookList = addressBookMapper.list(AddressBook.builder().userId(BaseContext.getCurrentId()).build());
        addressBookMapper.cleanDefaultByUserId(BaseContext.getCurrentId());
        addressBookMapper.update(AddressBook.builder()
                .userId(BaseContext.getCurrentId())
                .id(addressBook.getId())
                .isDefault(DefaultAddressConstant.DEFAULT)
                .build());
    }
}
