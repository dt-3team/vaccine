package anticorona;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

 @RestController
 public class VaccineController {

     @Autowired
     VaccineRepository vaccineRepository;

     @RequestMapping(value = "/vaccines/checkAndBookStock",
        method = RequestMethod.GET,
        produces = "application/json;charset=UTF-8")
    public boolean checkAndBookStock(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("##### /vaccine/checkAndBookStock  called #####");

        boolean status = false;

        Long vaccineId = Long.valueOf(request.getParameter("vaccineId"));
        
        Optional<Vaccine> vaccine = vaccineRepository.findById(vaccineId);
        if(vaccine.isPresent()){
            Vaccine vaccineValue = vaccine.get();
            //예약 가능한지 체크 
            if(vaccineValue.getStock() - vaccineValue.getBookQty() > 0) {
                //예약 가능하면 예약수량 증가
                status = true;
                vaccineValue.setBookQty(vaccineValue.getBookQty() + 1);
                vaccineRepository.save(vaccineValue);
            }
        }

        return status;
     }
 }
