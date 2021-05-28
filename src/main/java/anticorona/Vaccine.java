package anticorona;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.Date;

@Entity
@Table(name="Vaccine_table")
public class Vaccine {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long vaccineId;
    private String vcName;
    private Long stock;
    private Long bookQty;
    //@Transient
    private String command; //command 구분용 변수

    @PostPersist
    public void onPostPersist(){
        VcRegistered vcRegistered = new VcRegistered();
        BeanUtils.copyProperties(this, vcRegistered);
        vcRegistered.publishAfterCommit();


    }

    @PostUpdate
    public void onPostUpdate(){
        if(this.getCommand() != null) {
            if(this.getCommand().equals("addVcStock")) {
                VcStockAdded vcStockAdded = new VcStockAdded();
                BeanUtils.copyProperties(this, vcStockAdded);
                vcStockAdded.publishAfterCommit();
            } else if(this.getCommand().equals("chkAndModBookQty")){
                StockModified stockModified = new StockModified();
                BeanUtils.copyProperties(this, stockModified);
                stockModified.publishAfterCommit();
            }
        }
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

    public String getCommand() {
        return command;
    }
    public void setCommand(String command) {
        this.command = command;
    }

    /**
     * 예약 가능 여부를 확인합니다.
     * 예약 수량을 뺀 재고수량이 0 보다 큰 경우입니다.
     * @return true : 예약 가능을 나타냅니다. false : 예약 불가능을 나타냅니다.
     */
    public boolean canBook(){
        return this.stock - this.bookQty > 0;
    }
}
