package com.baway.dimensionsofelectricity.data.beans;

import java.util.List;

/*
 *@Auther:杨雅慧
 *@Date: 19.7.21
 *@Time: 19:40:07
 *@Description:
 * */public class MyCircleBean {

    /**
     * result : [{"commodityId":6,"content":"好用，就是太贵","createTime":1542818290000,"greatNum":0,"headPic":"http://172.17.8.100/images/small/head_pic/2018-11-21/20181121102818.jpg","id":5,"image":"http://172.17.8.100/images/small/circle_pic/2018-11-21/2580720181121103810.jpg,http://172.17.8.100/images/small/circle_pic/2018-11-21/2104320181121103810.jpg","nickName":"风","userId":12}]
     * message : 查詢成功
     * status : 0000
     */

    private String message;
    private String status;
    private List<ResultBean> result;

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

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * commodityId : 6
         * content : 好用，就是太贵
         * createTime : 1542818290000
         * greatNum : 0
         * headPic : http://172.17.8.100/images/small/head_pic/2018-11-21/20181121102818.jpg
         * id : 5
         * image : http://172.17.8.100/images/small/circle_pic/2018-11-21/2580720181121103810.jpg,http://172.17.8.100/images/small/circle_pic/2018-11-21/2104320181121103810.jpg
         * nickName : 风
         * userId : 12
         */

        private int commodityId;
        private String content;
        private long createTime;
        private int greatNum;
        private String headPic;
        private int id;
        private String image;
        private String nickName;
        private int userId;

        public int getCommodityId() {
            return commodityId;
        }

        public void setCommodityId(int commodityId) {
            this.commodityId = commodityId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getGreatNum() {
            return greatNum;
        }

        public void setGreatNum(int greatNum) {
            this.greatNum = greatNum;
        }

        public String getHeadPic() {
            return headPic;
        }

        public void setHeadPic(String headPic) {
            this.headPic = headPic;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
