package warehouse;

/*
 *
 * This class implements a warehouse on a Hash Table like structure, 
 * where each entry of the table stores a priority queue. 
 * Due to your limited space, you are unable to simply rehash to get more space. 
 * However, you can use your priority queue structure to delete less popular items 
 * and keep the space constant.
 * 
 * @author Ishaan Ivaturi
 */ 
public class Warehouse {
    private Sector[] sectors;
    
    // Initializes every sector to an empty sector
    public Warehouse() {
        sectors = new Sector[10];

        for (int i = 0; i < 10; i++) {
            sectors[i] = new Sector();
        }
    }
    
    /**
     * Provided method, code the parts to add their behavior
     * @param id The id of the item to add
     * @param name The name of the item to add
     * @param stock The stock of the item to add
     * @param day The day of the item to add
     * @param demand Initial demand of the item to add
     */
    public void addProduct(int id, String name, int stock, int day, int demand) {
        evictIfNeeded(id);
        addToEnd(id, name, stock, day, demand);
        fixHeap(id);
    }

    /**
     * Add a new product to the end of the correct sector
     * Requires proper use of the .add() method in the Sector class
     * @param id The id of the item to add
     * @param name The name of the item to add
     * @param stock The stock of the item to add
     * @param day The day of the item to add
     * @param demand Initial demand of the item to add
     */
    private void addToEnd(int id, String name, int stock, int day, int demand) {
        Product newProd=new Product(id, name, stock, day, demand);
        int sectorIndex=id%10;
        this.sectors[sectorIndex].add(newProd);
        // IMPLEMENT THIS METHOD
    }

    /**
     * Fix the heap structure of the sector, assuming the item was already added
     * Requires proper use of the .swim() and .getSize() methods in the Sector class
     * @param id The id of the item which was added
     */
    private void fixHeap(int id) {
        int sectorIndex=id%10;
        Sector currentSector=sectors[sectorIndex];
        currentSector.swim(currentSector.getSize());

        // IMPLEMENT THIS METHOD
    }

    /**
     * Delete the least popular item in the correct sector, only if its size is 5 while maintaining heap
     * Requires proper use of the .swap(), .deleteLast(), and .sink() methods in the Sector class
     * @param id The id of the item which is about to be added
     */
    private void evictIfNeeded(int id) {
        int sectorIndex=id%10;
        Sector currentSector=sectors[sectorIndex];
        if(currentSector.getSize()==5) {
            currentSector.swap(1,5);
            currentSector.deleteLast();
            currentSector.sink(1);
        }
       // IMPLEMENT THIS METHOD
    }

    /**
     * Update the stock of some item by some amount
     * Requires proper use of the .getSize() and .get() methods in the Sector class
     * Requires proper use of the .updateStock() method in the Product class
     * @param id The id of the item to restock
     * @param amount The amount by which to update the stock
     */
    public void restockProduct(int id, int amount) {
        int sectorIndex=id%10;
        Sector currentSector=sectors[sectorIndex];
        for(int i=1;i<=currentSector.getSize();i++) {
            Product currentProd = currentSector.get(i);
            if(currentProd.getId()==id) {
                currentProd.updateStock(amount);
            }
        }

        // IMPLEMENT THIS METHOD
    }
    
    /**
     * Delete some arbitrary product while maintaining the heap structure in O(logn)
     * Requires proper use of the .getSize(), .get(), .swap(), .deleteLast(), .sink() and/or .swim() methods
     * Requires proper use of the .getId() method from the Product class
     * @param id The id of the product to delete
     */
    public void deleteProduct(int id) {
        int sectorIndex=id%10;
        Sector currentSector=sectors[sectorIndex];
        for(int i=1;i<=currentSector.getSize();i++) {
            Product currentProd = currentSector.get(i);
            if(currentProd.getId()==id) {
                currentSector.swap(i,currentSector.getSize());
                currentSector.deleteLast();
                
                
                if(i<=currentSector.getSize()) currentSector.swim(i);
                if((i)>0) currentSector.sink(i);
            }
        }

        // IMPLEMENT THIS METHOD
    }
    
    /**
     * Simulate a purchase order for some product
     * Requires proper use of the getSize(), sink(), get() methods in the Sector class
     * Requires proper use of the getId(), getStock(), setLastPurchaseDay(), updateStock(), updateDemand() methods
     * @param id The id of the purchased product
     * @param day The current day
     * @param amount The amount purchased
     */
    public void purchaseProduct(int id, int day, int amount) {
        int sectorIndex=id%10;
        Sector currentSector=sectors[sectorIndex];
        for(int i=1;i<=currentSector.getSize();i++) {
            Product currentProd = currentSector.get(i);
            if(currentProd.getId()==id) {
                int prevStock=currentProd.getStock();
                
                if(amount<=prevStock) {
                    currentProd.setLastPurchaseDay(day);
                    currentProd.updateStock(-amount);
                    currentProd.updateDemand(amount);
                    
                currentSector.sink(i);
                if(i<=currentSector.getSize()) currentSector.swim(i);
                if((i)>0) currentSector.sink(i);
                }

            }
        }
        // IMPLEMENT THIS METHOD
    }
    
    /**
     * Construct a better scheme to add a product, where empty spaces are always filled
     * @param id The id of the item to add
     * @param name The name of the item to add
     * @param stock The stock of the item to add
     * @param day The day of the item to add
     * @param demand Initial demand of the item to add
     */
    public void betterAddProduct(int id, String name, int stock, int day, int demand) {
        Product newProd=new Product(id, name, stock, day, demand);
        int ogSectorIndex=id%10;
        int currentSectorIndex=id%10;
        Sector currentSector=sectors[currentSectorIndex];
        if(currentSector.getSize()<5) {
            currentSector.add(newProd);
            currentSector.swim(currentSector.getSize());
            return;
        }
        if(currentSectorIndex==9) {
            currentSectorIndex=0;
        } else if (0<=currentSectorIndex && currentSectorIndex<=8) {
            currentSectorIndex++;
        }
        currentSector=sectors[currentSectorIndex];
        while(currentSectorIndex!=ogSectorIndex) {
            if(currentSector.getSize()<5) {
                currentSector.add(newProd);
                currentSector.swim(currentSector.getSize());
                return;
            }
            if(currentSectorIndex==9) {
                currentSectorIndex=0;
            } else if (0<=currentSectorIndex && currentSectorIndex<=8) {
                currentSectorIndex++;
            }
            currentSector=sectors[currentSectorIndex];
        }
        if(currentSectorIndex==ogSectorIndex && currentSector.getSize()==5) {
            evictIfNeeded(id);
            currentSector.add(newProd);
            currentSector.swim(currentSector.getSize());
            return;
        }

        // IMPLEMENT THIS METHOD
    }

    /*
     * Returns the string representation of the warehouse
     */
    public String toString() {
        String warehouseString = "[\n";

        for (int i = 0; i < 10; i++) {
            warehouseString += "\t" + sectors[i].toString() + "\n";
        }
        
        return warehouseString + "]";
    }

    /*
     * Do not remove this method, it is used by Autolab
     */ 
    public Sector[] getSectors () {
        return sectors;
    }
}
