package com.td.framework.model;


import com.td.framework.moudle.suspension.IndexBar.bean.BaseIndexPinyinBean;
import com.td.framework.utils.PinyinUtil;

/**
 * 通讯录相关的所有数据对象
 */
public class PeopleBean extends BaseIndexPinyinBean {

    private String name;//名字 客户名称 联系人名称
    private boolean isTop;//是否是最上面的 不需要被转化成拼音的
    private String department;//部门
    private String position;//职位
    private String pinYin;//拼音
    private String company;//拼音 只在联系人中使用
    private int pinYinColor;//拼音的颜色
    private String sortKey;//搜索key
    private String createTime;//创建时间
    private int id;//id
    private int contactCount;//联系人数量

    public int getContactCount() {
        return contactCount;
    }

    public void setContactCount(int contactCount) {
        this.contactCount = contactCount;
    }

    public String getSortKey() {
        return sortKey;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PeopleBean() {
    }

    /**
     * 招商 我的客户中使用
     *
     * @param name
     * @param createTime
     */
    public PeopleBean(String name, String createTime, int id, int contactCount) {
        this.name = name;
        this.createTime = createTime;
        this.id = id;
        this.contactCount = contactCount;
    }

    /**
     * 客户联系人使用
     * @param name
     * @param department
     * @param id
     */
    public PeopleBean(String name, String department, int id) {
        this.name = name;
        this.department = department;
        this.id = id;
    }

    /***
     * 招商 联系人中使用
     * @param id id
     * @param name 联系人名称
     * @param position 职位
     * @param company 公司名称
     */
    public PeopleBean(int id, String name, String position, String company) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.company = company;
        this.pinYin = PinyinUtil.getSortLetterBySortKey(PinyinUtil.getPingYin(name));
    }

    public PeopleBean(String name, String createTime) {
        this.name = name;
        this.createTime = createTime;
    }

    /**
     * 通讯录中使用
     *
     * @param name
     * @param department
     * @param position
     * @param pinYinColor
     */
    public PeopleBean(String name, String department, String position, int pinYinColor) {
        this.name = name;
        this.department = department;
        this.position = position;
        setTopTag(department);
        this.pinYinColor = pinYinColor;
        this.pinYin = PinyinUtil.getSortLetterBySortKey(PinyinUtil.getPingYin(name));
    }

    public String getPinYin() {
        return PinyinUtil.getSortLetterBySortKey(PinyinUtil.getPingYin(name));
    }

    public void setPinYin(String pinYin) {
        this.pinYin = pinYin;
    }

    public String getDepartment() {
        return department == null ? "" : department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position == null ? "" : position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public PeopleBean setName(String name) {
        this.name = name;
        return this;
    }

    public int getPinYinColor() {
        return pinYinColor;
    }

    public void setPinYinColor(int pinYinColor) {
        this.pinYinColor = pinYinColor;
    }

    public boolean isTop() {
        return isTop;
    }

    public PeopleBean setTop(boolean top) {
        isTop = top;
        return this;
    }

    @Override
    public String getTarget() {
        return name;
    }

    @Override
    public boolean isNeedToPinyin() {
        return !isTop;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public boolean isShowSuspension() {
        return !isTop;
    }
}
