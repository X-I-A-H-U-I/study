package com.github.xia.security.admin.vo;

import com.github.xia.security.admin.entity.BaseUser;

import java.util.List;

/**
 * @explain：
 * @author: XIA
 * @date: 2020-03-11
 * @since: JDK 1.8
 * @version: 1.0
 */
public class GroupUsers {
    List<BaseUser> members ;
    List<BaseUser> leaders;

    public GroupUsers() {
    }

    public GroupUsers(List<BaseUser> members, List<BaseUser> leaders) {
        this.members = members;
        this.leaders = leaders;
    }

    public List<BaseUser> getMembers() {
        return members;
    }

    public void setMembers(List<BaseUser> members) {
        this.members = members;
    }

    public List<BaseUser> getLeaders() {
        return leaders;
    }

    public void setLeaders(List<BaseUser> leaders) {
        this.leaders = leaders;
    }
}
