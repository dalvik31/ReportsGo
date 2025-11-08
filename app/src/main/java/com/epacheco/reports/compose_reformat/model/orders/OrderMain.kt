package com.epacheco.reports.compose_reformat.model.orders


data class OrderMain(
    val orderId: String = "",
    val nameOrder: String = "",
    @Deprecated(message = "orderId is the new value") val dateOrder: String = "",
    val orderDate: String = "",
    val orderStatus: OrderStatus = OrderStatus.IN_PROGRESS,
    val orderSeason: Season? = null,
    var orderLists: HashMap<String?, Order?>? = null
) {
    fun geProgressList(): Float {
        var countOrders = 0f
        if (!orderLists.isNullOrEmpty()) {
            for (entry in orderLists!!.entries) {
                if ((entry.value as Order).orderBuy) {
                    countOrders++
                }
            }
            countOrders = (countOrders) / orderLists!!.size
        }
        return countOrders
    }


}
