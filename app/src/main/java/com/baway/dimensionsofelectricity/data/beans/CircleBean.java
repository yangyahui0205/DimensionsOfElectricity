package com.baway.dimensionsofelectricity.data.beans;

import java.util.List;

/*
 *@Auther:吴亦凡的小可爱
 *@Date: 2019/7/17
 *@Time: 17:08:03
 *@Description:
 * */
public class CircleBean {

    /**
     * result : [{"commodityId":25,"content":"给大家推荐一个好商品","createTime":1563399868000,"greatNum":0,"headPic":"http://172.17.8.100/images/small/head_pic/2019-07-17/20190717165528.png","id":2278,"image":"http://172.17.8.100/images/small/circle_pic/2019-07-17/4513020190717164428.png","nickName":"艾爸爸00","userId":3118,"whetherGreat":2},{"commodityId":1,"content":"今天真的很好","createTime":1563390344000,"greatNum":0,"headPic":"http://172.17.8.100/images/small/default/user.jpg","id":2275,"image":"","nickName":"冬天的血se","userId":7187,"whetherGreat":2}]
     * message : 查询成功
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
         * commodityId : 25
         * content : 给大家推荐一个好商品
         * createTime : 1563399868000
         * greatNum : 0
         * headPic : http://172.17.8.100/images/small/head_pic/2019-07-17/20190717165528.png
         * id : 2278
         * image : http://172.17.8.100/images/small/circle_pic/2019-07-17/4513020190717164428.png
         * nickName : 艾爸爸00
         * userId : 3118
         * whetherGreat : 2
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
        private int whetherGreat;

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

        public int getWhetherGreat() {
            return whetherGreat;
        }

        public void setWhetherGreat(int whetherGreat) {
            this.whetherGreat = whetherGreat;
        }
    }
}
