package model.order;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.*;

@XmlRootElement(name="CompletedOrderList")
public class CompletedOrderList {

   
    private String date ; 
    private List <CompletedOrder> oList = new ArrayList<CompletedOrder>();
    
    public CompletedOrderList(String date, List<CompletedOrder> oList) {
       
        this.date = date;
        this.oList = oList;
    }
    
    public  CompletedOrderList() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date();
        this.date = dateFormat.format(d);
    }

    @XmlAttribute(name="date")
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    @XmlElement(name="orders")
    public List<CompletedOrder> getoList() {
        return oList;
    }
    
    public void setoList(List<CompletedOrder> oList) {
        this.oList = oList;
    }
    
}
