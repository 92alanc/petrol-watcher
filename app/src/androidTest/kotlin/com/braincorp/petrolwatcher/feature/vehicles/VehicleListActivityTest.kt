package com.braincorp.petrolwatcher.feature.vehicles

import android.support.test.runner.AndroidJUnit4
import com.braincorp.petrolwatcher.base.BaseActivityTest
import com.braincorp.petrolwatcher.feature.vehicles.robots.vehicleList
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class VehicleListActivityTest : BaseActivityTest<VehicleListActivity>(
        VehicleListActivity::class.java) {

    @Test
    fun shouldFetchVehicles() {
        vehicleList {
            numberOfItemsIs(3)
        }
    }

    @Test
    fun whenClickingOnAddButton_shouldRedirectToCreateVehicleActivity() {
        vehicleList {
        } clickAddButton {
            redirectToCreateVehicleActivity()
        }
    }

    @Test
    fun whenSwipingAnItem_shouldRemoveFromRecyclerView() {
        vehicleList {
            numberOfItemsIs(3)
        } swipeItem {
            numberOfItemsIs(2)
        }
    }

}