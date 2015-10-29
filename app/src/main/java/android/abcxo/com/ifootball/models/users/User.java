package android.abcxo.com.ifootball.models.users;

/**
 * Created by SHARON on 15/10/29.
 */
public class User{
    public String userName;
    public String nickName;
    public String password;
    public String avatar;
    public String cover;
    public String location;
    public GenderType genderType;
    enum GenderType{
        MALE,
        FEMALE
    }
}
