package weShare.sharehappy.constant;

public enum PageSize {
    MAIN_PAGE_SIZE(8);

    private final int size;
    private PageSize(int size){
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
