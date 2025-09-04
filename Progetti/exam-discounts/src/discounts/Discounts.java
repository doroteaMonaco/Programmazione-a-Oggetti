package discounts;
import java.util.*;
import java.util.stream.Collectors;

public class Discounts {


	Map<Integer, Card> cards = new HashMap<>();
	Map<String, Product> products = new HashMap<>();
	Map<String, Discount> discounts = new HashMap<>();
	Map<Integer, Acquisto> acquisti = new HashMap<>();
	int codAcquisto = 1;
	

	public static class Card{
		private String name;
		private int code;

		public Card(int code, String name){
			this.code = code;
			this.name = name;
		}

		public int getCode(){
			return code;
		}

		public String getName(){
			return name;
		}
	}

	public static class Product{
		private String categoryID, productID;
		private double price;

		public Product(String categoryID, String productID, double price){
			this.categoryID = categoryID;
			this.productID = productID;
			this.price = price;
		}

		public String getCategoryID(){
			return categoryID;
		}

		public String getProductID(){
			return productID;
		}

		public double getPrice(){
			return price;
		}


	}

	public static class Discount{
		private String categoryID;
		private int perc;

		public Discount(String categoryID, int perc){
			this.categoryID = categoryID;
			this.perc = perc;
		}

		public String getCategory(){
			return categoryID;
		}

		public int getPercentuale(){
			return perc;
		}
	}

	public static class Acquisto{

		private int codAcquisto;
		private List<String> articoli;
		private boolean conCarta = false;
		private double discount;
		private double totalAmount;

		public Acquisto(int codAcquisto, boolean conCarta){
			this.codAcquisto = codAcquisto;
			this.articoli = new ArrayList<>();
			this.conCarta = conCarta;
			this.discount = 0.0;
			this.totalAmount = 0.0;
		}

		public void addItems(String i){
			if(!this.articoli.contains(i)){
				this.articoli.add(i);
			}
		}

		public List<String> getArticoli(){
			return articoli;
		}

		public int getCodiceAcquisto(){
			return codAcquisto;
		}

		public boolean conCarta(){
			return conCarta;
		}

		public void setTotalAmount(double tot){
			this.totalAmount = tot;
		}

		public double getTotalAmount(){
			return totalAmount;
		}

		public void setDiscount(double d){
			this.discount = d;
		}

		public double getDiscount(){
			return discount;
		}
	}
	
	//R1
	public int issueCard(String name) {

		int code = this.cards.size() + 1;

		this.cards.put(code, new Card(code, name));
	    return code;
	}
	
    public String cardHolder(int cardN) {

		String person = null;

		if(this.cards.containsKey(cardN)){
			person = this.cards.get(cardN).getName();
		}
        return person;
    }
    

	public int nOfCards() {
	       return this.cards.size();

	}
	
	//R2
	public void addProduct(String categoryId, String productId, double price) 
			throws DiscountsException {
		
		if(this.products.containsKey(productId)){
			throw new DiscountsException();
		}

		this.products.put(productId, new Product(categoryId, productId, price));
	}
	
	public double getPrice(String productId) 
			throws DiscountsException {

		if(!this.products.containsKey(productId)){
			throw new DiscountsException();
		}

		Product p = this.products.get(productId);
		double price = p.getPrice();
        return price;
	}

	public int getAveragePrice(String categoryId) throws DiscountsException {

		double somma = 0.0;
		int count = 0;
		double avg = 0.0;
		for(Product p: this.products.values()){
			if(p.getCategoryID().equals(categoryId)){
				somma += p.getPrice();
				count++;
			}
		}

		avg = somma / count;

        return (int) Math.round(avg);
	}
	
	//R3
	public void setDiscount(String categoryId, int percentage) throws DiscountsException {
		
		boolean flag = false;

		for(Product p : this.products.values()){
			if(p.getCategoryID().equals(categoryId)){
				flag = true;
			}
		}

		if(flag == false){
			throw new DiscountsException();
		}

		Discount d = new Discount(categoryId, percentage);
		this.discounts.put(categoryId, d);

	}

	public int getDiscount(String categoryId) {
		return this.discounts.get(categoryId).getPercentuale();
	}

	//R4
	public int addPurchase(int cardId, String... items) throws DiscountsException {

		return addPurchaseInternal(true, items);
	}

	public int addPurchase(String... items) throws DiscountsException {
		return addPurchaseInternal(false, items);
	}

	public int addPurchaseInternal(boolean carta, String... items) throws DiscountsException{
		double totalAmount = 0.0;
        double totalDiscount = 0.0;
		Acquisto a = new Acquisto(codAcquisto++, false);

		for(String item: items){
			String[] parts;
			parts = item.split(":");
			if(parts.length != 2){
				throw new DiscountsException();
			}

			String pID = parts[0];
			int quantity = Integer.parseInt(parts[1]);

			Product p = this.products.get(pID);
			if(p == null){
				throw new DiscountsException();
			}

			double price = p.getPrice();
				double discount = 0.0;

			if(carta){
				Discount categoryDiscount = this.discounts.get(p.getCategoryID());
				if (categoryDiscount != null) {
					discount = price * categoryDiscount.getPercentuale() / 100.0;
				}
			}
			double itemTotal = price * quantity;
			double itemDiscount = discount * quantity;

            totalAmount += itemTotal;
            totalDiscount += itemDiscount;
            a.addItems(item);

		}

		a.setTotalAmount(totalAmount - totalDiscount);
        a.setDiscount(totalDiscount);
        this.acquisti.put(a.getCodiceAcquisto(), a);
        return a.getCodiceAcquisto();
	}
	
	public double getAmount(int purchaseCode) {

		Acquisto a = this.acquisti.get(purchaseCode);
		return a.getTotalAmount();
	}
	
	public double getDiscount(int purchaseCode)  {

		Acquisto a = this.acquisti.get(purchaseCode);
        return a.getDiscount();
	}
	
	public int getNofUnits(int purchaseCode) {
		Acquisto acquisto = this.acquisti.get(purchaseCode);
        
        int totalUnits = 0;
        for (String item : acquisto.getArticoli()) {
            String[] parts = item.split(":");
            int quantity = Integer.parseInt(parts[1]);
            totalUnits += quantity;
        }
        return totalUnits;
	}
	
	//R5
	public SortedMap<Integer, List<String>> productIdsPerNofUnits() {
        Map<String, Integer> productUnits = new HashMap<>();
        for (Acquisto a : this.acquisti.values()) {
            for (String item : a.getArticoli()) {
                String[] parts = item.split(":");
                String productId = parts[0];
                int quantity = Integer.parseInt(parts[1]);
                productUnits.put(productId, productUnits.getOrDefault(productId, 0) + quantity);
            }
        }
        return productUnits.entrySet().stream()
                .collect(Collectors.groupingBy(
                        Map.Entry::getValue,
                        TreeMap::new,
                        Collectors.mapping(Map.Entry::getKey, Collectors.toList())
                ));
	}
	
	public SortedMap<Integer, Double> totalPurchasePerCard() {

		Map<Integer, Double> total = this.acquisti.values().stream()
		.filter(a -> a.conCarta())
		.collect(Collectors.groupingBy(Acquisto::getCodiceAcquisto, Collectors.summingDouble(Acquisto::getTotalAmount)));

        return total.entrySet().stream().collect(TreeMap::new, (map,a) ->
		map.put(a.getKey(), a.getValue()), TreeMap::putAll);
	}
	
	public int totalPurchaseWithoutCard() {
        Map<Integer, Double> total = this.acquisti.values().stream()
		.filter(a -> !a.conCarta())
		.collect(Collectors.groupingBy(Acquisto::getCodiceAcquisto, Collectors.summingDouble(Acquisto::getTotalAmount)));

		double tot = 0.0;

		for(Double t: total.values()){
			tot += t;
		}

        return (int) tot;
	}
	
	public SortedMap<Integer, Double> totalDiscountPerCard() {
        Map<Integer, Double> totalDiscount = this.acquisti.values().stream()
		.filter(a -> a.conCarta())
		.collect(Collectors.groupingBy(Acquisto::getCodiceAcquisto, Collectors.summingDouble(Acquisto::getDiscount)));

        return totalDiscount.entrySet().stream().collect(TreeMap::new, (map,a) ->
		map.put(a.getKey(), a.getValue()), TreeMap::putAll);
	}


}
