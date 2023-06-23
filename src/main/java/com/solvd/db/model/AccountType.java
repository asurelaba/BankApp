package com.solvd.db.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AccountType {

    @XmlAttribute(name = "id")
    @JsonProperty("accountTypeId")
    private int accountTypeId;

    @XmlElement(name = "typeName")
    @JsonProperty("accountType")
    private String accountType;

    public int getAccountTypeId() {
        return accountTypeId;
    }

    public void setAccountTypeId(int accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @Override
    public String toString() {
        return "AccountType{" +
                "accountTypeId=" + accountTypeId +
                ", accountType='" + accountType + '\'' +
                '}';
    }
}
