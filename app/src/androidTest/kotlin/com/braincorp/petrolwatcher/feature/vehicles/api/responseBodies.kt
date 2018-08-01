package com.braincorp.petrolwatcher.feature.vehicles.api

const val RESPONSE_YEARS = "?({ " +
                                "\"Years\": {" +
                                    "\"min_year\":\"1941\", " +
                                    "\"max_year\":\"2018\"" +
                                "} " +
                           "});"

const val RESPONSE_MANUFACTURERS = "?({" +
                                        "\"Makes\":[" +
                                            "{" +
                                                "\"make_id\":\"acura\"," +
                                                "\"make_display\":\"Acura\"," +
                                                "\"make_is_common\":\"1\"," +
                                                "\"make_country\":\"USA\"" +
                                            "}," +
                                            "{" +
                                                "\"make_id\":\"alfa-romeo\"," +
                                                "\"make_display\":\"Alfa Romeo\"," +
                                                "\"make_is_common\":\"1\"," +
                                                "\"make_country\":\"Italy\"" +
                                            "}," +
                                            "{" +
                                                "\"make_id\":\"alpina\"," +
                                                "\"make_display\":\"Alpina\"," +
                                                "\"make_is_common\":\"0\"," +
                                                "\"make_country\":\"UK\"" +
                                            "}," +
                                            "{" +
                                                "\"make_id\":\"ascari\"," +
                                                "\"make_display\":\"Ascari\"," +
                                                "\"make_is_common\":\"0\"," +
                                                "\"make_country\":\"UK\"" +
                                            "}" +
                                        "]" +
                                   "});"

const val RESPONSE_MODELS = "?({" +
                                "\"Models\":[" +
                                    "{" +
                                        "\"model_name\":\"A1\"," +
                                        "\"model_make_id\":\"audi\"" +
                                    "}," +
                                    "{" +
                                        "\"model_name\":\"A3\"," +
                                        "\"model_make_id\":\"audi\"" +
                                    "}," +
                                    "{" +
                                        "\"model_name\":\"A4\"," +
                                        "\"model_make_id\":\"audi\"" +
                                    "}," +
                                    "{" +
                                        "\"model_name\":\"A5\"," +
                                        "\"model_make_id\":\"audi\"" +
                                    "}" +
                                "]" +
                            "});"

const val RESPONSE_TRIMS = "?({" +
                                "\"Trims\":[" +
                                    "{" +
                                        "\"model_id\": \"50786\"," +
                                        "\"model_make_id\": \"audi\"," +
                                        "\"model_name\": \"TT\"," +
                                        "\"model_trim\": \"1.8 TFSi Convertible\"," +
                                        "\"model_year\": \"2012\"," +
                                        "\"model_body\": \"Convertible\"," +
                                        "\"model_engine_position\": \"Front\"," +
                                        "\"model_engine_cc\": \"1800\"," +
                                        "\"model_engine_cyl\": \"4\"," +
                                        "\"model_engine_type\": \"in-line\"," +
                                        "\"model_engine_valves_per_cyl\": \"4\"," +
                                        "\"model_engine_power_ps\": \"160\"," +
                                        "\"model_engine_power_rpm\": \"4500\"," +
                                        "\"model_engine_torque_nm\": \"250\"," +
                                        "\"model_engine_torque_rpm\": \"1500\"," +
                                        "\"model_engine_bore_mm\": null," +
                                        "\"model_engine_stroke_mm\": null," +
                                        "\"model_engine_compression\": null," +
                                        "\"model_engine_fuel\": \"Gasoline\"," +
                                        "\"model_top_speed_kph\": null," +
                                        "\"model_0_to_100_kph\": null," +
                                        "\"model_drive\": \"Front\"," +
                                        "\"model_transmission_type\": \"6-speed automated manual\"," +
                                        "\"model_seats\": \"2\"," +
                                        "\"model_doors\": \"2\"," +
                                        "\"model_weight_kg\": null," +
                                        "\"model_length_mm\": \"4201\"," +
                                        "\"model_width_mm\": \"1842\"," +
                                        "\"model_height_mm\": \"1359\"," +
                                        "\"model_wheelbase_mm\": \"2469\"," +
                                        "\"model_lkm_hwy\": null," +
                                        "\"model_lkm_mixed\": null," +
                                        "\"model_lkm_city\": null," +
                                        "\"model_fuel_cap_l\": \"60\"," +
                                        "\"model_sold_in_us\": \"0\"," +
                                        "\"model_co2\": null," +
                                        "\"model_make_display\": \"Audi\"," +
                                        "\"make_display\": \"Audi\"," +
                                        "\"make_country\": \"Germany\"" +
                                    "}," +
                                    "{" +
                                        "\"model_id\": \"50785\"," +
                                        "\"model_make_id\": \"audi\"," +
                                        "\"model_name\": \"TT\"," +
                                        "\"model_trim\": \"1.8 TFSI Coupe\"," +
                                        "\"model_year\": \"2012\"," +
                                        "\"model_body\": \"Coupe\"," +
                                        "\"model_engine_position\": \"Front\"," +
                                        "\"model_engine_cc\": \"1800\"," +
                                        "\"model_engine_cyl\": \"4\"," +
                                        "\"model_engine_type\": \"in-line\"," +
                                        "\"model_engine_valves_per_cyl\": \"4\"," +
                                        "\"model_engine_power_ps\": \"160\"," +
                                        "\"model_engine_power_rpm\": \"4500\"," +
                                        "\"model_engine_torque_nm\": \"250\"," +
                                        "\"model_engine_torque_rpm\": \"1500\"," +
                                        "\"model_engine_bore_mm\": null," +
                                        "\"model_engine_stroke_mm\": null," +
                                        "\"model_engine_compression\": null," +
                                        "\"model_engine_fuel\": \"Gasoline\"," +
                                        "\"model_top_speed_kph\": null," +
                                        "\"model_0_to_100_kph\": null," +
                                        "\"model_drive\": \"Front\"," +
                                        "\"model_transmission_type\": \"6-speed automated manual\"," +
                                        "\"model_seats\": \"4\"," +
                                        "\"model_doors\": \"2\"," +
                                        "\"model_weight_kg\": null," +
                                        "\"model_length_mm\": \"4201\"," +
                                        "\"model_width_mm\": \"1842\"," +
                                        "\"model_height_mm\": \"1359\"," +
                                        "\"model_wheelbase_mm\": \"2469\"," +
                                        "\"model_lkm_hwy\": null," +
                                        "\"model_lkm_mixed\": null," +
                                        "\"model_lkm_city\": null," +
                                        "\"model_fuel_cap_l\": \"60\"," +
                                        "\"model_sold_in_us\": \"0\"," +
                                        "\"model_co2\": null," +
                                        "\"model_make_display\": \"Audi\"," +
                                        "\"make_display\": \"Audi\"," +
                                        "\"make_country\": \"Germany\"" +
                                    "}," +
                                    "{" +
                                        "\"model_id\": \"50784\"," +
                                        "\"model_make_id\": \"audi\"," +
                                        "\"model_name\": \"TT\"," +
                                        "\"model_trim\": \"2.0 TDI Convertible Quattro\"," +
                                        "\"model_year\": \"2012\"," +
                                        "\"model_body\": \"Convertible\"," +
                                        "\"model_engine_position\": \"Front\"," +
                                        "\"model_engine_cc\": \"1964\"," +
                                        "\"model_engine_cyl\": \"4\"," +
                                        "\"model_engine_type\": \"in-line\"," +
                                        "\"model_engine_valves_per_cyl\": \"4\"," +
                                        "\"model_engine_power_ps\": \"170\"," +
                                        "\"model_engine_power_rpm\": \"4200\"," +
                                        "\"model_engine_torque_nm\": \"350\"," +
                                        "\"model_engine_torque_rpm\": \"1750\"," +
                                        "\"model_engine_bore_mm\": null," +
                                        "\"model_engine_stroke_mm\": null," +
                                        "\"model_engine_compression\": null," +
                                        "\"model_engine_fuel\": \"Diesel\"," +
                                        "\"model_top_speed_kph\": null," +
                                        "\"model_0_to_100_kph\": null," +
                                        "\"model_drive\": \"AWD\"," +
                                        "\"model_transmission_type\": \"6-speed automated manual\"," +
                                        "\"model_seats\": \"2\"," +
                                        "\"model_doors\": \"2\"," +
                                        "\"model_weight_kg\": null," +
                                        "\"model_length_mm\": \"4201\"," +
                                        "\"model_width_mm\": \"1842\"," +
                                        "\"model_height_mm\": \"1359\"," +
                                        "\"model_wheelbase_mm\": \"2469\"," +
                                        "\"model_lkm_hwy\": null," +
                                        "\"model_lkm_mixed\": null," +
                                        "\"model_lkm_city\": null," +
                                        "\"model_fuel_cap_l\": \"60\"," +
                                        "\"model_sold_in_us\": \"0\"," +
                                        "\"model_co2\": null," +
                                        "\"model_make_display\": \"Audi\"," +
                                        "\"make_display\": \"Audi\"," +
                                        "\"make_country\": \"Germany\"" +
                                    "}," +
                                    "{" +
                                        "\"model_id\": \"50783\"," +
                                        "\"model_make_id\": \"audi\"," +
                                        "\"model_name\": \"TT\"," +
                                        "\"model_trim\": \"2.0 TDI Coupe Quattro\"," +
                                        "\"model_year\": \"2012\"," +
                                        "\"model_body\": \"Coupe\"," +
                                        "\"model_engine_position\": \"Front\"," +
                                        "\"model_engine_cc\": \"1964\"," +
                                        "\"model_engine_cyl\": \"4\"," +
                                        "\"model_engine_type\": \"in-line\"," +
                                        "\"model_engine_valves_per_cyl\": \"4\"," +
                                        "\"model_engine_power_ps\": \"170\"," +
                                        "\"model_engine_power_rpm\": \"4200\"," +
                                        "\"model_engine_torque_nm\": \"350\"," +
                                        "\"model_engine_torque_rpm\": \"1750\"," +
                                        "\"model_engine_bore_mm\": null," +
                                        "\"model_engine_stroke_mm\": null," +
                                        "\"model_engine_compression\": null," +
                                        "\"model_engine_fuel\": \"Diesel\"," +
                                        "\"model_top_speed_kph\": null," +
                                        "\"model_0_to_100_kph\": null," +
                                        "\"model_drive\": \"AWD\"," +
                                        "\"model_transmission_type\": \"6-speed automated manual\"," +
                                        "\"model_seats\": \"4\"," +
                                        "\"model_doors\": \"2\"," +
                                        "\"model_weight_kg\": null," +
                                        "\"model_length_mm\": \"4201\"," +
                                        "\"model_width_mm\": \"1842\"," +
                                        "\"model_height_mm\": \"1359\"," +
                                        "\"model_wheelbase_mm\": \"2469\"," +
                                        "\"model_lkm_hwy\": null," +
                                        "\"model_lkm_mixed\": null," +
                                        "\"model_lkm_city\": null," +
                                        "\"model_fuel_cap_l\": \"60\"," +
                                        "\"model_sold_in_us\": \"0\"," +
                                        "\"model_co2\": null," +
                                        "\"model_make_display\": \"Audi\"," +
                                        "\"make_display\": \"Audi\"," +
                                        "\"make_country\": \"Germany\"" +
                                    "}" +
                                "]" +
                           "});"