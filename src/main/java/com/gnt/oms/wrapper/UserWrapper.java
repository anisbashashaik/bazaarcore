package com.gnt.oms.wrapper;

import com.gnt.oms.entities.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserWrapper {

    private Integer userId;

    private String userName;

    private String emailId;

    private String contactNo;

    private String status;

    public UserWrapper(Integer userId, String userName, String emailId, String contactNo, String status) {
        this.userId = userId;
        this.userName = userName;
        this.emailId = emailId;
        this.contactNo = contactNo;
        this.status = status;
    }

}
