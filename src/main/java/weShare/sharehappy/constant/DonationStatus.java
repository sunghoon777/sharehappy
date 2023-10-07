package weShare.sharehappy.constant;

public enum DonationStatus {
    READY("대기"), PAID("결제"), FAILED("실패"), CANCELLED("취소");

    private String krName;

    private DonationStatus(String krName){
        this.krName = krName;
    }

    public String getKrName() {
        return krName;
    }
}
