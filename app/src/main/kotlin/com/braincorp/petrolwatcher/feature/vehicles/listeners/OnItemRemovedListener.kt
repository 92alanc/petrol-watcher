package com.braincorp.petrolwatcher.feature.vehicles.listeners

/**
 * A listener triggered when a RecyclerView item is removed
 *
 * @param T the type of the item
 */
interface OnItemRemovedListener<T> {

    /**
     * Function called when an item is removed
     *
     * @param item the item removed
     */
    fun onItemRemoved(item: T)

}