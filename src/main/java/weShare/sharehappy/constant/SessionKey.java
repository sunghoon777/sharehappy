package weShare.sharehappy.constant;

import weShare.sharehappy.dto.user.UserSummary;

public enum SessionKey {
    USER_AUTH(UserSummary.class);

    private Class clazz;

    private SessionKey(Class<?> clazz){
        this.clazz = clazz;
    }

    public Class getClazz() {
        return clazz;
    }
}
