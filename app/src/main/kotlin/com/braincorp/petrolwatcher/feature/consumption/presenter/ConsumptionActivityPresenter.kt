package com.braincorp.petrolwatcher.feature.consumption.presenter

import com.alancamargo.validationchain.ValidationChain
import com.alancamargo.validationchain.model.Validation
import com.braincorp.petrolwatcher.feature.consumption.contract.ConsumptionActivityContract
import com.braincorp.petrolwatcher.feature.consumption.model.TankState
import com.braincorp.petrolwatcher.utils.tankStateToLitres

/**
 * The implementation of the presentation layer
 * of the consumption activity
 */
class ConsumptionActivityPresenter(private val view: ConsumptionActivityContract.View)
    : ConsumptionActivityContract.Presenter {

    /**
     * Calculates the average consumption
     *
     * @param odometerStart the initial state of the odometer
     * @param odometerEnd the final state of the odometer
     * @param tankStateStart the initial state of the tank
     * @param tankStateEnd the final state of the tank
     * @param fuelCapacity the fuel capacity
     */
    override fun calculateConsumption(odometerStart: String,
                                      odometerEnd: String,
                                      tankStateStart: TankState,
                                      tankStateEnd: TankState,
                                      fuelCapacity: Int) {
        val odometerStartNotBlank = Validation(odometerStart.isNotBlank(), view::showOdometerStartError)
        val odometerEndNotBlank = Validation(odometerEnd.isNotBlank(), view::showOdometerEndError)
        val validFuelCapacity = Validation(fuelCapacity > 0, view::showFuelCapacityError)

        ValidationChain().add(odometerStartNotBlank)
                .add(odometerEndNotBlank)
                .add(validFuelCapacity)
                .run {
                    val kmStart = odometerStart.toInt()
                    val kmEnd = odometerEnd.toInt()
                    if (kmStart >= kmEnd) {
                        view.showInvalidDistanceError()
                    } else {
                        val distance = kmEnd - kmStart
                        val tankStateStartLitres = tankStateToLitres(tankStateStart, fuelCapacity)
                        val tankStateEndLitres = tankStateToLitres(tankStateEnd, fuelCapacity)

                        if (tankStateEndLitres >= tankStateStartLitres) {
                            view.showInvalidTankStateError()
                        } else {
                            val fuelSpent = tankStateStartLitres - tankStateEndLitres

                            val consumption = (distance / fuelSpent).toFloat()
                            view.exportConsumption(consumption)
                        }
                    }
                }
    }

}