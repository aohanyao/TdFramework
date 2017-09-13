package com.td.framework.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 江俊超 on 2017/5/10 0010.
 * <p>版本:1.0.0</p>
 * <b>说明<b>名片信息<br/>
 * <li></li>
 */
public class VCardInfo implements Serializable{

    /**
     * address : [{"item":{"locality":"O 深圳市","street":"南山区深圳湾软件产业基地6栋3p","type":["work"]},"position":"194,1282,265,1282,265,2027,194,2027"}]
     * comment : [{"item":"リ唐豆","position":"905,1233,993,1233,993,1494,905,1494"}]
     * email : [{"item":"liliang@tangdoukeji.com","position":"313,889,376,889,376,1355,313,1355"}]
     * formatted_name : [{"item":"李良","position":"610,1830,703,1830,703,2012,610,2012"}]
     * label : [{"item":{"address":"O 深圳市南山区深圳湾软件产业基地6栋3p","type":["work"]},"position":"194,1282,265,1282,265,2027,194,2027"}]
     * name : [{"item":{"family_name":"李","given_name":"良"},"position":"0,469,0,469,0,469,0,469"}]
     * organization : [{"item":{"name":"唐豆科技(深1」の有限公司"},"position":"486,1463,544,1463,544,2021,486,2021"},{"item":{"name":"TangDou Tech (Shenzhen) Co., LTD"},"position":"426,1299,479,1299,479,2026,426,2026"}]
     * rotation_angle : 90
     * telephone : [{"item":{"number":"+8618938079117","type":["cellular","voice"]},"position":"341,1602,390,1602,390,1948,341,1948"},{"item":{"number":"075589995959","type":["work","voice"]},"position":"274,1559,328,1559,328,1861,274,1861"}]
     * title : [{"item":"高级顾问 & 产品经理","position":"599,1327,658,1327,658,1790,599,1790"}]
     * url : [{"item":"www.tangdoukeji.com","position":"249,935,313,935,313,1357,249,1357"}]
     */

    private String rotation_angle;
    private List<AddressBean> address;
    private List<CommentBean> comment;
    private List<EmailBean> email;
    private List<FormattedNameBean> formatted_name;
    private List<LabelBean> label;
    private List<NameBean> name;
    private List<OrganizationBean> organization;
    private List<TelephoneBean> telephone;
    private List<TitleBean> title;
    private List<UrlBean> url;

    public String getRotation_angle() {
        return rotation_angle;
    }

    public void setRotation_angle(String rotation_angle) {
        this.rotation_angle = rotation_angle;
    }

    public List<AddressBean> getAddress() {
        return address;
    }

    public void setAddress(List<AddressBean> address) {
        this.address = address;
    }

    public List<CommentBean> getComment() {
        return comment;
    }

    public void setComment(List<CommentBean> comment) {
        this.comment = comment;
    }

    public List<EmailBean> getEmail() {
        return email;
    }

    public void setEmail(List<EmailBean> email) {
        this.email = email;
    }

    public List<FormattedNameBean> getFormatted_name() {
        return formatted_name;
    }

    public void setFormatted_name(List<FormattedNameBean> formatted_name) {
        this.formatted_name = formatted_name;
    }

    public List<LabelBean> getLabel() {
        return label;
    }

    public void setLabel(List<LabelBean> label) {
        this.label = label;
    }

    public List<NameBean> getName() {
        return name;
    }

    public void setName(List<NameBean> name) {
        this.name = name;
    }

    public List<OrganizationBean> getOrganization() {
        return organization;
    }

    public void setOrganization(List<OrganizationBean> organization) {
        this.organization = organization;
    }

    public List<TelephoneBean> getTelephone() {
        return telephone;
    }

    public void setTelephone(List<TelephoneBean> telephone) {
        this.telephone = telephone;
    }

    public List<TitleBean> getTitle() {
        return title;
    }

    public void setTitle(List<TitleBean> title) {
        this.title = title;
    }

    public List<UrlBean> getUrl() {
        return url;
    }

    public void setUrl(List<UrlBean> url) {
        this.url = url;
    }

    public static class AddressBean implements Serializable{
        /**
         * item : {"locality":"O 深圳市","street":"南山区深圳湾软件产业基地6栋3p","type":["work"]}
         * position : 194,1282,265,1282,265,2027,194,2027
         */

        private ItemBean item;
        private String position;

        public ItemBean getItem() {
            return item;
        }

        public void setItem(ItemBean item) {
            this.item = item;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public static class ItemBean implements Serializable{
            /**
             * locality : O 深圳市
             * street : 南山区深圳湾软件产业基地6栋3p
             * type : ["work"]
             */

            private String locality;
            private String street;
            private List<String> type;

            public String getLocality() {
                return locality==null?"":locality;
            }

            public void setLocality(String locality) {
                this.locality = locality;
            }

            public String getStreet() {
                return street;
            }

            public void setStreet(String street) {
                this.street = street;
            }

            public List<String> getType() {
                return type;
            }

            public void setType(List<String> type) {
                this.type = type;
            }
        }
    }

    public static class CommentBean implements Serializable{
        /**
         * item : リ唐豆
         * position : 905,1233,993,1233,993,1494,905,1494
         */

        private String item;
        private String position;

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }
    }

    public static class EmailBean implements Serializable{
        /**
         * item : liliang@tangdoukeji.com
         * position : 313,889,376,889,376,1355,313,1355
         */

        private String item;
        private String position;

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }
    }

    public static class FormattedNameBean implements Serializable{
        /**
         * item : 李良
         * position : 610,1830,703,1830,703,2012,610,2012
         */

        private String item;
        private String position;

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }
    }

    public static class LabelBean implements Serializable{
        /**
         * item : {"address":"O 深圳市南山区深圳湾软件产业基地6栋3p","type":["work"]}
         * position : 194,1282,265,1282,265,2027,194,2027
         */

        private ItemBeanX item;
        private String position;

        public ItemBeanX getItem() {
            return item;
        }

        public void setItem(ItemBeanX item) {
            this.item = item;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public static class ItemBeanX implements Serializable{
            /**
             * address : O 深圳市南山区深圳湾软件产业基地6栋3p
             * type : ["work"]
             */

            private String address;
            private List<String> type;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public List<String> getType() {
                return type;
            }

            public void setType(List<String> type) {
                this.type = type;
            }
        }
    }

    public static class NameBean implements Serializable{
        /**
         * item : {"family_name":"李","given_name":"良"}
         * position : 0,469,0,469,0,469,0,469
         */

        private ItemBeanXX item;
        private String position;

        public ItemBeanXX getItem() {
            return item;
        }

        public void setItem(ItemBeanXX item) {
            this.item = item;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public static class ItemBeanXX implements Serializable{
            /**
             * family_name : 李
             * given_name : 良
             */

            private String family_name;
            private String given_name;

            public String getFamily_name() {
                return family_name;
            }

            public void setFamily_name(String family_name) {
                this.family_name = family_name;
            }

            public String getGiven_name() {
                return given_name;
            }

            public void setGiven_name(String given_name) {
                this.given_name = given_name;
            }
        }
    }

    public static class OrganizationBean implements Serializable{
        /**
         * item : {"name":"唐豆科技(深1」の有限公司"}
         * position : 486,1463,544,1463,544,2021,486,2021
         */

        private ItemBeanXXX item;
        private String position;

        public ItemBeanXXX getItem() {
            return item;
        }

        public void setItem(ItemBeanXXX item) {
            this.item = item;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public static class ItemBeanXXX implements Serializable{
            /**
             * name : 唐豆科技(深1」の有限公司
             */

            private String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }

    public static class TelephoneBean implements Serializable{
        /**
         * item : {"number":"+8618938079117","type":["cellular","voice"]}
         * position : 341,1602,390,1602,390,1948,341,1948
         */

        private ItemBeanXXXX item;
        private String position;

        public ItemBeanXXXX getItem() {
            return item;
        }

        public void setItem(ItemBeanXXXX item) {
            this.item = item;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public static class ItemBeanXXXX implements Serializable{
            /**
             * number : +8618938079117
             * type : ["cellular","voice"]
             */

            private String number;
            private List<String> type;

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public List<String> getType() {
                return type;
            }

            public void setType(List<String> type) {
                this.type = type;
            }
        }
    }

    public static class TitleBean implements Serializable{
        /**
         * item : 高级顾问 & 产品经理
         * position : 599,1327,658,1327,658,1790,599,1790
         */

        private String item;
        private String position;

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }
    }

    public static class UrlBean implements Serializable{
        /**
         * item : www.tangdoukeji.com
         * position : 249,935,313,935,313,1357,249,1357
         */

        private String item;
        private String position;

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }
    }
}
