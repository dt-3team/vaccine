package anticorona;

public class StockAdded extends AbstractEvent {

    private Long vaccineId;
    private String vcName;
    private Long stock;
    private Long bookQty;

    public StockAdded(){
        super();
    }

    public Long getVaccineId() {
        return vaccineId;
    }

    public void setVaccineId(Long vaccineId) {
        this.vaccineId = vaccineId;
    }
    public String getVcName() {
        return vcName;
    }

    public void setVcName(String vcName) {
        this.vcName = vcName;
    }
    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }
    public Long getBookQty() {
        return bookQty;
    }

    public void setBookQty(Long bookQty) {
        this.bookQty = bookQty;
    }
}
