package com.sky.mapper;

import com.sky.entity.AddressBook;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AddressBookMapper {

    List<AddressBook> list(AddressBook addressBook);

    @Insert("insert into address_book (user_id, consignee, sex, phone, province_code, province_name, city_code, city_name, district_code, district_name, detail, label) " +
            "VALUES (#{userId}, #{consignee},  #{sex}, #{phone},#{provinceCode}, #{provinceName}, #{cityCode}, #{cityName},#{districtCode}, #{districtName}, #{detail}, #{label})")
    void insert(AddressBook addressBook);

    @Select("select * from address_book where id = #{id}")
    AddressBook selectById(Long id);

    void update(AddressBook addressBook);

    @Delete("delete from address_book where id = #{id}")
    void deleteById(Long id);

    @Update("update address_book set is_default = ${@com.sky.constant.DefaultAddressConstant@NOT_DEFAULT} " +
            "where user_id = #{userId}")
    void cleanDefaultByUserId(Long userId);
}
