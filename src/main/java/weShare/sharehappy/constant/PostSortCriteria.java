package weShare.sharehappy.constant;

public enum PostSortCriteria {

    DESCENDING_REG_DATE("최신 순"),
    ASCENDING_END_DATE("종료 임박 순"),
    DESCENDING_CURRENT_AMOUNT("모금액 많은 순"),
    ASCENDING_CURRENT_AMOUNT("모금액 적은 순"),
    DESCENDING_FUND_PERCENTAGE("모금률 높은 순"),
    ASCENDING_FUND_PERCENTAGE("모금률 적은 순");

    private final String value;

    private PostSortCriteria(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
