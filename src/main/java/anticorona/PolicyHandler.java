package anticorona;

import anticorona.config.kafka.KafkaProcessor;
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
    public void wheneverBookCancelled_ModifyStock(@Payload BookCancelled bookCancelled){

        if(!bookCancelled.validate()) return;

        System.out.println("\n\n##### listener ModifyStock : " + bookCancelled.toJson() + "\n\n");

        // 예약수량 감소 //
        Vaccine vaccine = vaccineRepository.findByVaccineId(bookCancelled.getVaccineId());
        vaccine.setBookQty(vaccine.getBookQty()-1);
        vaccineRepository.save(vaccine);
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverVcCompleted_ModifyStock(@Payload VcCompleted vcCompleted){

        if(!vcCompleted.validate()) return;

        System.out.println("\n\n##### listener ModifyStock : " + vcCompleted.toJson() + "\n\n");

        // 재고수량 & 예약수량 감소 //
        Vaccine vaccine = vaccineRepository.findByVaccineId(vcCompleted.getVaccineId());
        vaccine.setStock(vaccine.getStock()-1);
        vaccine.setBookQty(vaccine.getBookQty()-1);
        vaccineRepository.save(vaccine);
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}


}
