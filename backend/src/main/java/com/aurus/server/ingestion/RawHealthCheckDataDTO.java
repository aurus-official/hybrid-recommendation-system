package com.aurus.server.ingestion;

public record RawHealthCheckDataDTO(
        boolean ads1,
        boolean ads2,
        boolean bme280,
        boolean guvas12sd,
        boolean ds18b20) {

    // @Override
    // public String toString() {
    // return String.format("{ ads_1 : %b,\nads_2 : %b,\nbme280 : %b,\nguva_s12sd :
    // %b,\nds18b20 : %b }", ads_1(),
    // ads_2(),
    // bme280(), guva_s12sd(), ds18b20());
    // }
}
