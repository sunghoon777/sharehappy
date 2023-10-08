package weShare.sharehappy.constant;

public enum PostDeleteCriteria {
    END_DATE_AFTER("마감 일자 이후에는 모금함 삭제 불가합니다"),
    NOT_EQUALS_AUTHOR("게시판 제작자가 아닌 사용자는 모금함 삭제 불가합니다"),
    AFTER_DONATION_SEVEN_DAYS("모금함에 기부한 기부 내역 중 7일이 지난 기부 내역이 있으면 삭제 불가합니다");

    private String discription;

    private PostDeleteCriteria(String discription){
        this.discription = discription;
    }

    public String getDiscription() {
        return discription;
    }
}
