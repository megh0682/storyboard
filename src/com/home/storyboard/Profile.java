package com.home.storyboard;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.io.Serializable;

@DatabaseTable(tableName="Profiles")
public class Profile
implements Serializable {
    public static final String FIRSTNAME_FIELD_NAME = "firstname";
    public static final String LASTNAME_FIELD_NAME = "lastname";
    public static final String EMAIL_FIELD_NAME = "email";
    public static final String PROFILEPIC_FIELD_NAME = "profpic";
    public static final String USERID_FIELD_NAME = "userid";
    @DatabaseField(columnName=FIRSTNAME_FIELD_NAME, canBeNull=false)
    private String firstname;
    @DatabaseField(columnName=LASTNAME_FIELD_NAME, canBeNull=false)
    private String lastname;
    @DatabaseField(columnName=EMAIL_FIELD_NAME, canBeNull=false)
    private String email;
    @DatabaseField(columnName=PROFILEPIC_FIELD_NAME, dataType=DataType.BYTE_ARRAY)
    private byte[] profpic;
    @DatabaseField(columnName=USERID_FIELD_NAME)
    private Integer userid;
    @DatabaseField(generatedId = true)
    private Integer id;

    Profile() {
    }

    public Profile(Integer id) {
        this.id = id;
    }

    public Profile(Integer id, String firstname, String lastname, String email) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }
    
    public Profile(Integer id, String firstname, String lastname, String email,Integer userid) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.userid = userid;
    }

    public Integer getId() {
        return this.id;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public String getEmail() {
        return this.email;
    }

    public byte[] getProfpic() {
        return this.profpic;
    }

    public Integer getUserId() {
        return this.userid;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProfpic(byte[] profpic) {
        this.profpic = profpic;
    }

    public void setUserId(Integer userid) {
        this.userid = userid;
    }

    public int hashCode() {
        int hash = 0;
        return hash += this.id != null ? this.id.hashCode() : 0;
    }

    public boolean equals(Object object) {
        if (!(object instanceof Profile)) {
            return false;
        }
        Profile other = (Profile)object;
        if (this.id == null && other.id != null || this.id != null && !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    public String toString() {
        return "com.home.storyboard.Profile[ id=" + this.id + " ]";
    }
}