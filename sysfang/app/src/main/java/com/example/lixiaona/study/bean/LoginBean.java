package com.example.lixiaona.study.bean;

import java.io.Serializable;

public class LoginBean implements Serializable {

    /**
     * checkDate : 1526865280000
     * ryToken : SMApRMUJcbUaT/Ufe/ph46d8p65dGp6PRA9Z3uNEvmghYA9Rnr/x8B/TcmQUtUIVUdGCM9dx7txyquu6yR6T/KMrZlX25G5QLQCadIBwYl4=
     * userId : 120
     * userType : 3
     * realName : 任文辉
     * phone : 13752350414
     * lastLoginDate : 2019-03-31 15:40:34
     * checkUserId : 95
     * cardBackUrl :
     * age : 20
     * userName : 13752350414
     * checkOpinion : 通过
     * lastLoginIP : 192.168.1.105
     * available : 0
     * cardNo : 140729198702130036
     * businessLicenUrl :
     * address : 天津市南开区
     * password : d7f75c7cd18ac72efe63d7c1593be047
     * createDate : 1526865170000
     * updateDate : 1554018038000
     * cardJustUrl :
     * accessToken : 120&5faad45c-707f-4909-b06d-5ba4af1b21e2
     * hygieneLicenUrl :
     * salt : 709403
     * checkUserName : 管理员
     * headUrl : http://118.244.233.204:9090/images/0/14/db85fea90b73444094ee792e2e48e349.jpeg
     * delFlag : 0
     * clientId : be81d87c525686a8925c9ea75a16f4fa
     * balance : 0.0
     * ryUserId : 13752350414_3
     * updateUserId : 95
     * checkState : 1
     * sex : 0
     */

    private long checkDate;
    private int userId;
    private String userType;
    private String realName;
    private String phone;
    private String lastLoginDate;
    private int checkUserId;
    private String cardBackUrl;
    private int age;
    private String userName;
    private String checkOpinion;
    private String lastLoginIP;
    private String communityName;
    private String available;
    private String cardNo;
    private String businessLicenUrl;
    private String address;
    private String password;
    private long createDate;
    private long updateDate;
    private String cardJustUrl;
    private String accessToken;
    private String hygieneLicenUrl;
    private String salt;
    private String checkUserName;
    private String headUrl;
    private String delFlag;
    private String clientId;
    private double balance;
    private int updateUserId;
    private String checkState;
    private String sex;
    private String ryUserId;
    private String ryToken;

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public long getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(long checkDate) {
        this.checkDate = checkDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(String lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public int getCheckUserId() {
        return checkUserId;
    }

    public void setCheckUserId(int checkUserId) {
        this.checkUserId = checkUserId;
    }

    public String getCardBackUrl() {
        return cardBackUrl;
    }

    public void setCardBackUrl(String cardBackUrl) {
        this.cardBackUrl = cardBackUrl;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCheckOpinion() {
        return checkOpinion;
    }

    public void setCheckOpinion(String checkOpinion) {
        this.checkOpinion = checkOpinion;
    }

    public String getLastLoginIP() {
        return lastLoginIP;
    }

    public void setLastLoginIP(String lastLoginIP) {
        this.lastLoginIP = lastLoginIP;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getBusinessLicenUrl() {
        return businessLicenUrl;
    }

    public void setBusinessLicenUrl(String businessLicenUrl) {
        this.businessLicenUrl = businessLicenUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }

    public String getCardJustUrl() {
        return cardJustUrl;
    }

    public void setCardJustUrl(String cardJustUrl) {
        this.cardJustUrl = cardJustUrl;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getHygieneLicenUrl() {
        return hygieneLicenUrl;
    }

    public void setHygieneLicenUrl(String hygieneLicenUrl) {
        this.hygieneLicenUrl = hygieneLicenUrl;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getCheckUserName() {
        return checkUserName;
    }

    public void setCheckUserName(String checkUserName) {
        this.checkUserName = checkUserName;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(int updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getCheckState() {
        return checkState;
    }

    public void setCheckState(String checkState) {
        this.checkState = checkState;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRyUserId() {
        return ryUserId;
    }

    public void setRyUserId(String ryUserId) {
        this.ryUserId = ryUserId;
    }

    public String getRyToken() {
        return ryToken;
    }

    public void setRyToken(String ryToken) {
        this.ryToken = ryToken;
    }
}
