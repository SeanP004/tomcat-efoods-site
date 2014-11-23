package control;

import java.util.Iterator;
import java.util.Map;
import model.order.Order;
import model.order.OrderList;


public class Start {

    public static void main(String[] args) throws Exception {
        
        Order o1 = new Order("0905A771", 2);     
        Order o2 = new Order("0905A044", 2);      
        OrderList orderlist = new OrderList(); 
        
        orderlist.addOrder(o1);
        orderlist.addOrder(o2);
        
        
        Map<String, Integer>  list = orderlist.getList() ;
    
        Iterator it = list.entrySet().iterator();
       
        while (it.hasNext()) {
            
            Map.Entry pairs = (Map.Entry)it.next();                
           
            Order o = new Order(pairs.getKey().toString(), Integer.parseInt(pairs.getValue().toString())); 
            
            Quote q = new Quote(o); 
            double quotedPrices[] = q.getQuoteArray();
       
            int minPriceIndex = q.getCheapPrice(quotedPrices);
       
            PlaceOrder po = new PlaceOrder(o);
       
            String conf = po.doOrder(minPriceIndex);
       
            System.out.print(conf);
            
            
            System.out.println(pairs.getKey() + " = " + pairs.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }

           
    }    

}
