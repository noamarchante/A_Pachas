/*
(1) 0/2 * * * *? Representación de cada 2 segundos
 (1) 0 0/2 * * *? Representa cada 2 minutos para realizar tareas.
 (1) 0 0 2 1 *? Instrucciones para ajustar la tarea a las 2 de la mañana del mes.
 (2) 0 15 10? * Mon-Fri significa que la operación se realiza todos los días a partir de las 10:15 am de lunes a viernes.
 (3) 0 15 10? 6L 2002-2006 Indica que el viernes pasado a las 10:15 entre 2002-2006
 (4) 0 0 10, 14, 16 * *? Todos los días, a las 2:00 PM, 4 en punto
 (5) 0 0/30 9-17 * *? Cada mitad de la mitad de las horas de trabajo de las nueve noches
 (6) 0 0 12? * MIERDO significa 12 del mediodía todos los miércoles
 (7) 0 0 12 * *? Activado a las 12 del mediodía todos los días
 (8) 0 15 10? * * Trigger todos los días a las 10:11 AM
 (9) 0 15 10 * *? Disparador todos los días a las 10:11 AM
 (10) 0 15 10 * *? Gatillo todos los días a las 10:15
 (11) 0 15 10 * *? 2005 activado todos los días a las 10:11 am
 (12) 0 * 14 * *? Disparador cada 1 minuto de 2 pm a 2:59 PM
 (13) 0 0/5 14 * *? Disparador cada 5 minutos de 2 pm a 2:55 PM
 (14) 0 0/5 14, 18 * *? Disparador cada 5 minutos durante cada tarde a 2:55 y 6:00 PM
 (15) 0 0-5 14 * *? Disparar cada minuto durante las 2 pm a 2:05 PM
 (16) 0 10, 44 14? ​​3 Casé desencadenantes 2:10 PM el miércoles, marzo
 (17) 0 15 10? * Mon-Fri activado a partir de las 10:15 am de lunes a viernes
 (18) 0 15 10 15 *? Disparador a las 10:11 am todos los meses
 (19) 0 15 10 l *? Disparador a las 10:11 GLAS en el último día de cada mes
 (20) 0 15 10? * 6L activado en el último viernes a las 10:15 am
 (21) 0 15 10? * 6L 2002-2005 2002 a 2005 Mensual el viernes pasado a las 10:15
 (22) 0 15 10? * 6 # 3 Disparador 10:15 en el tercer día del mes
*/
package esei.tfg.apachas.service;

import esei.tfg.apachas.converter.ConUser;
import esei.tfg.apachas.model.MEmailBody;
import esei.tfg.apachas.model.MUser;
import esei.tfg.apachas.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Timestamp;
import java.util.List;

@Service
@Async
public class SAsync {
    @Autowired
    @Qualifier("RUserEvent")
    private RUserEvent rUserEvent;

    @Autowired
    @Qualifier("RUserUserEvent")
    private RUserUserEvent rUserUserEvent;

    @Autowired
    @Qualifier("RUserUser")
    private RUserUser rUserUser;

    @Autowired
    @Qualifier("RUser")
    private RUser rUser;

    @Autowired
    @Qualifier("ConUser")
    private ConUser conUser;

    @Autowired
    @Qualifier("SEmail")
    SEmail sEmail;

    @Autowired
    @Qualifier("RGroupUser")
    private RGroupUser rGroupUser;

    //HOY SE TE HA AÑADIDO A UN EVENTO (cada 3 horas)
    @Scheduled(cron = " 0 0 0/3 * * ?")
    public void eventNotification(){
        Timestamp currentDate = new Timestamp(System.currentTimeMillis());

        Timestamp previousDate = new Timestamp(currentDate.getTime() - 7200000L);

        List<MUser> mUserList = conUser.conUserList(rUserEvent.findAddedNewEvent(currentDate, previousDate));

        for (MUser mUser: mUserList) {
            sendNotificationEmail(mUser, "src/main/resources/templates/eventNotification.html");
        }
    }

    //RECORDATORIO DE QUE SE APROXIMA EVENTO (1 vez al día)
    @Scheduled(cron = "0 0 8 ? * *")
    public void eventReminder(){
        //una vez al dia la fecha actual + 3 días sin tener en cuenta la hora
        Timestamp date = new Timestamp(System.currentTimeMillis() + 259200000L);
        List<MUser> mUserList = conUser.conUserList(rUserEvent.findNearEvent(date));
        for (MUser mUser: mUserList) {
            sendNotificationEmail(mUser, "src/main/resources/templates/eventReminder.html");
        }
    }

    //RECORDATORIO DE QUE EMPIEZA EVENTO (1 vez al día)
    @Scheduled(cron = "0 0 8 ? * *")
    public void eventStart(){
        //una vez al día la fecha actual sin contar las horas
        Timestamp date = new Timestamp(System.currentTimeMillis());
        List<MUser> mUserList = conUser.conUserList(rUserEvent.findEventStart(date));
        for (MUser mUser: mUserList) {
            sendNotificationEmail(mUser, "src/main/resources/templates/eventStart.html");
        }
    }

    //RECORDATORIO DE QUE SE HA CERRADO UN EVENTO (cada 3 horas)
    @Scheduled(cron = " 0 0 0/3 * * ?")
    public void eventFinished(){
        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        Timestamp previousDate = new Timestamp(currentDate.getTime() - 7200000L);
        List<MUser> mUserList = conUser.conUserList(rUserEvent.findEventFinished(currentDate, previousDate));
        for (MUser mUser: mUserList) {
            sendNotificationEmail(mUser, "src/main/resources/templates/eventFinished.html");
        }
    }

    //RECORDATORIO DE QUE SE TE HA AÑADIDO A UN GRUPO (cada 3 horas)
    @Scheduled(cron = " 0 0 0/3 * * ?")
    public void groupNotification(){
        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        Timestamp previousDate = new Timestamp(currentDate.getTime() - 7200000L);
        List<MUser> mUserList = conUser.conUserList(rGroupUser.findAddedNewGroup(currentDate, previousDate));
        for (MUser mUser: mUserList) {
            sendNotificationEmail(mUser, "src/main/resources/templates/groupNotification.html");
        }
    }

    //RECORDATORIO DE SOLICITUD DE AMISTAD (cada 3 horas)
    @Scheduled(cron = " 0 0 0/3 * * ?")
    public void userFriendRequest(){
        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        Timestamp previousDate = new Timestamp(currentDate.getTime() - 7200000L);
        List<MUser> mUserList = conUser.conUserList(rUserUser.findNewRequest(currentDate, previousDate));
        for (MUser mUser: mUserList) {
            sendNotificationEmail(mUser, "src/main/resources/templates/userFriendRequest.html");
        }
    }

    //RECORDATORIO DE TRANSACCIONES POR PAGAR (cada mes)
    @Scheduled(cron = "0 0 8 1 * ?")
    public void transactionPayReminder(){
        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        Timestamp previousDate = new Timestamp(currentDate.getTime() - 2592000000L);
        //cada mes sin tener en cuenta las horas entre el día actual y 30 días antes
        List<MUser> mUserList = conUser.conUserList(rUserUserEvent.findPendingPay(currentDate, previousDate));
        for (MUser mUser: mUserList) {
            sendNotificationEmail(mUser, "src/main/resources/templates/transactionPayReminder.html");
        }
    }

    //RECORDATORIO DE TRANSACCIONES POR CONFIRMAR (cada mes)
    @Scheduled(cron = "0 0 8 1 * ?")
    public void transactionConfirmReminder(){
        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        Timestamp previousDate = new Timestamp(currentDate.getTime() - 2592000000L);
        //cada mes sin tener en cuenta las horas entre el día actual y 30 días antes
        List<MUser> mUserList = conUser.conUserList(rUserUserEvent.findPendingConfirm(currentDate, previousDate));
        for (MUser mUser: mUserList) {
            sendNotificationEmail(mUser, "src/main/resources/templates/transactionConfirmReminder.html");
        }
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void prueba(){

    }

    private boolean sendNotificationEmail(MUser mUser, String urlTemplate){
        File file = null;
        String line;
        FileReader fr = null;
        BufferedReader br = null;
        String content = "";
        try {
            file = new File (urlTemplate);
            fr = new FileReader (file);
            br = new BufferedReader(fr);
            while((line=br.readLine())!=null){
                content += line;
            }
            content = content
                    .replace(" * ",  "cid:image001");
            boolean send = sEmail.sendEmailWithImage(new MEmailBody(content, mUser.getUserEmail(),"APACHAS: Notificación"));
            if (send){
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }finally{
            try{
                if( null != fr ){
                    fr.close();
                }
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }
    }
}
