package it.polito.oop.futsal;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;


public class Fields {

    Time openingTime;
    Time closingTime;
    public Map<Integer, Campo> fields = new HashMap<>();
    public Map<Integer, Associate> associates = new HashMap<>();


    public static class Features{
        private boolean indoor, ac, heating;


        public Features(Boolean indoor, Boolean heating, Boolean ac){
            this.indoor = indoor;
            this.heating = heating;
            this.ac = ac;

        }

        public boolean getIndoor(){
            return indoor;
        }

        public boolean getHeating(){
            return heating;
        }

        public boolean getAc(){
            return ac;
        }
    }

    public static class Associate{
        String name, surname, tel;

        public Associate(String name, String surname, String tel){
            this.name = name;
            this.surname = surname;
            this.tel = tel;
        }

        public String getName(){
            return name;
        }

        public String getSurname(){
            return surname;
        }

        public String getTel(){
            return tel;
        }

        public boolean matches(int associate){
            if(assoc)
        }


    }

    public static class Campo{
        private Features f;
        private Integer numberField;
        Map<String, Integer> booked = new HashMap<>();

        public Campo(Features f, Integer numberField){
            this.f = f;
            this.numberField = numberField;
        }

        public Features getFeatures(){
            return f;
        }

        public Integer getNumberField(){
            return numberField;
        }

        public void addBooking(int associate, String time){
            booked.put(time, associate);
        }

        public boolean booked(String time){
            if(booked.containsKey(time)){
                return true;
            }
            return false;
        }

        public int countBooking(int field){

            int countBookings = 0;
            for(Map.Entry<String, Integer> entry: booked.entrySet()){
                if(entry.getValue().equals(field)){
                    countBookings++;
                }
            }
            return countBookings;
        }

        public boolean matches(Features required){
            if((required.indoor == f.indoor) && (required.ac == f.ac) && (required.heating == f.heating)){
                return true;
            }
            return false;
        }

    }

    public static class Time{

        int hour, minute;

        Time(String time){
            String[] parts;
            parts = time.split(":");
            hour = Integer.parseInt(parts[0]);
            minute = Integer.parseInt(parts[1]);
        }

        public String toString(){
            return String.format("%02D:%02D", hour, minute);
        }

        public boolean aligned(Time time){
            return minute == time.minute;
        }
    }

    public void defineFields(Features... features) throws FutsalException {
        
        FutsalException fe = new FutsalException();
        boolean flag = false;

        for(Features f : features){
            if(f.getIndoor() && ((f.getHeating() || f.getAc() || (!f.getIndoor() && !f.getHeating() && !f.getAc())))){
                flag = true;
                Campo c = new Campo(f, fields.size() + 1);
                fields.put(fields.size() + 1, c);
            }
        }
        if(flag == false){
            throw fe;
        }
    }
    
    public long countFields() {
        return fields.size();
    }

    public long countIndoor() {
        
        int count = 0;

        for(Map.Entry<Integer, Campo> entry : fields.entrySet()){
            if(entry.getValue().getFeatures().getIndoor() == true){
                count++;
            }
        }

        return count;
    }
    
    public String getOpeningTime() {
        return openingTime.toString();
    } 
    
    public void setOpeningTime(String time) {
       this.openingTime = new Time(time);
    }
    
    public String getClosingTime() {
       return closingTime.toString();
    }
    
    public void setClosingTime(String time) {
       this.closingTime = new Time(time);
    }

    public int newAssociate(String first, String last, String mobile) {

        int count = 0;

        associates.put(count++, new Associate(first, last, mobile));
        return count;
    }
    
    public String getFirst(int associate) throws FutsalException {

        String name = null;
        boolean flag = false;

        for(Map.Entry<Integer, Associate> entry : associates.entrySet()){
            if(entry.getKey().equals(associate)){
                flag = true;
                name = entry.getValue().getName();
            }
        }

        if(flag == false){
            throw new FutsalException();
        }

        return name;
    }
    
    public String getLast(int associate) throws FutsalException {
        String surname = null;
        boolean flag = false;

        for(Map.Entry<Integer, Associate> entry : associates.entrySet()){
            if(entry.getKey().equals(associate)){
                flag = true;
                surname = entry.getValue().getSurname();
            }
        }

        if(flag == false){
            throw new FutsalException();
        }

        return surname;
    }
    
    public String getPhone(int associate) throws FutsalException {
        String tel = null;
        boolean flag = false;

        for(Map.Entry<Integer, Associate> entry : associates.entrySet()){
            if(entry.getKey().equals(associate)){
                flag = true;
                tel = entry.getValue().getTel();
            }
        }

        if(flag == false){
            throw new FutsalException();
        }

        return tel;
    }
    
    public int countAssociates() {
       return associates.size();
    }
    
    public void bookField(int field, int associate, String time) throws FutsalException {
       
        Time request = new Time(time);
        if(!request.aligned(openingTime)){
            throw new FutsalException();
        }
        if(!request.aligned(closingTime)){
            throw new FutsalException();
        }

        if(!associates.containsKey(associate)){
            throw new FutsalException();
        }
        if(!fields.containsKey(field)){
            throw new FutsalException();
        }

        if(!isBooked(field, time)){
            fields.get(field).addBooking(associate,time);
        }
    }

    public boolean isBooked(int field, String time) {
        return ((Campo) fields).booked(time);
    }
    

    public int getOccupation(int field) {
        return fields.get(field).countBooking(field);
    }

    public List<FieldOption> findOptions(String time, Features required){
        List<FieldOption> optionsRespected = new ArrayList<>();
        

        optionsRespected = fields.values().stream()
        .filter(c -> !c.booked(time)).filter(c -> c.matches(required))
        .sorted(Comparator.comparing(FieldOption::getOccupation).reversed()
        .thenComparing(FieldOption::getField)).collect(Collectors.toList());

        return optionsRespected;
    }
    
    public long countServedAssociates() {
        return 0;
    }
    
    public Map<Integer,Long> fieldTurnover() {
        return null;
    }
    
    public double occupation() {
        return 0;
    }
    
 }
