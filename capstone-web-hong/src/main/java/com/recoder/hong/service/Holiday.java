package com.recoder.hong.service;

import com.ibm.icu.util.ChineseCalendar;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
@Data
public class Holiday {

    static Set<String> holidaysSet = new HashSet<>();
    public static final int LD_SUNDAY = 7;
    public static final int LD_SATURDAY = 6;
    public static final int LD_MONDAY = 1;


    /**
     * 음력날짜를 양력날짜로 변환
     */
    public String Lunar2Solar(String yyyymmdd) {
        ChineseCalendar cc = new ChineseCalendar();

        if (yyyymmdd == null)
            return null;

        String date = yyyymmdd.trim();
        if (date.length() != 8) {
            if (date.length() == 4)
                date = date + "0101";
            else if (date.length() == 6)
                date = date + "01";
            else if (date.length() > 8)
                date = date.substring(0, 8);
            else
                return null;
        }

        cc.set(ChineseCalendar.EXTENDED_YEAR, Integer.parseInt(date.substring(0, 4)) + 2637);   // 년, year + 2637
        cc.set(ChineseCalendar.MONTH, Integer.parseInt(date.substring(4, 6)) - 1);              // 월, month -1
        cc.set(ChineseCalendar.DAY_OF_MONTH, Integer.parseInt(date.substring(6)));              // 일

        LocalDate solar = Instant.ofEpochMilli(cc.getTimeInMillis()).atZone(ZoneId.of("UTC")).toLocalDate();

        int y = solar.getYear();
        int m = solar.getMonth().getValue();
        int d = solar.getDayOfMonth();

        StringBuilder ret = new StringBuilder();
        ret.append(String.format("%04d", y));
        ret.append(String.format("%02d", m));
        ret.append(String.format("%02d", d));

        return ret.toString();
    }


    public Set<String> holidayArray(String yyyy){
        holidaysSet.clear();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        // 양력 휴일
        holidaysSet.add(yyyy+"0101");   // 신정
        holidaysSet.add(yyyy+"0301");   // 삼일절
        holidaysSet.add(yyyy+"0505");   // 어린이날
        holidaysSet.add(yyyy+"0606");   // 현충일
        holidaysSet.add(yyyy+"0815");   // 광복절
        holidaysSet.add(yyyy+"1003");   // 개천절
        holidaysSet.add(yyyy+"1009");   // 한글날
        holidaysSet.add(yyyy+"1225");   // 성탄절

        // 음력 휴일

        String prev_seol = LocalDate.parse(Lunar2Solar(yyyy+"0101"), formatter).minusDays(1).toString().replace("-","");
        holidaysSet.add(yyyy+prev_seol.substring(4));        // ""
        holidaysSet.add(yyyy+SolarDays(yyyy, "0101"));  // 설날
        holidaysSet.add(yyyy+SolarDays(yyyy, "0102"));  // ""
        holidaysSet.add(yyyy+SolarDays(yyyy, "0408"));  // 석탄일
        holidaysSet.add(yyyy+SolarDays(yyyy, "0814"));  // ""
        holidaysSet.add(yyyy+SolarDays(yyyy, "0815"));  // 추석
        holidaysSet.add(yyyy+SolarDays(yyyy, "0816"));  // ""


        try {
            // 어린이날 대체공휴일 검사 : 어린이날은 토요일, 일요일인 경우 그 다음 평일을 대체공유일로 지정

            int childDayChk = LocalDate.parse(yyyy+"0505", formatter).getDayOfWeek().getValue();
            if(childDayChk == LD_SUNDAY) {      // 일요일
                holidaysSet.add(yyyy+"0506");
            }
            if(childDayChk == LD_SATURDAY) {  // 토요일
                holidaysSet.add(yyyy+"0507");
            }

            // 설날 대체공휴일 검사
            if(LocalDate.parse(Lunar2Solar(yyyy+"0101"),formatter).getDayOfWeek().getValue() == LD_SUNDAY) {    // 일
                holidaysSet.add(Lunar2Solar(yyyy+"0103"));
            }
            if(LocalDate.parse(Lunar2Solar(yyyy+"0101"),formatter).getDayOfWeek().getValue() == LD_MONDAY) {    // 월
                holidaysSet.add(Lunar2Solar(yyyy+"0103"));
            }
            if(LocalDate.parse(Lunar2Solar(yyyy+"0102"),formatter).getDayOfWeek().getValue() == LD_SUNDAY) {    // 일
                holidaysSet.add(Lunar2Solar(yyyy+"0103"));
            }

            // 추석 대체공휴일 검사
            if(LocalDate.parse(Lunar2Solar(yyyy+"0814"), formatter).getDayOfWeek().getValue() == LD_SUNDAY) {
                holidaysSet.add(Lunar2Solar(yyyy+"0817"));
            }
            if(LocalDate.parse(Lunar2Solar(yyyy+"0815"), formatter).getDayOfWeek().getValue() == LD_SUNDAY) {
                holidaysSet.add(Lunar2Solar(yyyy+"0817"));
            }
            if(LocalDate.parse(Lunar2Solar(yyyy+"0816"), formatter).getDayOfWeek().getValue() == LD_SUNDAY) {
                holidaysSet.add(Lunar2Solar(yyyy+"0817"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return holidaysSet;
    }

    private String SolarDays(String yyyy, String date){
        return Lunar2Solar(yyyy+date).substring(4);
    }

    public List<String> holidayList(String startDate, String endDate){
        String[] startSplit = startDate.split("\\.");
        String[] endSplit=endDate.split("\\.");

        List<String> holidays=new ArrayList<>();

        if(startSplit[0].equals(endSplit[0])){
            Set<String> holidayTemp = holidayArray(startSplit[0]);

            Object[] values =holidayTemp.toArray();

            for (Object object : values) {
                StringBuffer st=new StringBuffer((String)object);
                st.insert(4,".");
                st.insert(7,".");
                holidays.add(st.toString());
            }
        }
        else{
            Set<String> holidayTemp = holidayArray(startSplit[0]);
            Set<String> holidayTemp2 = holidayArray(endSplit[0]);

            Object[] values =holidayTemp.toArray();

            for (Object object : values) {
                StringBuffer st=new StringBuffer((String)object);
                st.insert(4,".");
                st.insert(7,".");
                holidays.add(st.toString());
            }

            Object[] values2 =holidayTemp2.toArray();

            for (Object object : values2) {
                StringBuffer st=new StringBuffer((String)object);
                st.insert(4,".");
                st.insert(7,".");
                holidays.add(st.toString());
            }

        }

        return holidays;
    }

    public List<String> availableDateList(String startDate,String endDate,List<String> holidays) throws ParseException {
        Date format1=new SimpleDateFormat("yyyy.MM.dd").parse(startDate);
        Date format2=new SimpleDateFormat("yyyy.MM.dd").parse(endDate);

        DateFormat df = new SimpleDateFormat("yyyy.MM.dd");

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        c1.setTime( format1 );
        c2.setTime( format2 );

        List<String> dateList=new ArrayList<>();

        while( c1.compareTo( c2 ) !=1 ){

            //1은 일요일
            int dayNum = c1.get(Calendar.DAY_OF_WEEK);
            String date = df.format(c1.getTime());
            if(dayNum!=1 && !holidays.contains(date)) {
                dateList.add(date);
            }

            //시작날짜 + 1 일
            c1.add(Calendar.DATE, 1);
        }

        return dateList;
    }


}
