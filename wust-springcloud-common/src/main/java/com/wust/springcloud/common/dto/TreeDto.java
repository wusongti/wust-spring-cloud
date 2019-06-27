package com.wust.springcloud.common.dto;

/**
 * Created by WST on 2019/6/5.
 */
public class TreeDto implements java.io.Serializable{
    private static final long serialVersionUID = 8251385618215405526L;

    private String id;
    private String pId;
    private String name;
    private String type;
    private String checked;

    public String getId()
    {
        return id;
    }
    public void setId(String id)
    {
        this.id = id;
    }
    public String getpId()
    {
        return pId;
    }
    public void setpId(String pId)
    {
        this.pId = pId;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getType()
    {
        return type;
    }
    public void setType(String type)
    {
        this.type = type;
    }
    public String getChecked()
    {
        return checked;
    }
    public void setChecked(String checked)
    {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "FunctionTreeVO{" +
                "id='" + id + '\'' +
                ", pId='" + pId + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", checked='" + checked + '\'' +
                '}';
    }
}
