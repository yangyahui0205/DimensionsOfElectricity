package com.baway.dimensionsofelectricity.data.beans;

/*
 *@Auther:杨雅慧
 *@Date: 19.7.23
 *@Time: 11:55:37
 *@Description:
 * */public class UserBean {

    /**
     * result : {"createTime":1542816321000,"headPic":"http://172.17.8.100/images/small/head_pic/2018-11-21/20181121102818.jpg","nickName":"风","password":"qAMcuRhmMFPH2HhixiA1dA==","phone":"16619958760","sex":1,"userId":12}
     * message : 查询成功
     * status : 0000
     */

    private ResultBean result;
    private String message;
    private String status;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class ResultBean {
        /**
         * createTime : 1542816321000
         * headPic : http://172.17.8.100/images/small/head_pic/2018-11-21/20181121102818.jpg
         * nickName : 风
         * password : qAMcuRhmMFPH2HhixiA1dA==
         * phone : 16619958760
         * sex : 1
         * userId : 12
         */

        private long createTime;
        private String headPic;
        private String nickName;
        private String password;
        private String phone;
        private int sex;
        private int userId;

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getHeadPic() {
            return headPic;
        }

        public void setHeadPic(String headPic) {
            this.headPic = headPic;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
