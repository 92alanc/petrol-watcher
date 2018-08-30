package com.braincorp.petrolwatcher.ui

/**
 * Callback for RecyclerView item click events
 */
interface OnItemClickListener {
    /**
     * Function triggered when a RecyclerView item is clicked
     *
     * @param position the position in the list
     */
    fun onItemClick(position: Int)
}