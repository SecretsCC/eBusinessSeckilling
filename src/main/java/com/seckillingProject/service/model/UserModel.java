package com.seckillingProject.service.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserModel {
    private Integer id;
    @NotBlank(message = "user name can not be empty")
    private String name;

    @NotNull(message="gender can not be empty")
    private Byte gender;

    @NotNull(message="age can not be empty")
    @Min(value=0,message="age must larger than 0")
    @Max(value = 150,message="age must smaller than 150")
    private Integer age;

    @NotBlank(message = "phone number can not be empty")
    private String telephone;
    private String registerMode;
    private String thirdPartyId;

    @NotBlank(message = "password can not be empty")
    private String encryptPassWord;

    public String getEncryptPassWord() {
        return encryptPassWord;
    }

    public void setEncryptPassWord(String encryptPassWord) {
        this.encryptPassWord = encryptPassWord;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getRegisterMode() {
        return registerMode;
    }

    public void setRegisterMode(String registerMode) {
        this.registerMode = registerMode;
    }

    public String getThirdPartyId() {
        return thirdPartyId;
    }

    public void setThirdPartyId(String thirdPartyId) {
        this.thirdPartyId = thirdPartyId;
    }
}
