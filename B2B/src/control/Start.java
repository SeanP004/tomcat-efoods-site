package control;




public class Start {

    public static void main(String[] args) throws Exception {
        
        
        Quote q = new Quote(); 
        double quotedPrices[] = q.getQuoteArray();
        
        int minPriceIndex = q.getCheapPrice(quotedPrices);
        
        PlaceOrder po = new PlaceOrder();
        
        String conf = po.doOrder(minPriceIndex);
        
        System.out.print(conf);
           
    }    

}
