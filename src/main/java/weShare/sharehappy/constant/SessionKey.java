package weShare.sharehappy.constant;

import weShare.sharehappy.dto.user.UserSummary;
import weShare.sharehappy.vo.PasswordForgotUser;

public enum SessionKey {
    USER_AUTH(UserSummary.class),
    PASSWORD_FORGOT_USER(PasswordForgotUser.class);

    private Class clazz;

    private SessionKey(Class<?> clazz){
        this.clazz = clazz;
    }

    public Class getClazz() {
        return clazz;
    }
}
