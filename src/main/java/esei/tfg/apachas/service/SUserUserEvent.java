package esei.tfg.apachas.service;

import esei.tfg.apachas.converter.ConUserUserEvent;
import esei.tfg.apachas.entity.*;
import esei.tfg.apachas.entity.id.UserUserEventId;
import esei.tfg.apachas.model.MUserUserEvent;
import esei.tfg.apachas.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service("SUserUserEvent")
public class SUserUserEvent {

    @Autowired
    @Qualifier("RUserUserEvent")
    private RUserUserEvent rUserUserEvent;

    @Autowired
    @Qualifier("REvent")
    private REvent rEvent;

    @Autowired
    @Qualifier("RUserEvent")
    private RUserEvent rUserEvent;

    @Autowired
    @Qualifier("ConUserUserEvent")
    private ConUserUserEvent conUserUserEvent;

    public synchronized boolean deleteUserUserEvent(long eventId) {
        List<UserUserEvent> userUserEventList = rUserUserEvent.findByEvent_EventId(eventId);
        if (userUserEventList.size() > 0) {
            rUserUserEvent.deleteAll(userUserEventList);
            return true;
        } else {
            return false;
        }
    }

    public synchronized boolean insertUserUserEvent(long eventId) {
        Event existingEvent = rEvent.findByEventId(eventId);
        if (existingEvent != null) {
            /*setUserUserEventCost(existingEvent.getEventId());
            List<UserEvent> receiverList = rUserEvent.findByEvent_EventIdAndDebtGreaterThanEqualOrderByDebtAsc(eventId);
            List<UserEvent> senderList = rUserEvent.findByEvent_EventIdAndDebtGreaterThanEqualOrderByDebtDesc(eventId);
            while (receiverList.size() != 0 || senderList.size() !=0){
                receiverList.forEach((receiver) -> senderList.forEach((sender) -> {
                    if (receiver.getDebt() + sender.getDebt() == 0){
                        rUserUserEvent.save(new UserUserEvent(new UserUserEventId(sender.getUserEventId().getUserId(), receiver.getUserEventId().getUserId(), eventId), receiver.getDebt() + sender.getDebt(), false, false));
                        senderList.remove(sender);
                        receiverList.remove(receiver);
                    }else if (receiver.getDebt() + sender.getDebt() < 0) { //sender debe mas dinero
                        rUserUserEvent.save(new UserUserEvent(new UserUserEventId(sender.getUserEventId().getUserId(), receiver.getUserEventId().getUserId(), eventId), receiver.getDebt(), false, false));
                        sender.setDebt(sender.getDebt() + receiver.getDebt());
                        receiverList.remove(receiver);
                    }else if (receiver.getDebt() + sender.getDebt() > 0) { //deben mas a receiver
                        rUserUserEvent.save(new UserUserEvent(new UserUserEventId(sender.getUserEventId().getUserId(), receiver.getUserEventId().getUserId(), eventId), sender.getDebt(), false, false));
                        receiver.setDebt(sender.getDebt() + receiver.getDebt());
                        senderList.remove(sender);
                    }
                }));
            }*/
            return true;
        } else {
            return false;
        }
    }

    //selecciona los que emiten el pago
    private LinkedHashMap<Long, Double> getUserUserEventSender(LinkedHashMap<Long,Double> sortedUserUserEventCostHash){
        LinkedHashMap<Long, Double> userUserEventSenderHash = new LinkedHashMap<>();
        sortedUserUserEventCostHash.forEach((userId, cost)->{
            if (cost < 0){
                userUserEventSenderHash.put(userId,cost);
            }
        });
        return userUserEventSenderHash;
    }

    //selecciona los que reciben el pago
    private LinkedHashMap<Long, Double> getUserUserEventReceiver(LinkedHashMap<Long,Double> sortedUserUserEventCostHash){
        LinkedHashMap<Long, Double> userUserEventReceiverHash = new LinkedHashMap<>();
        sortedUserUserEventCostHash.forEach((userId, cost)->{
            if (cost >= 0){
                userUserEventReceiverHash.put(userId,cost);
            }
        });
        return  userUserEventReceiverHash;
    }

    //calcula el coste de las transacciones
    private void setUserUserEventCost (long eventId){
        List<UserEvent> userEventList = rUserEvent.findByUserEventId_EventId(eventId);
        userEventList.forEach((userEvent)->{
            userEvent.setDebt(userEvent.getTotalExpense() - userEvent.getDebt());
            rUserEvent.save(userEvent);
        });
    }

    //ordena los usuarios segun su deuda
    private LinkedHashMap<Long,Double> sortUserUserEventCost (HashMap<Long, Double> userUserEventCostHash){
        return userUserEventCostHash
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(a,b) -> a, LinkedHashMap::new));
    }

    public synchronized List<MUserUserEvent> selectPageableUserUserEvents(Long eventId, Pageable pageable) {
        List<UserUserEvent> userUserEventList = rUserUserEvent.findPageableUserUserEvents(eventId, pageable).getContent();
        return conUserUserEvent.conUserUserEventList(userUserEventList);
    }

    public synchronized Long countUserUserEvents(long eventId){
        return rUserUserEvent.countUserUserEvents(eventId);
    }

    public synchronized List<MUserUserEvent> selectPageableSearchUserUserEvents(String transactionActorName, long eventId, Pageable pageable) {
        return conUserUserEvent.conUserUserEventList(rUserUserEvent.findPageableSearchUserUserEvents(eventId, transactionActorName, pageable).getContent());
    }

    public synchronized Long countSearchUserUserEvents(String transactionActorName, long eventId){
        return rUserUserEvent.countSearchUserUserEvents(eventId, transactionActorName);
    }
}
