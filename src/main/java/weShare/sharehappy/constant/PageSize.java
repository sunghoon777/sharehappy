package weShare.sharehappy.constant;

public enum PageSize {
    MAIN_PAGE_SIZE(8),
    COMMENT_PAGE_SIZE(10),
    PAGE_LIST_SIZE(5),
    MY_DONATION_LIST_SIZE(20),
    MY_DONATION_POST_SIZE(8);

    private final int size;
    private PageSize(int size){
        this.size = size;
    }

    public int getSize() {
        return size;
    }

}
