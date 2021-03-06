package anticorona;

import anticorona.config.kafka.KafkaProcessor;

import java.util.Optional;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
    @Autowired VaccineRepository vaccineRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverBookingCancelled_ModifyStock(@Payload BookingCancelled bookingCancelled){

        if(!bookingCancelled.validate()) return;

        System.out.println("\n\n##### listener ModifyStock : " + bookingCancelled.toJson() + "\n\n");

        // 예약수량 감소 //
        Optional<Vaccine> vaccine = vaccineRepository.findById(bookingCancelled.getVaccineId());
        if(vaccine.isPresent()){
            Vaccine vaccineValue = vaccine.get();
            vaccineValue.setBookQty(vaccineValue.getBookQty()-1);
            vaccineRepository.save(vaccineValue);
        }
        
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverCompleted_ModifyStock(@Payload Completed completed){

        if(!completed.validate()) return;

        System.out.println("\n\n##### listener ModifyStock : " + completed.toJson() + "\n\n");

        // 재고수량 & 예약수량 감소 //
        Optional<Vaccine> vaccine = vaccineRepository.findById(completed.getVaccineId());
        if(vaccine.isPresent()){
            Vaccine vaccineValue = vaccine.get();
            vaccineValue.setStock(vaccineValue.getStock()-1);
            vaccineValue.setBookQty(vaccineValue.getBookQty()-1);
            vaccineRepository.save(vaccineValue);
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}


}
