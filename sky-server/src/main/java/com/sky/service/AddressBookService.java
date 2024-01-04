package com.sky.service;

import com.sky.entity.AddressBook;

import java.util.List;

public interface AddressBookService {
    List<AddressBook> list();

    void insert(AddressBook addressBook);

    AddressBook selectById(Long id);

    void update(AddressBook addressBook);

    void deleteById(Long id);

    AddressBook selectDefault();

    void setDefault(AddressBook addressBook);
}
