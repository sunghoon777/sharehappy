package sharehappy.enums;

public enum PostSortCriteria {

    REG_DATE("최신순"),
    END_DATE("종료임박순"),
    DESCENDING_TARGET_AMOUNT("모금액 많은 순"),
    ASCENDING_TARGET_AMOUNT("모금액 적은 순"),
    FUND_HIGH_PERCENTAGE("모금률 높은 순");

    private final String value;

    private PostSortCriteria(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
