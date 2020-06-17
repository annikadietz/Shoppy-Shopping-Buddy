package com.annikadietz.shoppy_shoppingbuddy

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.annikadietz.shoppy_shoppingbuddy.Model.*
import com.annikadietz.shoppy_shoppingbuddy.Model.Shop
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime


object NewDatabaseHelper : DatabaseHelperInterface {
    private var db = Firebase.firestore
    private var shops = arrayListOf<Shop>()
    private var myShops = arrayListOf<Shop>()
    private var products = arrayListOf<Product>()
    private var shoppingItems = arrayListOf<ShoppingItem>()
    private var myShoppingList = arrayListOf<Product>()
    private var myShoppingItems = arrayListOf<ShoppingItem>()
    var uid: String = ""
    var address: String = "Hoitingeslag%29,%207824%20KG"

    @RequiresApi(Build.VERSION_CODES.O)
    fun newData () {
        var jumbo = Shop("Jumbo", "7824JA", "Kerspellaan 9")
        var aldi = Shop("Aldi", "7824CP", "Peyserhof 2")
        var lidl = Shop("Lidl", "7823PH", "Houtweg 151")
        var albert = Shop("Albert Heijn", "7824CS", "Statenweg 33")

        db.collection("newShops").add(jumbo)
        db.collection("newShops").add(aldi)
        db.collection("newShops").add(lidl)
        db.collection("newShops").add(albert)

        var potatoes = Product("Potatoes - 1kg", Type("Vegetables"))
        var pizza = Product("Pizza - Italia", Type("Frozen"))
        var bananas = Product("Bananas - 1kg", Type("Fruits"))
        var eggs = Product("Large eggs", Type("Dairy, eggs, and butter"))

        db.collection("newProducts").add(potatoes)
        db.collection("newProducts").add(pizza)
        db.collection("newProducts").add(bananas)
        db.collection("newProducts").add(eggs)

        // jumbo
        var potatoesInJumbo = ShoppingItem(potatoes, jumbo, 3.0)
        var pizzaInJumbo = ShoppingItem(pizza, jumbo, 2.0)
        var bananasInJumbo = ShoppingItem(bananas, jumbo, 1.0)
        var eggsInJumbo = ShoppingItem(eggs, jumbo, 4.0)

        db.collection("shoppingItems").add(potatoesInJumbo)
        db.collection("shoppingItems").add(pizzaInJumbo)
        db.collection("shoppingItems").add(bananasInJumbo)
        db.collection("shoppingItems").add(eggsInJumbo)

        // aldi
        var potatoesInAldi = ShoppingItem(potatoes, aldi, 2.0)
        var pizzaInAldi = ShoppingItem(pizza, aldi, 3.0)
        var bananasInAldi = ShoppingItem(bananas, aldi, 1.5)
        var eggsInAldi = ShoppingItem(eggs, aldi, 3.0)

        db.collection("shoppingItems").add(potatoesInAldi)
        db.collection("shoppingItems").add(pizzaInAldi)
        db.collection("shoppingItems").add(bananasInAldi)
        db.collection("shoppingItems").add(eggsInAldi)
        // lidl
        var potatoesInLidl = ShoppingItem(potatoes, lidl, 2.5)
        var pizzaInLidl = ShoppingItem(pizza, lidl, 3.0)
        var bananasInLidl = ShoppingItem(bananas, lidl, 4.0)
        var eggsInLidl = ShoppingItem(eggs, lidl, 2.2)

        db.collection("shoppingItems").add(potatoesInLidl)
        db.collection("shoppingItems").add(pizzaInLidl)
        db.collection("shoppingItems").add(bananasInLidl)
        db.collection("shoppingItems").add(eggsInLidl)

        // albert
        var potatoesInAlbert = ShoppingItem(potatoes, albert, 4.3)
        var pizzaInAlbert = ShoppingItem(pizza, albert, 2.6)
        var bananasInAlbert = ShoppingItem(bananas, albert, 1.7)
        var eggsInAlbert = ShoppingItem(eggs, albert, 2.2)
        db.collection("shoppingItems").add(potatoesInAlbert)
        db.collection("shoppingItems").add(pizzaInAlbert)
        db.collection("shoppingItems").add(bananasInAlbert)
        db.collection("shoppingItems").add(eggsInAlbert)


    }
    fun subscribeShops() {
        db.collection("newShops")
            .addSnapshotListener { results, e ->

                shops.clear()
                results?.forEach { shop -> shops.add(shop.toObject(Shop::class.java)) }
            }

    }

    fun subscribeProducts() {
        db.collection("newProducts")
            .addSnapshotListener { results, e ->
                products.clear()
                results?.forEach { product -> products.add(product.toObject(Product::class.java)) }
            }

    }

    fun subscribeShoppingItems() {
        db.collection("shoppingItems")
            .addSnapshotListener { results, e ->
                shoppingItems.clear()
                results?.forEach { product -> shoppingItems.add(product.toObject(ShoppingItem::class.java)) }
            }

    }

    fun subscribeMyShops() {
        db.collection("myShops")
            .whereEqualTo("uid", uid)
            .addSnapshotListener { results, e ->
                myShops.clear()
                results?.forEach {
                    var shopParameters = it["shop"] as HashMap<String, String>
                    var shop = Shop(
                        shopParameters["name"],
                        shopParameters["postCode"],
                        shopParameters["streetAddress"]
                    )
                    myShops.add(shop)
                }
            }

    }

    fun subscribeMyShoppingList() {
        db.collection("shoppingLists")
            .document(uid)
            .collection("shoppingList")
            .addSnapshotListener { results, e ->
                myShoppingList.clear()
                results?.forEach { product -> myShoppingList.add(product.toObject(Product::class.java)) }
            }

    }

    fun subscribeMyShoppingItems() {
        db.collection("user1Data")
            .document(uid)
            .collection("shoppingItems")
            .addSnapshotListener { results, e ->
                myShoppingItems.clear()
                results?.forEach { item -> myShoppingItems.add(item.toObject(ShoppingItem::class.java)) }
            }

    }


    fun deleteMyShoppingItem(shoppingItem: ShoppingItem) {
        var shopsItemsInDatabase = db.collection("user1Data")
            .document(uid)
            .collection("shoppingItems")
            .whereEqualTo("price.price", shoppingItem.price.price)
            .whereEqualTo("price.lastConfirmed", shoppingItem.price.lastConfirmed)
            .whereEqualTo("product.name", shoppingItem.product.name)
            .get()
        shopsItemsInDatabase.addOnSuccessListener {
            it.forEach {
                db  .collection("userData")
                    .document(uid)
                    .collection("shoppingItems")
                    .document(it.id).delete()
            }
        }
    }

    fun saveMyCombo(combo: Combination){
        db  .collection("userData")
            .document(uid)
            .set(combo)
    }

    fun getMyCombo(): Task<DocumentSnapshot> {
        return db.collection("userData")
            .document(uid)
            .get()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun confirmPrice(shoppingItem: ShoppingItem) {
        val docs = db.collection("shoppingItems")
            .whereEqualTo("product.name", shoppingItem.product.name)
            .whereEqualTo("shop.name", shoppingItem.shop.name)
            .whereEqualTo("shop.postCode", shoppingItem.shop.postCode)
            .whereEqualTo("shop.streetAddress", shoppingItem.shop.streetAddress)
            .get()

        docs.addOnCompleteListener { it ->
            it.result?.documents?.forEach {
                val updates = hashMapOf(
                    "price.counter" to FieldValue.increment(1),
                    "price.lastConfirmed" to LocalDateTime.now().toString()
                )
                it.reference.update(updates)
            }
        }
    }

    fun confirmPurchase(shoppingItem: ShoppingItem) {
        db.collection("userData")
            .document(uid)
            .collection("confirmedPurchases")
            .add(shoppingItem)
    }


    fun addMyShoppingItem(combo: Combination) {
        combo.shoppingItems.forEach {

        }
//        db.collection("userData")
//            .document(uid)
//            .collection("shoppingItems")
//            .whereEqualTo("shop.streetAddress", shop.streetAddress)
//            .whereEqualTo("shop.name", shop.name)
//            .get().addOnSuccessListener {
//                results ->
//                if(results.size() == 0){
//                    db  .collection("userData")
//                        .document(uid)
//                        .collection("shoppingItems")
//                        .delete(shoppingItem)
//                }
//            }

    }

    fun deleteMyShop(shop: Shop) {
        var shopsInDatabase = db.collection("myShops")
            .whereEqualTo("shop.name", shop.name)
            .whereEqualTo("shop.postCode", shop.postCode)
            .whereEqualTo("shop.streetAddress", shop.streetAddress)
            .whereEqualTo("uid", uid)
            .get()
        shopsInDatabase.addOnSuccessListener {
            it.forEach {
                db.collection("myShops").document(it.id).delete()
            }
        }
    }

    fun addMyShop(shop: Shop) {
        val myShop: HashMap<String, Any> = hashMapOf(
            "shop" to shop,
            "uid" to uid
        )
        db.collection("myShops").document()
            .set(myShop)
            .addOnSuccessListener {
                Log.d("add my shops", "DocumentSnapshot successfully written!")
            }
            .addOnFailureListener { e -> Log.w("add my shops", "Error writing document", e) }
    }

    override fun getMyShops(): ArrayList<Shop> {
        return myShops
    }

    override fun getShops(): ArrayList<Shop> {
        return shops
    }

    override fun getProducts(): ArrayList<Product> {
        return products
    }

    override fun getShoppingItems(): ArrayList<ShoppingItem> {
        return shoppingItems
    }

    fun addProductToMyShoppingList(product: Product) {
        db.collection("shoppingLists")
            .document(uid)
            .collection("shoppingList")
            .add(product)
    }
    fun deleteProductFormMyShoppingList(product: Product) {
        var matchingShoppingListEntries = db.collection("shoppingLists")
            .document(uid)
            .collection("shoppingList")
            .whereEqualTo("name", product.name)
            .whereEqualTo("type.name", product.type?.name)
            .get()
        matchingShoppingListEntries.addOnSuccessListener {
            it.forEach {
                db.collection("shoppingLists")
                    .document(uid)
                    .collection("shoppingList")
                    .document(it.id)
                    .delete()
            }
        }
    }

    override fun getMyShoppingList(): ArrayList<Product> {
        return myShoppingList
    }


}
